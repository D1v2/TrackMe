package com.example.trackme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    EditText name,email,pass,phone;
    Button signup;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseAuth=FirebaseAuth.getInstance();
        name=findViewById(R.id.name);
        email=findViewById(R.id.semail);
        pass=findViewById(R.id.spass);
        phone=findViewById(R.id.phone);
        signup=findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname=name.getText().toString();
                String uemail=email.getText().toString();
                String uphone=phone.getText().toString();
                String upass=pass.getText().toString();

          //      rootnode=FirebaseDatabase.getInstance();
           //     reference=rootnode.getReference("UserDetail");
            //    UserDetailHelper userDetailHelper=new UserDetailHelper(uname,uemail,uphone,upass);
             //   reference.child(uphone).setValue(userDetailHelper);

                if(uemail.isEmpty()){
                    email.setError("Please enter email");
                    email.requestFocus();
                }
              /*  else if(uname.isEmpty()){
                    name.setError("Please enter your name");
                    name.requestFocus();
                }
                else if(uphone.isEmpty()){
                    phone.setError("Please enter phone number");
                    phone.requestFocus();
                } */
                else if(upass.isEmpty()){
                    pass.setError("Please enter password");
                    pass.requestFocus();
                }
                else if(!(uemail.isEmpty() && upass.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(uemail,upass).addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Signup.this,"Signup UnSuccesfull, Please Try again",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                startActivity(new Intent(Signup.this,MainActivity.class));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(Signup.this,"Error Occured !",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}