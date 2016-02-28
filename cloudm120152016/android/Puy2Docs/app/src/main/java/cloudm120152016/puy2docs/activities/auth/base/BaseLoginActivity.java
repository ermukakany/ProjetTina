package cloudm120152016.puy2docs.activities.auth.base;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import cloudm120152016.puy2docs.R;
import cloudm120152016.puy2docs.models.Token;
import cloudm120152016.puy2docs.utils.authentification.base.P2CAuthenticator;
import cloudm120152016.puy2docs.utils.retrofit.AccountGeneral;
import cloudm120152016.puy2docs.utils.retrofit.ServiceGenerator;
import cloudm120152016.puy2docs.utils.retrofit.UserService;
import retrofit2.Call;

import static cloudm120152016.puy2docs.utils.retrofit.AccountGeneral.ACCOUNT_TYPE;
import static cloudm120152016.puy2docs.utils.retrofit.AccountGeneral.serverQueries;

public class BaseLoginActivity extends AccountAuthenticatorActivity {

    public final static String ARG_ACCOUNT_TYPE = "accountType";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";

    public final static String ARG_ACCOUNT_NAME = "authAccount";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";

    public static final String KEY_ERROR_MESSAGE = "ERR_MSG";

    public final static String PARAM_USER_PASS = "USER_PASS";

    private final int REQ_SIGNUP = 1;

    private AccountManager accountManager;

    private String tokenType;

    private String accountName;
    private String accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_login);

        accountManager = AccountManager.get(getBaseContext());

        final Intent intent = getIntent();

        tokenType = intent.getStringExtra(ARG_AUTH_TYPE);

        if (tokenType == null)
            tokenType = P2CAuthenticator.AUTH_TOKEN_TYPE_GLOBAL;

        accountType = intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);

        accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);

        if (accountName != null) {
            ((EditText)findViewById(R.id.username)).setText(accountName);
        }


        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // The sign up activity returned that the user has successfully created an account
        if (requestCode == REQ_SIGNUP && resultCode == RESULT_OK) {
            finishLogin(data);
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    public void send() {

        final String userName = ((EditText) findViewById(R.id.username)).getText().toString();
        final String userPass = ((EditText) findViewById(R.id.password)).getText().toString();

        new AsyncTask<Void, Void, Intent>() {
            @Override
            protected Intent doInBackground(Void... params) {
                Bundle data = new Bundle();

                UserService userService = ServiceGenerator.createService(UserService.class);
                Call<Token> token = userService.login(userName, userPass);
                Token task = new Token();
                try {
                    task = token.execute().body();
                    Log.d("DATATATAT", task.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String authtoken = task.getToken();

                //String authtoken = serverQueries.userSignIn(userName, userPass);

                data.putString(AccountManager.KEY_ACCOUNT_NAME, userName);
                data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                data.putString(AccountManager.KEY_AUTHTOKEN, authtoken);
                data.putString(PARAM_USER_PASS, userPass);

                final Intent result = new Intent();
                /*result.putExtra(AccountManager.KEY_ACCOUNT_NAME, userName);
                result.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                result.putExtra(AccountManager.KEY_AUTHTOKEN, authtoken);
                result.putExtra(PARAM_USER_PASS, userPass);*/
                result.putExtras(data);
                return result;
            }
            @Override
            protected void onPostExecute(Intent intent) {
                finishLogin(intent);
            }
        }.execute();
    }

    private void finishLogin(Intent intent) {

        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        //String accountType = intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
        //Log.d("LOLOLO", intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
        //final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
        final Account account = new Account(accountName, AccountGeneral.ACCOUNT_TYPE);

        String authToken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);

        if (accountManager.addAccountExplicitly(account, accountPassword, null)) {
            Log.d("zero", "> finishLogin > addAccountExplicitly: YES!");
        } else {
            Log.d("zero", "> finishLogin > addAccountExplicitly: NO!");
        }

        accountManager.setAuthToken(account, tokenType, authToken);

            //accountManager.setPassword(account, accountPassword);

        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }
}
