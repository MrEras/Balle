package com.we.alejandroalcaraz.ballet;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DriversActivity extends AppCompatActivity{
    ListView list;
    List<String> nombres = new ArrayList<String>();
    List<String> descripciones = new ArrayList<String>();
    List<String> imagenes = new ArrayList<String>();
    String[] names;
    String[] images;
    String[] descriptions;



    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drivers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        FirebaseApp.initializeApp(this);

        DatabaseReference database_reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference root_child = database_reference.child("ChoferesBien");


        root_child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    nombres.add(postSnapshot.child("nombre").getValue().toString());
                    descripciones.add(postSnapshot.child("masInfo").getValue().toString()+" Edad: "+postSnapshot.child("edad").getValue().toString());
                    imagenes.add(postSnapshot.child("link").getValue().toString());
                }

                names = new String[nombres.size()];
                names = nombres.toArray(names);
                descriptions = new String[descripciones.size()];
                descriptions = descripciones.toArray(descriptions);
                images = new String[imagenes.size()];
                images = imagenes.toArray(images);

                CustomListAdapter adapter=new CustomListAdapter(DriversActivity.this, names, images,descriptions);
                list=(ListView)findViewById(R.id.list);
                list.setAdapter(adapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String Slecteditem = names[+position];
                        showImage(images[+position]);

                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });




    }

    public void showImage(String image) {
        Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });
        ImageView imageView = new ImageView(this);
        Picasso.with(getApplicationContext()).load(image).into(imageView);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
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
