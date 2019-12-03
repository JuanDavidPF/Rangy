package com.example.rangy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    //variables

    private EditText user_input;
    private EditText pass_imput;
    private EditText repass_imput;
    private EditText name_imput;


    //objeto Auth
    private FirebaseAuth mAuth;

    //objeto Database
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent alreadyLogged = new Intent(getApplicationContext(), Reserva.class);
            alreadyLogged.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(alreadyLogged);
            finish();
        }

        //inicializamos el objeto Auth
        mAuth = FirebaseAuth.getInstance();

        //inicializamos el objeto RealTime database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        //referenciamos los views
        user_input = findViewById(R.id.userUp_et);
        pass_imput = findViewById(R.id.passUp_et);
        repass_imput = findViewById(R.id.repassUp_et);
        name_imput = findViewById(R.id.nameUp_et);

    }//onCreate


    public void signUp(View signUp_btn) {
        //obtenemos los valores
        final String user = user_input.getText().toString().trim();
        String pass = pass_imput.getText().toString().trim();
        String repass = repass_imput.getText().toString().trim();
        final String name = name_imput.getText().toString().trim();


        //verifica que los campos sean correctos

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Por favor ingrese su nombre", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(user)) {
            Toast.makeText(getApplicationContext(), "Por favor ingrese su correo electronico", Toast.LENGTH_SHORT).show();
            return;
        }

        if (user.contains("@") == false) {
            Toast.makeText(getApplicationContext(), "Por favor ingrese un correo electronico valido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pass.length() <= 5) {
            Toast.makeText(getApplicationContext(), "La contraseña debe de tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Por favor ingrese su contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(repass)) {
            Toast.makeText(getApplicationContext(), "Por favor verifique la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pass.equals(repass) == false) {
            Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }


        //crear un usuario

        mAuth.createUserWithEmailAndPassword(user, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Se ha registrado exitosamente", Toast.LENGTH_SHORT).show();

                            myRef = database.getReference("/usuarios").push();


                            HashMap<String, String> datos = new HashMap();
                            datos.put("nombre", name);
                            datos.put("email", user);

                            myRef.setValue(datos);


                            Intent toReserva = new Intent(getApplicationContext(), Reserva.class);
                            startActivity(toReserva);
                            finish();


                        } else {

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                                Toast.makeText(getApplicationContext(), "El usuario ya está registrado", Toast.LENGTH_SHORT).show();
                            }//ya existe el usuario

                            else {
                                Toast.makeText(getApplicationContext(), "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });

    }//cuerra el metodo signUp


    public void goToLogin(View logIn_Link) {

        Intent toLogin = new Intent(getApplicationContext(), Login.class);
        startActivity(toLogin);
        finish();

    }//cierra el metodo goToLogin


}//main activity


