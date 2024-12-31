package com.saitempsa.saitemp.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saitempsa.saitemp.R;
import com.saitempsa.saitemp.clases.clases_adaptadores.CrmGuardado;

import java.util.List;

public class AdapterFormularioCrmGuardado extends RecyclerView.Adapter<AdapterFormularioCrmGuardado.ViewHolder> implements View.OnClickListener {

    private List<CrmGuardado> formularios_guardados;
    private View.OnClickListener listener;

    public AdapterFormularioCrmGuardado(List<CrmGuardado> formularios_guardados) {
        this.formularios_guardados = formularios_guardados;
    }

    // Método para establecer el listener de clic
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        // Asegúrate de que el listener no sea null antes de llamar al onClick
        if (listener != null) {
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public AdapterFormularioCrmGuardado.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crm_guardados, parent, false);
        // Establecer el listener en la vista
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Vincular los datos con las vistas
        CrmGuardado formulario = formularios_guardados.get(position);
        holder.sede.setText(formulario.getSede());
        holder.proceso.setText(formulario.getProceso());
        holder.solicitante.setText(formulario.getSolicitante());
        holder.nit.setText(formulario.getNit());
        holder.razon_social.setText(formulario.getRazon_social());
    }

    @Override
    public int getItemCount() {
        return formularios_guardados.size();
    }

    // Método para obtener el item en la posición dada
    public CrmGuardado getItemAtPosition(int position) {
        return formularios_guardados.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView sede, proceso, solicitante, nit, razon_social;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Vincular las vistas
            sede = itemView.findViewById(R.id.tv_res_sede);
            proceso = itemView.findViewById(R.id.tv_res_proceso);
            solicitante = itemView.findViewById(R.id.tv_res_solicitante);
            nit = itemView.findViewById(R.id.tv_res_nit);
            razon_social = itemView.findViewById(R.id.tv_res_razon_social);
        }
    }
}


//public class AdapterFormularioCrmGuardado extends RecyclerView.Adapter<AdapterFormularioCrmGuardado.ViewHolder> implements View.OnClickListener {
//
//    public Object setOnClickListener;
//    List<CrmGuardado> formularios_guardados = new ArrayList<>();
//    private View.OnClickListener listener;
//
//    public AdapterFormularioCrmGuardado(List<CrmGuardado> formularios_guardados) {
//        this.formularios_guardados = formularios_guardados;
//    }
//
//    @Override
//    public void onClick(View view) {
//        if (listener != null) {
//            listener.onClick(view);
//        }
//    }
//
//    @NonNull
//    @Override
//    public AdapterFormularioCrmGuardado.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crm_guardados, parent, false);
//        view.setOnClickListener(this);
//        return new AdapterFormularioCrmGuardado.ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull AdapterFormularioCrmGuardado.ViewHolder holder, int position) {
//        holder.sede.setText(formularios_guardados.get(position).getSede());
//        holder.proceso.setText(formularios_guardados.get(position).getProceso());
//        holder.solicitante.setText(formularios_guardados.get(position).getSolicitante());
//        holder.nit.setText(formularios_guardados.get(position).getNit());
//        holder.razon_social.setText(formularios_guardados.get(position).getRazon_social());
//        int id = formularios_guardados.get(position).getId();
//    }
//
//    @Override
//    public int getItemCount() {
//        return formularios_guardados.size();
//    }
//
//    public CrmGuardado getItemAtPosition(int position) {
//        return formularios_guardados.get(position);
//    }
//
//    public void setOnClickListener(View.OnClickListener onClickListener) {
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView sede, proceso, solicitante, nit, razon_social;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            sede = itemView.findViewById(R.id.tv_res_sede);
//            proceso = itemView.findViewById(R.id.tv_res_proceso);
//            solicitante = itemView.findViewById(R.id.tv_res_solicitante);
//            nit = itemView.findViewById(R.id.tv_res_nit);
//            razon_social = itemView.findViewById(R.id.tv_res_razon_social);
//        }
//    }
//}
