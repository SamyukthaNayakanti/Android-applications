package com.example.group6_hw1;
//Homework1
//Group6
//SamyukthaNayakanti

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {


    public String weight, gender,genderValue;
    SeekBar seek_bar;
    TextView textView_alcohol,status,textView,text_BAC;
    int progress_value,ounces,alcoholPercentage;
    ProgressBar prg;
    Switch switch1;
    double r,A,BAC,weight_value=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = (TextView) findViewById(R.id.textView);
        final TextView text_BAC = (TextView)findViewById(R.id.textView_BACvalue);
        final TextView status = (TextView)findViewById(R.id.textView_statusvalue);


        prg = (ProgressBar)findViewById(R.id.progressBar);
        Button save = ((Button) (findViewById(R.id.button_save)));


        seek_bar = (SeekBar) findViewById(R.id.seekBar_alcoholper);
        textView_alcohol = (TextView) findViewById(R.id.textViewalcolholpercent);

        seek_bar.setMax(5);
        seek_bar.setOnSeekBarChangeListener(this);

        save.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {




                weight = ((EditText) findViewById(R.id.editText_weight)).getText().toString();

                if(weight.isEmpty())
                {
                    ((EditText)findViewById(R.id.editText_weight)).setError("Enter the weight in lbs");
                }
                else {

                    weight_value = Double.parseDouble(weight);
                }

                if (((Switch) findViewById(R.id.switch_gender)).isChecked()) {
                    ((TextView) findViewById(R.id.textView)).setText("Female");
                    genderValue = "Female";
                    r = 0.55;

                } else {
                    ((TextView) findViewById(R.id.textView)).setText("Male");
                    r = 0.68;
                    genderValue = "Male";
                }

                 }
 });


        Button reset = (Button)findViewById(R.id.button_reset);

        reset.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {


                                         seek_bar.setProgress(0);
                                         ((Button) findViewById(R.id.button_save)).setEnabled(true);
                                         ((Button) findViewById(R.id.button_adddrink)).setEnabled(true);

                                         ((EditText) findViewById(R.id.editText_weight)).setText("");
                                         RadioButton oneounce = ((RadioButton) findViewById(R.id.radioButton_oneoz));
                                         oneounce.setChecked(true);
                                         weight_value = 0;
                                         r = 0;
                                         prg.setProgress(0);
                                         ((TextView)findViewById(R.id.textView_statusvalue)).setText("");
                                         ((TextView)findViewById(R.id.textView_BACvalue)).setText("");
 }
                                 }
        );

        Button addDrink = ((Button)findViewById(R.id.button_adddrink));
        addDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(weight_value > 0)
                {    Log.e("PreviousBAC",String.valueOf(BAC));
                    int OunceValue=getOunces();
                    int alcPer = getAlcoholPercentage();
                    Double BACvalue = BAC+calculateBAC(OunceValue,alcPer);
                    setBACComponents(BACvalue);
                }
                else{

                    ((EditText)findViewById(R.id.editText_weight)).setError("Enter the weight in lbs");
                }
            }
        });

    }


//    @Override
//    public void onCheckedChanged (CompoundButton compoundButton,boolean b){
//
//    }

    @Override
    public void onProgressChanged (SeekBar seekBar,int progress, boolean fromUser){
        progress_value = progress*5;
        textView_alcohol.setText("" + progress_value + "%");
        int alcoholPercentage= progress_value;
        Toast.makeText(MainActivity.this, "Seekbar in Progress", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStartTrackingTouch (SeekBar seekBar){
        Toast.makeText(MainActivity.this, "Seekbar in Start Tracking", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStopTrackingTouch (SeekBar seekBar){

        Toast.makeText(MainActivity.this, "Seekbar in stop Tracking", Toast.LENGTH_LONG).show();
    }
    public int getOunces(){

        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup_oz);
        final String ounce =
                ((RadioButton) findViewById(rg.getCheckedRadioButtonId()))
                        .getText().toString();

        if (((RadioGroup) findViewById(R.id.radioGroup_oz)).getCheckedRadioButtonId() == R.id.radioButton_oneoz) {
            ounces = 1;
        } else if (((RadioGroup) findViewById(R.id.radioGroup_oz)).getCheckedRadioButtonId() == R.id.radioButton_fiveoz) {
            ounces = 5;
        }
        else if (((RadioGroup) findViewById(R.id.radioGroup_oz)).getCheckedRadioButtonId() == R.id.radioButton_twelveoz) {
            ounces = 12;
        }

        return ounces;
    }


    public int getAlcoholPercentage(){

        alcoholPercentage= new Integer(((SeekBar)findViewById(R.id.seekBar_alcoholper)).getProgress());

        return alcoholPercentage;
    }


    public void setBACComponents(double BAC){

        int prgLevel= (int)((BAC/0.25)*100);
        DecimalFormat df = new DecimalFormat("#.##");
        ((TextView)findViewById(R.id.textView_BACvalue)).setText(String.valueOf(df.format(BAC)));


       ((ProgressBar)findViewById(R.id.progressBar)).setMax(100);
       ((ProgressBar)findViewById(R.id.progressBar)).setProgress(prgLevel);

       if(BAC<=0.08)
       {
           ((TextView)findViewById(R.id.textView_statusvalue)).setText(R.string.safe);
           ((TextView)findViewById(R.id.textView_statusvalue)).setBackgroundColor(Color.GREEN);

        }
        else if(BAC>0.08 && BAC<0.2)
        {
            ((TextView)findViewById(R.id.textView_statusvalue)).setText(R.string.careful);
            ((TextView)findViewById(R.id.textView_statusvalue)).setBackgroundColor(Color.YELLOW);
        }
        else if(BAC>=0.2 && BAC<0.25)
        {
            ((TextView)findViewById(R.id.textView_statusvalue)).setText(R.string.Overlimit);
            ((TextView)findViewById(R.id.textView_statusvalue)).setBackgroundColor(Color.RED);
        }
        else if(BAC>=0.25)

       {
               ((Button) findViewById(R.id.button_save)).setEnabled(false);
               ((Button) findViewById(R.id.button_adddrink)).setEnabled(false);
               Toast.makeText(getApplicationContext(),"No more drinks for You",Toast.LENGTH_SHORT).show();
           }
       }

    public double calculateBAC(int ounces, int alcoholPercentage){

        A=ounces*alcoholPercentage;
        BAC=((A*6.4)/(weight_value*r)    ) ;

        return BAC;
    }

}
