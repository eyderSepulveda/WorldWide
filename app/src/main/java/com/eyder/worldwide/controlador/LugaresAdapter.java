package com.eyder.worldwide.controlador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eyder.worldwide.R;
import com.eyder.worldwide.entidades.Continente;
import com.eyder.worldwide.entidades.Lugar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LugaresAdapter extends RecyclerView.Adapter<LugaresAdapter.ViewHolder> {

    private ArrayList<Lugar> lugarModelList;

    public LugaresAdapter(ArrayList<Lugar> lugarModelList) {
        this.lugarModelList = lugarModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lugares, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String name = lugarModelList.get(position).getNombre();
        String fechaVisita = lugarModelList.get(position).getFechaVisita();
        String imagenCarga = lugarModelList.get(position).getImagen();
        holder.name.setText(name);
        holder.fechaVisita.setText(fechaVisita);
        Picasso.get().load(imagenCarga).into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return lugarModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, fechaVisita;
        private ImageView imagen;


        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.nombreLugar);
            fechaVisita = (TextView) v.findViewById(R.id.resulTemp);
            imagen = v.findViewById(R.id.imgLugarmuestra);
        }
    }

}