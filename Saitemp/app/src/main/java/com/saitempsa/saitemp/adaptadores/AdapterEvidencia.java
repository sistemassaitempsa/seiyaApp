package com.saitempsa.saitemp.adaptadores;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saitempsa.saitemp.R;
import com.saitempsa.saitemp.clases.clases_adaptadores.Evidencia;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AdapterEvidencia extends RecyclerView.Adapter<AdapterEvidencia.ViewHolder> implements View.OnClickListener {
    private List<Evidencia> listaCompleta;  // Lista original
    private View.OnClickListener listener;
    private boolean isUpdating = false;

    public AdapterEvidencia(List<Evidencia> evidencias) {
        this.listaCompleta = evidencias;  // Referencia a la lista filtrada
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }


    @NonNull
    @Override
    public AdapterEvidencia.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.evidencia, parent, false);
        view.setOnClickListener(this);
        return new AdapterEvidencia.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEvidencia.ViewHolder holder, int position) {

        Evidencia evidencia = getEvidencias().get(position);
        holder.ruta.setText(listaCompleta.get(position).getNombre());
        holder.descripcion.setText(listaCompleta.get(position).getDescripcion());

        holder.eliminar_archivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaCompleta.remove(listaCompleta.get(position));
                notifyDataSetChanged();
            }
        });

        holder.descripcion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Acciones antes de que el texto cambie
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Acciones cuando el texto está cambiando
                evidencia.setDescripcion(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Acciones después de que el texto ha cambiado
            }
        });
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return listaCompleta.size();
    }

    public Evidencia getItemAtPosition(int position) {
        return listaCompleta.get(position);
    }

    public List<Evidencia> getEvidencias() {
        if (listaCompleta.size() > 0) {
            return listaCompleta;
        }
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextInputEditText ruta;
        private TextInputEditText descripcion;
        private Button eliminar_archivo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ruta = itemView.findViewById(R.id.tie_cargo_asistente);
            descripcion = itemView.findViewById(R.id.tie_nombre_asistente);
            eliminar_archivo = itemView.findViewById(R.id.btn_eliminar_archivo);
        }
    }
}
