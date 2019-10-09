package lk.axres.mobimart.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import lk.axres.mobimart.Model.Item;
import lk.axres.mobimart.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>  implements Filterable {

        private Context mContext;
        private List<Item> mItem;
        private List<Item> mItemFull;

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference();

        public ItemAdapter(Context mContext, List<Item> mItem) {
            this.mContext = mContext;
            this.mItem = mItem;
            mItemFull = new ArrayList<>(mItem);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.item_item, parent,false);
            return new ItemAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

            final Item item = mItem.get(position);
            holder.itemname.setText(item.getName());
            holder.unit.setText(item.getUnit());
            holder.lowest.setText("Lowest price: "+item.getLowest());
            Picasso.get().load(item.getProfilePic()).into(holder.itempic);
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.itemcount.setText(Integer.toString(Integer.parseInt(holder.itemcount.getText().toString())+1));
                }
            });

            holder.drop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.parseInt(holder.itemcount.getText().toString())>0){
                        holder.itemcount.setText(Integer.toString(Integer.parseInt(holder.itemcount.getText().toString()) - 1));
                    }
                }
            });

            holder.addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Item sItem = new Item(item.getId(),item.getUnit(),item.getName(),item.getProfilePic(),Integer.parseInt(holder.itemcount.getText().toString()));

                    mdatabase.child("Users").child(user.getUid()).child("ShopingCart").child(item.getId()).setValue(sItem);

                }
            });


        }

        @Override
        public int getItemCount() {
            return mItem.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            public TextView itemname;
            public TextView itemcount;
            public ImageView itempic;
            public ImageView add;
            public ImageView drop;
            public TextView unit;
            public TextView lowest;
            public Button addtocart;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                itemname = itemView.findViewById(R.id.itemname);
                itemcount = itemView.findViewById(R.id.itemcount);
                itempic = itemView.findViewById(R.id.itempic);
                add = itemView.findViewById(R.id.plus);
                unit = itemView.findViewById(R.id.unit);
                lowest = itemView.findViewById(R.id.starting);
                drop = itemView.findViewById(R.id.minus);
                addtocart = itemView.findViewById(R.id.addtocart);

            }
        }

        @Override
        public Filter getFilter() {
            return mFilter;
        }

        private Filter mFilter   = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Item> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(mItemFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Item item : mItemFull) {
                        if (item.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mItem.clear();
                mItem.addAll((List<Item>) results.values);
                notifyDataSetChanged();
            }
        };
}
