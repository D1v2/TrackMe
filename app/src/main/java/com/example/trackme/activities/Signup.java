package com.example.trackme.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trackme.R;
import com.google.firebase.auth.FirebaseAuth;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Signup extends AppCompatActivity {
    private EditText Name, inputEmail, inputPassword, inputConfirmPassword;
    private Button buttonSignUp;
    CircleImageView circleImageView;
    Uri resulturi;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findViewById(R.id.imageBack).setOnClickListener(v -> {
            onBackPressed();
        });
        findViewById(R.id.textSignIn).setOnClickListener(v -> {
            onBackPressed();
        });

        Name = findViewById(R.id.inputFirstName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        circleImageView = findViewById(R.id.ciclerImageView);
        auth = FirebaseAuth.getInstance();
        circleImageView.setOnClickListener(v -> imageSelected());

        buttonSignUp.setOnClickListener(v -> {
            if (Name.getText().toString().trim().isEmpty()) {
                Toast.makeText(Signup.this, "Enter First Name ", Toast.LENGTH_SHORT).show();
            } else if (inputEmail.getText().toString().trim().isEmpty()) {
                Toast.makeText(Signup.this, "Enter Email ", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString()).matches()) {
                Toast.makeText(Signup.this, "Enter Valid Email ", Toast.LENGTH_SHORT).show();
            } else if (inputPassword.getText().toString().trim().isEmpty()) {
                Toast.makeText(Signup.this, "Enter Password ", Toast.LENGTH_SHORT).show();
            } else if (inputConfirmPassword.getText().toString().trim().isEmpty()) {
                Toast.makeText(Signup.this, "Confirm your Password ", Toast.LENGTH_SHORT).show();
            } else if (!inputPassword.getText().toString().equals(inputConfirmPassword.getText().toString())) {
                Toast.makeText(Signup.this, "Password & Confirm Password must be same", Toast.LENGTH_SHORT).show();
            }else {
                signUp();
            }
        });
    }

    private void signUp() {
        if (resulturi == null || inputEmail.getText().toString().isEmpty() ||
                inputPassword.getText().toString().isEmpty() ||
                inputPassword.getText().toString().isEmpty() ||
                Name.getText().toString().isEmpty()) {
        }if (!(resulturi == null || inputEmail.getText().toString().isEmpty() ||
                inputPassword.getText().toString().isEmpty() ||
                inputPassword.getText().toString().isEmpty() ||
                Name.getText().toString().isEmpty())) {
        }

        generateCode();
    }

    private void generateCode(){
        Date myDate = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
        String date = format1.format(myDate);
        Random random = new Random();
        int c = 10000 + random.nextInt(90000);
        String code = String.valueOf(c);
        if (resulturi != null) {
            Intent intent = new Intent(Signup.this, InviteCodeActivity.class);
            intent.putExtra("name", Name.getText().toString());
            intent.putExtra("email", inputEmail.getText().toString());
            intent.putExtra("password", inputPassword.getText().toString());
            intent.putExtra("date", date);
            intent.putExtra("isSharing", "false");
            intent.putExtra("code", code);
            intent.putExtra("image", resulturi);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Please Choose Image", Toast.LENGTH_SHORT).show();
        }
    }

    public void imageSelected() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == RESULT_OK && data != null) {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resulturi = result.getUri();
                circleImageView.setImageURI(resulturi);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}