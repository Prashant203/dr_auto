package com.example.dr_auto.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dr_auto.R;
import com.example.dr_auto.db.Item;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ItemArrayAdapter extends RecyclerView.Adapter<ItemArrayAdapter.ViewHolder> implements Filterable {

    private final ArrayList<Item> itemList;
    private final ArrayList<Item> itemListfull;
    private final Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Item> filterList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(itemListfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Item item : itemListfull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filterList.add(item);
                    }

                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            itemList.clear();
            itemList.addAll((ArrayList) results.values);
            notifyDataSetChanged();

        }
    };
    public ServiceProviderListener serviceProviderListener;


    // Constructor of the class
    public ItemArrayAdapter(ArrayList<Item> list, ArrayList<Item> itemList, ServiceProviderListener serviceProviderListener) {
        this.itemList = itemList;
        itemListfull = new ArrayList<>(itemList);
        this.serviceProviderListener = serviceProviderListener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list__item, parent, false);

        return new ViewHolder(view, serviceProviderListener);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.grName.setText(itemList.get(position).getName());

        holder.address.setText(itemList.get(position).getStreet() + ", " + itemList.get(position).getArea() + ", " + itemList.get(position).getLandmark() + ", " + itemList.get(position).getPincode());
        holder.contact.setText(String.valueOf(itemList.get(position).getContact()));


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
    public Filter getFilter() {
        return itemFilter;
    }

    public interface ServiceProviderListener {
        void onProviderClick(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView grName, street, area, landmark, pincode, contact, address;
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
