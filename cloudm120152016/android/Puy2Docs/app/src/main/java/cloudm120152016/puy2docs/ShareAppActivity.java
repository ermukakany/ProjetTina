package cloudm120152016.puy2docs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

public class ShareAppActivity extends AppCompatActivity{
    private ShareActionProvider shared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_app);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_actionbar_folderfile, menu);
        MenuItem itemShared = (MenuItem) menu.findItem(R.id.action_share);
        shared =( ShareActionProvider)itemShared.getActionProvider();

        Intent intent = getDefaultShareIntent();


        if(intent!=null)
           shared.setShareIntent(intent);
        return super.onCreateOptionsMenu(menu);

    }

    private Intent getDefaultShareIntent(){

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "SUBJECT");
        intent.putExtra(Intent.EXTRA_TEXT,"Simple Contenu !");
        return intent;
    }
}
