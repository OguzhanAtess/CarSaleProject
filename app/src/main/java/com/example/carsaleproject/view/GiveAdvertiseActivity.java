package com.example.carsaleproject.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carsaleproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class GiveAdvertiseActivity extends AppCompatActivity {
    private FirebaseStorage firebaseStorage;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    Uri imageData;
    ImageView imageView;
    Button btn_giveAdvertise,btn_goback;
    EditText car_owner, car_brand,car_price,car_year,car_fuel,car_gear,car_km,phone_number,advertise_title;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_give_advertise);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imageView = findViewById(R.id.select_car_picture);
        btn_giveAdvertise = findViewById(R.id.btn_give_advertise);
        car_owner = findViewById(R.id.car_owner_name);
        car_brand = findViewById(R.id.car_brand);
        car_price = findViewById(R.id.car_price);
        car_year = findViewById(R.id.car_year);
        car_fuel = findViewById(R.id.car_fuel);
        car_gear = findViewById(R.id.car_gear);
        car_km = findViewById(R.id.car_km);
        phone_number = findViewById(R.id.phone_number);
        advertise_title = findViewById(R.id.advertise_title);
        btn_goback = findViewById(R.id.btn_goback);


        registerLauncher();

        firebaseStorage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = firebaseStorage.getReference();


        btn_giveAdvertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(imageData != null){
                    storageReference.child("gs://carsaleproject-1a5cc.appspot.com/images/image.png").putFile(imageData).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(GiveAdvertiseActivity.this, "Fotoğraf yüklendi.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(GiveAdvertiseActivity.this, "Fotoğraf yüklenemedi", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }*/
                if(!isEmptyFields()){
                    giveAdvertiseInfo();
                }

        }
        });

        btn_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GiveAdvertiseActivity.this,CarAdsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean isEmptyFields() {
        String title = advertise_title.getText().toString();
        String owner = car_owner.getText().toString();
        String brand = car_brand.getText().toString();
        String price = car_price.getText().toString();
        String year = car_year.getText().toString();
        String fuel = car_fuel.getText().toString();
        String gear = car_gear.getText().toString();
        String kilometer = car_km.getText().toString();
        String phoneNumber = phone_number.getText().toString();
        if(TextUtils.isEmpty(title)){
            advertise_title.setError(getString(R.string.title_empty));
            advertise_title.requestFocus();
            return true;
        } else if(TextUtils.isEmpty(owner)){
            car_owner.setError(getString(R.string.advertiser_empty));
            car_owner.requestFocus();
            return true;
        } else if(TextUtils.isEmpty(brand)){
            car_brand.setError(getString(R.string.car_brand_empty));
            car_brand.requestFocus();
            return true;
        } else if(TextUtils.isEmpty(price)){
            car_price.setError(getString(R.string.car_price_empty));
            car_price.requestFocus();
            return true;
        } else if(TextUtils.isEmpty(year)){
            car_year.setError(getString(R.string.car_year_empty));
            car_year.requestFocus();
            return true;
        } else if(TextUtils.isEmpty(fuel)){
            car_fuel.setError(getString(R.string.car_fuel_empty));
            car_fuel.requestFocus();
            return true;
        } else if(TextUtils.isEmpty(gear)){
            car_gear.setError(getString(R.string.car_gear_empty));
            car_gear.requestFocus();
            return true;
        } else if(TextUtils.isEmpty(kilometer)){
            car_km.setError(getString(R.string.kilometer_empty));
            car_km.requestFocus();
            return true;
        } else if(TextUtils.isEmpty(phoneNumber)){
            phone_number.setError(getString(R.string.phone_number_empty));
            phone_number.requestFocus();
            return true;
        } else {
            return false;
        }
    }

    private void giveAdvertiseInfo(){
        String title = advertise_title.getText().toString();
        String carOwner = car_owner.getText().toString();
        String carBrand = car_brand.getText().toString();
        String carPrice = car_price.getText().toString();
        String carYear = car_year.getText().toString();
        String carFuel = car_fuel.getText().toString();
        String carGear = car_gear.getText().toString();
        String carKm = car_km.getText().toString();
        String phoneNumber = phone_number.getText().toString();

        HashMap<String,Object> advertiseInfo = new HashMap<>();
        advertiseInfo.put("title",title);
        advertiseInfo.put("car_owner",carOwner);
        advertiseInfo.put("car_brand",carBrand);
        advertiseInfo.put("car_price",carPrice);
        advertiseInfo.put("car_year",carYear);
        advertiseInfo.put("car_fuel",carFuel);
        advertiseInfo.put("car_gear",carGear);
        advertiseInfo.put("car_km",carKm);
        advertiseInfo.put("phone_number",phoneNumber);
        advertiseInfo.put("date", FieldValue.serverTimestamp());

        firebaseFirestore.collection("Ads").add(advertiseInfo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Intent intent = new Intent(GiveAdvertiseActivity.this,CarAdsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GiveAdvertiseActivity.this, "İlan Verilemedi!", Toast.LENGTH_LONG).show();
            }
        });
    }

    /*public void giveAdvertiseClicked(View view){
        if(imageData != null){
            storageReference.child("images/image.jpg").putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(GiveAdvertiseActivity.this, "İlan Yüklendi.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(GiveAdvertiseActivity.this, "İlan Verilemedi.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }*/

    public void selectImage(View view){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_MEDIA_IMAGES)){
                Snackbar.make(view,"Bu uygulama galerinize erişmek istiyor.",Snackbar.LENGTH_INDEFINITE).setAction("İzin Ver", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //ask permission
                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
                    }
                }).show();
            } else {
                //ask permission
                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
            }
        } else {
            Intent intentToGalery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intentToGalery);
        }
    }

    private void registerLauncher(){
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                    Intent intentFromResult = result.getData();
                    if(intentFromResult != null){
                        imageData = intentFromResult.getData();
                        imageView.setImageURI(imageData);
                    }
                }
            }
        });

        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){
                    Intent intentGalery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intentGalery);
                } else {
                    Toast.makeText(GiveAdvertiseActivity.this, "İzin Gerekli!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}