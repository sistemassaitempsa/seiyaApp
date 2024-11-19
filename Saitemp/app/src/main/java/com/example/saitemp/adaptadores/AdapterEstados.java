package com.example.saitemp.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saitemp.GenericTextWatcher;
import com.example.saitemp.R;
import com.example.saitemp.clases.clases_adaptadores.Estado;

import java.util.ArrayList;
import java.util.List;

public class AdapterEstados extends RecyclerView.Adapter<AdapterEstados.ViewHolder> implements View.OnClickListener, GenericTextWatcher.Filterable  {

    private List<Estado> listaCompleta;  // Lista original
    private List<Estado> listaFiltrada;  // Lista que se mostrará después de aplicar el filtro
    private View.OnClickListener listener;

    public AdapterEstados(List<Estado> estados) {
        this.listaCompleta = new ArrayList<Estado>(estados);  // Copiamos la lista original
        this.listaFiltrada = estados;  // Referencia a la lista filtrada
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public AdapterEstados.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.estados, parent,false);
       view.setOnClickListener(this);
        return new AdapterEstados.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEstados.ViewHolder holder, int position) {
    holder.estado.setText(listaFiltrada.get(position).getNombre());
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return listaFiltrada.size();
    }

    public Estado getItemAtPosition(int position) {
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
            for (Estado estados : listaCompleta) {
                if (estados.getNombre().toLowerCase().contains(texto)) {
                    listaFiltrada.add(estados);
                }
            }
        }
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView estado;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            estado = itemView.findViewById(R.id.tv_estado);
        }
    }
}
