package cloudm120152016.puy2docs.activities;


import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.Iconics;
import com.mikepenz.iconics.IconicsDrawable;

import butterknife.Bind;
import butterknife.ButterKnife;
import cloudm120152016.puy2docs.R;
import cloudm120152016.puy2docs.activities.master.DividerItemDecoration;
import cloudm120152016.puy2docs.activities.master.ItemAdapter;
import cloudm120152016.puy2docs.activities.master.ItemsFragment;
import cloudm120152016.puy2docs.models.Item;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MasterActivity extends AppCompatActivity {

    private Stack<Fragment> fragmentStack;
    private FragmentManager fragmentManager;
    private ItemsFragment fragment;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;

    //@Bind(R.id.action_refresh)
    MenuItem refresh;
    MenuItem search;

    FloatingActionButton button;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    //RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_master);

        //ButterKnife.bind(this);

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);

        // DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        drawerLayout.setDrawerListener(drawerToggle);

        Iconify.with(new MaterialModule());

        fragmentStack = new Stack<>();

        fragment = ItemsFragment.newInstance("");

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.frameLayout, fragment);
        fragmentStack.push(fragment);
        ft.commit();
    }

    // DrawerLayout

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    // End DrawerLayout

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fragmentManager.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action, menu);

        refresh = menu.findItem(R.id.action_refresh).setIcon(
                new IconDrawable(this, MaterialIcons.md_refresh)
                        .color(Color.parseColor("#FFFFFF"))
                        .actionBarSize());
        search = menu.findItem(R.id.action_search).setIcon(
                new IconDrawable(this, MaterialIcons.md_search)
                        .color(Color.parseColor("#FFFFFF"))
                        .actionBarSize());
        return true;
    }
}
