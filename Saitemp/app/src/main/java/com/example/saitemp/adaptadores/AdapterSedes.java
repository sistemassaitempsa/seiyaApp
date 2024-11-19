package com.example.saitemp.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saitemp.GenericTextWatcher;
import com.example.saitemp.R;


import com.example.saitemp.clases.clases_adaptadores.Sede;

import java.util.ArrayList;
import java.util.List;

public class AdapterSedes extends RecyclerView.Adapter<AdapterSedes.ViewHolder> implements View.OnClickListener, GenericTextWatcher.Filterable {

    private List<Sede> listaCompleta;  // Lista original
    private List<Sede> listaFiltrada;  // Lista que se mostrará después de aplicar el filtro
    private View.OnClickListener listener;

    public AdapterSedes(List<Sede> sedes) {
        this.listaCompleta = new ArrayList<>(sedes);  // Copiamos la lista original
        this.listaFiltrada = sedes;  // Referencia a la lista filtrada
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public AdapterSedes.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sedes, parent,false);
        view.setOnClickListener(this);
        return new AdapterSedes.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSedes.ViewHolder holder, int position) {
        Sede sede = listaFiltrada.get(position);
        holder.sede.setText(listaFiltrada.get(position).getNombre());

    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public Sede getItemAtPosition(int position) {
        // Verifica si la lista filtrada está activa, si no, usa la lista completa
        if (listaFiltrada != null && !listaFiltrada.isEmpty()) {
            return listaFiltrada.get(position);
        } else {
            return listaCompleta.get(position);
        }
    }

    @Override
    public int getItemCount() {
        return listaFiltrada.size();  // Devuelve el tamaño de la lista filtrada
    }

    @Override
    public void filter(String texto) {
        listaFiltrada.clear();
        if (texto.isEmpty()) {
            listaFiltrada.addAll(listaCompleta);
        } else {
            texto = texto.toLowerCase();
            for (Sede sede : listaCompleta) {
                if (sede.getNombre().toLowerCase().contains(texto)) {
                    listaFiltrada.add(sede);
                }
            }
        }
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView sede;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sede = itemView.findViewById(R.id.tv_sede);        }
    }
}
