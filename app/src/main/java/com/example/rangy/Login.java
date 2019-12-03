package com.example.rangy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    //variables
    private EditText user_input;
    private EditText pass_input;

    //objeto Auth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //inicializamos el objeto Auth
        mAuth = FirebaseAuth.getInstance();

        //referenciamos los views
        user_input = findViewById(R.id.userIn_et);
        pass_input = findViewById(R.id.passIn_et);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent alreadyLogged = new Intent(getApplicationContext(), Reserva.class);
            alreadyLogged.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(alreadyLogged);
            finish();
        }

    }//cierra el metodo onCreate


    public void logIn(View btn_Image) {

        //obtenemos los valores
        String user = user_input.getText().toString().trim();
        String pass = pass_input.getText().toString().trim();


        //verifica que los campos sean correctos
        if (TextUtils.isEmpty(user)) {
            Toast.makeText(getApplicationContext(), "Por favor ingrese su nombre de usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Por favor ingrese su contraseña", Toast.LENGTH_SHORT).show();
            return;
        }


        //loggear

        mAuth.signInWithEmailAndPassword(user, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_SHORT).show();
                            Intent toReserva = new Intent(getApplicationContext(), Reserva.class);
                            startActivity(toReserva);
                            finish();

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "No se pudo ingresar", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }
                });


    }//cierra el metodo login


    public void goToRegister(View signUp_Link) {

        Intent toRegister = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(toRegister);
        finish();

    }//cierra el metodo goToRegister


}//cierra la actividad Login
