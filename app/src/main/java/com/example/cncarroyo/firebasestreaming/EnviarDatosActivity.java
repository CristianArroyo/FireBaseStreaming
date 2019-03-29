package com.example.cncarroyo.firebasestreaming;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EnviarDatosActivity extends AppCompatActivity {

    EditText edNombre,edApellido,edEmail,edTelefono,edAsunto;
    Button btnEnviarDatos,btnCerrarSesion;

    DatabaseReference mRootReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_datos);

        EstadoConexion();

        FirebaseApp.initializeApp(this);
        mRootReference = FirebaseDatabase.getInstance().getReference();

        edNombre = (EditText) findViewById(R.id.edNombre);
        edApellido = (EditText) findViewById(R.id.edApellido);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edTelefono = (EditText) findViewById(R.id.edTelefono);
        edAsunto = (EditText) findViewById(R.id.edAsunto);
        btnEnviarDatos = (Button) findViewById(R.id.btnEnviarDatos);
        btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);

        btnEnviarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    String nombre = edNombre.getText().toString();

                    String apellido = edApellido.getText().toString();

                    String e_mail = edEmail.getText().toString();

                    String telefono = edTelefono.getText().toString();

                    String asunto = edAsunto.getText().toString();

                    EnviaDatosFireBase(nombre, apellido, e_mail, telefono, asunto);

                    Toast.makeText(getApplicationContext(),"Datos ingresados correctamente",Toast.LENGTH_SHORT).show();

                }catch (Exception e){

                    Toast.makeText(EnviarDatosActivity.this, "Error al ingresar datos", Toast.LENGTH_SHORT).show();

                }

            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(EnviarDatosActivity.this,MenuPrincipalActivity.class);
                startActivity(intent);

            }
        });

    }

    private void EnviaDatosFireBase(String nombre, String apellido, String e_mail, String telefono, String asunto) {
        Map<String,Object> objectMap = new HashMap<>();

        objectMap.put("nombre",nombre);
        objectMap.put("apellido",apellido);
        objectMap.put("e_mail",e_mail);
        objectMap.put("telefono",telefono);
        objectMap.put("asunto",asunto);

        mRootReference.child("usuario").push().setValue(objectMap);


    }


    public void EstadoConexion(){

        ConnectivityManager connectivityManager =(ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();

        if (networkInfo!=null && networkInfo.isConnected()){

          //  Toast.makeText(getApplicationContext(),"Conectado a red de Internet",Toast.LENGTH_SHORT).show();

        }else {

            Toast.makeText(getApplicationContext(), "No se encuentra conectado a Internet", Toast.LENGTH_SHORT).show();
        }


    }


}
