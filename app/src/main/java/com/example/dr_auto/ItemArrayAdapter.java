package com.example.dr_auto;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemArrayAdapter extends RecyclerView.Adapter<ItemArrayAdapter.ViewHolder>   {

    private ArrayList<Item> itemList;
    // Constructor of the class
    public ItemArrayAdapter( ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list__item, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.grName.setText(itemList.get(position).grName());
        holder.price.setText(itemList.get(position).getPrice());
        holder.duuration.setText(itemList.get(position).getDuuration());
        holder.type.setText(itemList.get(position).getType());
        holder.time.setText(itemList.get(position).getTime());
        holder.addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent( v.getContext(), service_details.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView grName,price,duuration,type,time;
        Button addB;
        public ViewHolder(View itemView) {
            super(itemView);

            grName = (TextView) itemView.findViewById(R.id.grName);
            price = (TextView) itemView.findViewById(R.id.price);
            duuration = (TextView) itemView.findViewById(R.id.duuration);
            type = (TextView) itemView.findViewById(R.id.type);
            time = (TextView) itemView.findViewById(R.id.time);
            addB = (Button) itemView.findViewById(R.id.addB);


        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(),grName.getText().toString(),Toast.LENGTH_SHORT).show();
        }
    }
}
