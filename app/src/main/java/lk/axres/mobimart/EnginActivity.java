package lk.axres.mobimart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class EnginActivity extends AppCompatActivity {

    Button conti;
    String[] model = { "No Car", "BMW", "Toyota", "Hyundai", "Honda"};
    String[] car = { "No Car", "X1", "Z4", "3", "X7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engin);
        conti = findViewById(R.id.conti);

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,car);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter aaa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,model);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        spin2.setAdapter(aaa);

        conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnginActivity.this, MainActivity.class);

                startActivity(intent);
            }
        });
    }


}
