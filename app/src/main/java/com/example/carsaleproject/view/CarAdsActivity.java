package com.example.carsaleproject.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carsaleproject.R;
import com.example.carsaleproject.adapter.AdvertiseAdapter;
import com.example.carsaleproject.model.Advertise;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class CarAdsActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Advertise> advertiseArrayList;
    RecyclerView recyclerView;
    AdvertiseAdapter advertiseAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_car_ads);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);

        advertiseArrayList = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        
        getData();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        advertiseAdapter = new AdvertiseAdapter(advertiseArrayList,this);
        recyclerView.setAdapter(advertiseAdapter);

    }

    private void getData() {
        firebaseFirestore.collection("Ads").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(CarAdsActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

                if(value != null){

                    for (DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String,Object> data = snapshot.getData();

                        String title = (String) data.get("title");
                        String car_owner = (String) data.get("car_owner");
                        String car_brand = (String) data.get("car_brand");
                        String car_price = (String) data.get("car_price");
                        String car_year = (String) data.get("car_year");
                        String car_fuel = (String) data.get("car_fuel");
                        String car_gear = (String) data.get("car_gear");
                        String car_km = (String) data.get("car_km");
                        String phone_number = (String) data.get("phone_number");

                        Advertise advertise = new Advertise(title,car_owner,car_brand,car_price,car_year,car_fuel,car_gear,car_km,phone_number);
                        //Advertise advertise = new Advertise(title,car_brand,car_price);
                        System.out.println(car_brand);
                    }

                   advertiseAdapter.notifyDataSetChanged();

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.give_add){
            Intent intentToGiveAdd = new Intent(CarAdsActivity.this,GiveAdvertiseActivity.class);
            startActivity(intentToGiveAdd);
        } else if(item.getItemId() == R.id.signout){

            auth.signOut();

            Intent intentToSignOut = new Intent(CarAdsActivity.this,MainActivity.class);
            startActivity(intentToSignOut);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}