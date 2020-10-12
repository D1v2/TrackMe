package com.example.trackme.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trackme.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class InviteCodeActivity extends AppCompatActivity {

    TextView code;
    String name,email,password,date,tcode,isSharing,userId;
    Button buttonRegister;

    Uri uriImage;
    CircleImageView circleImageView;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference reference;
    ProgressDialog progressDialog;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);

        code=findViewById(R.id.code);
        buttonRegister=findViewById(R.id.buttonRegister);
        circleImageView=findViewById(R.id.ciclerImageView);
        findViewById(R.id.imageBack).setOnClickListener(v -> onBackPressed());

        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference().child("Users");
        storageReference= FirebaseStorage.getInstance().getReference().child("UserImages");

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
        buttonRegister.setOnClickListener(v -> {
           register();
        });
    }
    public void register(){
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                UserDetailHelper userDetailHelper=new UserDetailHelper(name,email,password,tcode,"false","no","no","no");
                user=firebaseAuth.getCurrentUser();
                userId=user.getUid();
                reference.child(userId).setValue(userDetailHelper).addOnCompleteListener(task1 -> {
                    if(task1.isSuccessful()) {

                        StorageReference sr=storageReference.child(user.getUid()+".jpg");
                        sr.putFile(uriImage).addOnCompleteListener(task11 -> {
                        if(task11.isSuccessful()){
                            String download_image= task11.getResult().getUploadSessionUri().toString();
                            reference.child(user.getUid()).child("imageUri").setValue(download_image).addOnCompleteListener(task111 -> {
                             if(task111.isSuccessful()){
                                 progressDialog.dismiss();
                                 SentEmailVerification();
                                 Intent intent=new Intent(InviteCodeActivity.this,MainActivity.class);
                                 startActivity(intent);
                                 finish();
                             }
                             else {
                                 progressDialog.dismiss();
                                 Toast.makeText(getApplicationContext(),"Unable to User Creating",Toast.LENGTH_SHORT).show();
                                 finish();
                             }
                            });
                        }
                        });
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Problem Occured",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }else{
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Something Error",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void SentEmailVerification() {
        user.sendEmailVerification().addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               Toast.makeText(getApplicationContext(), "Email Send For Verification", Toast.LENGTH_SHORT).show();
               firebaseAuth.signOut();
               finish();
           }else {
               Toast.makeText(getApplicationContext(), "Email Not Send", Toast.LENGTH_SHORT).show();
           }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}