package com.sen.cooey.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.sen.cooey.R;
import com.sen.cooey.storage.SessionManager;
import com.sen.cooey.storage.db.table.UserEntity;
import com.sen.cooey.utils.AppConstants;
import com.sen.cooey.utils.CommonUtilities;
import com.sen.cooey.viewmodel.MyViewModel;


public class RegisterActivity extends AppCompatActivity {

    /**
     * a class to do registration
     */

    private final String TAG = RegisterActivity.this.getClass().getSimpleName();

    private MyViewModel myViewModel;


    private TextInputEditText editTextName;
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private TextInputEditText editTextDob;

    private RadioGroup radioGroupGender;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;

    private Button buttonRegister;

    private TextView textViewExistingUser;

    private LinearLayout linearLayoutHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initilize();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });

        textViewExistingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);

                finish();
            }
        });


    }//eof onCreate


    private void initilize() {
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextDob = findViewById(R.id.editTextDob);

        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        buttonRegister = findViewById(R.id.buttonRegister);

        textViewExistingUser = findViewById(R.id.textViewExistingUser);

        linearLayoutHeader = findViewById(R.id.linearLayoutHeader);
        linearLayoutHeader.setVisibility(View.GONE);
    }

    private void doRegister() {

        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String dob = editTextDob.getText().toString().trim();


        if (name.length() < 2) {
            editTextName.setError(getResources().getString(R.string.valid_name));
            requestFocus(editTextName);
            return;
        }

        if (password.length() < 8) {
            editTextPassword.setError(getResources().getString(R.string.valid_password));
            requestFocus(editTextPassword);
            return;
        }

        if (!validateEmail(email)) {
            editTextEmail.setError(getResources().getString(R.string.valid_email));
            requestFocus(editTextEmail);
            return;
        }


        int gender = AppConstants.GENDER_MALE;

        if (radioButtonFemale.isChecked()) {
            gender = AppConstants.GENDER_FEMALE;
        }


        String image = "";

        try {
            UserEntity userObj = new UserEntity(name, email, password, dob, gender, image);
            myViewModel.insert(userObj);

            SessionManager sessionManager = SessionManager.getInstance(this);
            sessionManager.putLOGGED_IN(gender);

            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(i);

            finish();

        } catch (Exception e) {
            Log.e(TAG, "Exception " + e.getMessage());
        }//eof if try...catch

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
