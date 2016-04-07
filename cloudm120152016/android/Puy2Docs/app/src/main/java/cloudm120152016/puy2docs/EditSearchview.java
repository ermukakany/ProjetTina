package cloudm120152016.puy2docs;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class EditSearchview extends AppCompatActivity {
    ListView m_searchListView;

    private File m_currentDir;
    private ArrayList<FileItem> m_data;
    private ListAdapter m_model;
    private EditText m_searchEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_searchview);
        m_currentDir = new File("/sdcard/");
        m_data = ActivityListActivity.getDataModel(m_currentDir);
        m_model = new ListAdapter(this, m_data);
        m_searchListView = (ListView) findViewById(R.id.searchListView);
        m_searchListView.setAdapter(m_model);
        m_searchEdit = (EditText)findViewById(R.id.searchEditText);

        m_searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                m_model.getFilter().filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

}
