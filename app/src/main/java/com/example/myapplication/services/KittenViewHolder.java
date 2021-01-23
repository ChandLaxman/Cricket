package com.example.myapplication.services;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

/**
 * ViewHolder for kitten cells in our grid
 *
 * @author bherbst
 */
public class KittenViewHolder extends RecyclerView.ViewHolder {
    public TextView team_name,matches,won,lost;
    public  ImageView edit,delete;
    public  ImageView player_image;
    public KittenViewHolder(View itemView) {
        super(itemView);

        team_name = (TextView) itemView.findViewById(R.id.team_name);


       edit = (ImageView) itemView.findViewById(R.id.edit);
      delete = (ImageView) itemView.findViewById(R.id.delete);
   player_image = (ImageView) itemView.findViewById(R.id.player_image);



    }
}
