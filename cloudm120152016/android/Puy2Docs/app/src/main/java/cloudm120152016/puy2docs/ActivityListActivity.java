package cloudm120152016.puy2docs;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class ActivityListActivity extends AppCompatActivity {
    private File m_currentDir;
    ListView listView;
    ListAdapter m_model;
    ArrayList<FileItem> m_data;
    private ShareActionProvider shared;
    /*private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;*/
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);

        listView = (ListView) findViewById(R.id.listView);
        m_currentDir = new File("/sdcard/");
        m_data = getDataModel(m_currentDir);
        m_model = new ListAdapter(this, m_data);
        listView.setAdapter(m_model);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_actionbar_folderfile, menu);

        /*
        MenuItem itemShared = (MenuItem) menu.findItem(R.id.action_share);
        shared =(ShareActionProvider)itemShared.getActionProvider();

        Intent intent = getDefaultShareIntent();


        if(intent!=null)
            shared.setShareIntent(intent);
            */
        return true;

    }

    private Intent getDefaultShareIntent(){

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "SUBJECT");
        intent.putExtra(Intent.EXTRA_TEXT,"Simple Contenu !");
        return intent;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_done:
                ListCheckedItem();
                return true;
            case R.id.action_reset_checked:
                ResetCheckBox();
                return true;
            case R.id.action_search:
                ActionSearch();
                return true;
            //case R.id.action_share:
            // ActionShare();
            //return true;
            case R.id.action_Delete:
                DeleteSelectedFile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    static ArrayList<FileItem> getDataModel(File path) {
        File[] fileArray = path.listFiles();
        ArrayList<FileItem> fileItemArrayList = new ArrayList<FileItem>();

        for (File f : fileArray) {
            FileItem Itemfile = new FileItem(f);
            fileItemArrayList.add(Itemfile);
        }
        return fileItemArrayList;
    }

    private void ResetCheckBox() {
        for (FileItem item : m_data) {
            item.setChecked(false);
        }
        m_model.notifyDataSetChanged();
        //new AlertDialog.Builder(this).setTitle("Warning").setMessage("Refresh done!").setNeutralButton("Ok", null).show();
    }

    public void ListCheckedItem() {
        String CheckeItems = "";
        for (FileItem item : m_data) {
            if (item.getChecked()) {
                CheckeItems += item.getFile().getName() + "\n";
            }
        }
        new AlertDialog.Builder(this).setTitle("Warning").setMessage(CheckeItems).setNeutralButton("Close", null).show();

    }
//action share et delete, selecteDone

   /* public void ActionShare() {
        Intent share_activity = new Intent(this, ShareAppActivity.class);
        startActivity(share_activity);
    }*/

    public void ActionSearch() {
        //Intent search_activity = new Intent(this, SearchActivity.class);
        Intent search_activity = new Intent(this, EditSearchview.class);
        startActivity(search_activity);
    }




    public void DeleteSelectedFile() {

        //path= new File("/sdcard/");


        /*for (FileItem item : m_data) {
            if (item.getChecked()) {
                String CheckeItem = item.getFile().getName();
                File path = item.getFile();
                path.delete();
                //m_data.remove(item);
                new AlertDialog.Builder(this).setTitle("Warning").setMessage(CheckeItem).setNeutralButton("Ok", null).show();
            }
        }*/

        Iterator<FileItem> i = m_data.iterator();
        while (i.hasNext()) {
            FileItem item = i.next(); // must be called before you can call i.remove()
            if (item.getChecked()) {
                String CheckeItem = item.getFile().getName();
                File path = item.getFile();
                path.delete();
                i.remove();
                new AlertDialog.Builder(this).setTitle("Voulez-vous l'éffacer ?").setMessage(CheckeItem).setNeutralButton("Ok", null).show();
            }

        }
        m_model.notifyDataSetChanged();

    }
}

class ListAdapter extends ArrayAdapter implements Filterable{

    Context context;
    FileFilter m_fileFilter = null;
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
    public Filter getFilter() {
        if (m_fileFilter == null)
            m_fileFilter = new FileFilter();
        return m_fileFilter;
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
            checkBox.setChecked(m_fileList.get(position).getChecked());
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    m_fileList.get(position).Triggle();
                }
            });

            if(ff.isDirectory())
                folderIcon.setImageResource(R.drawable.closed_folder_yellow);
            else {
                folderIcon.setImageResource(R.drawable.imagefile);
            }

        }catch(Exception e)
        {

        }

        return rowItem;
    }

    private class FileFilter extends Filter {
        @Override
        protected Filter.FilterResults performFiltering(CharSequence constraint) {
            Filter.FilterResults results = new FilterResults();
            //new AlertDialog.Builder(context).setTitle("Warning").setMessage(constraint.toString().toUpperCase()).setNeutralButton("Close", null).show();
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = m_fileList;
                results.count = m_fileList.size();

            } else {

                ArrayList<FileItem> filterList = new ArrayList<FileItem>();
                //new AlertDialog.Builder(context).setTitle("Warning").setMessage(constraint.toString().toUpperCase()).setNeutralButton("Close", null).show();


                for (FileItem p : m_fileList) {
                    final String text = p.getFile().getName().toUpperCase();
                    if (text.contains(constraint.toString().toUpperCase())){
                        filterList.add(p);

                    }

                }

                results.values = filterList;
                results.count = filterList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                m_fileList = (ArrayList<FileItem>) results.values;
                notifyDataSetChanged();


            }

        }


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