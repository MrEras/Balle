package com.we.alejandroalcaraz.ballet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;


public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView name = (TextView) findViewById(R.id.name);
        TextView phone = (TextView) findViewById(R.id.phone);
        TextView mail = (TextView) findViewById(R.id.mail);
        TextView car = (TextView) findViewById(R.id.car);

        SharedPreferences sp = getSharedPreferences("preference", ServiceActivity.MODE_PRIVATE);
        final String namepref = sp.getString("name", "");
        final String phonepref = sp.getString("phone", "");
        final String mailpref = sp.getString("email", "");
        final String carpref = sp.getString("car", "");

        name.setText(namepref);
        phone.setText(phonepref);
        mail.setText(mailpref);
        car.setText(carpref);



    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
