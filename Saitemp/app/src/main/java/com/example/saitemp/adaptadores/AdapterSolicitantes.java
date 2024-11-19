package com.example.saitemp.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saitemp.GenericTextWatcher;
import com.example.saitemp.R;
import com.example.saitemp.clases.clases_adaptadores.Solicitante;

import java.util.ArrayList;
import java.util.List;

public class AdapterSolicitantes extends RecyclerView.Adapter<AdapterSolicitantes.ViewHolder> implements View.OnClickListener, GenericTextWatcher.Filterable {
    private List<Solicitante> listaCompleta;  // Lista original
    private List<Solicitante> listaFiltrada;  // Lista que se mostrará después de aplicar el filtro
    private View.OnClickListener listener;

    public AdapterSolicitantes(List<Solicitante> solicitantes) {
        this.listaCompleta = new ArrayList<Solicitante>(solicitantes);  // Copiamos la lista original
        this.listaFiltrada = solicitantes;  // Referencia a la lista filtrada
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public AdapterSolicitantes.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.solicitante, parent,false);
        view.setOnClickListener(this);
        return new AdapterSolicitantes.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSolicitantes.ViewHolder holder, int position) {
        holder.solicitante.setText(listaFiltrada.get(position).getNombre());
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public Solicitante getItemAtPosition(int position) {
        // Verifica si la lista filtrada está activa, si no, usa la lista completa
        if (listaFiltrada != null && !listaFiltrada.isEmpty()) {
            return listaFiltrada.get(position);
        } else {
            return listaCompleta.get(position);
        }
    }
    @Override
    public int getItemCount() {
        return listaFiltrada.size();
    }

    @Override
    public void filter(String texto) {
        listaFiltrada.clear();
        if (texto.isEmpty()) {
            listaFiltrada.addAll(listaCompleta);
        } else {
            texto = texto.toLowerCase();
            for (Solicitante sede : listaCompleta) {
                if (sede.getNombre().toLowerCase().contains(texto)) {
                    listaFiltrada.add(sede);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView solicitante;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            solicitante = itemView.findViewById(R.id.tv_solicitante);        }
    }
}
