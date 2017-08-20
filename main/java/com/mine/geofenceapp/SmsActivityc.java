package com.mine.geofenceapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SmsActivity extends AppCompatActivity {

    public EditText message;
    public TextView Number;
    public ImageButton Send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        message= (EditText)findViewById(R.id.editText);
         Number= (TextView) findViewById(R.id.textView);
        Send= (ImageButton) findViewById(R.id.sendButton);

        Intent intent=getIntent();
        Number.setText(intent.getStringExtra("numbers"));

        //String default_message="Your request has been approved";


        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Message= message.getText().toString();
                String number= Number.getText().toString();

                sendMsg(Message,number);

            }
        });

    }

    private void sendMsg(String message, String number) {
        /*SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(number,null,message,null,null);*/
Intent intent = new Intent(Intent.ACTION_VIEW,Uri.fromParts("sms",number,null));
        intent.putExtra("sms_body",message);
            startActivity(intent);
    }
}
