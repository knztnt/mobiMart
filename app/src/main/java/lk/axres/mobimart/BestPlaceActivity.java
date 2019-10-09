package lk.axres.mobimart;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import lk.axres.mobimart.Adapters.CartAdapter;
import lk.axres.mobimart.Adapters.ItemAdapter;
import lk.axres.mobimart.Adapters.ShopAdapter;
import lk.axres.mobimart.Model.Item;
import lk.axres.mobimart.Model.Shop;

public class BestPlaceActivity extends AppCompatActivity {
    RecyclerView recyclerView,recyclerView2;
    ArrayList<Item> list2;
    ArrayList<Shop> list3;
    TextView textmincost;
    CartAdapter cartAdapter;
    ShopAdapter shopAdapter;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mdatabase2 = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_place);

        list3 = new ArrayList<Shop>();

        recyclerView2 = findViewById(R.id.recycler_view2);

        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        mdatabase2 = FirebaseDatabase.getInstance().getReference().child("Shops");


        mdatabase2.orderByChild("bamount").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){
                    Shop i = dataSnapshot2.getValue(Shop.class);
                    list3.add(i);
                }

                //textmincost.setText("Your minimum total cost is Rs."+Double.toString(minCost));

                shopAdapter = new ShopAdapter(BestPlaceActivity.this,list3);
                recyclerView2.setAdapter(shopAdapter );
                shopAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
