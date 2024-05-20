package com.example.carsaleproject.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carsaleproject.R;
import com.example.carsaleproject.model.Advertise;

public class AdvertiseActivity extends AppCompatActivity {
    TextView title,car_owner,car_brand,car_price,car_year,car_fuel,car_gear,car_km,phone_number;
    Button btn_goback;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_advertise);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        title = findViewById(R.id.txt_title);
        car_owner = findViewById(R.id.txt_car_owner_name);
        car_brand = findViewById(R.id.txt_car_brand);
        car_price = findViewById(R.id.txt_car_price);
        car_year = findViewById(R.id.txt_car_year);
        car_fuel = findViewById(R.id.txt_car_fuel);
        car_gear = findViewById(R.id.txt_car_gear);
        car_km = findViewById(R.id.txt_car_km);
        phone_number = findViewById(R.id.txt_car_owner_phonenumber);
        btn_goback = findViewById(R.id.btn_goback);

        Intent intent = getIntent();
        Advertise selectedAdvertise = (Advertise) intent.getSerializableExtra("advertise");
        title.setText(selectedAdvertise.getTitle());
        car_owner.setText(selectedAdvertise.getCar_owner());
        car_brand.setText(selectedAdvertise.getCar_brand());
        car_price.setText(car_price.getText() + " " + selectedAdvertise.getCar_price());
        car_year.setText(car_year.getText() + " " + selectedAdvertise.getCar_year());
        car_fuel.setText(car_fuel.getText() + " " + selectedAdvertise.getCar_fuel());
        car_gear.setText(car_gear.getText() + " " + selectedAdvertise.getCar_gear());
        car_km.setText(car_km.getText() + " " + selectedAdvertise.getCar_km());
        phone_number.setText(phone_number.getText() + " " + selectedAdvertise.getCar_price());
        //date.setText(car_price.getText() + " " + selectedAdvertise.getCar_price());


        btn_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdvertiseActivity.this,CarAdsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}