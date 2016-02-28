package cloudm120152016.puy2docs.activities;


import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.afollestad.materialdialogs.MaterialDialog;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cloudm120152016.puy2docs.R;
import cloudm120152016.puy2docs.activities.master.Fab;
import cloudm120152016.puy2docs.activities.master.fragments.ItemsFragment;

public class MasterActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;

    //@Bind(R.id.nest_scrollview)
    //NestedScrollView scrollView;

    //@Bind(R.id.action_refresh)
    MenuItem refresh;
    MenuItem search;

    public ItemsFragment currentFragment;

    MaterialSheetFab materialSheetFab;

    @Bind(R.id.fab)
    Fab fab;
    @Bind(R.id.fab_sheet)
    View sheetView;
    @Bind(R.id.overlay)
    View overlay;

    @Bind(R.id.fab_sheet_item_new_folder)
    TextView newFolder;

    @Bind(R.id.fab_sheet_item_new_file)
    TextView newFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_master);

        ButterKnife.bind(this);

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);

        //ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), "Name");
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(toolbar.getTitle());
        //collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));


        // DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        drawerLayout.setDrawerListener(drawerToggle);

        Iconify.with(new MaterialModule());

        setupFab();

        if (savedInstanceState != null) {
            currentFragment = (ItemsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "currentFragment");
            //currentFragment = (ItemsFragment) getSupportFragmentManager().fin(savedInstanceState, "currentFragment");
        } else {

            currentFragment = ItemsFragment.newInstance("");

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, currentFragment, ItemsFragment.TAG).commit();
        }

    }

    void setupFab() {
        fab.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).color(Color.WHITE).sizeDp(16));
        int sheetColor = ContextCompat.getColor(this, R.color.background_card);
        int fabColor = ContextCompat.getColor(this, R.color.theme_accent);

        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay,
                sheetColor, fabColor);

        newFolder.setCompoundDrawablesWithIntrinsicBounds(new IconicsDrawable(this, FontAwesome.Icon.faw_folder).sizeDp(24).color(Color.parseColor("#2196F3")), null, null, null);

        newFile.setCompoundDrawablesWithIntrinsicBounds(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_file_upload).sizeDp(24).color(Color.parseColor("#2196F3")), null, null, null);
    }

    @OnClick(R.id.fab_sheet_item_new_folder)
    public void createNewFolder() {
        new MaterialDialog.Builder(this)
                .title(R.string.new_folder)
                .positiveText("ok")
                .negativeText("non")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        Log.d("Input", input.toString());
                        if (!TextUtils.isEmpty(input)) {
                            currentFragment.createNewFolder(input.toString());
                        }
                    }
                })
                .show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        currentFragment = (ItemsFragment) getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (currentFragment != null) {
            getSupportFragmentManager().putFragment(outState, "currentFragment", currentFragment);
        }

        super.onSaveInstanceState(outState);
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
        if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                Log.i("MainActivity", "popping backstack");
                getSupportFragmentManager().popBackStack();
            } else {
                Log.i("MainActivity", "nothing on backstack, calling super");
                super.onBackPressed();
            }
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
