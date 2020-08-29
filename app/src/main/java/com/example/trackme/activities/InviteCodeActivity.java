package com.example.trackme.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.trackme.R;

public class InviteCodeActivity extends AppCompatActivity {

    TextView code;
    String name,email,password,date,tcode,isSharing;
    Button buttonRegister;
    Uri uriImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);

        code=findViewById(R.id.code);
        buttonRegister=findViewById(R.id.buttonRegister);

        Intent intent=getIntent();
        if (intent!=null){
            name=intent.getStringExtra("name");
            email=intent.getStringExtra("email");
            password=intent.getStringExtra("password");
            date=intent.getStringExtra("date");
            tcode=intent.getStringExtra("code");
            isSharing=intent.getStringExtra("isSharing");
            uriImage=intent.getParcelableExtra("image");
        }
        code.setText(tcode);
    }
}
