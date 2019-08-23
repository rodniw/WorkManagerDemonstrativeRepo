package ru.skillbranch.devintensive.workmanagerdemonstrativerepo.notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import ru.skillbranch.devintensive.workmanagerdemonstrativerepo.R;

public class LoginToNotifByFirebase extends AppCompatActivity {

    private TextView btnLogin, btnRegister;
    private EditText editEmail, editPassword;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_to_notif_by_firebase);

        auth = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.login_btn_notif_firebase);
        btnRegister = findViewById(R.id.register_btn_to_notif_firebase);
        editEmail = findViewById(R.id.ET_name_notif);
        editPassword = findViewById(R.id.ET_password_notif);

        btnLogin.setOnClickListener( view -> {
            userLogin();
        });
        btnRegister.setOnClickListener( view -> {
            createUser();
        });
    }

    //this method login user
    private void userLogin() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        //checking that our fields are not empty
        if (!checkFieldsNotEmpty(email, password)) {
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        intentToActivity();
                    }
                });
    }

    //this method creates a new user with a given email and password
    private void createUser() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        //checking that our fields are not empty
        if (!checkFieldsNotEmpty(email, password)) {
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        intentToActivity();
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            userLogin();
                        } else {
                            Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //this method send user to the main page
    private void intentToActivity() {
        Intent intent = new Intent(this, FirebaseAuthNotifActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    //checking that fields are not empty
    private boolean checkFieldsNotEmpty(String email, String password) {
        if (email.isEmpty()) {
            editEmail.setError("Email required");
            editEmail.requestFocus();
            return false;
        } else if(password.isEmpty()) {
            editPassword.setError("Password required");
            editPassword.requestFocus();
            return false;
        } else if (password.length() < 6) {
            editPassword.setError("Password should be at least 6 characters");
            editPassword.requestFocus();
            return false;
        }
        return true;
    }
}
