package cloudm120152016.puy2docs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cloudm120152016.puy2docs.activities.MasterActivity;
import cloudm120152016.puy2docs.activities.auth.base.BaseLoginActivity;
import cloudm120152016.puy2docs.activities.auth.base.BaseSignUpActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Verifier si compte est valide si oui on redirige sur la page des résultats
        // Sinon on continue.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeActivityLoginChoice(View view) {
        Intent intent = new Intent(this, LoginChoiceActivity.class);
        startActivity(intent);
    }

    public void changeActivityFiles(View view) {
        Intent intent = new Intent(this, MasterActivity.class);
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
}
