package com.we.alejandroalcaraz.ballet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LogIn extends AppCompatActivity {
    //String[] emails  = {"alex","nadal"};
    List<String> emails = new ArrayList<String>();
    List<String> passwords = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Button btn_login = (Button) findViewById(R.id.btn_login);
        TextView register = (TextView)findViewById(R.id.link_signup);
        register.setMovementMethod(LinkMovementMethod.getInstance());

        FirebaseApp.initializeApp(this);

        DatabaseReference database_reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference root_child = database_reference.child("users");

        root_child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    emails.add(postSnapshot.child("correo").getValue().toString());
                    passwords.add(postSnapshot.child("contraseña").getValue().toString());
                }


            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });
        emails.add("alex@gmail.com");
        passwords.add("1234");

        btn_login.setOnClickListener(new View.OnClickListener()
        {    public void onClick(View v)
        {
            EditText email = (EditText) findViewById(R.id.email);
            EditText password = (EditText) findViewById(R.id.password);
            int flag = 0;

            for (int i = 0;i<emails.size();i++)
            {
                if(emails.get(i).equals(email.getText().toString())){
                    if(passwords.get(i).equals(password.getText().toString())){
                        flag = 1;
                        break;
                    }
                }
            }
            if(flag == 0) {
                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecto", Toast.LENGTH_LONG).show();
            }
            if(flag == 1) {
                Intent intent = new Intent(getApplicationContext(), ServiceActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                SharedPreferences prefs = getSharedPreferences("preference", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("logged_in", 1);
                editor.commit();
        }


        }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });



    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return true;
    }



}