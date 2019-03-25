package com.example.cncarroyo.firebasestreaming;

import android.content.Intent;
import android.drm.DrmStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EnviarDatosActivity extends AppCompatActivity {

    EditText edNombre,edSubtitulo,edContenido,edUrl,edTwitter;
    Button btnEnviarDatos,btnCerrarSesion;

    DatabaseReference mRootReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_datos);

        FirebaseApp.initializeApp(this);
        mRootReference = FirebaseDatabase.getInstance().getReference();

        edNombre = (EditText) findViewById(R.id.edNombre);
        edSubtitulo = (EditText) findViewById(R.id.edSubtitulo);
        edContenido = (EditText) findViewById(R.id.edContenido);
        edUrl = (EditText) findViewById(R.id.edUrl);
        edTwitter = (EditText) findViewById(R.id.edTwitter);
        btnEnviarDatos = (Button) findViewById(R.id.btnEnviarDatos);
        btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);

        btnEnviarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = edNombre.getText().toString();
                String subtitulo = edSubtitulo.getText().toString();
                String contenido = edContenido.getText().toString();
                String url = edUrl.getText().toString();
                String twitter = edTwitter.getText().toString();

                EnviaDatosFireBase(nombre, subtitulo, contenido, url, twitter);

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

    private void EnviaDatosFireBase(String nombre, String subtitulo, String contenido, String url, String twitter) {
        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("nombre",nombre);
        objectMap.put("subtitulo",subtitulo);
        objectMap.put("contenido",contenido);
        objectMap.put("url",url);
        objectMap.put("twitter",twitter);

        mRootReference.child("equipo").push().setValue(objectMap);
    }


}
