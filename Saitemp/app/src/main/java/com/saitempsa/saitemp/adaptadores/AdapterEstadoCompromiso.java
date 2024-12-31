package com.saitempsa.saitemp.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saitempsa.saitemp.GenericTextWatcher;
import com.saitempsa.saitemp.R;
import com.saitempsa.saitemp.clases.clases_adaptadores.EstadoCompromiso;

import java.util.ArrayList;
import java.util.List;

public class AdapterEstadoCompromiso extends RecyclerView.Adapter<AdapterEstadoCompromiso.ViewHolder> implements View.OnClickListener, GenericTextWatcher.Filterable {
    private List<EstadoCompromiso> listaCompleta;  // Lista original
    private List<EstadoCompromiso> listaFiltrada;  // Lista que se mostrará después de aplicar el filtro
    private View.OnClickListener listener;

    public AdapterEstadoCompromiso(List<EstadoCompromiso> estadoscompromiso) {
        this.listaCompleta = new ArrayList<EstadoCompromiso>(estadoscompromiso);  // Copiamos la lista original
        this.listaFiltrada = estadoscompromiso;  // Referencia a la lista filtrada
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }


    @NonNull
    @Override
    public AdapterEstadoCompromiso.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.estados, parent,false);
        view.setOnClickListener(this);
        return new AdapterEstadoCompromiso.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEstadoCompromiso.ViewHolder holder, int position) {
        holder.estadocompromiso.setText(listaFiltrada.get(position).getNombre());
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return listaFiltrada.size();
    }

    public EstadoCompromiso getItemAtPosition(int position) {
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
            for (EstadoCompromiso estadoscomrpomiso : listaCompleta) {
                if (estadoscomrpomiso.getNombre().toLowerCase().contains(texto)) {
                    listaFiltrada.add(estadoscomrpomiso);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView estadocompromiso;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            estadocompromiso = itemView.findViewById(R.id.tv_estado);
        }
    }
}
