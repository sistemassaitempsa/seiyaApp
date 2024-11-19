package com.example.saitemp.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.saitemp.GenericTextWatcher;
import com.example.saitemp.R;
import com.example.saitemp.clases.clases_adaptadores.ClienteDebidaDiligencia;
import java.util.ArrayList;
import java.util.List;

public class AdapterClienteDebidaDiligencia extends RecyclerView.Adapter<AdapterClienteDebidaDiligencia.ViewHolder> implements View.OnClickListener, GenericTextWatcher.Filterable{
    private List<ClienteDebidaDiligencia> listaCompleta;  // Lista original
    private List<ClienteDebidaDiligencia> listaFiltrada;  // Lista que se mostrará después de aplicar el filtro
    private View.OnClickListener listener;

    public AdapterClienteDebidaDiligencia(List<ClienteDebidaDiligencia> clientes) {
        this.listaCompleta = new ArrayList<>(clientes);  // Copiamos la lista original
        this.listaFiltrada = clientes;  // Referencia a la lista filtrada
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }
    @NonNull
    @Override
    public AdapterClienteDebidaDiligencia.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cliente_debida_diligencia, parent,false);
        view.setOnClickListener(this);
        return new AdapterClienteDebidaDiligencia.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClienteDebidaDiligencia.ViewHolder holder, int position) {
        holder.cliente.setText(listaFiltrada.get(position).getNit_numero_documento());
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return listaFiltrada.size();
    }

    public ClienteDebidaDiligencia getItemAtPosition(int position) {
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
            for (ClienteDebidaDiligencia clientes : listaCompleta) {
                if (clientes.getNit_numero_documento().toLowerCase().contains(texto)) {
                    listaFiltrada.add(clientes);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cliente;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cliente = itemView.findViewById(R.id.tv_clientes_debida_diligencia);
        }
    }
}
