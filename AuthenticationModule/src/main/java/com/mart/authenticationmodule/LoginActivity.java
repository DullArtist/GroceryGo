package com.mart.authenticationmodule;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.mart.authenticationmodule.databinding.ActivityLoginBinding;
import com.mart.authenticationmodule.utils.ProgressDialogHelper;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private boolean iSAllFieldsChecked = false;

    private ProgressDialogHelper progressDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialogHelper = new ProgressDialogHelper(this);

        mAuth = FirebaseAuth.getInstance();

        binding.buttonSignUn.setOnClickListener(view -> startActivity(new Intent(this,SignUpActivity.class)));

        binding.buttonSignIn.setOnClickListener(view -> {

            iSAllFieldsChecked = isAllFieldsOk();
            if (iSAllFieldsChecked) {

                progressDialogHelper.ShowProgressDialog("Please Wait","We are logging you in",false);

                String Email = binding.editTextEmail.getText().toString();
                String Password = binding.editTextPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(Email,Password).addOnSuccessListener(authResult -> {
                    Toast.makeText(LoginActivity.this, "OK", Toast.LENGTH_SHORT).show();
                    progressDialogHelper.CancelProgressDialog();
                    clearAllFields();
                    finish();
                }).addOnFailureListener(e -> {
                    progressDialogHelper.CancelProgressDialog();
                    Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });
            }

        });



    }

    private void clearAllFields() {
        binding.editTextPassword.getText().clear();
        binding.editTextEmail.getText().clear();
    }

    private boolean isAllFieldsOk() {

        if (binding.editTextEmail.getText().length() == 0) {
            return false;
        }
        if (binding.editTextPassword.getText().length() == 0) {
            return false;
        }



        return true;
    }
}