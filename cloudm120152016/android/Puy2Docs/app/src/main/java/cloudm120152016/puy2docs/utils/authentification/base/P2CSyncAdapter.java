package cloudm120152016.puy2docs.utils.authentification.base;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import cloudm120152016.puy2docs.utils.retrofit.AccountGeneral;

public class P2CSyncAdapter extends AbstractThreadedSyncAdapter {

    private final AccountManager accountManager;

    public P2CSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        accountManager = AccountManager.get(context);
    }

    public P2CSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs, AccountManager accountManager) {
        super(context, autoInitialize, allowParallelSyncs);
        this.accountManager = accountManager;
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d("Puy2Docs", "onPerformSync for account[" + account.name + "]");
        try {
            String authToken = accountManager.blockingGetAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, true);

            // Sync shares

            // Sync local storage
        } catch (OperationCanceledException | IOException | AuthenticatorException e) {
            e.printStackTrace();
        }

    }
}
