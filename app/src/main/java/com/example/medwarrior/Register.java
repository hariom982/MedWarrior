package com.example.medwarrior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medwarrior.ui.MainActivity2;
//import com.example.medwarrior.ui.home.Mainscreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText name,phone,email,password;
    TextView textView3 , logintextview , textview4;
    Button button;
    FirebaseFirestore fstore;
    FirebaseAuth fauth;
    String userId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.mobile);
        email = findViewById(R.id.emailid);
        password = findViewById(R.id.password);
        button = findViewById(R.id.button);
        logintextview = findViewById(R.id.logintextview);

        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Name = name.getText().toString();
                final String Password = password.getText().toString();
                final String Email = email.getText().toString();
                final String Phone = phone.getText().toString();

                if(TextUtils.isEmpty(Name)){

                }
                fauth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser fuser = fauth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Successfully Registered!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"OnFailure : Email Not Sent " + e.getMessage());
                                }
                            });
                            Toast.makeText(getApplicationContext(), "user Created", Toast.LENGTH_SHORT).show();
                            userId = fauth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("user").document(userId);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Name",Name);
                            user.put("Email",Email);
                            user.put("Phone",Phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"OnSuccess: User Profile is created for "+userId);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"OnFailure: " +e.toString());
                                }
                            });
                            Intent intent  = new Intent(getApplicationContext(), MainActivity2.class);
                            startActivity(intent);

                        }
                    }
                });
            }
        });
        logintextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Loginpage.class);
                startActivity(intent);
            }
        });



    }
}