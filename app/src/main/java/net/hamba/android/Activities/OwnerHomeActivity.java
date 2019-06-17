package net.hamba.android.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import net.hamba.android.Fragments.OwnerHomeFragment;
import net.hamba.android.R;
import net.hamba.android.Utils.PermissionsUtil;
import net.hamba.android.Utils.PrefsUtils;

import java.util.ArrayList;
import java.util.List;

public class OwnerHomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    public static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_owner_home);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        try {
           if(!PermissionsUtil.checkPermissions(this, PermissionsUtil.getPermissions(this))){
               checkPermissions();
           }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, new OwnerHomeFragment());
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.owner_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            FirebaseAuth.getInstance().signOut();
            PrefsUtils.setLoggedinUserType(-1);
            startActivity(new Intent(OwnerHomeActivity.this,ShopCustomerActivity.class));
            finish();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    int i;
                    List<Integer> indexesOfPermissionsNeededToShow = new ArrayList();
                    for (i = 0; i < permissions.length; i++) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                            indexesOfPermissionsNeededToShow.add(Integer.valueOf(i));
                        }
                    }
                    int size = indexesOfPermissionsNeededToShow.size();
                    if (size != 0) {
                        boolean isPermissionGranted = true;
                        for (i = 0; i < size && isPermissionGranted; i++) {
                            isPermissionGranted = grantResults[((Integer) indexesOfPermissionsNeededToShow.get(i)).intValue()] == 0;
                        }
                        if (!isPermissionGranted) {
                            showDialogNotCancelable("Permissions mandatory", "All the permissions are required for this app", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    OwnerHomeActivity.this.checkPermissions();

                                }
                            });
                        }
                    }
                }
                try {
                    if (PermissionsUtil.checkPermissions(this, PermissionsUtil.getPermissions(this))) {
                        startActivity(new Intent(this, OwnerHomeActivity.class));
                        finish();
                        return;
                    }
                    return;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    return;
                }
            default:
                return;
        }
    }

    private void showDialogNotCancelable(String title, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this).setTitle((CharSequence) title).setMessage((CharSequence) message).setPositiveButton((CharSequence) "OK", okListener).setCancelable(false).create().show();
    }
    private void checkPermissions() {
        PermissionsUtil.askPermissions(this);
    }

}
