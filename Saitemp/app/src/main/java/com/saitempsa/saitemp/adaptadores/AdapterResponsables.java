package com.saitempsa.saitemp.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saitempsa.saitemp.GenericTextWatcher;
import com.saitempsa.saitemp.R;
import com.saitempsa.saitemp.clases.clases_adaptadores.Responsable;

import java.util.ArrayList;
import java.util.List;

public class AdapterResponsables extends RecyclerView.Adapter<AdapterResponsables.ViewHolder> implements View.OnClickListener, GenericTextWatcher.Filterable  {

    private List<Responsable> listaCompleta;  // Lista original
    private List<Responsable> listaFiltrada;  // Lista que se mostrará después de aplicar el filtro
    private View.OnClickListener listener;

    public AdapterResponsables(List<Responsable> responsables) {
        this.listaCompleta = new ArrayList<>(responsables);  // Copiamos la lista original
        this.listaFiltrada = responsables;  // Referencia a la lista filtrada
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }


    @NonNull
    @Override
    public AdapterResponsables.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.responsable, parent,false);
        view.setOnClickListener(this);
        return new AdapterResponsables.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterResponsables.ViewHolder holder, int position) {
        holder.responsable.setText(listaFiltrada.get(position).getNombre());
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return listaFiltrada.size();
    }

    public Responsable getItemAtPosition(int position) {
        // Verifica si la lista filtrada está activa, si no, usa la lista completa
        if (listaFiltrada != null && !listaFiltrada.isEmpty()) {
            return listaFiltrada.get(position);
        } else {
            return listaCompleta.get(position);
        }
    }

    @Override
    public void filter(String texto) {
        listaFiltrada.clear();
        if (texto.isEmpty()) {
            listaFiltrada.addAll(listaCompleta);
        } else {
            texto = texto.toLowerCase();
            for (Responsable responsables : listaCompleta) {
                if (responsables.getNombre().toLowerCase().contains(texto)) {
                    listaFiltrada.add(responsables);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView responsable;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            responsable = itemView.findViewById(R.id.tv_responsable);
        }
    }
}
