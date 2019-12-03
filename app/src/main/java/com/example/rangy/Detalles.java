package com.example.rangy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;


public class Detalles extends AppCompatActivity implements modalnferior.clickListener {


    //objeto Database
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    private String tabletQuantity;
    private String laptopQuantity;
    private String headphoneQuantity;


    private ImageView mainTablet;
    private ImageView mainLaptop;
    private ImageView mainHeadphones;

    private ImageView midTablet;
    private ImageView midLaptop;
    private ImageView midHeadphones;

    private ImageView leftTablet;
    private ImageView leftLaptop;
    private ImageView leftHeadphones;

    private ImageView rightTablet;
    private ImageView rightLaptop;
    private ImageView rightHeadphones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        //inicializamos el objeto RealTime database
        database = FirebaseDatabase.getInstance();


        //referenciamos la imagen a cambiar
        mainTablet = findViewById(R.id.mainTablet);
        mainLaptop = findViewById(R.id.mainLaptop);
        mainHeadphones = findViewById(R.id.mainHeadphones);

        //referencia las miniaturas
        midTablet = findViewById(R.id.midTablet);
        midLaptop = findViewById(R.id.midLaptop);
        midHeadphones = findViewById(R.id.midHead);

        leftTablet = findViewById(R.id.leftTablet);
        leftLaptop = findViewById(R.id.leftLaptop);
        leftHeadphones = findViewById(R.id.leftHead);

        rightTablet = findViewById(R.id.rightTablet);
        rightLaptop = findViewById(R.id.rightLaptop);
        rightHeadphones = findViewById(R.id.rightHead);

        myRef = database.getReference();
        myRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //actualizar cantidades
                tabletQuantity = dataSnapshot.child("/recursos").child("/tablets").child("/cantidad").getValue().toString();

                laptopQuantity = dataSnapshot.child("/recursos").child("/portatiles").child("/cantidad").getValue().toString();

                headphoneQuantity = dataSnapshot.child("/recursos").child("/audifonos").child("/cantidad").getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }//cierra el metodo oncreate


    public void goBack(View backBtn) {

        Intent toReserve = new Intent(getApplicationContext(), Reserva.class);
        startActivity(toReserve);
        finish();
    }


    public void showModal(View productCard) {
        myRef = database.getReference();

        modalnferior modal = new modalnferior();
        Bundle args = new Bundle();

        if (productCard == mainTablet) {
            args.putString("producto", "Tablet");
        }

        if (productCard == mainLaptop) {

            args.putString("producto", "Macbook");
        }

        if (productCard == mainHeadphones) {

            args.putString("producto", "Audifonos");
        }

        args.putString("cantidadTablets", tabletQuantity);
        args.putString("cantidadPortatiles", laptopQuantity);
        args.putString("cantidadAudifonos", headphoneQuantity);
        modal.setArguments(args);
        modal.show(getSupportFragmentManager(), "modalProducto");

    }//cierra el metodo show modal






    public void changeImage(View imageBtn) {

        if (imageBtn == leftTablet) {
            mainTablet.setImageResource(R.drawable.tableft);
        }

        if (imageBtn == midTablet) {
            mainTablet.setImageResource(R.drawable.tabmid);
        }

        if (imageBtn == rightTablet) {
            mainTablet.setImageResource(R.drawable.tabright);
        }

        if (imageBtn == leftLaptop) {
            mainLaptop.setImageResource(R.drawable.lapleft);
        }

        if (imageBtn == midLaptop) {
            mainLaptop.setImageResource(R.drawable.lapmid);
        }

        if (imageBtn == rightLaptop) {
            mainLaptop.setImageResource(R.drawable.lapright);
        }

        if (imageBtn == leftHeadphones) {
            mainHeadphones.setImageResource(R.drawable.headleft);
        }

        if (imageBtn == midHeadphones) {
            mainHeadphones.setImageResource(R.drawable.headmid);
        }

        if (imageBtn == rightHeadphones) {
            mainHeadphones.setImageResource(R.drawable.headright);
        }


    }//cierra el metodo changeImage


    @Override
    public void modalBtnClicked(String producto) {

        int cantidad;

        switch (producto) {
            case "Tablet":
                cantidad = Integer.parseInt(tabletQuantity);

                if (cantidad <= 0) {
                    Toast.makeText(getApplicationContext(), "No hay tablets disponibles", Toast.LENGTH_SHORT).show();
                } else {

                    Intent goToSolicitud = new Intent(getApplicationContext(), Solicitud.class);
                    goToSolicitud.putExtra("Producto", producto);
                    startActivity(goToSolicitud);

                }

                break;

            case "Macbook":
                cantidad = Integer.parseInt(laptopQuantity);

                if (cantidad <= 0) {
                    Toast.makeText(getApplicationContext(), "No hay Macbooks disponibles", Toast.LENGTH_SHORT).show();
                } else {

                    Intent goToSolicitud = new Intent(getApplicationContext(), Solicitud.class);
                    goToSolicitud.putExtra("Producto", producto);
                    startActivity(goToSolicitud);

                }

                break;

            case "Audifonos":
                cantidad = Integer.parseInt(headphoneQuantity);

                if (cantidad <= 0) {
                    Toast.makeText(getApplicationContext(), "No hay audífonos disponibles", Toast.LENGTH_SHORT).show();
                } else {

                    Intent goToSolicitud = new Intent(getApplicationContext(), Solicitud.class);
                    goToSolicitud.putExtra("Producto", producto);
                    startActivity(goToSolicitud);

                }

                break;

        }//ciera el switch de producto
    }//cierra el metodo click modal
}//cierra la clase detalles
