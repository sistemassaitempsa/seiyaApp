package com.example.saitemp.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.saitemp.GenericTextWatcher;
import com.example.saitemp.R;
import com.example.saitemp.clases.clases_adaptadores.CargoVisitante;
import java.util.ArrayList;
import java.util.List;

public class AdapterCargoVisitante extends RecyclerView.Adapter<AdapterCargoVisitante.ViewHolder> implements View.OnClickListener, GenericTextWatcher.Filterable {

    private List<CargoVisitante> listaCompleta;  // Lista original
    private List<CargoVisitante> listaFiltrada;  // Lista que se mostrará después de aplicar el filtro
    private View.OnClickListener listener;

    public AdapterCargoVisitante(List<CargoVisitante> cargosvisitantes) {
        this.listaCompleta = new ArrayList<CargoVisitante>(cargosvisitantes);  // Copiamos la lista original
        this.listaFiltrada = cargosvisitantes;  // Referencia a la lista filtrada
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }


    @NonNull
    @Override
    public AdapterCargoVisitante.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cargos_visitante, parent,false);
        view.setOnClickListener(this);
        return new AdapterCargoVisitante.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCargoVisitante.ViewHolder holder, int position) {
        holder.cargo_visitante.setText(listaFiltrada.get(position).getNombre());
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return listaFiltrada.size();
    }

    public CargoVisitante getItemAtPosition(int position) {
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
            for (CargoVisitante cargosvisitantes : listaCompleta) {
                if (cargosvisitantes.getNombre().toLowerCase().contains(texto)) {
                    listaFiltrada.add(cargosvisitantes);
                }
            }
        }
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView cargo_visitante;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cargo_visitante = itemView.findViewById(R.id.tv_cargos_visitantes);
        }
    }
}
