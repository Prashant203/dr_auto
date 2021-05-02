package com.example.dr_auto.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dr_auto.R;
import com.example.dr_auto.db.Item;
import com.example.dr_auto.service_details;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ItemArrayAdapter extends RecyclerView.Adapter<ItemArrayAdapter.ViewHolder> {

    private final ArrayList<Item> itemList;

    // Constructor of the class
    public ItemArrayAdapter(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list__item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.grName.setText(itemList.get(position).getGrName());
        holder.address.setText(itemList.get(position).getAddress());
     /*   holder.price.setText(itemList.get(position).getPrice());
        holder.duuration.setText(itemList.get(position).getDuration());
        holder.type.setText(itemList.get(position).getType());
*/
        holder.addB.setOnClickListener(v -> v.getContext().startActivity(new Intent(v.getContext(), service_details.class)));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView grName, price, duuration, type, address;
        Button addB;

        public ViewHolder(View itemView) {
            super(itemView);

            grName = itemView.findViewById(R.id.grName);
            price = itemView.findViewById(R.id.price);
            duuration = itemView.findViewById(R.id.duuration);
            type = itemView.findViewById(R.id.type);
            address = itemView.findViewById(R.id.grAddress);
            addB = itemView.findViewById(R.id.addB);


        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), grName.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
