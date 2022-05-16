package com.eyder.worldwide.controlador;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lugar, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String name = lugarModelList.get(position).getNombre();
        String fechaVisita = lugarModelList.get(position).getFechaVisita();
        String imagenCarga = lugarModelList.get(position).getImagen();
        String descripcion = lugarModelList.get(position).getDescripcion();
        String transporte = lugarModelList.get(position).getTransporte();
        holder.name.setText(name);
        holder.fechaVisita.setText(fechaVisita);
        Picasso.get().load(imagenCarga).into(holder.imagen);
        holder.descripcion.setText(descripcion);
        holder.transporte.setText(transporte);

        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (holder.hiddenView.getVisibility() == View.VISIBLE) {

                    // The transition of the hiddenView is carried out
                    //  by the TransitionManager class.
                    // Here we use an object of the AutoTransition
                    // Class to create a default transition.
                    TransitionManager.beginDelayedTransition(holder.cardView,
                            new AutoTransition());
                    holder.hiddenView.setVisibility(View.GONE);
                    holder.arrow.setImageResource(R.drawable.ic_expand_more);
                }

                // If the CardView is not expanded, set its visibility
                // to visible and change the expand more icon to expand less.
                else {

                    TransitionManager.beginDelayedTransition(holder.cardView,
                            new AutoTransition());
                    holder.hiddenView.setVisibility(View.VISIBLE);
                    holder.arrow.setImageResource(R.drawable.ic_expand_less);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return lugarModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, fechaVisita, descripcion, transporte;
        private ImageView imagen;
        private ImageButton arrow;
        private LinearLayout hiddenView;
        private CardView cardView;


        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.nombreLugar);
            fechaVisita = (TextView) v.findViewById(R.id.resulTemp);
            imagen = v.findViewById(R.id.imgLugarmuestra);
            descripcion = v.findViewById(R.id.descripcion);
            arrow = v.findViewById(R.id.showMore);
            hiddenView = v.findViewById(R.id.hidden_view);
            cardView = v.findViewById(R.id.base_cardview);
            transporte = v.findViewById(R.id.transporte);
        }
    }

}