package com.example.trackme.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trackme.Helper.UserDetailHelper;
import com.example.trackme.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class InviteCodeActivity extends AppCompatActivity {

    TextView code;
    String name,email,password,date,tcode,isSharing,userId;
    Button buttonRegister;

    Uri uriImage;
    CircleImageView circleImageView;

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;

    ProgressBar progressBar;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);

        code=findViewById(R.id.code);
        buttonRegister=findViewById(R.id.buttonRegister);
        circleImageView=findViewById(R.id.ciclerImageView);
        findViewById(R.id.imageBack).setOnClickListener(v -> onBackPressed());

        auth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressBar=findViewById(R.id.signUpProgressBar);
        reference= FirebaseDatabase.getInstance().getReference().child("Users");

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
        circleImageView.setImageURI(uriImage);


        buttonRegister.setOnClickListener(v -> registerUser());

    }
    public void registerUser(){
        progressDialog.setMessage("Please Wait ...");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {

                    try{
                    if (task.isSuccessful()) {
                        UserDetailHelper userDetailHelper = new UserDetailHelper(name, email, password, tcode, "false", "na", "na", "na");

                        user = auth.getCurrentUser();
                        userId = user.getUid();
                        reference.child(userId).setValue(userDetailHelper).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Signup successfull complete", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "User not created", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Unable to create a user ", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                       Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
