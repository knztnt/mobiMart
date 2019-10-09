package lk.axres.mobimart.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import lk.axres.mobimart.CartActivity;
import lk.axres.mobimart.MainActivity;
import lk.axres.mobimart.MapsActivity;
import lk.axres.mobimart.Model.Item;
import lk.axres.mobimart.Model.Shop;
import lk.axres.mobimart.R;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder>  implements Filterable {

    private Context mContext;
    private List<Shop> mShop;
    private List<Shop> mShopFull;
    private static DecimalFormat df = new DecimalFormat("0.00");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference();

    public ShopAdapter(Context mContext, List<Shop> mShop) {
        this.mContext = mContext;
        this.mShop = mShop;
        mShopFull = new ArrayList<>(mShop);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_shop, parent,false);
        return new ShopAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final String total;
        final Shop shop = mShop.get(position);

        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              Double data = dataSnapshot.child("Users").child(user.getUid()).child("shopTotal").child("shop"+shop.getId()).getValue(Double.class);
              holder.itemcount.setText("Rs."+data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.itemname.setText(shop.getName());

        holder.dist.setText(shop.getDist()+"KM (Rs."+df.format(Double.valueOf(shop.getDist())*50)+")");
        Picasso.get().load(shop.getDp()).into(holder.itempic);

        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MapsActivity.class);

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mShop.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView itemname;
        public TextView itemcount;
        public TextView dist;
        public ImageView itempic;
        public Button buy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemname = itemView.findViewById(R.id.itemname);
            itemcount = itemView.findViewById(R.id.itemcount);
            itempic = itemView.findViewById(R.id.itempic);
            dist = itemView.findViewById(R.id.dist);
            buy = itemView.findViewById(R.id.buy);

        }
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter   = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Shop> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mShop);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Shop shop : mShopFull) {
                    if (shop.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(shop);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mShop.clear();
            mShop.addAll((List<Shop>) results.values);
            notifyDataSetChanged();
        }
    };
}
