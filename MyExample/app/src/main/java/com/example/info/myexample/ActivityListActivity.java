package com.example.info.myexample;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.widget.TextView;

public class ActivityListActivity extends Activity {
    private File currentDir;

    String [] memeTitles;
    String [] memeDescriptions;
    ListView listView;
    int []images  = {R.drawable.ic_folder_open_black_36dp, R.drawable.menu_1, R.drawable.menu_10, R.drawable.menu_11, R.drawable.menu_2 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);

        Resources res = getResources();
        memeTitles = res.getStringArray(R.array.titles);
        memeDescriptions = res.getStringArray(R.array.descriptions);
        listView = (ListView)findViewById(R.id.listView);

        FileListAdapter fileListAdapter = new FileListAdapter(this, memeTitles, images, memeDescriptions);
        listView.setAdapter(fileListAdapter);


        currentDir = new File("/sdcard/");
        fill(currentDir);

    }

    private void fill(File f) {
        File[] dirs = f.listFiles();
        this.setTitle("Current Dir: " + f.getName());
        List dir = new ArrayList();
        List fls = new ArrayList();

        try{
            for (File ff : dirs) {
                String name = ff.getName();
                Date lastModDate = new Date(ff.lastModified());
                DateFormat formater = DateFormat.getDateTimeInstance();
                String date_modify = formater.format(lastModDate);

                /*
                 * Note: Remove this
                 * name.equalsIgnoreCase("Covenant and Augment Softsol" if u
                 * want to list all ur sd card file and folder
                 */
                /*
                if (ff.isDirectory() && name.equalsIgnoreCase("Covenant and Augment Softsol")) {

                    File[] fbuf = ff.listFiles();
                    int buf = 0;

                    if (fbuf != null) {
                        buf = fbuf.length;
                    } else {
                        buf = 0;
                    }

                    String num_item = String.valueOf(buf);
                    if (buf == 0)
                        num_item = num_item + " item";
                    else
                        num_item = num_item + " items";

                    // String formated = lastModDate.toString();
                    dir.add(new Albumb(ff.getName(), num_item, date_modify, ff
                            .getAbsolutePath(), "directory_icon"));
                } else {*/
                 /*
                  * Note: Remove this
                  * f.getName().equalsIgnoreCase("Covenant and Augment Softsol"
                  * if u want to list all ur sd card file and folder
                  */
                /*    if (f.getName().equalsIgnoreCase(
                            "Covenant and Augment Softsol")) {
                        fls.add(new Albumb(ff.getName(), ff.length() + " Byte",
                                date_modify, ff.getAbsolutePath(), "file_icon"));
                    }
                }*/
            }

        }catch (Exception e) {

        }

    }
}




class FileListAdapter extends ArrayAdapter
{
    //private final int mResource;
    Context context;
    int [] images;
    String [] titlesArray;
    String [] descrArray;

    public FileListAdapter (Context c, String[] titles, int [] imgs, String [] desc)
    {
        super(c, R.layout.single_row, R.id.tvTitle, titles);
        this.context = c;
        this.images = imgs;
        this.titlesArray = titles;
        this.descrArray = desc;

        //mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.single_row, parent, false);

        ImageView folderIcon = (ImageView)row.findViewById(R.id.imageViewItem);
        TextView title = (TextView)row.findViewById(R.id.tvTitle);
        TextView description = (TextView)row.findViewById(R.id.tvDescription);


        title.setText(titlesArray[position]);
        description.setText(descrArray[position]);
        
        folderIcon.setImageResource(images[position]);

        return row;
    }
}