package com.sen.cooey.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.sen.cooey.R;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CommonUtilities {

    private CommonUtilities() {
        // This utility class is not publicly instantiable
    }



    public static void loadSnack(Context context, View v, String message) {
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(context.getResources().getColor(R.color.text_color_green));

        TextView snackbarActionTextView = (TextView) snackbar.getView().findViewById(R.id.snackbar_action);
        snackbarActionTextView.setTextSize(14);
        snackbarActionTextView.setTypeface(snackbarActionTextView.getTypeface(), Typeface.BOLD);

        TextView snackbarTextView = (TextView) snackbar.getView().findViewById(R.id.snackbar_text);
        snackbarTextView.setTextSize(14);
        snackbarTextView.setMaxLines(3);
        snackbarTextView.setTypeface(snackbarTextView.getTypeface(), Typeface.BOLD);

        snackbar.getView().setBackgroundResource(R.color.colorAccent);

        snackbar.show();
    }

    public static void loadSnack(Context context, View v, int message) {
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(context.getResources().getColor(R.color.shadow_light));

        TextView snackbarActionTextView = (TextView) snackbar.getView().findViewById(R.id.snackbar_action);
        snackbarActionTextView.setTextSize(14);
        snackbarActionTextView.setTypeface(snackbarActionTextView.getTypeface(), Typeface.BOLD);

        TextView snackbarTextView = (TextView) snackbar.getView().findViewById(R.id.snackbar_text);
        snackbarTextView.setTextSize(14);
        snackbarTextView.setMaxLines(3);
        snackbarTextView.setTypeface(snackbarTextView.getTypeface(), Typeface.BOLD);

        snackbar.getView().setBackgroundResource(R.color.text_color_green);

        snackbar.show();
    }

    public static boolean validateEmail(String email) {
        String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
