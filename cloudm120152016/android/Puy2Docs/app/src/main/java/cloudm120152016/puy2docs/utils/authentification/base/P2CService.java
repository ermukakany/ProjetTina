package cloudm120152016.puy2docs.utils.authentification.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class P2CService extends Service {

    private static final Object sSyncAdapterLock = new Object();
    private static P2CSyncAdapter sSyncAdapter  = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter  == null)
                sSyncAdapter  = new P2CSyncAdapter(getApplicationContext(), true);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        //return new P2CAuthenticator(this).getIBinder();
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
