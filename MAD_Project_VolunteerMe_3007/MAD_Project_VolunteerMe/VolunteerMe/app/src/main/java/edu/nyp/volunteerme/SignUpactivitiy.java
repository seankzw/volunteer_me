package edu.nyp.volunteerme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class  SignUpactivitiy extends AppCompatActivity {


    //Declare variables for profile and response
    EditText input_name;
    EditText input_email;
    EditText input_password;
    EditText input_confPassword;
    Button signupBtn = null;

    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    DatabaseReference reff;
    Users user;

    //On Create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivitiy);

        progressDialog = new ProgressDialog(this);

        input_email = (EditText) findViewById(R.id.input_email);
        input_name = (EditText) findViewById(R.id.input_name);
        input_password = (EditText) findViewById(R.id.input_password);
        input_confPassword = (EditText) findViewById(R.id.input_confPassword);
        user = new Users();

        mAuth = FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference().child("Users");


        TextView loginAccount = (TextView) findViewById(R.id.link_login);
        loginAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpactivitiy.this, LoginActivity.class);
                startActivity(intent);
            }
        }); // end of loginAccount onclicklistener

        signupBtn = (Button) findViewById(R.id.btn_signup);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass1 = input_password.getText().toString().trim();
                String pass2 = input_confPassword.getText().toString().trim();
                final String name = input_name.getText().toString().trim();
                final String email = input_email.getText().toString().trim();

                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in your name", Toast.LENGTH_SHORT).show();
                    input_name.requestFocus();
                } else if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in your email", Toast.LENGTH_SHORT).show();
                    input_email.requestFocus();
                } else if (pass1.isEmpty() || pass2.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in your passwords", Toast.LENGTH_SHORT).show();
                } else if (!pass1.equals(pass2)) {
                    Toast.makeText(getApplicationContext(), "Password must be the same!", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setMessage("Registering User...");
                    progressDialog.show();


                    Users user = new Users();
                    user.setEmail(email);
                    user.setName(name);
                    reff.push().setValue(user);

                    mAuth.createUserWithEmailAndPassword(email,pass1)
                            .addOnCompleteListener(SignUpactivitiy.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Unable to create account!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }else{
                                Toast.makeText(getApplicationContext(), "Account Created!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                }
            }// end of onCreate

        });
    }// end of on create
}
