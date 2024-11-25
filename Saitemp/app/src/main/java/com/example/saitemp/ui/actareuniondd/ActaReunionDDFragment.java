package com.example.saitemp.ui.actareuniondd;

import static android.app.Activity.RESULT_OK;

import static com.example.saitemp.clases.guardaformulario.GuardaformularioVisita.eliminaFirmasAsync;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.saitemp.GenericTextWatcher;
import com.example.saitemp.R;
import com.example.saitemp.adaptadores.AdapterAsistentes;
import com.example.saitemp.adaptadores.AdapterCargoVisitante;
import com.example.saitemp.adaptadores.AdapterClienteDebidaDiligencia;
import com.example.saitemp.adaptadores.AdapterEstadoCompromiso;
import com.example.saitemp.adaptadores.AdapterEstados;
import com.example.saitemp.adaptadores.AdapterEvidencia;
import com.example.saitemp.adaptadores.AdapterPQRSF;
import com.example.saitemp.adaptadores.AdapterProcesos;
import com.example.saitemp.adaptadores.AdapterResponsables;
import com.example.saitemp.adaptadores.AdapterSedes;
import com.example.saitemp.adaptadores.AdapterSolicitantes;
import com.example.saitemp.adaptadores.AdapterTiposAtencion;
import com.example.saitemp.adaptadores.AdapterVisitante;
import com.example.saitemp.bd.saitemp.DatabaseHelper;
import com.example.saitemp.clases.Compromiso;
import com.example.saitemp.clases.Formulario_visitas;
import com.example.saitemp.clases.IdsCompromiso;
import com.example.saitemp.clases.LocationManagerHelper;
import com.example.saitemp.clases.sincronizaformulario.SincronizarFormularioDebidaDiligencia;
import com.example.saitemp.clases.TablaPrincipal;
import com.example.saitemp.clases.clases_adaptadores.Asistente;
import com.example.saitemp.clases.clases_adaptadores.CargoVisitante;
import com.example.saitemp.clases.clases_adaptadores.ClienteDebidaDiligencia;
import com.example.saitemp.clases.clases_adaptadores.EstadoCompromiso;
import com.example.saitemp.clases.clases_adaptadores.Evidencia;
import com.example.saitemp.clases.clases_adaptadores.Visitante;
import com.example.saitemp.clases.consultaformularioguardado.ConsultaFormularioGuardado;
import com.example.saitemp.clases.guardaformulario.GuardaformularioVisita;
import com.example.saitemp.consultasApi.ConsultaCargoVisitante;
import com.example.saitemp.consultasApi.ConsultaDebidaDiligencia;
import com.example.saitemp.consultasApi.ConsultaEstadoCompromiso;
import com.example.saitemp.consultasApi.ConsultaEstados;
import com.example.saitemp.consultasApi.ConsultaPQRSF;
import com.example.saitemp.consultasApi.ConsultaProcesos;
import com.example.saitemp.consultasApi.ConsultaResponsable;
import com.example.saitemp.consultasApi.ConsultaSedes;
import com.example.saitemp.clases.clases_adaptadores.Estado;
import com.example.saitemp.clases.clases_adaptadores.PQRSF;
import com.example.saitemp.clases.clases_adaptadores.Proceso;
import com.example.saitemp.clases.clases_adaptadores.Responsable;
import com.example.saitemp.clases.clases_adaptadores.Sede;
import com.example.saitemp.clases.clases_adaptadores.Solicitante;
import com.example.saitemp.clases.clases_adaptadores.Tipo_atencion;
import com.example.saitemp.consultasApi.ConsultaSolicitante;
import com.example.saitemp.consultasApi.ConsultaTipoAtencion;
import com.example.saitemp.consultasApi.ConsultaVisitantes;
import com.example.saitemp.interfaz.EliminaFirmasCallback;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ActaReunionDDFragment extends Fragment {
    private RequestQueue requestQueue;
    private List<Sede> sedes;
    private List<Proceso> procesos;
    private List<Solicitante> solicitantes;
    private List<Tipo_atencion> Tipos_atencion;
    private List<Estado> estado;
    private List<PQRSF> pqrsf;
    private List<Responsable> responsables;
    private List<ClienteDebidaDiligencia> clientes;
    private RecyclerView recyclerSedes, recyclerProcesos, recyclerSolicitantes, recyclerTiposAtencion, recyclerCierraRadicado,
            recyclerEstados, recyclerPqrsf, recyclerResponsables, recyclerVisitantes, recyclerCargoVisitante,
            recyclerAsistente, recyclerEvidencia, recyclerClientes;
    private AdapterEstados adapterEstados;
    private AdapterSedes adapterSedes;
    private AdapterProcesos adapterProcesos;
    private AdapterSolicitantes adapterSolicitantes;
    private AdapterTiposAtencion adapterTiposAtencion;
    private AdapterPQRSF adapterPQRSF;
    private AdapterResponsables adapterResponsable;
    private AdapterResponsables adapterCierraRadicado;
    private AdapterVisitante adapterVisitante;
    private AdapterCargoVisitante adapterCargoVisitante;
    private AdapterEstadoCompromiso adapterEstadoCompromiso;
    private AdapterEvidencia adapterEvidencia;
    private AdapterClienteDebidaDiligencia adapterClienteDebidaDiligencia;
    private ActaReunionDDViewModel mViewModel;
    private TextInputLayout til_sede, til_proceso, til_solicitante, til_hora_inicio, til_tema_visita,
            til_nit, til_numero_documento, til_razon_social, til_telefono, til_correo, til_visitado,
            til_cargo_visitado, til_objetivo, til_alcance_til_hora_inicio, til_cierra_radicado, til_tipo_atencion,
            til_visitante, til_cargo_visitante, til_estado, til_pqrsf, til_responsable, til_alcance ;
    private TextInputEditText tie_proceso, tie_sede, tie_solicitante, tie_tipo_atencion, tie_razon_social,
            tie_pqrs, tie_responsable, tie_visitante, tie_cargo_visitante, tie_nit, tie_telefono,
            tie_correo, tie_visitado, tie_cargo_visitado, tie_objetivo, tie_alcance, tie_tema_visita,
            tie_estado, tie_pqrsf, tie_hora_inicio, tie_observacion, tie_numero_documento, tie_cierra_radicado;
    private AdapterAsistentes adapterAsistentes;
    private LinearLayout linearLayout;
    private int indexOfExistingField;
    private Button btn_add_imput, btn_carga_archivo;
    private List<Responsable> responsable;
    private List<EstadoCompromiso> estadoCompromisos;
    private RecyclerView recyclerViewResponsable, recyclerViewEstadoCompromiso, recyclerView;
    private static final int PICK_FILE = 100;
    private Uri imageUri;
    private List<Evidencia> evidencias = new ArrayList<>();
    private Evidencia evidencia;
    private List<TablaPrincipal> listaTablas;
    private List<TextInputEditText> inputs = new ArrayList<>();
    private List<TextInputLayout> inputslayout = new ArrayList<>();

    private List<IdsCompromiso> idsCompromisos = new ArrayList<>();

    private int sede_id, proceso_id, solicitante_id, nit_id, medio_atencion_id, visitante_id, cargo_visitante_id, estado_pqrsf_id, pqrsf_id, responsable_id;

    public static ActaReunionDDFragment newInstance() {
        return new ActaReunionDDFragment();
    }

    private LocationManagerHelper locationManagerHelper;

    private int PERMISO_UBICACION_REQUEST_CODE = 100;

    private boolean actualizar = false;

    private int id_formulario;
    private List<Compromiso> compromisos1;
    private List<Compromiso> compromisosglobal = new ArrayList<>();

    private List<String> responsableid_cierreid_compromiso = new ArrayList();
    private int posicionCompromiso = 0;
    private int posicionObjetoCompromiso = 0;

    private String correo_responsable = "";

    private View contextoVew;
    private  ScrollView scrollView;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_acta_reunion_d_d, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        locationManagerHelper = new LocationManagerHelper(requireContext());
        permisosGps();

        contextoVew = view;

        linearLayout = view.findViewById(R.id.linearLayoutContainer);
        tie_proceso = view.findViewById(R.id.tie_proceso);
        tie_sede = view.findViewById(R.id.tie_sede);
        tie_solicitante = view.findViewById(R.id.tie_solicitante);
        tie_tipo_atencion = view.findViewById(R.id.tie_tipo_atencion);
        tie_nit = view.findViewById(R.id.tie_nit);
        tie_razon_social = view.findViewById(R.id.tie_razon_social);
        tie_telefono = view.findViewById(R.id.tie_telefono);
        tie_correo = view.findViewById(R.id.tie_correo);
        tie_visitado = view.findViewById(R.id.tie_visitado);
        tie_cargo_visitado = view.findViewById(R.id.tie_cargo_visitado);
        tie_objetivo = view.findViewById(R.id.tie_objetivo);
        tie_alcance = view.findViewById(R.id.tie_alcance);
        tie_tema_visita = view.findViewById(R.id.tie_tema_visita);
        tie_estado = view.findViewById(R.id.tie_estado);
        tie_pqrs = view.findViewById(R.id.tie_pqrsf);
        tie_hora_inicio = view.findViewById(R.id.tie_hora_inicio);
        tie_responsable = view.findViewById(R.id.tie_responsable);
        tie_visitante = view.findViewById(R.id.tie_visitante);
        tie_cargo_visitante = view.findViewById(R.id.tie_cargo_visitante);
        tie_observacion = view.findViewById(R.id.tie_observacion);
        tie_numero_documento = view.findViewById(R.id.tie_numero_documento);
        tie_cierra_radicado = view.findViewById(R.id.tie_cierra_radicado);
        til_sede = view.findViewById(R.id.til_sede);
        til_proceso = view.findViewById(R.id.til_proceso);
        til_solicitante = view.findViewById(R.id.til_solicitante);
        til_tipo_atencion = view.findViewById(R.id.til_tipo_atencion);
        til_visitante = view.findViewById(R.id.til_visitante);
        til_cargo_visitante = view.findViewById(R.id.til_cargo_visitante);
        til_estado = view.findViewById(R.id.til_estado);
        til_pqrsf = view.findViewById(R.id.til_pqrsf);
        til_responsable = view.findViewById(R.id.til_responsable);
        til_hora_inicio = view.findViewById(R.id.til_hora_inicio);
        til_nit = view.findViewById(R.id.til_nit);
        til_numero_documento = view.findViewById(R.id.til_numero_documento);
        til_razon_social = view.findViewById(R.id.til_razon_social);
        til_telefono = view.findViewById(R.id.til_telefono);
        til_correo = view.findViewById(R.id.til_correo);
        til_visitado = view.findViewById(R.id.til_visitado);
        til_cargo_visitado = view.findViewById(R.id.til_cargo_visitado);
        til_objetivo = view.findViewById(R.id.til_objetivo);
        til_alcance = view.findViewById(R.id.til_alcance);
        til_tema_visita = view.findViewById(R.id.til_tema_visita);
        til_cierra_radicado = view.findViewById(R.id.til_cierra_radicado);
        Button btn_add_imput = view.findViewById(R.id.btn_add_imput);
        Button btn_carga_archivo = view.findViewById(R.id.btn_carga_archivo);
        MaterialButton sincronizar_formulario = view.findViewById(R.id.sincronizar_formulario);
        MaterialButton guardar_formulario = view.findViewById(R.id.guardar_formulario);
        MaterialButton agregar_formulario = view.findViewById(R.id.agregar_formulario);
        MaterialButton eliminar_formulario = view.findViewById(R.id.eliminar_formulario);
        scrollView = view.findViewById(R.id.scroll);
        indexOfExistingField = linearLayout.indexOfChild(view.findViewById(R.id.tv_compromisos));

        linearLayout.setVisibility(View.GONE);
        til_cierra_radicado.setVisibility(View.GONE);

        recyclerEstados = (RecyclerView) view.findViewById(R.id.rv_estados);
        recyclerEstados.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Estado> estado = ConsultaEstados.obtenerEstados(getContext());

        recyclerSedes = (RecyclerView) view.findViewById(R.id.rv_sedes);
        recyclerSedes.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Sede> sede = ConsultaSedes.obtenerSedes(getContext());

        recyclerProcesos = (RecyclerView) view.findViewById(R.id.rv_procesos);
        recyclerProcesos.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Proceso> proceso = ConsultaProcesos.obtenerProcesos(getContext());

        recyclerSolicitantes = (RecyclerView) view.findViewById(R.id.rv_solicitantes);
        recyclerSolicitantes.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Solicitante> solicitante = ConsultaSolicitante.obtenerSolicitantes(getContext());

        recyclerTiposAtencion = (RecyclerView) view.findViewById(R.id.rv_tipos_atencion);
        recyclerTiposAtencion.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Tipo_atencion> tipo_atencion = ConsultaTipoAtencion.obtenerTipoAtencion(getContext());

        recyclerPqrsf = (RecyclerView) view.findViewById(R.id.rv_pqrsf);
        recyclerPqrsf.setLayoutManager(new LinearLayoutManager(getContext()));
        List<PQRSF> pqrsf = ConsultaPQRSF.obtenerPQRSF(getContext());

        recyclerResponsables = (RecyclerView) view.findViewById(R.id.rv_responsables);
        recyclerResponsables.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Responsable> responsable = ConsultaResponsable.obtenerResponsables(getContext());

        recyclerVisitantes = (RecyclerView) view.findViewById(R.id.rv_visitante);
        recyclerVisitantes.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Visitante> visitantes = ConsultaVisitantes.obtenerVisitantes(getContext());

        recyclerCierraRadicado = (RecyclerView) view.findViewById(R.id.rv_cierra_radicado);
        recyclerCierraRadicado.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Responsable> cierraRadicado = ConsultaResponsable.obtenerResponsables(getContext());

        recyclerCargoVisitante = (RecyclerView) view.findViewById(R.id.rv_cargo_visitante);
        recyclerCargoVisitante.setLayoutManager(new LinearLayoutManager(getContext()));
        List<CargoVisitante> cargoVisitantes = ConsultaCargoVisitante.obtenerCargoVisitante(getContext());

        recyclerAsistente = (RecyclerView) view.findViewById(R.id.rv_asistentes);
        recyclerAsistente.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerEvidencia = (RecyclerView) view.findViewById(R.id.rv_evidencia);
        recyclerEvidencia.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerClientes = (RecyclerView) view.findViewById(R.id.rv_cliente_debida_diligencia);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(getContext()));
        List<ClienteDebidaDiligencia> clientes = ConsultaDebidaDiligencia.obtenerClientesDebidaDiligencia(getContext());

        adapterEstados = new AdapterEstados(estado);
        recyclerEstados.setAdapter(adapterEstados);

        adapterSedes = new AdapterSedes(sede);
        recyclerSedes.setAdapter(adapterSedes);

        adapterProcesos = new AdapterProcesos(proceso);
        recyclerProcesos.setAdapter(adapterProcesos);

        adapterSolicitantes = new AdapterSolicitantes(solicitante);
        recyclerSolicitantes.setAdapter(adapterSolicitantes);

        adapterTiposAtencion = new AdapterTiposAtencion(tipo_atencion);
        recyclerTiposAtencion.setAdapter(adapterTiposAtencion);

        adapterPQRSF = new AdapterPQRSF(pqrsf);
        recyclerPqrsf.setAdapter(adapterPQRSF);

        adapterResponsable = new AdapterResponsables(responsable);
        recyclerResponsables.setAdapter(adapterResponsable);

        adapterCierraRadicado = new AdapterResponsables(cierraRadicado);
        recyclerCierraRadicado.setAdapter(adapterCierraRadicado);

        adapterVisitante = new AdapterVisitante(visitantes);
        recyclerVisitantes.setAdapter(adapterVisitante);

        adapterCargoVisitante = new AdapterCargoVisitante(cargoVisitantes);
        recyclerCargoVisitante.setAdapter(adapterCargoVisitante);

        adapterClienteDebidaDiligencia = new AdapterClienteDebidaDiligencia(clientes);
        recyclerClientes.setAdapter(adapterClienteDebidaDiligencia);

        List<EstadoCompromiso> estadoCompromisos = ConsultaEstadoCompromiso.obtenerEstadosCompromiso(getContext());
        adapterEstadoCompromiso = new AdapterEstadoCompromiso(estadoCompromisos);

        Asistente asistente = new Asistente();
        List<Asistente> asistentes = new ArrayList<>();
        asistente.setNombre("");
        asistente.setCargo("");
        asistente.setCorreo("");
        asistentes.add(asistente);
        adapterAsistentes = new AdapterAsistentes(asistentes);
        recyclerAsistente.setAdapter(adapterAsistentes);


        RecyclerView recyclerProcesos = view.findViewById(R.id.rv_procesos);
        recyclerProcesos.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView recyclerSedes = view.findViewById(R.id.rv_sedes);
        recyclerSedes.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView recyclerSolicitantes = view.findViewById(R.id.rv_solicitantes);
        recyclerSolicitantes.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView recyclerTiposAtencion = view.findViewById(R.id.rv_tipos_atencion);
        recyclerTiposAtencion.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView recyclerEstados = view.findViewById(R.id.rv_estados);
        recyclerEstados.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView recyclerPqrsf = view.findViewById(R.id.rv_pqrsf);
        recyclerPqrsf.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView recyclerResponsables = view.findViewById(R.id.rv_responsables);
        recyclerResponsables.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView recyclerVisitante = view.findViewById(R.id.rv_visitante);
        recyclerVisitante.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView recyclerCargoVisitante = view.findViewById(R.id.rv_cargo_visitante);
        recyclerCargoVisitante.setLayoutManager(new LinearLayoutManager(getContext()));


        RecyclerView recyclerClientes = view.findViewById(R.id.rv_cliente_debida_diligencia);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerInvisible();


        tie_proceso.addTextChangedListener(new GenericTextWatcher(adapterProcesos));
        tie_sede.addTextChangedListener(new GenericTextWatcher(adapterSedes));
        tie_solicitante.addTextChangedListener(new GenericTextWatcher(adapterSolicitantes));
        tie_tipo_atencion.addTextChangedListener(new GenericTextWatcher(adapterTiposAtencion));
        tie_estado.addTextChangedListener(new GenericTextWatcher(adapterEstados));
        tie_pqrs.addTextChangedListener(new GenericTextWatcher(adapterPQRSF));
        tie_responsable.addTextChangedListener(new GenericTextWatcher(adapterResponsable));
        tie_visitante.addTextChangedListener(new GenericTextWatcher(adapterVisitante));
        tie_cargo_visitante.addTextChangedListener(new GenericTextWatcher(adapterCargoVisitante));
        tie_nit.addTextChangedListener(new GenericTextWatcher(adapterClienteDebidaDiligencia));
        tie_cierra_radicado.addTextChangedListener(new GenericTextWatcher(adapterCierraRadicado));

        mensajeError(tie_correo, til_correo);
        mensajeError(tie_telefono, til_telefono);
        mensajeError(tie_visitado, til_visitado);
        mensajeError(tie_cargo_visitado, til_cargo_visitado);
        mensajeError(tie_objetivo, til_objetivo);
        mensajeError(tie_alcance, til_alcance);
        mensajeError(tie_tema_visita, til_tema_visita);
        mensajeError(tie_hora_inicio, til_hora_inicio);

        configurarClickListener(tie_proceso, recyclerProcesos);
        configurarClickListener(tie_sede, recyclerSedes);
        configurarClickListener(tie_solicitante, recyclerSolicitantes);
        configurarClickListener(tie_tipo_atencion, recyclerTiposAtencion);
        configurarClickListener(tie_nit, recyclerClientes);
        configurarClickListener(tie_estado, recyclerEstados);
        configurarClickListener(tie_pqrs, recyclerPqrsf);
        configurarClickListener(tie_responsable, recyclerResponsables);
        configurarClickListener(tie_visitante, recyclerVisitante);
        configurarClickListener(tie_cargo_visitante, recyclerCargoVisitante);
        configurarClickListener(tie_cierra_radicado, recyclerCierraRadicado);

        adapterSedes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerSedes.getChildAdapterPosition(v);
                if (position != RecyclerView.NO_POSITION) {
                    // Llama a una función del adaptador o accede a un ítem
                    Sede item = adapterSedes.getItemAtPosition(position); // Debes implementar getItemAtPosition en el adaptador
                    tie_sede.setText(item.getNombre());
                    sede_id = item.getId();
                    til_sede.setErrorEnabled(false);
                    tie_sede.setSelection(item.getNombre().length());
                    recyclerInvisible();
                }
            }
        });

        adapterProcesos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerProcesos.getChildAdapterPosition(v);
                if (position != RecyclerView.NO_POSITION) {
                    // Llama a una función del adaptador o accede a un ítem
                    Proceso item = adapterProcesos.getItemAtPosition(position); // Debes implementar getItemAtPosition en el adaptador
                    tie_proceso.setText(item.getNombre());
                    proceso_id = item.getId();
                    tie_proceso.setSelection(item.getNombre().length());
                    til_proceso.setErrorEnabled(false);
                    recyclerInvisible();
                }
            }
        });

        adapterTiposAtencion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerTiposAtencion.getChildAdapterPosition(v);
                if (position != RecyclerView.NO_POSITION) {
                    // Llama a una función del adaptador o accede a un ítem
                    Tipo_atencion item = adapterTiposAtencion.getItemAtPosition(position); // Debes implementar getItemAtPosition en el adaptador
                    tie_tipo_atencion.setText(item.getNombre());
                    medio_atencion_id = item.getId();
                    tie_tipo_atencion.setSelection(item.getNombre().length());
                    til_tipo_atencion.setErrorEnabled(false);
                    if ((medio_atencion_id == 5 || medio_atencion_id == 6) && medio_atencion_id != 0) {
                        linearLayout.setVisibility(View.VISIBLE);
                        pqrsf_id = 6;
                        til_pqrsf.setVisibility(view.GONE);

                        Asistente asistente = new Asistente();
                        List<Asistente> asistentes = new ArrayList<>();
                        asistente.setNombre("");
                        asistente.setCargo("");
                        asistente.setCorreo("");
                        asistentes.add(asistente);
                        adapterAsistentes = new AdapterAsistentes(asistentes);

                        recyclerAsistente.setAdapter(adapterAsistentes);
                    } else {
                        linearLayout.setVisibility(View.GONE);
                        til_pqrsf.setVisibility(view.VISIBLE);
                    }
                    recyclerInvisible();
                }
            }
        });

        adapterEstados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerEstados.getChildAdapterPosition(v);
                if (position != RecyclerView.NO_POSITION) {
                    // Llama a una función del adaptador o accede a un ítem
                    Estado item = adapterEstados.getItemAtPosition(position); // Debes implementar getItemAtPosition en el adaptador
                    tie_estado.setText(item.getNombre());
                    estado_pqrsf_id = item.getId();
                    tie_estado.setSelection(item.getNombre().length());
                    til_estado.setErrorEnabled(false);
                    if (estado_pqrsf_id == 3) {
                        til_cierra_radicado.setVisibility(View.VISIBLE);
                    } else if (estado_pqrsf_id == 4) {
                        til_cierra_radicado.setVisibility(View.GONE);
                    }
                    recyclerInvisible();
                }
            }
        });

        adapterSolicitantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerSolicitantes.getChildAdapterPosition(v);
                if (position != RecyclerView.NO_POSITION) {
                    Solicitante item = adapterSolicitantes.getItemAtPosition(position); // Debes implementar getItemAtPosition en el adaptador
                    tie_solicitante.setText(item.getNombre());
                    solicitante_id = item.getId();
                    recyclerInvisible();
                    if (solicitante_id == 1) {
                        til_numero_documento.setVisibility(View.GONE);
                        til_nit.setVisibility(View.VISIBLE);
                        til_solicitante.setErrorEnabled(false);
                        pqrsf_id = 6;
                    } else {
                        til_nit.setVisibility(View.GONE);
                        til_numero_documento.setVisibility(View.VISIBLE);
                        tie_nit.setText("");
                        tie_razon_social.setText("");
                        tie_razon_social.setEnabled(true);
                        til_solicitante.setErrorEnabled(false);
                    }
                    tie_solicitante.setSelection(item.getNombre().length());
                }
            }
        });

        adapterPQRSF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerSolicitantes.getChildAdapterPosition(v);
                if (position != RecyclerView.NO_POSITION) {
                    // Llama a una función del adaptador o accede a un ítem
                    PQRSF item = adapterPQRSF.getItemAtPosition(position); // Debes implementar getItemAtPosition en el adaptador
                    tie_pqrs.setText(item.getNombre());
                    pqrsf_id = item.getId();
                    tie_pqrs.setSelection(item.getNombre().length());
                    til_pqrsf.setErrorEnabled(false);
                    recyclerInvisible();
                }
            }
        });

        adapterResponsable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerResponsables.getChildAdapterPosition(v);
                if (position != RecyclerView.NO_POSITION) {
                    // Llama a una función del adaptador o accede a un ítem
                    Responsable item = adapterResponsable.getItemAtPosition(position); // Debes implementar getItemAtPosition en el adaptador
                    tie_responsable.setText(item.getNombre());
                    responsable_id = item.getId();
                    tie_responsable.setSelection(item.getNombre().length());
                    til_responsable.setErrorEnabled(false);
                    correo_responsable = item.getEmail();
                    recyclerInvisible();
                }
            }
        });

        adapterVisitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerVisitantes.getChildAdapterPosition(v);
                if (position != RecyclerView.NO_POSITION) {
                    // Llama a una función del adaptador o accede a un ítem
                    Visitante item = adapterVisitante.getItemAtPosition(position); // Debes implementar getItemAtPosition en el adaptador
                    tie_visitante.setText(item.getNombre());
                    visitante_id = item.getId();
                    tie_visitante.setSelection(item.getNombre().length());
                    til_visitante.setErrorEnabled(false);
                    recyclerInvisible();

                }
            }
        });


        adapterCargoVisitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerCargoVisitante.getChildAdapterPosition(v);
                if (position != RecyclerView.NO_POSITION) {
                    // Llama a una función del adaptador o accede a un ítem
                    CargoVisitante item = adapterCargoVisitante.getItemAtPosition(position); // Debes implementar getItemAtPosition en el adaptador
                    tie_cargo_visitante.setText(item.getNombre());
                    cargo_visitante_id = item.getId();
                    tie_cargo_visitante.setSelection(item.getNombre().length());
                    til_cargo_visitante.setErrorEnabled(false);
                    recyclerInvisible();
                }
            }
        });

        til_nit.setVisibility(View.GONE);
        til_numero_documento.setVisibility(View.GONE);
        adapterClienteDebidaDiligencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerClientes.getChildAdapterPosition(v);
                if (position != RecyclerView.NO_POSITION) {
                    // Llama a una función del adaptador o accede a un ítem
                    ClienteDebidaDiligencia item = adapterClienteDebidaDiligencia.getItemAtPosition(position); // Debes implementar getItemAtPosition en el adaptador
                    tie_nit.setText(item.getNit_numero_documento().trim());
                    tie_razon_social.setText(item.getNombre());
                    tie_razon_social.setEnabled(false);
                    til_nit.setErrorEnabled(false);
                    til_razon_social.setErrorEnabled(false);
                    nit_id = item.getId();
                    recyclerInvisible();
                }
            }
        });

        adapterCierraRadicado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerCierraRadicado.getChildAdapterPosition(v);
                if (position != RecyclerView.NO_POSITION) {
                    // Llama a una función del adaptador o accede a un ítem
                    Responsable item = adapterCierraRadicado.getItemAtPosition(position); // Debes implementar getItemAtPosition en el adaptador
                    tie_cierra_radicado.setText(item.getNombre().trim());
                    til_cierra_radicado.setErrorEnabled(false);
                    recyclerInvisible();
                }
            }
        });

        til_hora_inicio.getEditText().setInputType(InputType.TYPE_NULL); // Desactiva el teclado
        til_hora_inicio.getEditText().setFocusable(false); // Evita que se enfoque

        til_hora_inicio.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(til_hora_inicio);
            }
        });

        btn_add_imput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarCompromiso();
            }
        });

        btn_carga_archivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker();
            }
        });

        sincronizar_formulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sincronizar_formulario.setEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    CompletableFuture.delayedExecutor(30, TimeUnit.SECONDS).execute(() -> {
                        // Cambiar el estado del botón en el hilo principal
                        requireActivity().runOnUiThread(() -> sincronizar_formulario.setEnabled(true));
                    });
                }

                boolean scroll = true;
                if (tie_sede.getText().toString().isEmpty() || sede_id == 0) {
                    til_sede.setError("Este campo debe ser diligenciado");
                    scroll = false;
                }
                if (tie_proceso.getText().toString().isEmpty() || proceso_id == 0) {
                    til_proceso.setError("Este campo debe ser diligenciado");
                    scroll = false;
                }
                if (tie_solicitante.getText().toString().isEmpty() || solicitante_id == 0) {
                    til_solicitante.setError("Este campo debe ser diligenciado");
                    scroll = false;
                }
                if (tie_tipo_atencion.getText().toString().isEmpty() || medio_atencion_id == 0) {
                    til_tipo_atencion.setError("Este campo debe ser diligenciado");
                    scroll = false;
                }
                if (solicitante_id == 1 || solicitante_id == 0) {
                    if (tie_nit.getText().toString().isEmpty() || (nit_id == 0 && !actualizar)) {
                        til_nit.setError("Este campo debe ser diligenciado");
                        scroll = false;

                    }
                } else {
                    if (tie_numero_documento.getText().toString().isEmpty()) {
                        til_numero_documento.setError("Este campo debe ser diligenciado");
                        scroll = false;
                    }
                }
                if (tie_razon_social.getText().toString().isEmpty()) {
                    til_razon_social.setError("Este campo debe ser diligenciado");
                    scroll = false;
                }
                if (tie_telefono.getText().toString().isEmpty()) {
                    til_telefono.setError("Este campo debe ser diligenciado");
                    scroll = false;
                }
                if (tie_correo.getText().toString().isEmpty()) {
                    til_correo.setError("Este campo debe ser diligenciado");
                    scroll = false;
                }
                if (tie_estado.getText().toString().isEmpty() || estado_pqrsf_id == 0) {
                    til_estado.setError("Este campo debe ser diligenciado");
                    scroll = false;
                }
                if (medio_atencion_id != 5 && medio_atencion_id != 6) {
                    if (tie_pqrs.getText().toString().isEmpty() || pqrsf_id == 0) {
                        til_pqrsf.setError("Este campo debe ser diligenciado");
                        scroll = false;
                    }
                }
                if (tie_responsable.getText().toString().isEmpty() || responsable_id == 0) {
                    til_responsable.setError("Este campo debe ser diligenciado");
                    scroll = false;
                }
                if ((estado_pqrsf_id == 3 || estado_pqrsf_id == 0) && tie_cierra_radicado.getText().toString().isEmpty()) {
                    til_cierra_radicado.setError("Este campo debe ser diligenciado");
                    scroll = false;
                }

                if (medio_atencion_id == 5 || medio_atencion_id == 6) {

                    if (tie_hora_inicio.getText().toString().isEmpty()) {
                        til_hora_inicio.setError("Este campo debe ser diligenciado");
                        scroll = false;
                    }
                    if (tie_visitante.getText().toString().isEmpty() || visitante_id == 0) {
                        til_visitante.setError("Este campo debe ser diligenciado");
                        scroll = false;
                    }
                    if (tie_cargo_visitante.getText().toString().isEmpty() || cargo_visitante_id == 0) {
                        til_cargo_visitante.setError("Este campo debe ser diligenciado");
                        scroll = false;
                    }
                    if (tie_visitado.getText().toString().isEmpty()) {
                        til_visitado.setError("Este campo debe ser diligenciado");
                        scroll = false;
                    }
                    if (tie_cargo_visitado.getText().toString().isEmpty()) {
                        til_cargo_visitado.setError("Este campo debe ser diligenciado");
                        scroll = false;
                    }
                    if (tie_objetivo.getText().toString().isEmpty()) {
                        til_objetivo.setError("Este campo debe ser diligenciado");
                        scroll = false;
                    }
                    if (tie_alcance.getText().toString().isEmpty()) {
                        til_alcance.setError("Este campo debe ser diligenciado");
                        scroll = false;
                    }
                    if (tie_tema_visita.getText().toString().isEmpty()) {
                        til_tema_visita.setError("Este campo debe ser diligenciado");
                        scroll = false;
                    }
                }
                if (!scroll) {
                    scrollToField(scrollView, tie_sede);
                    Toast.makeText(getContext(), "Por favor, verifique el llenado de todos los campos.", Toast.LENGTH_SHORT).show();
                } else {
                    SincronizarFormularioDebidaDiligencia sincronizar = new SincronizarFormularioDebidaDiligencia(getContext());
                    sincronizar.consultarFormulario(String.valueOf(id_formulario));
                }
            }
        });


        guardar_formulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    List<Compromiso> compromisos = new ArrayList<>();
                    for (int i = 0; i < inputs.size(); i += 4) {
                        if (i + 3 < inputs.size()) {  // Verificar que haya al menos 4 elementos más
                            Compromiso compromiso1 = new Compromiso();
                            compromiso1.setCompromiso(inputs.get(i).getText().toString());
                            compromiso1.setResponsable(inputs.get(i + 1).getText().toString());
                            compromiso1.setEstado(inputs.get(i + 2).getText().toString());
                            compromiso1.setObservacion(inputs.get(i + 3).getText().toString());
                            int responsble_id = 0;
                            int estado_id = 0;
                            if (responsableid_cierreid_compromiso.size() > 0) {
                                int auxIndex = i / 4 * 2; // Calcula el índice en responsableid_cierreid_compromiso
                                if (responsableid_cierreid_compromiso.size() > auxIndex + 1) {
                                    responsble_id = Integer.parseInt(responsableid_cierreid_compromiso.get(auxIndex));
                                    estado_id = Integer.parseInt(responsableid_cierreid_compromiso.get(auxIndex + 1));
                                    if (responsble_id > 0) {
                                        compromiso1.setResponsable_id(responsble_id);
                                    }
                                    if (estado_id > 0) {
                                        compromiso1.setEstado_id(estado_id);
                                    }
                                }
                            }
                            compromisos.add(compromiso1);


                        }
                    }

                    String sede = tie_sede.getText().toString().trim();

                    String proceso = tie_proceso.getText().toString().trim();
                    String solicitante = tie_solicitante.getText().toString().trim();
                    String medio_atencion = tie_tipo_atencion.getText().toString().trim();
                    String nit = "";
                    if (solicitante_id == 1) {
                        nit = tie_nit.getText().toString().trim();
                    } else {
                        nit = tie_numero_documento.getText().toString().trim();
                    }

                    String razon_social = tie_razon_social.getText().toString().trim();
                    String telefono = tie_telefono.getText().toString().trim();
                    String correo = tie_correo.getText().toString().trim();
                    String visitante = tie_visitante.getText().toString();
                    String cargo_visitante = tie_cargo_visitante.getText().toString().trim();
                    String visitado = tie_visitado.getText().toString().trim();
                    String cargo_visitado = tie_cargo_visitado.getText().toString().trim();
                    String objetivo = tie_objetivo.getText().toString().trim();
                    String alcance = tie_alcance.getText().toString().trim();
                    String tema_visita = tie_tema_visita.getText().toString().trim();
                    String estado_pqrsf = tie_estado.getText().toString().trim();
                    String pqrsf = tie_pqrs.getText().toString().trim();
                    String responsable = tie_responsable.getText().toString().trim();
                    String hora_inicio = tie_hora_inicio.getText().toString().trim();
                    String observacion = tie_observacion.getText().toString().trim();
                    String cierra_radicado = tie_cierra_radicado.getText().toString().trim();

                    if(sede.isEmpty()){
                        sede_id = 0;
                    }if(proceso.isEmpty()){
                        proceso_id = 0;
                    }if(solicitante.isEmpty()){
                        solicitante_id = 0;
                    }if(medio_atencion.isEmpty()){
                        medio_atencion_id = 0;
                    }if(nit.isEmpty()){
                        nit_id = 0;
                    }if(visitante.isEmpty()){
                        visitante_id = 0;
                    }if(cargo_visitante.isEmpty()){
                        cargo_visitante_id = 0;
                    }if(estado_pqrsf.isEmpty()){
                        estado_pqrsf_id = 0;
                    }if(pqrsf.isEmpty()){
                        pqrsf_id = 0;
                    }if(responsable.isEmpty()){
                        responsable_id = 0;
                    }

                    List<Asistente> asistentesFormulario = new ArrayList<>();
                    asistentesFormulario = adapterAsistentes.getEmpleados();

                    List<Evidencia> evidencias1 = new ArrayList<>();
                    if (evidencias.size() > 0) { // se verifica que el adaptador halla sido inicializado antes de llamarlo
                        evidencias1 = adapterEvidencia.getEvidencias();
                    }
                    Formulario_visitas formulario = new Formulario_visitas(sede, sede_id, proceso,
                            proceso_id, solicitante, solicitante_id, medio_atencion, medio_atencion_id, nit,
                            razon_social, cierra_radicado, telefono, correo, visitante,
                            visitante_id, cargo_visitante, cargo_visitante_id, visitado, cargo_visitado, objetivo,
                            alcance, tema_visita, estado_pqrsf, estado_pqrsf_id, pqrsf, pqrsf_id, responsable, responsable_id, hora_inicio, observacion, correo_responsable);

                    formulario.setAsistentes(asistentesFormulario);
                    formulario.setCompromisos(compromisos);
                    formulario.setEvidencias(evidencias1);

                    if (actualizar) {
                        ContentValues values_formulario = new ContentValues();
                        values_formulario.put("sede", sede);
                        values_formulario.put("sede_id", sede_id);
                        values_formulario.put("proceso", proceso);
                        values_formulario.put("proceso_id", proceso_id);
                        values_formulario.put("solicitante", solicitante);
                        values_formulario.put("solicitante_id", solicitante_id);
                        values_formulario.put("medio_atencion", medio_atencion);
                        values_formulario.put("medio_atencion_id", medio_atencion_id);
                        values_formulario.put("nit_numero_identificacion", nit);
                        values_formulario.put("nombre_razon_social", razon_social);
                        values_formulario.put("cierra_radicado", cierra_radicado);
                        values_formulario.put("telefono_contacto", telefono);
                        values_formulario.put("correo_contacto", correo);
                        values_formulario.put("visitante", visitante);
                        values_formulario.put("visitante_id", visitante_id);
                        values_formulario.put("cargo_visitante", cargo_visitante);
                        values_formulario.put("cargo_visitante_id", cargo_visitante_id);
                        values_formulario.put("visitado", visitado);
                        values_formulario.put("cargo_visitado", cargo_visitado);
                        values_formulario.put("objetivo", objetivo);
                        values_formulario.put("alcance", alcance);
                        values_formulario.put("tema", tema_visita);
                        values_formulario.put("estado", estado_pqrsf);
                        values_formulario.put("estado_id", estado_pqrsf_id);
                        values_formulario.put("pqrsf", pqrsf);
                        values_formulario.put("pqrsf_id", pqrsf_id);
                        values_formulario.put("responsable", responsable);
                        values_formulario.put("correo_responsable", correo_responsable);
                        values_formulario.put("responsable_id", responsable_id);
                        values_formulario.put("hora_incio", hora_inicio);
                        values_formulario.put("observaciones", observacion);
                        GuardaformularioVisita.actualizaRegistro(getContext(), "usr_app_formulario_ingreso", values_formulario, String.valueOf(id_formulario));

                        List<Asistente> actualizarAsistentesFormulario = new ArrayList<>();
                        List<Asistente> actualizarAsistentes = new ArrayList<>();
                        List<Asistente> insertarAsistentes = new ArrayList<>();
                        if (adapterAsistentes != null && adapterAsistentes.getEmpleados() != null && !adapterAsistentes.getEmpleados().isEmpty()) {
                            actualizarAsistentesFormulario = adapterAsistentes.getEmpleados();
                            for (Asistente asistente : actualizarAsistentesFormulario) {
                                int id = asistente.getId();
                                if (id != 0) {
                                    actualizarAsistentes.add(asistente);
                                } else {
                                    insertarAsistentes.add(asistente);
                                }
                            }

                            eliminaFirmasAsync(getContext(), id_formulario, new EliminaFirmasCallback() {
                                @Override
                                public void onSuccess() {
                                    // Aquí puedes actualizar la UI o realizar otra acción
                                    if (actualizarAsistentes.size() > 0) {
                                        GuardaformularioVisita.manejoAsistentes(getContext(), actualizarAsistentes, 3, 0);
                                    }
                                    if (insertarAsistentes.size() > 0) {
                                        GuardaformularioVisita.manejoAsistentes(getContext(), insertarAsistentes, 1, id_formulario);
                                    }

                                }

                                @Override
                                public void onFailure(Exception e) {
                                    Log.e("firmas", "Error al eliminar firmas", e);
                                    // Aquí puedes manejar el error y notificar al usuario
                                }
                            });

                        }

                        if (adapterEvidencia != null && adapterEvidencia.getEvidencias() != null && !adapterEvidencia.getEvidencias().isEmpty()) {
                            List<Evidencia> actualizarEvidencias = new ArrayList<>();
                            List<Evidencia> insertarEvidencias = new ArrayList<>();
                            List<Evidencia> evidencias2 = new ArrayList<>();
                            evidencias2 = adapterEvidencia.getEvidencias();
                            ContentValues values_evidencias = new ContentValues();
                            for (Evidencia evidencia : evidencias2) {
                                int id = evidencia.getId();
                                if (id != 0) {
                                    values_evidencias.put("nombre", evidencia.getNombre());
                                    values_evidencias.put("path", evidencia.getPath());
                                    values_evidencias.put("descripcion", evidencia.getDescripcion());
                                    actualizarEvidencias.add(evidencia);
                                } else {
                                    insertarEvidencias.add(evidencia);
                                }
                            }
                            if (insertarEvidencias.size() > 0) {
                                GuardaformularioVisita.manejoEvidencias(getContext(), insertarEvidencias, 1, id_formulario);
                            }
                            if (actualizarEvidencias.size() > 0) {
                                GuardaformularioVisita.manejoEvidencias(getContext(), actualizarEvidencias, 2, 0);
                            }
                        }

                        List<Compromiso> actualizaCompromisos = new ArrayList<>();
                        List<Compromiso> insertaCompromisos = new ArrayList<>();
                        for (int i = 0; i < inputs.size(); i += 4) {
                            if (i + 3 < inputs.size()) {
                                String compromiso = inputs.get(i).getText().toString();
                                String responsable_compromiso = inputs.get(i + 1).getText().toString();
                                String estado = inputs.get(i + 2).getText().toString();
                                String observacion_compromiso = inputs.get(i + 3).getText().toString();
                                int responsble_id = 0;
                                int estado_id = 0;
                                if (responsableid_cierreid_compromiso.size() > 0) {
                                    int auxIndex = i / 4 * 2; // Calcula el índice en responsableid_cierreid_compromiso
                                    if (responsableid_cierreid_compromiso.size() > auxIndex + 1) {
                                        responsble_id = Integer.parseInt(responsableid_cierreid_compromiso.get(auxIndex));
                                        estado_id = Integer.parseInt(responsableid_cierreid_compromiso.get(auxIndex + 1));
                                    }
                                }
                                if (inputs.get(i).getTag() != null) {
                                    int id = Integer.parseInt(inputs.get(i).getTag().toString());
                                    Compromiso compromiso1 = new Compromiso(id, id_formulario, compromiso, responsable_compromiso, estado, observacion_compromiso);
                                    compromiso1.setResponsable_id(responsble_id);
                                    compromiso1.setEstado_id(estado_id);
                                    actualizaCompromisos.add(compromiso1);
                                } else {
                                    Compromiso compromiso1 = new Compromiso(compromiso, responsable_compromiso, estado, observacion_compromiso);
                                    if (responsble_id > 0) {
                                        compromiso1.setResponsable_id(responsble_id);
                                    }
                                    if (estado_id > 0) {
                                        compromiso1.setEstado_id(estado_id);
                                    }
                                    insertaCompromisos.add(compromiso1);
                                }
                            }
                        }
                        if (actualizaCompromisos.size() > 0) {
                            GuardaformularioVisita.manejoCompromisos(getContext(), actualizaCompromisos, 2, id_formulario);
                        }
                        if (insertaCompromisos.size() > 0) {
                            GuardaformularioVisita.manejoCompromisos(getContext(), insertaCompromisos, 1, id_formulario);
                        }

                        try {
                            actualizarDinamicos(view);
                        } catch (JSONException e) {
                            Log.d("error", e.toString());
                        }
                    } else {
                        obtenerUbicacion(formulario, view);
                    }

                } else {
                    permisosGps();

                }
            }
        });

        agregar_formulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posicionCompromiso = 0;
                posicionObjetoCompromiso = 0;
                responsableid_cierreid_compromiso.clear();
                actualizar = false;
                linearLayout.setVisibility(View.GONE);
                id_formulario = 0;
                tie_sede.setText("");
                sede_id = 0;
                tie_proceso.setText("");
                tie_numero_documento.setText("");
                tie_nit.setText("");
                proceso_id = 0;
                tie_solicitante.setText("");
                solicitante_id = 0;
                tie_tipo_atencion.setText("");
                medio_atencion_id = 0;
                tie_razon_social.setText("");
                tie_cierra_radicado.setText("");
                tie_telefono.setText("");
                tie_correo.setText("");
                tie_visitante.setText("");
                visitante_id = 0;
                tie_cargo_visitante.setText("");
                cargo_visitante_id = 0;
                tie_visitado.setText("");
                tie_cargo_visitado.setText("");
                tie_objetivo.setText("");
                tie_alcance.setText("");
                tie_tema_visita.setText("");
                tie_estado.setText("");
                estado_pqrsf_id = 0;
                tie_pqrs.setText("");
                pqrsf_id = 6;
                tie_responsable.setText("");
                responsable_id = 0;
                tie_hora_inicio.setText("");
                tie_observacion.setText("");
                evidencias.clear();
                asistentes.clear();
                asistente.setNombre("");
                asistente.setCargo("");
                asistente.setCorreo("");
                asistentes.add(asistente);
                adapterEvidencia = new AdapterEvidencia(evidencias);
                recyclerEvidencia.setAdapter(adapterEvidencia);
                adapterAsistentes = new AdapterAsistentes(asistentes);
                recyclerAsistente.setAdapter(adapterAsistentes);
                // Notificar al adaptador que los datos han cambiado
                adapterEvidencia.notifyDataSetChanged();
                adapterAsistentes.notifyDataSetChanged();

                //Destrulle los setInputLayout de los compromisos para restablecerlos de cero
                for (TextInputLayout input : inputslayout) {
                    linearLayout.removeView(input);
                }
                for (TextInputEditText input : inputs) {
                    linearLayout.removeView(input);
                }
                inputslayout.clear();
                inputs.clear();
                //restablece la posicion de donde se deben empezar a insertar los campos de compromiso
                indexOfExistingField = linearLayout.indexOfChild(view.findViewById(R.id.tv_compromisos));

                mensajeError(tie_correo, til_correo);
                mensajeError(tie_telefono, til_telefono);
                mensajeError(tie_visitado, til_visitado);
                mensajeError(tie_cargo_visitado, til_cargo_visitado);
                mensajeError(tie_objetivo, til_objetivo);
                mensajeError(tie_alcance, til_alcance);
                mensajeError(tie_tema_visita, til_tema_visita);
                mensajeError(tie_hora_inicio, til_hora_inicio);

                til_pqrsf.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Por favor registré los datos del nuevo formulario.", Toast.LENGTH_SHORT).show();
            }
        });

        eliminar_formulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar = false;
                DatabaseHelper dbHelper = new DatabaseHelper(getContext()); // "this" es el contexto
                Boolean borrado = dbHelper.eliminarRegistro("usr_app_formulario_ingreso", "id", id_formulario);
                if (borrado) {
                    dbHelper.eliminarRegistro("usr_app_evidencias", "formulario_id", id_formulario);
                    dbHelper.eliminarRegistro("usr_app_compromisos", "formulario_id", id_formulario);
                    GuardaformularioVisita.eliminaFirmas(getContext(), id_formulario);
                    evidencias.clear();
                    asistentes.clear();
                    asistente.setNombre("");
                    asistente.setCargo("");
                    asistente.setCorreo("");
                    asistentes.add(asistente);
                    adapterEvidencia = new AdapterEvidencia(evidencias);
                    recyclerEvidencia.setAdapter(adapterEvidencia);
                    adapterAsistentes = new AdapterAsistentes(asistentes);
                    recyclerAsistente.setAdapter(adapterAsistentes);
                    for (TextInputLayout input : inputslayout) {
                        linearLayout.removeView(input);
                    }
                    for (TextInputEditText input : inputs) {
                        linearLayout.removeView(input);
                    }
                    inputslayout.clear();
                    inputs.clear();
                    //restablece la posicion de donde se deben empezar a insertar los campos de compromiso
                    indexOfExistingField = linearLayout.indexOfChild(view.findViewById(R.id.tv_compromisos));
                    Toast.makeText(getContext(), "Registro eliminado de manera exitosa.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error al eliminar el registro por favor intente nuevamente.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        if (getArguments() != null) {
            id_formulario = getArguments().getInt("id");
            actualizar = getArguments().getBoolean("actualizar");
            ConsultaFormularioGuardado consultaFormulario = new ConsultaFormularioGuardado(getContext());
            JSONObject formulario;
            try {
                formulario = consultaFormulario.consultarFormulario(String.valueOf(id_formulario));
                Log.d("formulario", formulario.getString("id"));
            } catch (JSONException e) {
                actualizar = false;
                return;
            }

            try {
                medio_atencion_id = Integer.parseInt(formulario.getString("medio_atencion_id"));
                solicitante_id = Integer.parseInt(formulario.getString("solicitante_id"));
                if (medio_atencion_id == 5 || medio_atencion_id == 6) {
                    linearLayout.setVisibility(View.VISIBLE);
                    til_pqrsf.setVisibility(view.GONE);
                    pqrsf_id = 6;
                } else {
                    til_pqrsf.setVisibility(view.VISIBLE);
                    pqrsf_id = Integer.parseInt(formulario.getString("pqrsf_id"));
                }
                if (solicitante_id == 1) {
                    til_nit.setVisibility(View.VISIBLE);
                    tie_nit.setText(formulario.getString("nit"));
                } else if (solicitante_id == 2 || solicitante_id == 3) {
                    til_numero_documento.setVisibility(View.VISIBLE);
                    tie_numero_documento.setText(formulario.getString("nit"));
                }

                JSONArray evidencias = formulario.getJSONArray("evidencias");
                List<Evidencia> evidencias1 = new ArrayList<>();
                for (int i = 0; i < evidencias.length(); i++) {
                    JSONObject obj = evidencias.getJSONObject(i);
                    int idObject = obj.getInt("id");
                    int formulario_id = obj.getInt("formulario_id");
                    String nombre = obj.getString("nombre");
                    String path = obj.getString("path");
                    String descripcion = obj.getString("descripcion");
                    Evidencia evidencia1 = new Evidencia(idObject, formulario_id, nombre, path, descripcion);
                    evidencias1.add(evidencia1);
                }
                JSONArray compromisos = formulario.getJSONArray("compromisos");
                compromisos1 = new ArrayList<>();
                for (int i = 0; i < compromisos.length(); i++) {
                    JSONObject obj = compromisos.getJSONObject(i);
                    int idObject = obj.getInt("key");
                    int formulario_id = obj.getInt("formulario_id");
                    String compromiso = obj.getString("compromiso");
                    String responsable_compromiso = obj.getString("responsable");
                    int responsable_id = obj.getInt("responsable_id");
                    String estado_compromiso = obj.getString("estado");
                    int estado_id = obj.getInt("estado_id");
                    String observacion = obj.getString("observacion");
                    Compromiso compromiso1 = new Compromiso(idObject, formulario_id, compromiso, responsable_compromiso, responsable_id, estado_compromiso, estado_id, observacion);
                    compromisos1.add(compromiso1);
                    compromisosglobal.add(compromiso1);
                    agregarCompromiso();
                }

                JSONArray asistentes_visita = formulario.getJSONArray("asistentes");
                List<Asistente> asistentes1 = new ArrayList<>();
                for (int i = 0; i < asistentes_visita.length(); i++) {
                    JSONObject obj = asistentes_visita.getJSONObject(i);
                    int idObject = obj.getInt("id");
                    int formulario_id = obj.getInt("formulario_id");
                    String cargo = obj.getString("cargo");
                    String nombre = obj.getString("nombre");
                    String correo = obj.getString("correo");
                    Bitmap Bitmapfirma = convertirBase64ABitmap(obj.getString("firma"));
                    Bitmap firma = Bitmapfirma;
                    Asistente asistente1 = new Asistente(idObject, formulario_id, nombre, cargo, correo, firma);
                    asistentes1.add(asistente1);
                }
                if (evidencias1.size() > 0) { // se verifica que el adaptador halla sido inicializado antes de llamarlo
                    adapterEvidencia = new AdapterEvidencia(evidencias1);
                    recyclerEvidencia.setAdapter(adapterEvidencia);
                }
                if (compromisos1.size() > 0) { // se verifica que el adaptador halla sido inicializado antes de llamarlo
                    for (int i = 0; i < inputs.size(); i += 4) {
                        int compromisoIndex = i / 4; // Cada 4 elementos en 'inputs' corresponde a un 'compromiso'
                        if (i + 3 < inputs.size() && compromisoIndex < compromisos1.size()) {  // Verifica que hay suficientes elementos
                            inputs.get(i).setTag(compromisos1.get(compromisoIndex).getId());
                            inputs.get(i).setText(compromisos1.get(compromisoIndex).getCompromiso());
                            inputs.get(i + 1).setText(compromisos1.get(compromisoIndex).getResponsable());
                            inputs.get(i + 2).setText(compromisos1.get(compromisoIndex).getEstado());
                            inputs.get(i + 3).setText(compromisos1.get(compromisoIndex).getObservacion());

                            if (responsableid_cierreid_compromiso.size() > 0) {
                                int auxIndex = i / 4 * 2; // Calcula el índice en responsableid_cierreid_compromiso
                                if (responsableid_cierreid_compromiso.size() > auxIndex + 1) {
                                    responsableid_cierreid_compromiso.add(String.valueOf(compromisos1.get(compromisoIndex).getResponsable_id()));
                                    responsableid_cierreid_compromiso.add(String.valueOf(compromisos1.get(compromisoIndex).getEstado_id()));
                                }
                            }

                        }
                    }
                }
                if (asistentes1.size() > 0) { // se verifica que el adaptador halla sido inicializado antes de llamarlo
                    adapterAsistentes = new AdapterAsistentes(asistentes1);
                    recyclerAsistente.setAdapter(adapterAsistentes);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            try {
                tie_sede.setText(formulario.getString("sede"));
                sede_id = Integer.parseInt(formulario.getString("sede_id"));
                tie_proceso.setText(formulario.getString("proceso"));
                proceso_id = Integer.parseInt(formulario.getString("proceso_id"));
                tie_solicitante.setText(formulario.getString("solicitante"));
                solicitante_id = Integer.parseInt(formulario.getString("solicitante_id"));
                tie_tipo_atencion.setText(formulario.getString("medio_atencion"));
                medio_atencion_id = Integer.parseInt(formulario.getString("medio_atencion_id"));
                tie_razon_social.setText(formulario.getString("razon_social"));
                tie_cierra_radicado.setText(formulario.getString("cierra_radicado"));
                tie_telefono.setText(formulario.getString("telefono"));
                tie_correo.setText(formulario.getString("correo"));
                tie_visitante.setText(formulario.getString("visitante"));
                visitante_id = Integer.parseInt(formulario.getString("visitante_id"));
                tie_cargo_visitante.setText(formulario.getString("cargo_visitante"));
                cargo_visitante_id = Integer.parseInt(formulario.getString("cargo_visitante_id"));
                tie_visitado.setText(formulario.getString("visitado"));
                tie_cargo_visitado.setText(formulario.getString("cargo_visitado"));
                tie_objetivo.setText(formulario.getString("objetivo"));
                tie_alcance.setText(formulario.getString("alcance"));
                tie_tema_visita.setText(formulario.getString("tema"));
                tie_estado.setText(formulario.getString("estado"));
                estado_pqrsf_id = Integer.parseInt(formulario.getString("estado_id"));
                tie_pqrs.setText(formulario.getString("pqrsf"));
                tie_responsable.setText(formulario.getString("responsable"));
                correo_responsable = formulario.getString("correo_responsable");
                responsable_id = Integer.parseInt(formulario.getString("responsable_id"));
                tie_hora_inicio.setText(formulario.getString("hora_incio"));
                tie_observacion.setText(formulario.getString("observaciones"));
                if (estado_pqrsf_id == 3) {
                    til_cierra_radicado.setVisibility(View.VISIBLE);
                } else if (estado_pqrsf_id == 4) {
                    til_cierra_radicado.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void nuevoAsistente() {
        Asistente asistente = new Asistente();
        List<Asistente> asistentes = new ArrayList<>();
        asistente.setNombre("");
        asistente.setCargo("");
        asistente.setCorreo("");
        asistentes.add(asistente);
        adapterAsistentes = new AdapterAsistentes(asistentes);
    }

    public void mensajeError(TextInputEditText textInputEditText, TextInputLayout textInputLayout) {
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayout.setErrorEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (!actualizar) {
                textInputLayout.setErrorEnabled(false);
//                }
            }
        });
    }

    public Bitmap convertirBase64ABitmap(String base64String) {
        try {
            // Decodificar la cadena base64 en un array de bytes
            byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
            // Convertir el array de bytes en un Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return bitmap;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void permisosGps() {
        // Verificar y solicitar permisos
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (obtenerIntentospermisosGps() == "1") {
                mostrarDialogoExplicativoActivacionLocalizacion();
                return;
            }
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                // El usuario ya denegó antes, mostrar explicación y solicitar de nuevo
                establecerIntentospermisosGps("1");
                mostrarDialogoExplicativo();
            } else {
                // Solicitar el permiso directamente
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISO_UBICACION_REQUEST_CODE);
            }
        } else {
            establecerIntentospermisosGps("0");
        }
    }

    public void scrollToField(ScrollView scrollView, TextInputEditText targetField) {
        if (scrollView != null && targetField != null) {
            int targetY = targetField.getTop(); // Obtener la posición Y del campo objetivo

            // Animar el desplazamiento
            ValueAnimator animator = ValueAnimator.ofInt(scrollView.getScrollY(), targetY);
            animator.setDuration(1000); // Duración de la animación en milisegundos
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scrollView.scrollTo(0, (int) animation.getAnimatedValue());
                }
            });
            animator.start();
        }
    }

    public byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // Puedes cambiar PNG a JPEG según tu necesidad
        return stream.toByteArray();
    }

    private void establecerIntentospermisosGps(String intentos) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("gps", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("intentos", intentos);
        editor.apply(); // O editor.commit();
    }

    private String obtenerIntentospermisosGps() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("gps", Context.MODE_PRIVATE);
        return sharedPreferences.getString("intentos", "");
    }

    private void mostrarDialogoExplicativo() {
        new AlertDialog.Builder(getContext())
                .setTitle("Permiso de Ubicación Necesario")
                .setMessage("Esta aplicación necesita acceso a la ubicación para poder guardar el formulario correctamente.")
                .setPositiveButton("Conceder Permiso", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Solicitar el permiso nuevamente
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                100);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // El usuario decidió cancelar, volver a solicitarlo
                        Toast.makeText(getContext(), "Debes conceder el permiso para continuar.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(true)
                .show();
    }

    private void mostrarDialogoExplicativoActivacionLocalizacion() {
        new AlertDialog.Builder(getContext())
                .setTitle("Permiso de Ubicación Necesario")
                .setMessage("Esta aplicación necesita acceso a la ubicación para poder guardar el formulario correctamente. debes activar los permisos de ubicación de manera manual desde la configuración del dispositivo.")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        irAConfiguracionDeAplicacion();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "La aplicación no cuenta con los permisos de ubicación.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void irAConfiguracionDeAplicacion() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", requireActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }


    private void obtenerUbicacion(Formulario_visitas formulario, View view) {

        locationManagerHelper.getLastLocation(new LocationManagerHelper.LocationListener() {
            @Override
            public void onLocationReceived(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                formulario.setLatitud(String.valueOf(latitude));
                formulario.setLongitud(String.valueOf(longitude));
                try {
                    id_formulario = (int) GuardaformularioVisita.guardaFormulario(getContext(), formulario);
                    actualizarDinamicos(view);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                actualizar = true;

            }

            @Override
            public void onLocationError(String errorMessage) {
                Log.e("LocationError", errorMessage);
            }
        });
    }

    public void agregarEvidencia(String nombre, String path) {
        // Verifica si el adaptador es null y lo inicializa si es necesario
        if (adapterEvidencia == null) {
            evidencias = new ArrayList<>();
            adapterEvidencia = new AdapterEvidencia(evidencias);
            recyclerEvidencia.setAdapter(adapterEvidencia);
        } else if (actualizar) {
            // Solo actualiza evidencias si no es null
            List<Evidencia> nuevaLista = adapterEvidencia.getEvidencias();
            if (nuevaLista != null) {
                evidencias = nuevaLista;
            }
        }

        // Asegúrate de que la lista evidencias esté inicializada
        if (evidencias == null) {
            evidencias = new ArrayList<>();
        }

        // Añade la nueva evidencia
        Evidencia nuevaEvidencia = new Evidencia(nombre, path, "");
        evidencias.add(nuevaEvidencia);

        // Notifica al adaptador que los datos han cambiado
        adapterEvidencia.notifyDataSetChanged();
    }


    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*"); // Esto permite seleccionar cualquier tipo de archivo
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Selecciona un archivo"), PICK_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_FILE) {
            Uri fileUri = data.getData();
            if (fileUri != null) {
                String filePath = String.valueOf(fileUri);
                String fileName = getFileName(fileUri);
                getContext().getContentResolver().takePersistableUriPermission(
                        fileUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                );

                Toast.makeText(getContext(), "Archivo seleccionado: " + fileName, Toast.LENGTH_SHORT).show();
                agregarEvidencia(fileName, filePath);
            }
        }
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String fileName = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                // Obtener el nombre del archivo desde el cursor
                fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                cursor.close();
            }
        }

        // Si no se obtiene el nombre desde el esquema "content", usar el último segmento de la URI
        if (fileName == null) {
            fileName = uri.getLastPathSegment();
        }
        return fileName;
    }

    public void agregarCompromiso() {
        TextInputLayout textInputLayout1 = crearTextInputLayout("Compromiso");
        TextInputLayout textInputLayout2 = crearTextInputLayout("Responsable");
        TextInputLayout textInputLayout3 = crearTextInputLayout("Estado");
        TextInputLayout textInputLayout4 = crearTextInputLayout("Observación");

        textInputLayout1.setBoxBackgroundColor(Color.TRANSPARENT);
        textInputLayout2.setBoxBackgroundColor(Color.TRANSPARENT);
        textInputLayout3.setBoxBackgroundColor(Color.TRANSPARENT);
        textInputLayout4.setBoxBackgroundColor(Color.TRANSPARENT);

        textInputLayout1.setErrorEnabled(true);
        textInputLayout2.setErrorEnabled(true);
        textInputLayout3.setErrorEnabled(true);
        textInputLayout4.setErrorEnabled(true);

        // Asignar IDs a los TextInputLayouts
        textInputLayout2.setId(R.id.til_responsable + indexOfExistingField + 2);  // Para manejar lista
        textInputLayout3.setId(R.id.til_estado_compromiso + indexOfExistingField + 3);  // Para manejar lista

        linearLayout.addView(textInputLayout1, indexOfExistingField + 1);
        linearLayout.addView(textInputLayout2, indexOfExistingField + 2);


        // Crear y agregar nuevos RecyclerViews y adaptadores
        RecyclerView recyclerViewResponsable = crearRecyclerView();
        RecyclerView recyclerViewEstadoCompromiso = crearRecyclerView();

        recyclerViewResponsable.setVisibility(View.GONE);
        recyclerViewEstadoCompromiso.setVisibility(View.GONE);

        List<Responsable> responsable = ConsultaResponsable.obtenerResponsables(getContext());
        List<EstadoCompromiso> estadoCompromisos = ConsultaEstadoCompromiso.obtenerEstadosCompromiso(getContext());

        AdapterResponsables adapterResponsable = new AdapterResponsables(responsable);
        AdapterEstadoCompromiso adapterEstadoCompromiso = new AdapterEstadoCompromiso(estadoCompromisos);

        configurarRecyclerView(recyclerViewResponsable, adapterResponsable);
        configurarRecyclerView(recyclerViewEstadoCompromiso, adapterEstadoCompromiso);

        linearLayout.addView(recyclerViewResponsable, indexOfExistingField + 3);
        linearLayout.addView(textInputLayout3, indexOfExistingField + 4);
        linearLayout.addView(recyclerViewEstadoCompromiso, indexOfExistingField + 5);
        linearLayout.addView(textInputLayout4, indexOfExistingField + 6);


        // Obtener el EditText de cada TextInputLayout
        TextInputEditText editTextResponsable = (TextInputEditText) textInputLayout2.getEditText();
        editTextResponsable.setId(View.generateViewId());
        editTextResponsable.setPadding(50, 50, 50, 50);
        editTextResponsable.setTag(posicionCompromiso);
        posicionCompromiso++;

        TextInputEditText editTextEstadoCompromiso = (TextInputEditText) textInputLayout3.getEditText();
        editTextEstadoCompromiso.setId(View.generateViewId());
        editTextEstadoCompromiso.setPadding(50, 50, 50, 50);
        editTextEstadoCompromiso.setTag(posicionCompromiso);
        posicionCompromiso++;


        TextInputEditText editTextCompromiso = (TextInputEditText) textInputLayout1.getEditText();
        editTextEstadoCompromiso.setId(View.generateViewId());
        editTextCompromiso.setPadding(50, 50, 50, 50);

        TextInputEditText editTextObservacion = (TextInputEditText) textInputLayout4.getEditText();
        editTextEstadoCompromiso.setId(View.generateViewId());
        editTextObservacion.setPadding(50, 50, 50, 50);

        InputFilter[] compromisoFilters = new InputFilter[]{new InputFilter.LengthFilter(4000)};
        InputFilter[] observacionFilters = new InputFilter[]{new InputFilter.LengthFilter(3000)};
        editTextCompromiso.setFilters(compromisoFilters);
        editTextObservacion.setFilters(observacionFilters);
        textInputLayout1.setCounterEnabled(true);
        textInputLayout1.setCounterMaxLength(4000);
        textInputLayout4.setCounterEnabled(true);
        textInputLayout4.setCounterMaxLength(3000);

        inputs.add(editTextCompromiso);
        inputs.add(editTextResponsable);
        inputs.add(editTextEstadoCompromiso);
        inputs.add(editTextObservacion);

        inputslayout.add(textInputLayout1);
        inputslayout.add(textInputLayout2);
        inputslayout.add(textInputLayout3);
        inputslayout.add(textInputLayout4);

        // Marca la posición del último elemento insertado
        indexOfExistingField += 6;

        // Aplicar los filtros al textInputLayout
        editTextResponsable.addTextChangedListener(new GenericTextWatcher(adapterResponsable));
        editTextEstadoCompromiso.addTextChangedListener(new GenericTextWatcher(adapterEstadoCompromiso));

        if (actualizar) {
            // Verifica que la posición solicitada sea válida en la lista
            if (posicionObjetoCompromiso < compromisosglobal.size()) {
                responsableid_cierreid_compromiso.add(String.valueOf(compromisosglobal.get(posicionObjetoCompromiso).getResponsable_id()));
                responsableid_cierreid_compromiso.add(String.valueOf(compromisosglobal.get(posicionObjetoCompromiso).getEstado_id()));

                posicionObjetoCompromiso++;
            } else {
                // Si la lista es demasiado pequeña para acceder al índice, maneja el caso adecuadamente
                responsableid_cierreid_compromiso.add("0");
                responsableid_cierreid_compromiso.add("0");
            }
        } else {
            responsableid_cierreid_compromiso.add("0");
            responsableid_cierreid_compromiso.add("0");
        }


        adapterResponsable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerViewResponsable.getChildAdapterPosition(v);
                if (position != RecyclerView.NO_POSITION) {
                    Responsable item = adapterResponsable.getItemAtPosition(position); // Asegúrate de implementar este método
                    editTextResponsable.setText(item.getNombre());
                    Toast.makeText(getContext(), item.getEmail(), Toast.LENGTH_SHORT).show();
                    recyclerViewResponsable.setVisibility(View.GONE);
                    textInputLayout2.setErrorEnabled(false);
                    int posicion = (int) editTextResponsable.getTag();
                    Log.d("posicion ", "" + posicion);
                    Log.d("posicion array", responsableid_cierreid_compromiso.toString());
                    responsableid_cierreid_compromiso.set(posicion, String.valueOf(item.getId()));
                }
            }
        });

        adapterEstadoCompromiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerViewEstadoCompromiso.getChildAdapterPosition(v);
                if (position != RecyclerView.NO_POSITION) {
                    EstadoCompromiso item = adapterEstadoCompromiso.getItemAtPosition(position); // Asegúrate de implementar este método
                    editTextEstadoCompromiso.setText(item.getNombre());
                    recyclerViewEstadoCompromiso.setVisibility(View.GONE);
                    textInputLayout3.setErrorEnabled(false);

                    // Obtener la posición desde el tag del EditText
                    int posicion = (int) editTextEstadoCompromiso.getTag();
                    Log.d("posicion ", "" + posicion);
                    Log.d("posicion array", responsableid_cierreid_compromiso.toString());
                    responsableid_cierreid_compromiso.set(posicion, String.valueOf(item.getId()));

                }
            }
        });

        configurarClickListener(editTextResponsable, recyclerViewResponsable);
        configurarClickListener(editTextEstadoCompromiso, recyclerViewEstadoCompromiso);

    }


    // Función para configurar RecyclerView
    public void configurarRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adaptador) {
        recyclerView.setAdapter(adaptador);
    }


    // Método para crear un nuevo RecyclerView
    private RecyclerView crearRecyclerView() {
        recyclerView = new RecyclerView(getContext());

        // Configurar el LayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Configurar los parámetros de Layout para el RecyclerView
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                200  // Altura fija para mostrar varias filas, ajusta según necesidad
        );
