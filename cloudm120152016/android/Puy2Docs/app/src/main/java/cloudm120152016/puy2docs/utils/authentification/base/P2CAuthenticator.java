package cloudm120152016.puy2docs.utils.authentification.base;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import java.io.IOException;

import cloudm120152016.puy2docs.activities.auth.base.BaseLoginActivity;
import cloudm120152016.puy2docs.models.Token;
import cloudm120152016.puy2docs.utils.retrofit.AccountGeneral;
import cloudm120152016.puy2docs.utils.retrofit.ServiceGenerator;
import cloudm120152016.puy2docs.utils.retrofit.UserService;
import retrofit2.Call;

import static cloudm120152016.puy2docs.utils.retrofit.AccountGeneral.*;

public class P2CAuthenticator extends AbstractAccountAuthenticator {

    public static final String AUTH_TOKEN_TYPE_GLOBAL = "global";

    private final Context context;

    public P2CAuthenticator(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        final Intent intent = new Intent(context, BaseLoginActivity.class);
        intent.putExtra(BaseLoginActivity.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(BaseLoginActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(BaseLoginActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {

        if (!authTokenType.equals(AccountGeneral.AUTHTOKEN_TYPE_READ_ONLY) && !authTokenType.equals(AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
            return result;
        }

        final AccountManager accountManager = AccountManager.get(context);

        String authToken = accountManager.peekAuthToken(account, authTokenType);

        if (TextUtils.isEmpty(authToken)) {
            final String password = accountManager.getPassword(account);
            if (password != null) {

                UserService userService = ServiceGenerator.createService(UserService.class);
                Call<Token> token = userService.login(account.name, password);
                Token task = new Token();
                try {
                    task = token.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                authToken = task.getToken();
                //authToken = serverQueries.userSignIn(account.name, password);
            }
        }

        // If we get an authToken - we return it
        if (!TextUtils.isEmpty(authToken)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        // If we get here, then we couldn't access the user's password - so we
        // need to re-prompt them for their credentials. We do that by creating
        // an intent to display our AuthenticatorActivity.
        final Intent intent = new Intent(context, BaseLoginActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(BaseLoginActivity.ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(BaseLoginActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(BaseLoginActivity.ARG_ACCOUNT_NAME, account.name);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {

        if (AUTHTOKEN_TYPE_FULL_ACCESS.equals(authTokenType))
            return AUTHTOKEN_TYPE_FULL_ACCESS_LABEL;
        else if (AUTHTOKEN_TYPE_READ_ONLY.equals(authTokenType))
            return AUTHTOKEN_TYPE_READ_ONLY_LABEL;
        else
            return authTokenType + " (Label)";
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        return null;
    }
}
