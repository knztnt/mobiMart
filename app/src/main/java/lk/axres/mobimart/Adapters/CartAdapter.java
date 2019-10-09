package lk.axres.mobimart.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import lk.axres.mobimart.CartActivity;
import lk.axres.mobimart.MainActivity;
import lk.axres.mobimart.Model.Item;
import lk.axres.mobimart.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>  implements Filterable {

    private Context mContext;
    private List<Item> mItem;
    private List<Item> mItemFull;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference();

    public CartAdapter(Context mContext, List<Item> mItem) {
        this.mContext = mContext;
        this.mItem = mItem;
        mItemFull = new ArrayList<>(mItem);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cart, parent,false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Item item = mItem.get(position);
        holder.itemname.setText(item.getName());
        holder.lowest.setText(item.getLowest());
        holder.unit.setText(item.getUnit());
        holder.itemcount.setText(Integer.toString(item.getAmount()));
        Picasso.get().load(item.getProfilePic()).into(holder.itempic);

        holder.bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdatabase.child("Users").child(user.getUid()).child("ShopingCart").child(item.getId()).setValue(null);
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
        public ImageButton bin;
        public TextView lowest;
        public TextView unit;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemname = itemView.findViewById(R.id.itemname);
            itemcount = itemView.findViewById(R.id.itemcount);
            itempic = itemView.findViewById(R.id.itempic);
            bin = itemView.findViewById(R.id.bin);
            lowest = itemView.findViewById(R.id.starting);
            unit = itemView.findViewById(R.id.unit);

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
