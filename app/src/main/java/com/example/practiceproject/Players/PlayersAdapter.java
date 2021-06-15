package com.example.practiceproject.Players;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceproject.InternetConnection.AppExecutor;
import com.example.practiceproject.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersViewHolder> {
    Context context;
    List<Players> playersList = new ArrayList<>();

    boolean color = true;
    String linkColorValue = null;


    public PlayersAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PlayersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlayersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_players, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayersViewHolder holder, int position) {
        Picasso.get().load(playersList.get(position).getPlayerImageUrl()).resize(80, 80).centerCrop().into(holder.playersImageView);
        holder.playersName.setText(playersList.get(position).getPlayerName());
        if (color) {
            holder.playersName.setTextColor(Color.argb(255, 255, 0,0));
        }
        holder.playersJerseyNumber.setText(playersList.get(position).getPlayerJerseyNumber());
        holder.playersAge.setText(playersList.get(position).getPlayerAge());
        holder.playersCategory.setText(playersList.get(position).getPlayerCategory());
        holder.itemView.setTag(playersList.get(position).getPlayerJerseyNumber());
    }

    @Override
    public int getItemCount() {
        if (!playersList.isEmpty()){
            return playersList.size();
        } else {
            return 0;
        }
    }

    public List<Players> getPlayersList() { return playersList; }

    public void setPlayersList(List<Players> playersList) { this.playersList = playersList; }

    public void setColor(boolean color) {
        this.color = color;
    }
}
