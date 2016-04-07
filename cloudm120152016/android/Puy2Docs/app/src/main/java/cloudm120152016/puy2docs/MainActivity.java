package cloudm120152016.puy2docs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cloudm120152016.puy2docs.activities.MasterActivity;
import cloudm120152016.puy2docs.activities.auth.base.BaseLoginActivity;
import cloudm120152016.puy2docs.activities.auth.base.BaseSignUpActivity;
import cloudm120152016.puy2docs.activities.auth.base.Disconnect;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Verifier si compte est valide si oui on redirige sur la page des résultats
        // Sinon on continue.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeActivityFiles(View view) {
        Intent intent = new Intent(this,MasterActivity.class);
        startActivity(intent);
    }

    public void onClickButtonLocalFile(View view) {
        Intent intent = new Intent(this,ActivityListActivity.class);
        startActivity(intent);
    }

    public void changeActivityLoginChoice(View view) {
        Intent intent = new Intent(this, LoginChoiceActivity.class);
        startActivity(intent);
    }

    public void changeActivitySignIn(View view) {
        Intent intent = new Intent(this, BaseLoginActivity.class);
        startActivity(intent);
    }

    public void changeActivitySignUp(View view) {
        Intent intent = new Intent(this, BaseSignUpActivity.class);
        startActivity(intent);
    }

    public void userDisconnect(){
        Intent intent = new Intent(this, Disconnect.class);
        startActivity(intent);
    }
}
