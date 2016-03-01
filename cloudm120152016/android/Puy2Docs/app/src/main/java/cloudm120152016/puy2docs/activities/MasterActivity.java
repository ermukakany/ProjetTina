package cloudm120152016.puy2docs.activities;


import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;


import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.folderselector.FileChooserDialog;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.MenuSheetView;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
//import com.liulishuo.filedownloader.BaseDownloadTask;
//import com.liulishuo.filedownloader.FileDownloadListener;
//import com.liulishuo.filedownloader.FileDownloader;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import cloudm120152016.puy2docs.R;
import cloudm120152016.puy2docs.activities.master.Account;
import cloudm120152016.puy2docs.activities.master.fragments.ItemsFragment;

public class MasterActivity extends AppCompatActivity implements FileChooserDialog.FileCallback {

    public ItemsFragment currentFragment;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    MenuItem refresh;
    MenuItem search;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.bottomsheet)
    BottomSheetLayout bottomSheet;

    //private FileDownloadNotificationHelper<NotificationItem> notificationHelper;

    long enqueue;
    DownloadManager dm;
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_master);

        ButterKnife.bind(this);

        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);

        collapsingToolbarLayout.setTitle(toolbar.getTitle());

        // DrawerLayout
        drawerToggle = setupDrawerToggle();
        drawerLayout.setDrawerListener(drawerToggle);

        Iconify.with(new MaterialModule());

        setupFab();

        if (savedInstanceState != null) {
            currentFragment = (ItemsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "currentFragment");
        } else {

            currentFragment = ItemsFragment.newInstance("");

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, currentFragment, ItemsFragment.TAG).commit();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equalsIgnoreCase(
                        DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                    Toast.makeText(context, "Download Complte", Toast.LENGTH_LONG)
                            .show();
                }
            }
        };
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    void setupFab() {
        fab.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).color(Color.WHITE).sizeDp(16));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetNew();
            }
        });
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
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    // End DrawerLayout

    @Override
    public void onBackPressed() {
        if (bottomSheet.isSheetShowing()) {
            bottomSheet.dismissSheet();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // TODO ADD Refrech Method
                // do whatever
                return true;
            case R.id.action_refresh:
                currentFragment.refreshData();
                // do whatever
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean checkPermissionRead(){
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkPermissionWrite(){
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private static final int PERMISSION_REQUEST_CODE = 1;

    private void requestPermissionRead(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){

            Toast.makeText(this, "NONONONONO.", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private void requestPermissionWrite(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            Toast.makeText(this, "NONONONONO.", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onFileSelection(@NonNull FileChooserDialog dialog, @NonNull File file) {
        Toast.makeText(this, file.getName(), Toast.LENGTH_LONG).show();
        currentFragment.uploadNewFile(file);

    }

    public void sheetNew() {
        MenuSheetView menuSheetView =
                new MenuSheetView(this, MenuSheetView.MenuType.GRID, R.string.new_item, new MenuSheetView.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        if (bottomSheet.isSheetShowing()) {
                            bottomSheet.dismissSheet();
                        }
                        switch (item.getItemId()) {
                            case R.id.new_folder: createNewFolder();
                                break;
                            default: uploadNewFile();
                                break;
                        }
                        return true;
                    }
                });
        menuSheetView.updateMenu();

        menuSheetView.inflateMenu(R.menu.create);
        bottomSheet.showWithSheetView(menuSheetView);
        Log.d("Bottom", "" + bottomSheet.isSheetShowing());

    }

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

    public void uploadNewFile() {
        Toast.makeText(this, "New File.", Toast.LENGTH_LONG).show();

        if (checkPermissionRead()) {
            new FileChooserDialog.Builder(this)
                    .chooseButton(R.string.md_choose_label)  // changes label of the choose button
                    .initialPath("/sdcard/Download")  // changes initial path, defaults to external storage directory
                    .tag("optional-identifier")
                    .show();
        }
        else {
            requestPermissionRead();
        }
    }

    public void sheetEditFolder() {
        MenuSheetView menuSheetView =
                new MenuSheetView(this, MenuSheetView.MenuType.LIST, R.string.new_item, new MenuSheetView.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        if (bottomSheet.isSheetShowing()) {
                            bottomSheet.dismissSheet();
                        }
                        switch (item.getItemId()) {
                            case R.id.delete_folder:
                                currentFragment.deleteFolder();
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
        menuSheetView.updateMenu();

        menuSheetView.inflateMenu(R.menu.edit_folder);
        bottomSheet.showWithSheetView(menuSheetView);
        Log.d("Bottom", "" + bottomSheet.isSheetShowing());

    }

    public void sheetEditFile() {
        MenuSheetView menuSheetView =
                new MenuSheetView(this, MenuSheetView.MenuType.LIST, R.string.new_item, new MenuSheetView.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        if (bottomSheet.isSheetShowing()) {
                            bottomSheet.dismissSheet();
                        }
                        switch (item.getItemId()) {
                            case R.id.ddl:
                                if (checkPermissionWrite()) {
                                    downloadFile();
                                }
                                else {
                                    requestPermissionWrite();
                                }
                                break;
                            case R.id.delete_file:
                                currentFragment.deleteFile();
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
        menuSheetView.updateMenu();

        menuSheetView.inflateMenu(R.menu.edit_file);
        bottomSheet.showWithSheetView(menuSheetView);
        Log.d("Bottom", "" + bottomSheet.isSheetShowing());

    }

    void downloadFile() {

        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        Log.d("URLLLLLLLLLLLLLLLLLLL", Account.url_document_id(currentFragment.item_id));
        Uri uri = Uri.parse(Account.url_document_id(currentFragment.item_id));
        //Uri uri = Uri.parse("https://sites.google.com/site/compiletimeerrorcom/android-programming/oct-2013/DownloadManagerAndroid1.zip");
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.addRequestHeader("Authorization", Account.tokenHeader(getApplicationContext()));

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(currentFragment.fileName)
                .setDescription("Something useful. No, really.")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, currentFragment.fileName);

        enqueue = dm.enqueue(request);
    }
}
