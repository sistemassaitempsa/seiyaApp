package com.example.saitemp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.saitemp.bd.saitemp.DatabaseHelper;
import com.example.saitemp.bd.saitemp.ReconstruirTabla;
import com.example.saitemp.clases.TablaPrincipal;
import com.example.saitemp.consultasApi.ConsultaActualizacionTabla;
import com.example.saitemp.consultasApi.ConsultaCargoVisitante;
import com.example.saitemp.consultasApi.ConsultaDebidaDiligencia;
import com.example.saitemp.consultasApi.ConsultaEstadoCompromiso;
import com.example.saitemp.consultasApi.ConsultaEstados;
import com.example.saitemp.consultasApi.ConsultaPQRSF;
import com.example.saitemp.consultasApi.ConsultaProcesos;
import com.example.saitemp.consultasApi.ConsultaResponsable;
import com.example.saitemp.consultasApi.ConsultaSedes;
import com.example.saitemp.consultasApi.ConsultaSolicitante;
import com.example.saitemp.consultasApi.ConsultaTablasApi;
import com.example.saitemp.consultasApi.ConsultaTipoAtencion;
import com.example.saitemp.consultasApi.ConsultaVisitantes;
import com.example.saitemp.interfaz.ApiCallbackTablas;
import com.example.saitemp.interfaz.ApiCallbackTablas2;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;


public class MainActivity extends AppCompatActivity {

    private TextInputEditText correo;
    private TextInputEditText contrasena;

    private Button ingresar;

    private Button offline;

    private ProgressBar progressbar;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private List<TablaPrincipal> listaTablas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contrasena);
        ingresar = findViewById(R.id.ingresar);
        progressbar = findViewById(R.id.indeterminate_linear_indicator);

        offline = findViewById(R.id.btn_offline);

        progressbar.setVisibility(View.GONE);

        databaseHelper = new DatabaseHelper(getBaseContext());
        database = databaseHelper.getWritableDatabase();

        credenciales();

        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NavegacionActivity.class);
