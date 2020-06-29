package com.sen.cooey.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.sen.cooey.R;
import com.sen.cooey.storage.SessionManager;
import com.sen.cooey.storage.db.table.UserEntity;
import com.sen.cooey.utils.CommonUtilities;
import com.sen.cooey.viewmodel.MyViewModel;

import java.util.List;


public class LoginActivity extends AppCompatActivity {

    /**
     * a class to do login
     */

    private final String TAG = LoginActivity.this.getClass().getSimpleName();

    private MyViewModel myViewModel;


    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;


    private Button buttonLogin;

    private TextView textViewNewUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initilize();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

        textViewNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });


    }//eof onCreate


    private void initilize() {
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewNewUser = findViewById(R.id.textViewNewUser);
        CommonUtilities.loadSnack(this,buttonLogin,"Login under process !");
    }

    private void doLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (!validateEmail(email)) {
            editTextEmail.setError(getResources().getString(R.string.valid_email));
            requestFocus(editTextEmail);
            return;
        }

        if (password.length() < 8) {
            editTextPassword.setError(getResources().getString(R.string.valid_password));
            requestFocus(editTextPassword);
            return;
        }


        try {

            List<UserEntity> alUser = myViewModel.getUsers().getValue();

            if (alUser != null) {
                if (alUser.size() > 0) {
                    SessionManager sessionManager = SessionManager.getInstance(this);
                    sessionManager.putLOGGED_IN(alUser.get(0).getGender());
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);

                    finish();
                }

            }


        } catch (Exception e) {
            Log.e(TAG, "Exception " + e.getMessage());
        }

    }

    public static boolean validateEmail(String target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return CommonUtilities.validateEmail(target);

        }
    }

    private void requestFocus(View view) {
        try {
            if (view.requestFocus()) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e.getMessage());
        }
    }


}//eof SplashActivity
