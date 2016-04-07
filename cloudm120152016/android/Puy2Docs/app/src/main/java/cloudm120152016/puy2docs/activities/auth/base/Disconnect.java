package cloudm120152016.puy2docs.activities.auth.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cloudm120152016.puy2docs.R;

public class Disconnect extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        Button sendButton = (Button) findViewById(R.id.disconnect);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResume();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }
}