//        layoutParams.setMargins(16, 16, 16, 16);  // Márgenes si es necesario
        recyclerView.setLayoutParams(layoutParams);

        recyclerView.setBackgroundResource(R.drawable.border_recyclerview);

        recyclerView.setPadding(2, 2, 2, 2); // Padding de 2dp en todos los lados

        // Establecer la elevación (solo en Android 5.0 y superior)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            recyclerView.setElevation(5f); // Elevación de 5dp
        }

        recyclerView.setClipToPadding(true);

        return recyclerView;
    }

    private void configurarClickListener(final TextInputEditText textInputEditText, RecyclerView recycler) {
        textInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!textInputEditText.getText().toString().isEmpty()) {
                        adapterSedes.filter("");
                        adapterProcesos.filter("");
                        adapterSolicitantes.filter("");
                        adapterTiposAtencion.filter("");
                        adapterVisitante.filter("");
                        adapterCargoVisitante.filter("");
                        adapterEstados.filter("");
                        adapterPQRSF.filter("");
                    }
                    try {
                        String resourceName = getResources().getResourceEntryName(textInputEditText.getId());
                        recyclerVisible(resourceName, recycler);
                    } catch (Exception e) {
                        String resourceName = textInputEditText.getId() + "";
                        recyclerVisible(resourceName, recycler);
                    }

                }else{
                    limpiarCampoTipoLista();
                }
            }
        });

        textInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String resourceName = getResources().getResourceEntryName(textInputEditText.getId());
                    recyclerVisible(resourceName, recycler);
                } catch (Exception e) {
                    String resourceName = textInputEditText.getId() + "";
                    recyclerVisible(resourceName, recycler);
                }
            }
        });
    }

    public void limpiarCampoTipoLista(){
        if (sede_id == 0 && !tie_sede.getText().toString().isEmpty()) {
            tie_sede.setText("");
        }if(proceso_id == 0 && !tie_proceso.getText().toString().isEmpty()){
            tie_proceso.setText("");
        }if(solicitante_id == 0 && !tie_solicitante.getText().toString().isEmpty()){
            tie_solicitante.setText("");
        }if(medio_atencion_id == 0 && !tie_tipo_atencion.getText().toString().isEmpty()){
            tie_tipo_atencion.setText("");
        }if(nit_id == 0 && !tie_nit.getText().toString().isEmpty()){
            tie_nit.setText("");
        }if(estado_pqrsf_id == 0 && !tie_estado.getText().toString().isEmpty()){
            tie_estado.setText("");
        }if(pqrsf_id == 0 && !tie_pqrs.getText().toString().isEmpty()){
            tie_pqrs.setText("");
        }if(responsable_id == 0 && !tie_responsable.getText().toString().isEmpty()){
            tie_responsable.setText("");
        }if(visitante_id == 0 && !tie_visitante.getText().toString().isEmpty()){
            tie_visitante.setText("");
        }if(cargo_visitante_id == 0 && !tie_cargo_visitante.getText().toString().isEmpty()){
            tie_cargo_visitante.setText("");
        }
    }


    public void recyclerVisible(String resourceName, RecyclerView recycler) {
        recyclerInvisible();
        recycler.setVisibility(View.VISIBLE);
    }

    public void recyclerInvisible() {
        recyclerSedes.setVisibility(View.GONE);
        recyclerProcesos.setVisibility(View.GONE);
        recyclerSolicitantes.setVisibility(View.GONE);
        recyclerTiposAtencion.setVisibility(View.GONE);
        recyclerEstados.setVisibility(View.GONE);
        recyclerPqrsf.setVisibility(View.GONE);
        recyclerResponsables.setVisibility(View.GONE);
        recyclerVisitantes.setVisibility(View.GONE);
        recyclerCargoVisitante.setVisibility(View.GONE);
        recyclerClientes.setVisibility(View.GONE);
        recyclerCierraRadicado.setVisibility(View.GONE);

    }

    private void showTimePicker(final TextInputLayout textInputLayout) {
        // Obtener la hora actual
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Crear un TimePickerDialog y establecer la hora actual como predeterminada
        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute1) {
                        Calendar selectedTime = Calendar.getInstance();
                        selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedTime.set(Calendar.MINUTE, minute1);
                        updateInput(textInputLayout, selectedTime);
                    }
                }, hour, minute, false);

        // Mostrar el TimePickerDialog
        timePickerDialog.show();
    }

    private void updateInput(TextInputLayout textInputLayout, Calendar selectedTime) {
        // Actualizar el TextInputLayout con la hora seleccionada
        String formattedTime = formatTime(selectedTime);
        textInputLayout.getEditText().setText(formattedTime);
    }

    private String formatTime(Calendar calendar) {
        // Formatear la hora como desees
        // En este ejemplo, se muestra la hora en formato de 24 horas ("HH:mm")
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return hora.format(calendar.getTime());
    }

    public TextInputLayout crearTextInputLayout(String hintText) {
        // Crear un nuevo TextInputLayout
        TextInputLayout nuevoTextInputLayout = new TextInputLayout(getContext());

        // Configurar el modo de fondo del TextInputLayout
        nuevoTextInputLayout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
        nuevoTextInputLayout.setBoxBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white)); // Color de fondo

        // Crear un nuevo EditText
        TextInputEditText nuevoEditText = new TextInputEditText(nuevoTextInputLayout.getContext()); // Usar el contexto del TextInputLayout

        // Configurar el EditText
        nuevoEditText.setHint(hintText);
        nuevoEditText.setTextSize(17);
        nuevoEditText.setPadding(19, 19, 19, 19);

        // Configurar los parámetros del TextInputLayout
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, 7);  // Establecer márgenes si es necesario
        nuevoTextInputLayout.setLayoutParams(layoutParams);

        // Agregar el EditText al TextInputLayout
        nuevoTextInputLayout.addView(nuevoEditText);

        return nuevoTextInputLayout;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ActaReunionDDViewModel.class);
        // TODO: Use the ViewModel
    }

    public void actualizarDinamicos(View view) throws JSONException {
        Log.d("actualizando", "actualizando dinamicos");
        ConsultaFormularioGuardado consultaFormulario = new ConsultaFormularioGuardado(getContext());
        JSONObject formulario = consultaFormulario.consultarFormulario(String.valueOf(id_formulario));


        JSONArray evidencias_ = formulario.getJSONArray("evidencias");
        if (evidencias_.length() >= 1) {
            List<Evidencia> evidencias1 = new ArrayList<>();
            for (int i = 0; i < evidencias_.length(); i++) {
                JSONObject obj = evidencias_.getJSONObject(i);
                int idObject = obj.getInt("id");
                int formulario_id = obj.getInt("formulario_id");
                String nombre = obj.getString("nombre");
                String path = obj.getString("path");
                String descripcion = obj.getString("descripcion");
                Evidencia evidencia1 = new Evidencia(idObject, formulario_id, nombre, path, descripcion);
                evidencias1.add(evidencia1);
            }

            adapterEvidencia = new AdapterEvidencia(evidencias1);
            recyclerEvidencia.setAdapter(adapterEvidencia);
            adapterEvidencia.notifyDataSetChanged();
        }


        JSONArray asistentes_visita = formulario.getJSONArray("asistentes");
        if (asistentes_visita.length() >= 1) {
            List<Asistente> asistentes1 = new ArrayList<>();
            for (int i = 0; i < asistentes_visita.length(); i++) {
                JSONObject obj = asistentes_visita.getJSONObject(i);
                int idObject = obj.getInt("id");
                int formulario_id = obj.getInt("formulario_id");
                String cargo = obj.getString("cargo");
                String nombre = obj.getString("nombre");
                String correo = obj.getString("correo");
                Bitmap Bitmapfirma = convertirBase64ABitmap(obj.getString("firma"));
                Bitmap firma = Bitmapfirma;
                Asistente asistente1 = new Asistente(idObject, formulario_id, nombre, cargo, correo, firma);
                asistentes1.add(asistente1);
            }
            adapterAsistentes = new AdapterAsistentes(asistentes1);
            recyclerAsistente.setAdapter(adapterAsistentes);
            adapterAsistentes.notifyDataSetChanged();
        }


        JSONArray compromisos = formulario.getJSONArray("compromisos");
        if (compromisos.length() >= 1) {
            for (TextInputLayout input : inputslayout) {
                linearLayout.removeView(input);
            }
            for (TextInputEditText input : inputs) {
                linearLayout.removeView(input);
            }
            inputslayout.clear();
            inputs.clear();
            //restablece la posicion de donde se deben empezar a insertar los campos de compromiso
            indexOfExistingField = linearLayout.indexOfChild(view.findViewById(R.id.tv_compromisos));

            compromisos1 = new ArrayList<>();
            for (int i = 0; i < compromisos.length(); i++) {
                JSONObject obj = compromisos.getJSONObject(i);
                int idObject = obj.getInt("key");
                int formulario_id = obj.getInt("formulario_id");
                String compromiso = obj.getString("compromiso");
                String responsable_compromiso = obj.getString("responsable");
                int responsable_id = obj.getInt("responsable_id");
                String estado_compromiso = obj.getString("estado");
                int estado_id = obj.getInt("estado_id");
                String observacion = obj.getString("observacion");
                Compromiso compromiso1 = new Compromiso(idObject, formulario_id, compromiso, responsable_compromiso, responsable_id, estado_compromiso, estado_id, observacion);
                compromisosglobal.add(compromiso1);
                agregarCompromiso();
            }

            if (compromisosglobal.size() > 0) { // se verifica que el adaptador halla sido inicializado antes de llamarlo
                for (int i = 0; i < inputs.size(); i += 4) {
                    int compromisoIndex = i / 4; // Cada 4 elementos en 'inputs' corresponde a un 'compromiso'
                    if (i + 3 < inputs.size() && compromisoIndex < compromisosglobal.size()) {  // Verifica que hay suficientes elementos
                        inputs.get(i).setTag(compromisosglobal.get(compromisoIndex).getId());
                        inputs.get(i).setText(compromisosglobal.get(compromisoIndex).getCompromiso());
                        inputs.get(i + 1).setText(compromisosglobal.get(compromisoIndex).getResponsable());
                        inputs.get(i + 2).setText(compromisosglobal.get(compromisoIndex).getEstado());
                        inputs.get(i + 3).setText(compromisosglobal.get(compromisoIndex).getObservacion());

                        if (responsableid_cierreid_compromiso.size() > 0) {
                            int auxIndex = i / 4 * 2; // Calcula el índice en responsableid_cierreid_compromiso
                            if (responsableid_cierreid_compromiso.size() > auxIndex + 1) {
                                responsableid_cierreid_compromiso.add(String.valueOf(compromisosglobal.get(compromisoIndex).getResponsable_id()));
                                responsableid_cierreid_compromiso.add(String.valueOf(compromisosglobal.get(compromisoIndex).getEstado_id()));
                            }
                        }

                    }
                }
            }
        }

    }

}