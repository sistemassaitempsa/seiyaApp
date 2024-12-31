package com.saitempsa.saitemp.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saitempsa.saitemp.GenericTextWatcher;
import com.saitempsa.saitemp.R;
import com.saitempsa.saitemp.clases.clases_adaptadores.Proceso;

import java.util.ArrayList;
import java.util.List;

public class AdapterProcesos extends RecyclerView.Adapter<AdapterProcesos.ViewHolder> implements View.OnClickListener, GenericTextWatcher.Filterable {

    private List<Proceso> listaCompleta;  // Lista original
    private List<Proceso> listaFiltrada;  // Lista que se mostrará después de aplicar el filtro
    private View.OnClickListener listener;

    public AdapterProcesos(List<Proceso> procesos) {
        this.listaCompleta = new ArrayList<>(procesos);  // Copiamos la lista original
        this.listaFiltrada = procesos;  // Referencia a la lista filtrada
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public AdapterProcesos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.procesos, parent,false);
        view.setOnClickListener(this);
        return new AdapterProcesos.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProcesos.ViewHolder holder, int position) {
        holder.proceso.setText(listaFiltrada.get(position).getNombre());
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public Proceso getItemAtPosition(int position) {
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
            for (Proceso proceso : listaCompleta) {
                if (proceso.getNombre().toLowerCase().contains(texto)) {
                    listaFiltrada.add(proceso);
                }
            }
        }
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView proceso;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            proceso = itemView.findViewById(R.id.tv_proceso);        }
    }
}
