package cloudm120152016.puy2docs.utils.authentification.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;


public class P2CAuthenticatorService extends Service {

    private P2CAuthenticator authenticator;

    @Override
    public void onCreate() {
        authenticator = new P2CAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }
}
