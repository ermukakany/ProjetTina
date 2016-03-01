package cloudm120152016.puy2docs.activities.master;

import android.accounts.AccountManager;
import android.content.Context;

import cloudm120152016.puy2docs.utils.retrofit.ServiceGenerator;

import static cloudm120152016.puy2docs.utils.retrofit.AccountGeneral.ACCOUNT_TYPE;

public class Account {

    public static String url_document = ServiceGenerator.API_BASE_URL;

    public static String getToken(Context context) {
        AccountManager manager = AccountManager.get(context);
        android.accounts.Account[]accounts = manager.getAccountsByType(ACCOUNT_TYPE);
        return manager.peekAuthToken(accounts[0], "global");

    }

    public static String tokenHeader(Context context) {
        return "Token " + getToken(context);
    }

    public static String url_document_id(String id) {
        return url_document+"/document/"+id+"/";
    }
}
