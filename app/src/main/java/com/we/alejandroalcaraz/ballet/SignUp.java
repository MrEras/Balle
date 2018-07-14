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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView register = (TextView)findViewById(R.id.link_login);
        Button btnSignUp = (Button) findViewById(R.id.btnSignUp);
        final EditText name = (EditText) findViewById(R.id.name);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText password = (EditText) findViewById(R.id.password);
        final EditText car_description = (EditText) findViewById(R.id.car_description);
        final EditText phone = (EditText) findViewById(R.id.phone);


        register.setMovementMethod(LinkMovementMethod.getInstance());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatabaseReference database_reference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference root_child = database_reference.child("users").push();
                root_child.child("admin").setValue(false);
                root_child.child("contraseña").setValue(password.getText().toString());
                root_child.child("correo").setValue(email.getText().toString());
                root_child.child("marcaColor").setValue(car_description.getText().toString());
                root_child.child("nombre").setValue(name.getText().toString());
                root_child.child("numero").setValue(phone.getText().toString());

                SharedPreferences prefs = getSharedPreferences("preference", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("name", name.getText().toString());
                editor.putString("phone", phone.getText().toString());
                editor.putString("email", email.getText().toString());
                editor.putString("car", car_description.getText().toString());
                editor.commit();

                Intent intent = new Intent(SignUp.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });


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
