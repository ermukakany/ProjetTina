package com.example.info.myexample;

import android.app.Activity;
import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class LocalModeActivity extends Activity {
    String[] nomDc;
    String[] nomTypeDoc;
    Context context;
    ArrayList<ItemFile> itemFileArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_mode);
        itemFileArrayList = ListFichier();
    }

    public LocalModeActivity(Context c, String[] folderName, String[] typeName) {
        this.nomDc = folderName;
        this.nomTypeDoc = typeName;
        this.context = c;


    }

    public ArrayList<ItemFile> ListFichier(){
        ArrayList<ItemFile> myList;
        myList = new ArrayList<ItemFile>();

        String root_sd = Environment.getExternalStorageDirectory().toString();
        File file;
        file = new File(root_sd + "/external_sd");
        File list[] = file.listFiles();

        for (int i = 0; i < list.length; i++) {
            String name = list[i].getName();
            String type = " ";

            if(list[i].isDirectory())
            {
                type = "Dossier";
            }
            else
            {
                type = "Fichier";
            }
           ItemFile itemFile = new ItemFile(name, type);

            myList.add(itemFile);
        }

        return myList;
    }
}

class ItemFile
{
    private String m_filename;
    private String m_type;

    public ItemFile(String filename, String type)
    {
        this.m_filename = filename;
        this.m_type = type;
    }

    String getM_filename()
    {
        return this.m_filename;
    }

    String getM_type()
    {
        return this.m_type;
    }

   void setM_filename(String filename)
   {
       this.m_filename = filename;
   }

    void setM_type(String type)
    {
        this.m_type = type;
    }


}

class AdaptorList extends ArrayAdapter
{
    Context context;
    int [] images;
    String [] titlesArray;

    public AdaptorList(Context context, ArrayList<ItemFile> itemFileArrayList) {
        super(context, R.layout.single_item);

    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row_item = convertView;

        if (row_item == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.single_item, parent, false);

        }


        ImageView folderIcon = (ImageView)row_item.findViewById(R.id.fileIcon);
        folderIcon.setImageResource(position);
        /*if(folderIcon)
        {

        }*/
        TextView title = (TextView)row_item.findViewById(R.id.tvfilename);
      return row_item;
    }
}