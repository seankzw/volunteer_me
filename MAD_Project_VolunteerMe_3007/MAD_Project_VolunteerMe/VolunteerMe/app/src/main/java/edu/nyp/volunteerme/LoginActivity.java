package edu.nyp.volunteerme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

//    EditText loginEmail = (EditText)findViewById(R.id.input_email);
//    EditText loginPassword = (EditText)findViewById(R.id.input_password);

    //TAG for debug log message
    private final String TAG = this.getClass().getSimpleName();

    //Declare variables for profile and response
    EditText etEmail, etPassword;

    //login Authentication + get name
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference dbReff;
    String logonName;

    ProgressDialog progressDialog;
    Button signInBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        TextView createAccount = findViewById(R.id.link_signup);

        //DB
        mAuth = FirebaseAuth.getInstance();
        dbReff = FirebaseDatabase.getInstance().getReference("users");

        etEmail = (EditText) findViewById(R.id.input_email);
        etPassword = (EditText) findViewById(R.id.input_password);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpactivitiy.class);
                startActivity(intent);
            }// on click
        });// end of createAccount.setonclicklistener

        signInBtn = (Button) findViewById(R.id.btn_login);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String login_email = etEmail.getText().toString();
                final String login_password = etPassword.getText().toString();

                if(TextUtils.isEmpty(login_email)){
                    Toast.makeText(getApplicationContext(), "Please enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(login_password)){
                    Toast.makeText(getApplicationContext(),"Please enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
//                                                .orderByChild("password").equalTo(login_password);

                progressDialog.setMessage("Login in...");
                progressDialog.show();

                mAuth.signInWithEmailAndPassword(login_email, login_password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if(task.isSuccessful()){
                                    //if successful
                                    Toast.makeText(getApplicationContext(), "Welcome back", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Incorrect email or password!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });// end of authSignin
            }
        });//end of signin Btn onclick

    }// end of onCreate



}
