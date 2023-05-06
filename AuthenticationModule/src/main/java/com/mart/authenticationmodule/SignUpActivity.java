package com.mart.authenticationmodule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mart.authenticationmodule.databinding.ActivitySignUpBinding;
import com.mart.authenticationmodule.model.User;
import com.mart.authenticationmodule.utils.ProgressDialogHelper;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private boolean isAllFieldsChecked = false;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private ProgressDialogHelper progressDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialogHelper = new ProgressDialogHelper(this);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("All_Users");

        binding.buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialogHelper.ShowProgressDialog(" Please Wait","We are making your account",false);

                isAllFieldsChecked = isAllFieldsOk();
                if (isAllFieldsChecked) {
                    String Email = binding.editTextEmail.getText().toString();
                    String Username = binding.editTextUsername.getText().toString();
                    String pass = binding.editTextPassword.getText().toString();
                    String c_pass = binding.editTextConfirmPassword.getText().toString();
                    if (doPasswordsMatch(pass,c_pass)) {

                        mAuth.createUserWithEmailAndPassword(Email,pass).addOnSuccessListener(authResult ->
                                databaseReference.child(authResult.getUser().getUid()).setValue(new User(Username,Email,authResult.getUser().getUid())).addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        progressDialogHelper.CancelProgressDialog();
                                        clearAllFields();
                                        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
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

                }
            }
        });
    }

    private void clearAllFields() {
        binding.editTextPassword.getText().clear();
        binding.editTextConfirmPassword.getText().clear();
        binding.editTextEmail.getText().clear();
        binding.editTextUsername.getText().clear();
    }
    private boolean isAllFieldsOk() {
        if (binding.editTextUsername.getText().length() == 0) {
            return false;
        }
        if (binding.editTextEmail.getText().length() == 0) {
            return false;
        }
        if (binding.editTextPassword.getText().length() == 0) {
            return false;
        }
        if (binding.editTextConfirmPassword.getText().length() == 0) {
            return false;
        }


        return true;
    }

    private boolean doPasswordsMatch(String pass, String c_pass) {
        return pass.equals(c_pass);
    }
}