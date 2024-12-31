package com.saitempsa.saitemp.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saitempsa.saitemp.GenericTextWatcher;
import com.saitempsa.saitemp.R;
import com.saitempsa.saitemp.clases.clases_adaptadores.Visitante;

import java.util.ArrayList;
import java.util.List;

public class AdapterVisitante extends RecyclerView.Adapter<AdapterVisitante.ViewHolder> implements View.OnClickListener, GenericTextWatcher.Filterable{
    private ArrayList<Visitante> listaCompleta;  // Lista original
    private List<Visitante> listaFiltrada;  // Lista que se mostrará después de aplicar el filtro
    private View.OnClickListener listener;

    public AdapterVisitante(List<Visitante> visitantes) {
        this.listaCompleta = new ArrayList<Visitante>(visitantes);  // Copiamos la lista original
        this.listaFiltrada = visitantes;  // Referencia a la lista filtrada
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public AdapterVisitante.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visitantes, parent,false);
        view.setOnClickListener(this);
        return new AdapterVisitante.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVisitante.ViewHolder holder, int position) {
        holder.visitante.setText(listaFiltrada.get(position).getNombre());
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return listaFiltrada.size();
    }

    public Visitante getItemAtPosition(int position) {
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
            for (Visitante estados : listaCompleta) {
                if (estados.getNombre().toLowerCase().contains(texto)) {
                    listaFiltrada.add(estados);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView visitante;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            visitante = itemView.findViewById(R.id.tv_visitante);
        }
    }
}
