package com.example.mmaperfomancetracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.mmaperfomancetracker.R;
import com.example.mmaperfomancetracker.db.tables.Sport;

import java.util.ArrayList;

public class SportAdapter extends ArrayAdapter<Sport> {

    public SportAdapter(Context context, ArrayList<Sport> sports){
        super(context,0, sports);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Sport sport=getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.stats_adapter_view_layout, parent,false);
        }

        com.google.android.material.textview.MaterialTextView sportId,sport1,sport2;

        sportId= convertView.findViewById(R.id.textView1);
        sport1= convertView.findViewById(R.id.textView2);
        sport2= convertView.findViewById(R.id.textView3);

        sportId.setText((String.valueOf(sport.sportId)));
        sport1.setText(sport.sportName);
        sport2.setText(sport.sportName);

        return convertView;

    }

}
