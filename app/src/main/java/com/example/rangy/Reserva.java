package com.example.rangy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Reserva extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    //objeto Auth
    private FirebaseAuth mAuth;


    //objeto Database
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    //cantidad de los recursos
    private TextView tabletAmount;
    private TextView laptopAmount;
    private TextView headphonesAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);
        Toolbar barraNav = findViewById(R.id.Reserva_Toolbar);
        setSupportActionBar(barraNav);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //inicializamos el objeto Auth
        mAuth = FirebaseAuth.getInstance();

        //inicializamos el objeto RealTime database
        database = FirebaseDatabase.getInstance();


        //referenciamos las cantidades

        tabletAmount = findViewById(R.id.tabletQuantity);
        laptopAmount = findViewById(R.id.laptopQuantity);
        headphonesAmount = findViewById(R.id.headphonesQuantity);

        myRef = database.getReference();
        myRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //actualizar cantidades
                String cantidadTablets = dataSnapshot.child("/recursos").child("/tablets").child("/cantidad").getValue().toString();
                tabletAmount.setText(cantidadTablets);

                String cantidadPortatiles = dataSnapshot.child("/recursos").child("/portatiles").child("/cantidad").getValue().toString();
                laptopAmount.setText(cantidadPortatiles);

                String cantidadAudifonos = dataSnapshot.child("/recursos").child("/audifonos").child("/cantidad").getValue().toString();
                headphonesAmount.setText(cantidadAudifonos);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

    public void signOut(View logOut_btn) {

        try {
            mAuth.signOut();
            Toast.makeText(getApplicationContext(), "Has cerrado sesión exitosamente", Toast.LENGTH_SHORT).show();
            Intent loggedOut = new Intent(getApplicationContext(), Login.class);
            startActivity(loggedOut);
            finish();
        } catch (Exception e) {
            Log.e(TAG, "onClick: Exception " + e.getMessage(), e);
        }

    }//cierra el metodo signOut

    public void goToDetails(View view) {

        Intent toDetails = new Intent(getApplicationContext(), Detalles.class);
        startActivity(toDetails);

    }


}//cierra la actividad reserva