//                intent.putExtra("fragmentToLoad", "Fragment_formulario");
                SharedPreferences sharedPreferences = getSharedPreferences("saitemp", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("token");
                editor.apply();

                startActivity(intent);
            }
        });


        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {

                    String username = correo.getText().toString();
                    String password = contrasena.getText().toString();
                    progressbar.setVisibility(View.VISIBLE);
                    loginRequest(username, password);

//                    makeVolleyRequest();
                } else {
                    Toast.makeText(getApplicationContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        configuraApp();
    }

    public void configuraApp() {
        listaTablas = ConsultaActualizacionTabla.consultaListaTablas(getBaseContext());
        if (listaTablas.size() == 0) {
            if (isNetworkAvailable()) {
//            Consulta las tablas y registros desde el servidor verificandi si hay conexion a internet
                guardaRegistroTablas();
                offline.setVisibility(View.VISIBLE);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Sin conexión a internet");
                builder.setMessage("Este dispositivo no cuenta con una conexión a internet por favor encienda los datos móviles o conecte el dispositivo a una red wifi para configurar la app por primera vez.");
                AlertDialog alert = builder.create();
                alert.show();

                offline.setVisibility(View.INVISIBLE);
            }
        } else {
            List<TablaPrincipal> tablas = new ArrayList<>();
            tablas = ConsultaActualizacionTabla.consultaListaTablas(getBaseContext());
            verificaActualizaTablas(tablas);

        }
    }

    private void loginRequest(final String email, final String password) {
        String apiUrl = " https://debidadiligencia.saitempsa.com:8484/aplicaciones/api/public/api/v1/login";
        MainActivity SSLHelper = null;
        SSLHelper.disableSSLCertificateChecking();

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                apiUrl,
                jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            String respuesta = response.getString("status");
                            if (respuesta.equals("error")) {
                                Toast.makeText(getBaseContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                progressbar.setVisibility(View.INVISIBLE);
                            } else {
                                String token = response.getString("access_token");
                                SharedPreferences sharedPreferences = getSharedPreferences("saitemp", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("token", token);
                                editor.putString("usuario", correo.getText().toString());
                                editor.putString("contrasena", contrasena.getText().toString());
                                editor.apply();

                                Intent intent = new Intent(getApplicationContext(), NavegacionActivity.class);
                                progressbar.setVisibility(View.GONE);
                                startActivity(intent);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja errores de la solicitud
                        Log.e("Login Error", error.toString());
                        Toast.makeText(MainActivity.this, "Verifique las credenciales de acceso. ", Toast.LENGTH_SHORT).show();
                        progressbar.setVisibility(View.GONE);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Si tu servicio de autenticación requiere encabezados adicionales, puedes agregarlos aquí
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        // Añade la solicitud a la cola de Volley
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }


    private void credenciales() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("saitemp", Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", "");
        String contrasena_usuario = sharedPreferences.getString("contrasena", "");
        if (!usuario.isEmpty() && !contrasena_usuario.isEmpty()) {
            correo.setText(usuario);
            contrasena.setText(contrasena_usuario);
        }
    }

    public static void disableSSLCertificateChecking() {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                // Aceptar cualquier nombre de host
                return true;
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void guardaRegistroTablas() {

        String url = "https://debidadiligencia.saitempsa.com:8484/aplicaciones/api/public/api/v1/tablasandroid2";
        ConsultaTablasApi.consultaTablasApi2(getBaseContext(), url, new ApiCallbackTablas2.ApiCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                // Procesar el JSONArray aquí
                try {
                    JSONArray usr_app_tablas_android = result.getJSONArray("usr_app_tablas_android");
                    JSONArray usr_app_sedes_saitemp = result.getJSONArray("usr_app_sedes_saitemp");
                    JSONArray usr_app_estado_cierre_crm = result.getJSONArray("usr_app_estado_cierre_crm");
                    JSONArray usr_app_estado_compromiso_crm = result.getJSONArray("usr_app_estado_compromiso_crm");
                    JSONArray usr_app_cargos_crm = result.getJSONArray("usr_app_cargos_crm");
                    JSONArray usr_app_pqrsf_crm = result.getJSONArray("usr_app_pqrsf_crm");
                    JSONArray usr_app_solicitante_crm = result.getJSONArray("usr_app_solicitante_crm");
                    JSONArray usr_app_atencion_interacion = result.getJSONArray("usr_app_atencion_interacion");
                    JSONArray usr_app_procesos = result.getJSONArray("usr_app_procesos");
                    JSONArray usr_app_usuarios_responsable = result.getJSONArray("usr_app_usuarios_responsable");
                    JSONArray usr_app_usuarios_visitante = result.getJSONArray("usr_app_usuarios_visitante");
                    JSONArray usr_app_clientes = result.getJSONArray("usr_app_clientes");

                    ConsultaActualizacionTabla.llenarTabla(getBaseContext(), usr_app_tablas_android);
                    ConsultaEstados.llenarTabla(getBaseContext(), usr_app_estado_cierre_crm);
                    ConsultaCargoVisitante.llenarTabla(getBaseContext(), usr_app_cargos_crm);
                    ConsultaPQRSF.llenarTabla(getBaseContext(), usr_app_pqrsf_crm);
                    ConsultaSedes.llenarTabla(getBaseContext(), usr_app_sedes_saitemp);
                    ConsultaSolicitante.llenarTabla(getBaseContext(), usr_app_solicitante_crm);
                    ConsultaTipoAtencion.llenarTabla(getBaseContext(), usr_app_atencion_interacion);
                    ConsultaEstadoCompromiso.llenarTabla(getBaseContext(), usr_app_estado_compromiso_crm);
                    ConsultaProcesos.llenarTabla(getBaseContext(), usr_app_procesos);
                    ConsultaVisitantes.llenarTabla(getBaseContext(), usr_app_usuarios_visitante);
                    ConsultaResponsable.llenarTabla(getBaseContext(), usr_app_usuarios_responsable);
                    ConsultaDebidaDiligencia.llenarTabla(getBaseContext(), usr_app_clientes);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String error) {
                Log.e("API Error", "Error en la solicitud API: " + error.toString());
            }
        });
    }

    public void verificaActualizaTablas(List<TablaPrincipal> tablas) {
        String url = "https://debidadiligencia.saitempsa.com:8484/aplicaciones/api/public/api/v1/tablasandroid";
        ConsultaTablasApi.consultaTablasApi(getBaseContext(), url, new ApiCallbackTablas.ApiCallback<JSONArray>() {

            @Override
            public void onSuccess(JSONArray result) throws JSONException {
                for (int i = 0; i < result.length(); i++) {
                    JSONObject tabla1 = result.getJSONObject(i);
                    for (TablaPrincipal tabla2 : tablas) {
                        if (tabla1.getString("nombre_tabla").equals(tabla2.getNombre())) {
                            if (tabla1.getInt("id") != tabla2.getId() || tabla1.getInt("version") != tabla2.getVersion()) {
                                ReconstruirTabla.reiniciarTabla(tabla2.getNombre(), getBaseContext());
                            }
                        }
                    }
                }
//                ReconstruirTabla.reiniciarTabla("usr_app_solicitante_crm", getBaseContext());
//                ReconstruirTabla.reiniciarTabla("usr_app_usuarios_responsable", getBaseContext());
//                ReconstruirTabla.reiniciarTabla("usr_app_usuarios_visitante", getBaseContext());
            }

            @Override
            public void onError(String error) {
                Log.e("API Error", "Error en la solicitud API: " + error.toString());
            }
        });
    }
}