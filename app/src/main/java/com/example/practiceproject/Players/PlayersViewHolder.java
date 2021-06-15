package com.example.practiceproject.Players;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceproject.R;

public class PlayersViewHolder extends RecyclerView.ViewHolder {
    ImageView playersImageView;
    TextView playersName;
    TextView playersJerseyNumber;
    TextView playersAge;
    TextView playersCategory;

    public PlayersViewHolder(@NonNull View itemView) {
        super(itemView);
        playersImageView = itemView.findViewById(R.id.player_image);
        playersName = itemView.findViewById(R.id.player_name);
        playersJerseyNumber = itemView.findViewById(R.id.jersey_number);
        playersAge = itemView.findViewById(R.id.player_age);
        playersCategory = itemView.findViewById(R.id.player_category);
    }
}
