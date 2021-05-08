package com.example.dr_auto.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dr_auto.R;
import com.example.dr_auto.db.Item;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ItemArrayAdapter extends RecyclerView.Adapter<ItemArrayAdapter.ViewHolder> {

    private final ArrayList<Item> itemList;

    public ServiceProviderListener serviceProviderListener;


    // Constructor of the class
    public ItemArrayAdapter(ArrayList<Item> itemList, ServiceProviderListener serviceProviderListener) {
        this.itemList = itemList;
        this.serviceProviderListener = serviceProviderListener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list__item, parent, false);

        return new ViewHolder(view, serviceProviderListener);

    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.grName.setText(itemList.get(position).getName());

        holder.address.setText(itemList.get(position).getStreet() + ", " + itemList.get(position).getArea() + ", " + itemList.get(position).getLandmark() + ", " + itemList.get(position).getPincode());
        holder.contact.setText(String.valueOf(itemList.get(position).getContact()));
        holder.distance.setText(itemList.get(position).getFormat() + " km");


        // holder.contact.setText((int) itemList.get(position).getContact());
     /*   holder.price.setText(itemList.get(position).getPrice());
        holder.duuration.setText(itemList.get(position).getDuration());
        holder.type.setText(itemList.get(position).getType());
*/
   /*     holder.addB.setOnClickListener((View v) -> {
            v.getContext().startActivity(new Intent(v.getContext(), service_details.class));
        });*/
    }


    @Override
    public int getItemCount() {
        int limit = 50;
        return Math.min(itemList.size(), limit);

    }


    public interface ServiceProviderListener {
        void onProviderClick(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView grName, street, area, landmark, pincode, contact, address, distance;
        Button addB;
        ServiceProviderListener providerListener;

        public ViewHolder(View itemView, ServiceProviderListener providerListener) {
            super(itemView);

            grName = itemView.findViewById(R.id.grName);
            street = itemView.findViewById(R.id.street);
            area = itemView.findViewById(R.id.area);
            landmark = itemView.findViewById(R.id.landmark);
            pincode = itemView.findViewById(R.id.pincode);
            address = itemView.findViewById(R.id.address);
            contact = itemView.findViewById(R.id.contact1);
            distance = itemView.findViewById(R.id.type);
            this.providerListener = providerListener;

            //  addB = itemView.findViewById(R.id.addB);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            providerListener.onProviderClick(getAdapterPosition());
            //   Toast.makeText(view.getContext(), grName.getText().toString()+contact.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
