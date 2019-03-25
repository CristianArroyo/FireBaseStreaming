package com.example.cncarroyo.firebasestreaming;

import android.Manifest;
import android.app.LauncherActivity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VerInformacionActivity extends AppCompatActivity {


    RecyclerView recyclerViewUsu;
    RecyclerView.Adapter adapter;
    List<Usuario> listUsuario;
    DatabaseReference dbFireBase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_informacion);


        FirebaseApp.initializeApp(this);
        dbFireBase = FirebaseDatabase.getInstance().getReference();

        recyclerViewUsu = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerViewUsu.setHasFixedSize(true);
        recyclerViewUsu.setLayoutManager(new LinearLayoutManager(this));

        listUsuario = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        adapter = (RecyclerView.Adapter) new MyAdapter(listUsuario, getApplicationContext());


   /*      recyclerViewUsu.setAdapter(adapter);

         database.getReference().getRoot().addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                // listUsuario.retainAll(listUsuario);

                 for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                     Usuario usu = snapshot.getValue(Usuario.class);
                     listUsuario.add(usu);

                 }

                 adapter.notifyDataSetChanged();
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         }); */


        dbFireBase.child("equipo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listUsuario.removeAll(listUsuario);
                for (final DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    dbFireBase.child("equipo").child(dataSnapshot1.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            Usuario usuario = dataSnapshot1.getValue(Usuario.class);
                            String nombre = usuario.getNombre();
                            String subtitulo = usuario.getSubtitulo();
                            String contenido = usuario.getContenido();
                            String twitter = usuario.getTwitter();

                            String url = usuario.getUrl();


                            listUsuario.add(usuario);


                            //     adapter =(RecyclerView.Adapter) new MyAdapter(listUsuario,getApplicationContext());
                            //     recyclerViewUsu.setAdapter((RecyclerView.Adapter)adapter);

                            //   Toast.makeText(getApplicationContext(),"Nombre :"+nombre+" Subtitulo :"+subtitulo+ " contenido :"+contenido+ " Twitter :"+twitter,Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    adapter = (RecyclerView.Adapter) new MyAdapter(listUsuario, getApplicationContext());
                    recyclerViewUsu.setAdapter((RecyclerView.Adapter) adapter);

                    // Revisar solo visuliza el primer registro

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}