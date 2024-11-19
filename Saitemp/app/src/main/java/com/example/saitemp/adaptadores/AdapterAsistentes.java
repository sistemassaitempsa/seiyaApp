package com.example.saitemp.adaptadores;

import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saitemp.GenericTextWatcher;
import com.example.saitemp.R;
import com.example.saitemp.clases.clases_adaptadores.Asistente;
import com.example.saitemp.clases.clases_adaptadores.CargoVisitante;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AdapterAsistentes extends RecyclerView.Adapter<AdapterAsistentes.ViewHolder> implements View.OnClickListener {

    private List<Asistente> asistentes;
    private View.OnClickListener listener;
    private Boolean enabledpad = false;

    public AdapterAsistentes(List<Asistente> asistentes) {
        this.asistentes = asistentes;
    }

    @NonNull
    @Override
    public AdapterAsistentes.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.asistentes, parent, false);
        view.setOnClickListener(this);
        return new AdapterAsistentes.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAsistentes.ViewHolder holder, int position) {
        Asistente asistente = asistentes.get(position);

        if (position == asistentes.size() - 1) {
            holder.btn_agrega_asistente.setVisibility(View.VISIBLE);  // Mostrar botón en el último
        } else {
            holder.btn_agrega_asistente.setVisibility(View.INVISIBLE);  // Ocultar botón en los demás
        }
        if (position == 0) {
            holder.btn_quitar_asistente.setVisibility(View.INVISIBLE);
        }

        holder.cargo.setText(asistentes.get(position).getCargo());
        holder.cargo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Acciones antes de que el texto cambie
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Acciones cuando el texto está cambiando
                asistente.setCargo(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Acciones después de que el texto ha cambiado
            }
        });


        holder.nombre.setText(asistentes.get(position).getNombre());
        if (asistente.getNombre().isEmpty()) {
            // Limpiamos el pad de firmas si el nombre está vacío
            holder.firma.clear();
            asistente.setFirma(null);
        }
        holder.nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Acciones antes de que el texto cambie
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Acciones cuando el texto está cambiando
                asistente.setNombre(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Acciones después de que el texto ha cambiado
            }
        });

        holder.correo.setText(asistentes.get(position).getCorreo());
        holder.correo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Acciones antes de que el texto cambie
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Acciones cuando el texto está cambiando
                asistente.setCorreo(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Acciones después de que el texto ha cambiado
            }
        });

        holder.firma.setEnabled(false);
        holder.firma.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                // Evento cuando se toca el pad
            }

            @Override
            public void onSigned() {
                // Evento cuando el pad se ha firmado
                Bitmap bitmap = holder.firma.getSignatureBitmap();

                // Obtener el objeto PadFirma en la posición actual
                Asistente asistente = asistentes.get(position);

                // Asignar el Bitmap al atributo firma del objeto PadFirma
                asistente.setFirma(bitmap);

            }

            @Override
            public void onClear() {
                // Evento cuando el pad se ha limpiado
                asistente.setFirma(null);
            }
        });

        holder.btn_habilita_firma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enabledpad = !enabledpad;
                holder.firma.setEnabled(enabledpad);
                holder.btn_habilita_firma.setText("Habilita firma");
                if (enabledpad) {
                    holder.btn_habilita_firma.setText("Firmar");
                }
            }
        });

        holder.btn_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.firma.clear();
            }
        });

        holder.btn_agrega_asistente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Asistente nuevoasistente = new Asistente();
                nuevoasistente.setNombre("");
                nuevoasistente.setCargo("");
                nuevoasistente.setCorreo("");
                nuevoasistente.setFirma(null);
                asistentes.add(nuevoasistente);
                notifyItemInserted(asistentes.size() - 1);
            }
        });

        holder.btn_quitar_asistente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (asistentes.size() >= 2) {
                    holder.firma.clear();
                    asistentes.remove(asistentes.get(position));
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, asistentes.size());
                }
            }
        });

        Bitmap firmaBitmap = asistentes.get(position).getFirma();
        if (firmaBitmap != null) {
            holder.firma.setSignatureBitmap(firmaBitmap);
        }
    }

    public List<Asistente> getEmpleados() {
        if (asistentes.size() > 0) {
            return asistentes;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return asistentes.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextInputEditText cargo, nombre, correo;
        SignaturePad firma;
        private Button btn_borrar, btn_agrega_asistente, btn_habilita_firma, btn_quitar_asistente;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cargo = (TextInputEditText) itemView.findViewById(R.id.tie_cargo_asistente);
            nombre = (TextInputEditText) itemView.findViewById(R.id.tie_nombre_asistente);
            correo = (TextInputEditText) itemView.findViewById(R.id.tie_correo_asistente);
            firma = (SignaturePad) itemView.findViewById(R.id.signaturePad);
            btn_borrar = (Button) itemView.findViewById(R.id.btn_borrar);
            btn_agrega_asistente = (Button) itemView.findViewById(R.id.btn_agregar_asistente);
            btn_habilita_firma = (Button) itemView.findViewById(R.id.btn_habilita_firma);
            btn_quitar_asistente = (Button) itemView.findViewById(R.id.btn_quitar_asistente);
        }
    }
}
