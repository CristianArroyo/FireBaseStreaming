package com.example.cncarroyo.firebasestreaming;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by cncarroyo on 27-02-2019.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List<Usuario> listUsuario;
    Context context;

    public MyAdapter(List<Usuario> listUsuario , Context context){

        this.listUsuario = listUsuario;
        this.context = context;

    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

        Usuario listUsu = listUsuario.get(position);




        holder.tvNombre.setText(listUsu.getNombre());
        holder.tvSubTitulo.setText(listUsu.getSubtitulo());
        holder.tvContenido.setText(listUsu.getContenido());
        holder.tvTwitter.setText(listUsu.getTwitter());
        holder.imgPersonal.setImageURI(Uri.parse(listUsu.getUrl()));

    }

    @Override
    public int getItemCount() {
        return listUsuario.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvNombre;
        public TextView tvSubTitulo;
        public TextView tvContenido;
        public TextView tvTwitter;

        public ImageView imgPersonal;

        public ViewHolder(View itemView) {
            super(itemView);


            tvNombre= itemView.findViewById(R.id.tvNombre);
            tvSubTitulo = itemView.findViewById(R.id.tvSubTitulo);
            tvContenido = itemView.findViewById(R.id.tvContenido);
            tvTwitter = itemView.findViewById(R.id.tvTwitter);

            imgPersonal = itemView.findViewById(R.id.imgPersonal);

        }
    }

}
