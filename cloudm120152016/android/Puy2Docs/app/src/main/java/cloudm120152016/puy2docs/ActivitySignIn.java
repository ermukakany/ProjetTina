package cloudm120152016.puy2docs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cloudm120152016.puy2docs.activities.MasterActivity;
import cloudm120152016.puy2docs.activities.auth.base.Disconnect;

public class ActivitySignIn extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        // Verifier si compte est valide si oui on redirige sur la page des résultats
        // Sinon on continue.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
    }

    public void changeActivityFiles(View view) {
        Intent intent = new Intent(this,MasterActivity.class);
        startActivity(intent);
    }

    public void onClickButtonLocalFile(View view) {
        Intent intent = new Intent(this,ActivityListActivity.class);
        startActivity(intent);
    }

    public void userDisconnect(View view) {
        Intent intent = new Intent(this, Disconnect.class);
        startActivity(intent);
    }
}
