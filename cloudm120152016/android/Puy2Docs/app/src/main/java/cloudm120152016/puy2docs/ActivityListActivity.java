package cloudm120152016.puy2docs;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;


public class ActivityListActivity extends AppCompatActivity {
    private File currentDir;
    String[] memeTitles;
    String[] memeDescriptions;
    ListView listView;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);

        Resources res = getResources();
        memeTitles = res.getStringArray(R.array.titles);
        memeDescriptions = res.getStringArray(R.array.descriptions);
        listView = (ListView) findViewById(R.id.listView);
        imgView = (ImageView)findViewById(R.id.imageViewItem);

        //FileListAdapter fileListAdapter = new FileListAdapter(this, memeTitles, memeDescriptions);
        //listView.setAdapter(fileListAdapter);


        currentDir = new File("/sdcard/");
        ListAdapter listFileFolder = new ListAdapter(this, currentDir);
        listView.setAdapter(listFileFolder);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_actionbar_folderfile, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_done:
                //newGame();
                return true;
            case R.id.action_refresh:
                //showHelp();
                return true;
            case R.id.action_search:
                //showHelp();
                return true;
            case R.id.action_share:
                ActionShare();
                return true;
            case R.id.action_Delete:
                //showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ActionShare()
    {
        Intent share_activity = new Intent(this, ShareAppActivity.class);
        startActivity(share_activity);
    }
}

class FileListAdapter extends ArrayAdapter {

    Context context;
    String[] titlesArray;
    String[] descrArray;

    public FileListAdapter(Context c, String[] titles, String[] desc) {
        super(c, R.layout.single_item, R.id.tvTitle, titles);
        this.context = c;
        this.titlesArray = titles;
        this.descrArray = desc;

        //mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowItem = convertView;
    if(rowItem == null) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowItem = layoutInflater.inflate(R.layout.single_item, parent, false);
    }
        ImageView folderIcon = (ImageView)rowItem.findViewById(R.id.imageViewItem);
        TextView title = (TextView)rowItem.findViewById(R.id.tvTitle);
        TextView description = (TextView)rowItem.findViewById(R.id.tvDescription);


        title.setText(titlesArray[position]);
        description.setText(descrArray[position]);
        folderIcon.setImageResource(R.drawable.ic_folder_open_black_36dp);

        return rowItem;
    }
}

class ListAdapter extends ArrayAdapter {

    Context context;
    String[] titlesArray;
    String[] descrArray;
    private File[] m_fileList;

    public ListAdapter(Context c,File f) {
        super(c, R.layout.single_item, R.id.tvTitle);
        this.context = c;
        m_fileList = f.listFiles();
        if(m_fileList.length == 0)
        {
            //new AlertDialog.Builder(c).setTitle("Warning").setMessage("Empty list!").setNeutralButton("Close", null).show();
        }
       /* else{
            String names = " ";
            for (File ff : m_fileList) {
                names += ff.getName()+" ";
            }
            new AlertDialog.Builder(c).setTitle("Warning").setMessage(names).setNeutralButton("Close", null).show();
        }*/
        //mResource = resource;
    }
    @Override
    public int getCount() {
        return m_fileList.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowItem = convertView;
        if(rowItem == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowItem = layoutInflater.inflate(R.layout.single_item, parent, false);
        }
        ImageView folderIcon = (ImageView)rowItem.findViewById(R.id.imageViewItem);
        TextView title = (TextView)rowItem.findViewById(R.id.tvTitle);
        TextView description = (TextView)rowItem.findViewById(R.id.tvDescription);

       // new AlertDialog.Builder(context).setTitle("Warning").setMessage("liste file").setNeutralButton("Close", null).show();
        try{

            File ff = m_fileList[position];
            title.setText(ff.getName());
            Date lastModDate = new Date(ff.lastModified());
            DateFormat formater = DateFormat.getDateTimeInstance();
            String date_modify = formater.format(lastModDate);

            description.setText(date_modify);

            if(ff.isDirectory())
            {
                folderIcon.setImageResource(R.drawable.ic_folder_open_black_36dp);

            }
            else {
                folderIcon.setImageResource(R.drawable.ic_insert_drive_file_black_24dp);
            }

        }catch(Exception e)
        {

        }

       return rowItem;
    }



}
