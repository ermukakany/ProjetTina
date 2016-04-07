package cloudm120152016.puy2docs;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends ActionBarActivity {
    SearchView searchEditText;
    ListView listSearch;

    private File m_currentDir;
    private ArrayList<FileItem> m_data;
    private ListAdapter m_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEditText = (SearchView) findViewById(R.id.searchViewEdite);
        m_currentDir = new File("/sdcard/");
        m_data = ActivityListActivity.getDataModel(m_currentDir);
        m_model = new ListAdapter(this, m_data);
        listSearch = (ListView) findViewById(R.id.listViewsearch);
        listSearch.setAdapter(m_model);

        searchEditText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.isEmpty())
                {

                   //listSearch.clearTextFilter();
                }
                else{
                    ArrayList<FileItem> filterList = null;
                    /*for (FileItem p : m_data) {
                        final String text = p.getFile().getName().toUpperCase();
                        if (text.contains(newText.toUpperCase())) {
                            //new AlertDialog.Builder(SearchActivity.this).setTitle("Warning").setMessage(newText).setNeutralButton("Close", null).show();
                            filterList.add(p);
                        }

                    }*/
                    m_model.getFilter().filter(newText);
                }
                return false;
            }
        });



    }



}