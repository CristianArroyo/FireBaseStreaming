package com.example.cncarroyo.firebasestreaming;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;

public class SubirImagenFireBase extends AppCompatActivity {


    private StorageReference mstore;
    private static final int GALLERY_INTENT=1;
    private Button btnSubirFotoFirebase;
    private ImageView ImgVerFoto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_imagen_fire_base);

         EstadoConexion();

         mstore = FirebaseStorage.getInstance().getReference();

         btnSubirFotoFirebase=(Button)findViewById(R.id.btnSubirFoto);
         ImgVerFoto =(ImageView) findViewById(R.id.imageViewVerFoto);


         btnSubirFotoFirebase.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                 Intent intent = new Intent(Intent.ACTION_PICK);
                 intent.setType("image/*");
                 startActivityForResult(intent,GALLERY_INTENT);

             }
         });


    }



    @Override
    protected void onActivityResult(int requestCode ,int resultCode ,Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            Uri uri = data.getData();

            StorageReference filePath = mstore.child("fotos").child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    Toast.makeText(getApplicationContext()," foto subida exitosamente",Toast.LENGTH_SHORT).show();

                }
            });

        }

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
