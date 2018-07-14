package com.we.alejandroalcaraz.ballet;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] names;
    private final String[] descriptions;
    private final String[] images;

    public CustomListAdapter(Activity context, String[] names, String[] images, String[] descriptions) {
        super(context, R.layout.drivers_list, names);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.names = names;
        this.images = images;
        this.descriptions = descriptions;
    }


    public View getView(int position,View view,ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.drivers_list, null,true);

        TextView drivers_name = (TextView) rowView.findViewById(R.id.drivers_name);
        ImageView drivers_image = (ImageView) rowView.findViewById(R.id.drivers_image);
        TextView drivers_description = (TextView) rowView.findViewById(R.id.drivers_description);

        drivers_name.setText(names[position]);
        Picasso
                .with(context)
                .load(images[position])
                .fit() // will explain later
                .into((ImageView) drivers_image);
        drivers_description.setText(descriptions[position]);
        return rowView;

    };
}