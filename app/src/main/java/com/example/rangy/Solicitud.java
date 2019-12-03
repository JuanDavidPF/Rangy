package com.example.rangy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Solicitud extends AppCompatActivity {

    private ImageView traeloBtn;
    private ImageView recojoBtn;
    private TextView bolsaProducto;
    //objeto Database
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    String producto;
    String entrega;
    String userEmail;

    //objeto Auth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud);

        traeloBtn = findViewById(R.id.traemeloBtn);
        recojoBtn = findViewById(R.id.recojoBtn);
        bolsaProducto = findViewById(R.id.bolsaProducto);

        Intent fromSolicitud = getIntent();
        producto = getIntent().getStringExtra("Producto");

        bolsaProducto.setText(producto);

        //inicializamos el objeto RealTime database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        entrega = "traer";

        //inicializamos el objeto Auth
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
        } else {
            // No user is signed in
        }
    }//cierra el metodo oncreate


    public void goBack(View backBtn) {

        Intent toDetails = new Intent(getApplicationContext(), Detalles.class);
        startActivity(toDetails);
        finish();
    }//cierra el metod goBack


    public void changeBtn(View btn) {

        if (btn == traeloBtn) {
            traeloBtn.setImageResource(R.drawable.btnclicked);
            recojoBtn.setImageResource(R.drawable.btnunclicked);
            entrega = "traer";
        }

        if (btn == recojoBtn) {
            recojoBtn.setImageResource(R.drawable.btnclicked);
            traeloBtn.setImageResource(R.drawable.btnunclicked);
            entrega = "recoger";
        }

    }//cierra el metodo changeBtn

    public void crearSolicitud(View newSolicitud) {

        producto = producto.toLowerCase();

        if (producto.equals("macbook")) producto = "portatiles";
        if (producto.equals("tablet")) producto = "tablets";

        String currentDate = new SimpleDateFormat("dd/MM", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());


        //inicializamos el objeto RealTime database
        DatabaseReference myRef = database.getReference("/solicitudes").push();

        HashMap<String, String> datos = new HashMap();
        datos.put("nombre", "JohnTuputamadre");
        datos.put("email", userEmail);
        datos.put("entrega", entrega);
        datos.put("recurso", producto);
        datos.put("fecha", currentDate + "-" + currentTime);
        datos.put("estado", "pendiente");


        myRef.setValue(datos);
        finish();
    }


}//cierra la clase solicitud
