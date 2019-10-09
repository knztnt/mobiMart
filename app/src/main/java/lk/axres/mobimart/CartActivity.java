package lk.axres.mobimart;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

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

public class CartActivity extends AppCompatActivity {
    public RecyclerView recyclerView2;
    ArrayList<Item> list2;
    Button button;
    public CartAdapter cartAdapter;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mdatabase2 = FirebaseDatabase.getInstance().getReference();


    public static CartActivity instance;

    public static CartActivity Instance() {
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        list2 = new ArrayList<Item>();

        recyclerView2 = findViewById(R.id.recycler_view);
        button = findViewById(R.id.findets);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("ShopingCart");
        mdatabase2 = FirebaseDatabase.getInstance().getReference().child("Shops");



        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list2.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Item i = dataSnapshot1.getValue(Item.class);
                    list2.add(i);
                }
                cartAdapter = new CartAdapter(CartActivity.this, list2);
                recyclerView2.setAdapter(cartAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mdatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    double totalcost = 0;

                    for(Item i : list2){

                        String cost = dataSnapshot1.child("items").child(i.getId()).getValue().toString();

                        totalcost = totalcost+(i.getAmount())*Double.valueOf(cost);

                    }
                    FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("shopTotal").child(dataSnapshot1.getKey()).setValue(totalcost);
                    FirebaseDatabase.getInstance().getReference().child("Shops").child(dataSnapshot1.getKey()).child("bamount").setValue(totalcost);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, BestPlaceActivity.class);

                startActivity(intent);
            }
        });
    }
}
