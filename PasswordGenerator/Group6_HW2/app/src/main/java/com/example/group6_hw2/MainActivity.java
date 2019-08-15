package com.example.group6_hw2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {


    Handler handler;
    ProgressDialog progressDialog;
    static String THREAD_KEY = "Async";
    static String ASYNC_KEY = "Async1";
    static String PASSWORD_KEY = "PASSWORD";
    static String COUNT_KEY = "Thread_Count";
    static String COUNT_KEY_ASYNC = "Async_Count";
    ExecutorService threadpool;
    SeekBar seekbar_thread_count, seekbar_thread_length, seekbar_async_count, seekbar_async_length;
    TextView threadCountValue, threadLengthValue, asyncCountValue, asyncLengthValue;
    int thread_count, thread_length, async_count, async_length, thread_count_value, thread_length_value, async_count_value, async_length_value;
    ArrayList<String> password = null;
    ArrayList<String> passwordAsync = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Password Generator");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        threadpool = Executors.newFixedThreadPool(2);
        thread_count_value = ((SeekBar) findViewById(R.id.seekBar_thread_count)).getProgress();
        thread_length_value = ((SeekBar) findViewById(R.id.seekBar_thread_length)).getProgress();
        async_count_value = ((SeekBar) findViewById(R.id.seekBar_thread_count)).getProgress();
        async_length_value = ((SeekBar) findViewById(R.id.seekBar_thread_count)).getProgress();


        threadCountValue = findViewById(R.id.textView_seek_thread_count);
        threadLengthValue = findViewById(R.id.textView_seek_thread_length);
        seekbar_thread_count = findViewById(R.id.seekBar_thread_count);
        seekbar_thread_length = findViewById(R.id.seekBar_thread_length);
        seekbar_async_count = findViewById(R.id.seekBar_Async_count);
        seekbar_async_length = findViewById(R.id.seekBar_Async_Length);

        seekbar_thread_length.setMax(23);

        seekbar_thread_count.setMax(10);

        seekbar_async_count.setMax(10);

        seekbar_async_length.setMax(23);


        seekbar_thread_length.setOnSeekBarChangeListener(this);
        seekbar_thread_count.setOnSeekBarChangeListener(this);
        seekbar_async_count.setOnSeekBarChangeListener(this);
        seekbar_async_length.setOnSeekBarChangeListener(this);


        Button button_generate = findViewById(R.id.button_generate);
        button_generate.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {

                                                   thread_count_value = ((SeekBar) findViewById(R.id.seekBar_thread_count)).getProgress();
                                                   thread_length_value = ((SeekBar) findViewById(R.id.seekBar_thread_length)).getProgress();
                                                   async_count_value = ((SeekBar) findViewById(R.id.seekBar_thread_count)).getProgress();
                                                   async_length_value = ((SeekBar) findViewById(R.id.seekBar_thread_count)).getProgress();

                                                   final Intent intent = new Intent(MainActivity.this, GeneratedPasswords.class);

                                                   intent.putExtra(COUNT_KEY, thread_count_value);
                                                   intent.putExtra(COUNT_KEY_ASYNC, async_count_value);

                                                   handler = new Handler(new Handler.Callback() {
                                                       @Override
                                                       public boolean handleMessage(Message msg) {



                                                           if (msg.getData().getInt("ThreadStatus") == 1) {
                                                               password = msg.getData().getStringArrayList(THREAD_KEY);
                                                               intent.putExtra(THREAD_KEY, password);
                                                           } else if (msg.getData().getInt("AsyncStatus") == 1){


                                                           passwordAsync = msg.getData().getStringArrayList(ASYNC_KEY);


                                                           intent.putExtra(ASYNC_KEY, passwordAsync);
                                                       }
                                                           if(password !=null && passwordAsync!=null) {
                                                               progressDialog.dismiss();
                                                               startActivity(intent);
                                                           }

                                                           return true;
                                                       }
                                                   }

                                                   );
                                                   final ArrayList<Integer> passing = new ArrayList<Integer>();

                                                   passing.add(async_count_value);
                                                   passing.add(async_length_value);
                                                   threadpool.execute(new genPassword());

                                                   new getPasswordsAsync().execute(passing);
                                                   progressDialog = new ProgressDialog(MainActivity.this);
                                                   progressDialog.setMessage("Generating Passwords");
                                                   progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                                   progressDialog.setCancelable(false);
                                                   progressDialog.show();



 }
                                           }

        );


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (seekBar.equals(findViewById(R.id.seekBar_thread_length))) {
            ((TextView) (findViewById(R.id.textView_seek_thread_length))).setText("" + progress);
            thread_length = progress;
        } else if (seekBar.equals(findViewById(R.id.seekBar_thread_count))) {
            ((TextView) (findViewById(R.id.textView_seek_thread_count))).setText("" + progress);
            thread_count = progress;

        } else if (seekBar.equals(findViewById(R.id.seekBar_Async_Length))) {
            ((TextView) (findViewById(R.id.textView_seek_Async_length))).setText("" + progress);


        } else if (seekBar.equals(findViewById(R.id.seekBar_Async_count))) {
            ((TextView) (findViewById(R.id.textView_seek_Async_count))).setText("" + progress);


        }


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    class getPasswordsAsync extends AsyncTask<ArrayList<Integer>, Void, ArrayList<String>> {



        @Override
        protected ArrayList<String> doInBackground(ArrayList<Integer>... arrayLists) {
            ArrayList<String> async_pass = new ArrayList<>();
            int async_count = arrayLists[0].get(0);
            int async_length = arrayLists[0].get(1);

            for (int i = 0; i < async_count; i++) {
                String password = Util.getPassword(async_length);
                async_pass.add(password);


 }

            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putInt("AsyncStatus", 1);
            bundle.putStringArrayList(ASYNC_KEY,async_pass);

            message.setData(bundle);
            message.obj = async_pass;
            handler.sendMessage(message);
            return async_pass;
        }

    }

    class genPassword implements Runnable {


        @Override
        public void run() {

            int i;
            ArrayList<String> a = new ArrayList<>();
            thread_count_value = ((SeekBar) findViewById(R.id.seekBar_thread_count)).getProgress();
            thread_length_value = ((SeekBar) findViewById(R.id.seekBar_thread_length)).getProgress();

            for (i = 0; i < thread_count_value; i++) {
                String password = Util.getPassword(thread_length_value);
                a.add(password);


            }
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putInt("ThreadStatus", 1);
            bundle.putStringArrayList(THREAD_KEY,a);

            message.setData(bundle);
            message.obj = a;
            handler.sendMessage(message);

        }


    }


}
