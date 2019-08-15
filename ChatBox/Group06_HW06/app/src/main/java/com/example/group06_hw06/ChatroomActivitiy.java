package com.example.group06_hw06;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class ChatroomActivitiy extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public final static int CAMERA_PIC_REQUEST = 1;
    int imageUploaded = -1;
    ArrayList<Message> messages=new ArrayList<Message>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom_activitiy);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        ((ImageButton)findViewById(R.id.imageButton_power_off)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(ChatroomActivitiy.this,"Loggedout successfully",Toast.LENGTH_LONG).show();
                Intent i = new Intent(ChatroomActivitiy.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        ((ImageView)findViewById(R.id.imageView_add_image)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });
        ((ImageView)findViewById(R.id.imageView_send_message)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText msg = (EditText)findViewById(R.id.editText_msg);
                ImageView eimageView = (ImageView)findViewById(R.id.imageView_add_image);
                if(msg.getText().toString().isEmpty())
                {
                    msg.setError("Enter a message");
                    return;
                }
                final String msgvalue = msg.getText().toString();
                msg.setText("");
                if(getImageUploaded() > 0)
                {
                    //we have image to be uploaded
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    final StorageReference imagesref = storage.getReference().child(user.getUid()+"/images/"+ UUID.randomUUID().toString()+".JPEG");

                    eimageView.setDrawingCacheEnabled(true);
                    eimageView.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) eimageView.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = imagesref.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(ChatroomActivitiy.this,"Did not upload the image",Toast.LENGTH_LONG).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(ChatroomActivitiy.this,"Uploaded the image",Toast.LENGTH_LONG).show();
                        }
                    });


                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return imagesref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                System.out.println("Upload " + downloadUri);
                                Toast.makeText(ChatroomActivitiy.this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                                if (downloadUri != null) {

                                    String downloadurl = downloadUri.toString(); //YOU WILL GET THE DOWNLOAD URL HERE !!!!
                                    System.out.println("Upload " + downloadurl);
                                    Message e = new Message();
                                    e.setMessage(msgvalue);

                                    e.setImageURL(downloadurl);
                                    e.setDate(Calendar.getInstance().getTime());
                                    e.setName(user.getDisplayName());
                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                    final DatabaseReference expensesRef = mDatabase.child("expenses");
                                    String key = expensesRef.push().getKey();
                                    e.setKey(key);
                                    DatabaseReference mypushref = expensesRef.child(key);
                                    mypushref.setValue(e);
                                    Toast.makeText(ChatroomActivitiy.this, "Successfully created the expense", Toast.LENGTH_SHORT).show();

                                }

                            } else {
                                Toast.makeText(ChatroomActivitiy.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else
                {
                    Message e = new Message();
                    e.setMessage(msgvalue);
                    e.setImageURL(null);
                    e.setDate(Calendar.getInstance().getTime());
                    e.setName(user.getDisplayName());
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    final DatabaseReference expensesRef = mDatabase.child("expenses");
                    String key = expensesRef.push().getKey();
                    e.setKey(key);
                    DatabaseReference mypushref = expensesRef.child(key);
                    mypushref.setValue(e);
                    Toast.makeText(ChatroomActivitiy.this, "Successfully created the expense", Toast.LENGTH_SHORT).show();

                }
            }
        });


        ((TextView)findViewById(R.id.textView_name)).setText(user.getDisplayName());

        ListView chats_list = (ListView)findViewById(R.id.chats_list);
        final ChatAdapter adapter = new ChatAdapter(this,R.layout.chatroom_listview_layout,messages);
        chats_list.setAdapter(adapter);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference expensesRef = mDatabase.child("expenses");

        expensesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Message e = d.getValue(Message.class);
                    messages.add(e);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == CAMERA_PIC_REQUEST)
        {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ImageView image =(ImageView) findViewById(R.id.imageView_add_image);
            image.setImageBitmap(thumbnail);
            imageUploaded = 1;
        }
        else
        {
            Toast.makeText(ChatroomActivitiy.this, "Picture NOt taken", Toast.LENGTH_LONG).show();
        }

    }

    int getImageUploaded()
    {
        return imageUploaded;
    }
}
