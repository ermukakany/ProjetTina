package cloudm120152016.puy2docs.activities.auth.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

import cloudm120152016.puy2docs.R;
import cloudm120152016.puy2docs.utils.retrofit.UserService;
import cloudm120152016.puy2docs.utils.retrofit.ServiceGenerator;
import cloudm120152016.puy2docs.utils.NetworkUtil;
import cloudm120152016.puy2docs.utils.PatternValidor;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseSignUpActivity extends AppCompatActivity {

    // UI references.
    EditText email;
    EditText username;
    EditText password;
    EditText passwordBis;
    EditText lastname;
    EditText firstname;
    EditText infoBirthday;
    Button btnChooseDate;
    View formView;
    View progressView;

    //Boolean join = false;

    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_sign_up);

        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        passwordBis = (EditText) findViewById(R.id.passwordBis);
        lastname = (EditText) findViewById(R.id.lastname);
        firstname = (EditText) findViewById(R.id.firstname);

        infoBirthday = (EditText) findViewById(R.id.infoBirthday);
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        Button sendButton = (Button) findViewById(R.id.register_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    send(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        formView = findViewById(R.id.signup_form);
        progressView = findViewById(R.id.signup_progress);

        dateButton();

        if (NetworkUtil.getConnectivityStatus(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Vous êtes connecté(e)", Toast.LENGTH_SHORT).show();
        }
    }

    public void dateButton() {

        btnChooseDate = (Button) findViewById(R.id.btnChooseDate);

        btnChooseDate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            infoBirthday.setText(new StringBuilder().append(formatTwoDigit(day))
                    .append("/")
                    .append(formatTwoDigit(month + 1))
                    .append("/").append(year)
                    .append(""));
        }
    };

    public String formatTwoDigit(int value) {
        return String.format("%02d", value);
    }

    public void send(View v) throws IOException {

        if (!formValidate()) {

            showProgress(true);

            UserService userService = ServiceGenerator.createService(UserService.class);
            Call<ResponseBody> postData = userService.join(email.getText().toString(),
                    username.getText().toString(),
                    password.getText().toString(),
                    lastname.getText().toString(),
                    firstname.getText().toString(),
                    infoBirthday.getText().toString());

            postData.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Response<ResponseBody> response) {
                    if (response.isSuccess()) {
                        showProgress(false);
                        Toast.makeText(getApplicationContext(), "Inscription done", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), BaseLoginActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d("Error", t.getMessage());
                }
            });
        }

    }

    private boolean isEmailValid(String email) {
        return PatternValidor.isEmailValid(email);
    }

    private boolean isPasswordValid(String password) {
        PatternValidor patternValidor = new PatternValidor();
        return patternValidor.isPasswordValid(password);
    }

    private boolean isBirthdayValid(String birthday) {
        PatternValidor patternValidor = new PatternValidor();
        return patternValidor.isBirthdayValid(birthday);
    }

    private boolean formValidate() {
        email.setError(null);
        password.setError(null);
        infoBirthday.setError(null);
        passwordBis.setError(null);

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password.getText().toString()) && !isPasswordValid(password.getText().toString())) {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }

        if (!(password.getText().toString().equals(passwordBis.getText().toString()))){
            passwordBis.setError(getString(R.string.error_same_password));
            focusView = passwordBis;
            cancel = true;
        }

        if (!isBirthdayValid(infoBirthday.getText().toString())) {
            infoBirthday.setError(getString(R.string.error_invalid_birthday));
            focusView = infoBirthday;
            cancel = true;
        }

        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;
        } else if (!isEmailValid(email.getText().toString())) {
            email.setError(getString(R.string.error_invalid_email));
            focusView = email;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }

        return cancel;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            formView.setVisibility(show ? View.GONE : View.VISIBLE);
            formView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    formView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            formView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
