package com.saitempsa.saitemp.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saitempsa.saitemp.GenericTextWatcher;
import com.saitempsa.saitemp.R;
import com.saitempsa.saitemp.clases.clases_adaptadores.Tipo_atencion;

import java.util.ArrayList;
import java.util.List;

public class AdapterTiposAtencion extends RecyclerView.Adapter<AdapterTiposAtencion.ViewHolder> implements View.OnClickListener, GenericTextWatcher.Filterable {

    private List<Tipo_atencion> listaCompleta;  // Lista original
    private List<Tipo_atencion> listaFiltrada;  // Lista que se mostrará después de aplicar el filtro
    private View.OnClickListener listener;

    public AdapterTiposAtencion(List<Tipo_atencion> tipos_atencion) {
        this.listaCompleta = new ArrayList<Tipo_atencion>(tipos_atencion);  // Copiamos la lista original
        this.listaFiltrada = tipos_atencion;  // Referencia a la lista filtrada
    }


    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public AdapterTiposAtencion.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tipo_atencion, parent,false);
        view.setOnClickListener(this);
        return new AdapterTiposAtencion.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTiposAtencion.ViewHolder holder, int position) {
        holder.tipo_atencion.setText(listaFiltrada.get(position).getNombre());
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public Tipo_atencion getItemAtPosition(int position) {
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
            for (Tipo_atencion tipos_atencion : listaCompleta) {
                if (tipos_atencion.getNombre().toLowerCase().contains(texto)) {
                    listaFiltrada.add(tipos_atencion);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaFiltrada.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tipo_atencion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tipo_atencion = itemView.findViewById(R.id.tv_tipo_atencion);        }
    }
}
