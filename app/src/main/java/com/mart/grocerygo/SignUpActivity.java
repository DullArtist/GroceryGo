package com.mart.grocerygo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mart.grocerygo.databinding.ActivitySignUpBinding;
import com.mart.grocerygo.helpers.ProgressDialogHelper;
import com.mart.grocerygo.model.User;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private ProgressDialogHelper progressDialogHelper;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("All_Users");

        //Initialize Helper classes
        progressDialogHelper = new ProgressDialogHelper(this);

        //sign in button
        binding.btnSignIn.setOnClickListener(view -> {
            startActivity(new Intent(this,LoginActivity.class));
        });


        binding.btnCreateAccount.setOnClickListener(view -> {
            if (checkAllFields()) registerUser();
        });

    }

    private boolean checkAllFields() {

        if (Objects.requireNonNull(binding.etUsername.getEditText()).getText().length() == 0) {
            binding.etUsername.getEditText().setError("Username is required");
            return false;
        }
        if(Objects.requireNonNull(binding.etEmail.getEditText()).getText().length() == 0) {
            binding.etEmail.getEditText().setError("Please Enter Valid Email Address");
            return false;
        }
        if (Objects.requireNonNull(binding.etPassword.getEditText()).getText().length() == 0) {
            binding.etPassword.getEditText().setError("Password is required");
            return false;
        }
        if(binding.etPassword.getEditText().getText().length() <8) {
            binding.etPassword.getEditText().setError("Password should be min of 8");
            return false;
        }

        return true;
    }
    private void registerUser() {

        progressDialogHelper.ShowProgressDialog(" Please Wait","We are making your account",false);

            String Email = binding.etEmail.getEditText().getText().toString();
            String Username = binding.etUsername.getEditText().getText().toString();
            String pass = binding.etPassword.getEditText().getText().toString();

            mAuth.createUserWithEmailAndPassword(Email,pass).addOnSuccessListener(authResult ->
                    databaseReference.child(authResult.getUser().getUid()).setValue(new User(Username,Email,authResult.getUser().getUid())).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            progressDialogHelper.CancelProgressDialog();
                            clearAllFields();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            Toast.makeText(SignUpActivity.this, "SignUp Complete", Toast.LENGTH_SHORT).show();
                        }else {
                            progressDialogHelper.CancelProgressDialog();
                            Toast.makeText(SignUpActivity.this, "SignUp Not Completed", Toast.LENGTH_SHORT).show();
                        }
                    })).addOnFailureListener(e -> {
                progressDialogHelper.CancelProgressDialog();
                Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            });

    }

    private void clearAllFields() {
        binding.etPassword.getEditText().getText().clear();
        binding.etEmail.getEditText().getText().clear();
        binding.etUsername.getEditText().getText().clear();
    }


}