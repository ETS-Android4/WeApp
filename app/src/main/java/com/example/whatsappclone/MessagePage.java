package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class MessagePage extends AppCompatActivity {
    Toolbar mainPageToolBar;
    ViewPager viewPager;
    SectionsPagerAdapter secionsPagerAdapater;
    TabLayout tabLayout;
    SharedPreferences pref;
    DatabaseReference ref;
    String UID;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_page);

        mainPageToolBar = findViewById(R.id.mainAppBar);
        viewPager = findViewById(R.id.tabPager);
        secionsPagerAdapater = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(secionsPagerAdapater);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        pref = getSharedPreferences("UID",MODE_APPEND);
        UID = pref.getString("phoneNumber","");
        ref = FirebaseDatabase.getInstance().getReference().child("users").child(UID);

        setSupportActionBar(mainPageToolBar);
        getSupportActionBar().setTitle("We App");
    }

    @Override
    protected void onStart() {
        super.onStart();
        ref.child("online").setValue(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ref.child("online").setValue(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ref.child("online").setValue(false);
        ref.child("lastSeen").setValue(ServerValue.TIMESTAMP);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_page,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
         if(item.getItemId()==R.id.logOut){
             ref.child("token").setValue(null);
             Intent intentLogOut = new Intent(MessagePage.this,Login.class);
             intentLogOut.putExtra("check",1);
             startActivity(intentLogOut);
             finish();
         }
         if(item.getItemId()==R.id.settings){
             Intent intentSettings = new Intent(MessagePage.this,Settings.class);
             startActivity(intentSettings);
         }
         if(item.getItemId()==R.id.searchUser){
             Intent intentUsers = new Intent(MessagePage.this,AllUsers.class);
             startActivity(intentUsers);
         }
         return true;
    }
}