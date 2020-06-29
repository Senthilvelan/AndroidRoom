package com.sen.cooey.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.sen.cooey.R;
import com.sen.cooey.storage.SessionManager;
import com.sen.cooey.storage.db.table.UserEntity;
import com.sen.cooey.ui.SplashActivity;
import com.sen.cooey.utils.AppConstants;
import com.sen.cooey.viewmodel.MyViewModel;

import java.util.List;


public class ProfileFragment extends Fragment {

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
    private TextView textViewRegTitle;
    private TextView textViewRegSubTitle;

    private LinearLayout linearLayoutHeader;


    public ProfileFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        ProfileFragment instance;
        instance = new ProfileFragment();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_register, null);

        editTextName = v.findViewById(R.id.editTextName);
        editTextEmail = v.findViewById(R.id.editTextEmail);
        editTextPassword = v.findViewById(R.id.editTextPassword);
        editTextDob = v.findViewById(R.id.editTextDob);

        radioGroupGender = v.findViewById(R.id.radioGroupGender);
        radioButtonMale = v.findViewById(R.id.radioButtonMale);
        radioButtonFemale = v.findViewById(R.id.radioButtonFemale);
        buttonRegister = v.findViewById(R.id.buttonRegister);

        textViewExistingUser = v.findViewById(R.id.textViewExistingUser);
        textViewRegTitle = v.findViewById(R.id.textViewRegTitle);
        textViewRegSubTitle = v.findViewById(R.id.textViewRegSubTitle);
        linearLayoutHeader = v.findViewById(R.id.linearLayoutHeader);

        editTextName.setEnabled(false);
        editTextEmail.setEnabled(false);
        editTextPassword.setVisibility(View.GONE);
        editTextDob.setEnabled(false);
        radioGroupGender.setEnabled(false);
        radioButtonMale.setEnabled(false);
        radioButtonFemale.setEnabled(false);
        textViewExistingUser.setVisibility(View.GONE);
        buttonRegister.setVisibility(View.GONE);
        textViewRegSubTitle.setVisibility(View.GONE);

        textViewRegTitle.setText("Profile");
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        List<UserEntity> alUser = myViewModel.getUsers().getValue();

        if (alUser != null) {
            //Consider the first one as of now
            UserEntity userObj = alUser.get(0);

            editTextName.setText("" + userObj.getName());
            editTextEmail.setText("" + userObj.getEmail());
            editTextDob.setText("" + userObj.getDob());

            radioButtonMale.setChecked(true);

            if (userObj.getGender() == AppConstants.GENDER_FEMALE) {
                radioButtonFemale.setChecked(true);
            }

        }

        linearLayoutHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager = SessionManager.getInstance(getActivity());
                sessionManager.clearAll();
                startActivity(new Intent(getActivity(), SplashActivity.class));
                getActivity().finish();
            }
        });

        return v;
    }


}