package cloudm120152016.puy2docs;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ActivityListActivity extends AppCompatActivity {
   private File m_currentDir;
    ListView listView;
    ImageView imgView;
    ListAdapter m_model;
    CheckBox checkBoxfile;
    ArrayList<FileItem> m_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);

        Resources res = getResources();
        listView = (ListView) findViewById(R.id.listView);
        imgView = (ImageView)findViewById(R.id.imageViewItem);
        checkBoxfile =(CheckBox)findViewById(R.id.checkBoxfile);

        m_currentDir = new File("/sdcard/");
        m_data = getDataModel(m_currentDir);
        m_model = new ListAdapter(this,m_data);
        listView.setAdapter(m_model);

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
                ListCheckedItem();
                return true;
            case R.id.action_refresh:

                ResetCheckBox();
                return true;
            case R.id.action_search:
                SearchFodder();
                return true;
            case R.id.action_share:
                ActionShare();
                return true;
            case R.id.action_Delete:
                DeleteSelectedFile(m_currentDir);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ArrayList<FileItem> getDataModel(File path)
    {
        File[] fileArray = path.listFiles();
        ArrayList<FileItem> fileItemArrayList = new ArrayList<FileItem>();

        for(File f:fileArray)
        {
            FileItem Itemfile = new FileItem(f);
            fileItemArrayList.add(Itemfile);
        }
        return fileItemArrayList;
    }
    private void ResetCheckBox() {
        /** get all values of the EditText-Fields */
        View v, row;
        CheckBox checkBox;
        for (int i = 0; i < listView.getCount(); i++) {
            v = listView.getAdapter().getView(i, null, null);
            row = v.findViewById(i);
            checkBox = (CheckBox) row.findViewById(R.id.checkBoxfile);
            checkBox.setChecked(false);
        }
        for(FileItem item :m_data)
        {
            if(item.getChecked())
            {
                item.setChecked(false);
            }
        }

    }

    public void ListCheckedItem()
    {
        String CheckeItems = "";
        for(FileItem item :m_data)
        {
            if(item.getChecked())
            {
                CheckeItems += item.getFile().getName()+"\n";
            }
        }
       // new AlertDialog.Builder(this).setTitle("Warning").setMessage(CheckeItems).setNeutralButton("Close", null).show();
        new AlertDialog.Builder(this).setTitle("Warning").setMessage(CheckeItems).setNegativeButton("Cancel", null).setPositiveButton("OK", null).show();

    }
//action share et delete, selecteDone

    public void ActionShare()
    {
        Intent share_activity = new Intent(this, ShareAppActivity.class);
        startActivity(share_activity);
    }




    public void DeleteSelectedFile(File path)
    {
        if(path.isDirectory())

        {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteFile(files[i].getName());
                }
            }

        }else
            path.delete();
    }


    public void SearchFodder()
    {

    }

}

class ListAdapter extends ArrayAdapter {

    Context context;
    private ArrayList<FileItem> m_fileList;

    public ListAdapter(Context c,ArrayList<FileItem> fileList) {
        super(c, R.layout.single_item, R.id.tvTitle);
        this.context = c;
        m_fileList = fileList;
        if(m_fileList.size() == 0)
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
        return m_fileList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowItem = convertView;
        if(rowItem == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowItem = layoutInflater.inflate(R.layout.single_item, parent, false);
        }
        ImageView folderIcon = (ImageView)rowItem.findViewById(R.id.imageViewItem);
        TextView title = (TextView)rowItem.findViewById(R.id.tvTitle);
        TextView description = (TextView)rowItem.findViewById(R.id.tvDescription);

        CheckBox checkBox =(CheckBox)rowItem.findViewById(R.id.checkBoxfile);

       // new AlertDialog.Builder(context).setTitle("Warning").setMessage("liste file").setNeutralButton("Close", null).show();
        try{

            File ff = m_fileList.get(position).getFile();
            title.setText(ff.getName());
            Date lastModDate = new Date(ff.lastModified());
            DateFormat formater = DateFormat.getDateTimeInstance();
            String date_modify = formater.format(lastModDate);

            description.setText(date_modify);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    m_fileList.get(position).Triggle();
                }
            });
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


class FileItem
{

    private File m_file;
    private boolean m_checked = false;

    public FileItem(){

    }
    public FileItem(File file){
        this.m_file = file;
    }

    public void Triggle(){

        this.m_checked = !this.m_checked;
    }

    public boolean getChecked(){
        return m_checked;
    }

    public void setChecked(boolean checked){
        this.m_checked = checked;
    }

    public File getFile(){
        return m_file;
    }

    public void setFile(File file){
        this.m_file = file;
    }


}