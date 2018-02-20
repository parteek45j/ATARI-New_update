package com.icar.atari;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.icar.atari.ChatFrag.ChatFragment;
import com.icar.atari.FrontFrag.FrontFragment;
import com.icar.atari.KvksFrag.KvksFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView textViewName,textViewEmail;
    CircleImageView circleImageView;
//    ImageView imageViewProfile;
    String name,phone,email,profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FrontFragment fg = new FrontFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragme, fg);
        transaction.commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        preferences=getSharedPreferences(Util.loginPrefs,MODE_PRIVATE);
        editor=preferences.edit();
        name=preferences.getString(Util.username,"");
        phone=preferences.getString(Util.phone,"");
        email=preferences.getString(Util.email,"");
        profile=preferences.getString(Util.profile,"");
        View nv=navigationView.getHeaderView(0);
        textViewName=(TextView)nv.findViewById(R.id.textName);
        textViewEmail=(TextView)nv.findViewById(R.id.textEmail);
        textViewName.setText(name);
        textViewEmail.setText(email);
        circleImageView=(CircleImageView) nv.findViewById(R.id.imageView11);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfile fraEditProfile=new EditProfile();
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragme,fraEditProfile);
                transaction.commit();
            }
        });
        if (profile.length()>0 && !(profile.contains("null"))) {
            Glide.with(this).load(profile).into(circleImageView);

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view fragment_home clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

                FrontFragment fg = new FrontFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragme, fg);
                transaction.commit();



        } else if (id == R.id.nav_writePost) {
            boolean admin=preferences.getBoolean(Util.admin,false);
            if (admin){
                HomeFragment fragment=new HomeFragment();
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragme,fragment);
                transaction.commit();
            }else {
                LoginFragment fragment=new LoginFragment();
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragme,fragment);
                transaction.commit();
            }

        } else if (id == R.id.nav_sharefeed) {
            NewsFeed fragment =new NewsFeed();
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragme,fragment);
            transaction.commit();
        } else if (id == R.id.nav_editProfie) {
            EditProfile fraEditProfile=new EditProfile();
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragme,fraEditProfile);
            transaction.commit();
        } else if (id == R.id.nav_editProfie1) {


        } else if (id == R.id.nav_logout) {
            AlertDialog.Builder ad=new AlertDialog.Builder(this);
            ad.setCancelable(false);
            ad.setTitle("Do you wish to Logout?");
            ad.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    editor.clear();
                    editor.commit();
                    startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                    finishAffinity();
                }
            });
            ad.setPositiveButton("No",null);
            ad.create().show();
        }
        else if (id == R.id.nav_kvks) {

            KvksFragment kk = new KvksFragment();
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragme,kk);
            transaction.commit();
        }
        else if (id==R.id.chat)
        {
            ChatFragment cc = new ChatFragment();
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragme,cc);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        editor.putBoolean(Util.admin,false);
        editor.commit();
    }
}
