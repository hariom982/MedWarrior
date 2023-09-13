package com.example.medwarrior;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medwarrior.ui.MainActivity2;
//import com.example.medwarrior.ui.home.Mainscreen;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class Loginpage extends AppCompatActivity {
    public  static final String TAG = "TAG";
    EditText email2,password2;
    TextView textView2;
    TextView Logintext;
    Button loginbtn , button2;
   // GoogleSignInClient client;
    FirebaseFirestore fstore;
    FirebaseAuth fauth;
    String userId;

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = fauth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
//            startActivity(intent);
//        }
//    }



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        email2 = findViewById(R.id.email2);
        password2 = findViewById(R.id.password2);
        Logintext = findViewById(R.id.Logintext);
        loginbtn = findViewById(R.id.loginbtn);
        button2 = findViewById(R.id.button2);
        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        Logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email2.getText().toString();
                String Password  = password2.getText().toString();
                fauth.signInWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login succesfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Invalid Account", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });




    }
}