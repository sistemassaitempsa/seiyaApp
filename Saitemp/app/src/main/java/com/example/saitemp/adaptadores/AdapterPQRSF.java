package com.example.saitemp.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saitemp.GenericTextWatcher;
import com.example.saitemp.R;
import com.example.saitemp.clases.clases_adaptadores.PQRSF;

import java.util.ArrayList;
import java.util.List;

public class AdapterPQRSF extends RecyclerView.Adapter<AdapterPQRSF.ViewHolder> implements View.OnClickListener, GenericTextWatcher.Filterable {

    private List<PQRSF> listaCompleta;  // Lista original
    private List<PQRSF> listaFiltrada;  // Lista que se mostrará después de aplicar el filtro
    private View.OnClickListener listener;

    public AdapterPQRSF(List<PQRSF> pqrsf) {
        this.listaCompleta = new ArrayList<PQRSF>(pqrsf);  // Copiamos la lista original
        this.listaFiltrada = pqrsf;  // Referencia a la lista filtrada
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public AdapterPQRSF.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pqrsf, parent,false);
        view.setOnClickListener(this);
        return new AdapterPQRSF.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPQRSF.ViewHolder holder, int position) {
        holder.pqrsf.setText(listaFiltrada.get(position).getNombre());
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public PQRSF getItemAtPosition(int position) {
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
            for (PQRSF pqrsf : listaCompleta) {
                if (pqrsf.getNombre().toLowerCase().contains(texto)) {
                    listaFiltrada.add(pqrsf);
                }
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return listaFiltrada.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView pqrsf;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pqrsf = itemView.findViewById(R.id.tv_pqrsf);        }
    }
}
