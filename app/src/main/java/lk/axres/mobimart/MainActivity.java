package lk.axres.mobimart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import lk.axres.mobimart.Adapters.ItemAdapter;
import lk.axres.mobimart.Model.Item;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<Item> list;
    ItemAdapter itemAdapter;
    FloatingActionButton cart;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("ShopingCart").setValue(null);

        list = new ArrayList<Item>();

        recyclerView = findViewById(R.id.recycler_view);
        cart = findViewById(R.id.fabButton);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Items");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Item i = dataSnapshot1.getValue(Item.class);
                    list.add(i);
                }
                itemAdapter = new ItemAdapter(MainActivity.this,list);
                recyclerView.setAdapter(itemAdapter );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                finish();
                return true;
            case R.id.activity_dashboard:
                Intent intent = new Intent(this, DashBordActivity.class);
                startActivity(intent);
                return true;
            case R.id.about:
                Intent intent2 = new Intent(this, AboutActivity.class);
                startActivity(intent2);
                return true;

            case R.id.action_bil:
                Intent intent3 = new Intent(this, BillsActivity.class);
                startActivity(intent3);
                return true;

            case R.id.action_helth:
                Intent intent4 = new Intent(this, helActivity.class);
                startActivity(intent4);
                return true;
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_MASK_ACTION);

        try {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    itemAdapter.getFilter().filter(newText);

                    return false;
                }
            });
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
