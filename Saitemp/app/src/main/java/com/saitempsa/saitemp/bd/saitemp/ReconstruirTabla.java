package com.saitempsa.saitemp.bd.saitemp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.saitempsa.saitemp.consultasApi.ConsultaActualizacionTabla;
import com.saitempsa.saitemp.consultasApi.ConsultaCargoVisitante;
import com.saitempsa.saitemp.consultasApi.ConsultaDebidaDiligencia;
import com.saitempsa.saitemp.consultasApi.ConsultaEstadoCompromiso;
import com.saitempsa.saitemp.consultasApi.ConsultaEstados;
import com.saitempsa.saitemp.consultasApi.ConsultaPQRSF;
import com.saitempsa.saitemp.consultasApi.ConsultaProcesos;
import com.saitempsa.saitemp.consultasApi.ConsultaResponsable;
import com.saitempsa.saitemp.consultasApi.ConsultaSedes;
import com.saitempsa.saitemp.consultasApi.ConsultaSolicitante;
import com.saitempsa.saitemp.consultasApi.ConsultaTipoAtencion;
import com.saitempsa.saitemp.consultasApi.ConsultaVisitantes;
import com.saitempsa.saitemp.interfaz.ApiCallbackTablas;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class ReconstruirTabla {

    public static void reiniciarTabla(String nombreTabla, Context context) throws JSONException {
        // Aquí llamas al método que elimina los registros
        eliminarRegistros(nombreTabla, context);
        // Y aquí puedes volver a construir la tabla según sea necesario
        construirTabla(nombreTabla, context);
    }

    // Función para eliminar registros de la tabla
    public static void eliminarRegistros(String nombreTabla, Context context) {
        // Lógica para eliminar todos los registros de la tabla
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // Obtener la base de datos
        db.execSQL("DELETE FROM " + nombreTabla); // Ejecutar el comando SQL
        db.close(); // Cerrar la base de datos
    }

    // Función para construir la tabla (ejemplo básico)
    private static void construirTabla(String nombreTabla, Context context) throws JSONException {
        String url = "https://contratacion.saitempsa.com/aplicaciones/api/public/api/v1/tablasandroid_" + nombreTabla;
        consultaTablasApi(context, url, new ApiCallbackTablas.ApiCallback<JSONArray>() {

            @Override
            public void onSuccess(JSONArray result) throws JSONException {
                switch (nombreTabla) {
                    case "usr_app_tablas_android":
                        ConsultaActualizacionTabla.llenarTabla(context, result);
                        break;
                    case "usr_app_sedes_saitemp":
                        ConsultaSedes.llenarTabla(context, result);
                        break;
                    case "usr_app_procesos":
                        ConsultaProcesos.llenarTabla(context, result);
                        break;
                    case "usr_app_solicitante_crm":
                        ConsultaSolicitante.llenarTabla(context, result);
                        break;
                    case "usr_app_atencion_interacion":
                        ConsultaTipoAtencion.llenarTabla(context, result);
                        break;
                    case "usr_app_usuarios_responsable":
                        ConsultaResponsable.llenarTabla(context, result);
                        break;
                    case "usr_app_usuarios_visitante":
                        ConsultaVisitantes.llenarTabla(context, result);
                        break;
                    case "usr_app_cargos_crm":
                        ConsultaCargoVisitante.llenarTabla(context, result);
                        break;
                    case "usr_app_estado_cierre_crm":
                        ConsultaEstados.llenarTabla(context, result);
                        break;
                    case "usr_app_estado_compromiso_crm":
                        ConsultaEstadoCompromiso.llenarTabla(context, result);
                        break;
                    case "usr_app_pqrsf_crm":
                        ConsultaPQRSF.llenarTabla(context, result);
                        break;
                    case "usr_app_cliente_debida_diligencia":
                        ConsultaDebidaDiligencia.llenarTabla(context, result);
                        break;

                }
//
            }

            @Override
            public void onError(String error) {
                Log.e("API Error", "Error en la solicitud API: " + error.toString());
            }
        });
    }

    public static void consultaTablasApi(Context context, String url_consulta, final ApiCallbackTablas.ApiCallback<JSONArray> callback) {
        String url = url_consulta;
        String token = getToken(context);

        // Deshabilitar la verificación del certificado SSL (si es autofirmado)
        ConsultaActualizacionTabla SSLHelper = null;
        SSLHelper.disableSSLCertificateChecking();

        // Crear una RequestQueue
        RequestQueue queue = Volley.newRequestQueue(context);

        // Crear la solicitud de tipo JsonArrayRequest
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, // Método HTTP (GET)
                url, // URL de la API
                null, // Cuerpo de la solicitud (en GET es null)
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Manejar la respuesta exitosa (un array de objetos)
                        // Pasar los datos al callback
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error de la solicitud
                        Log.e("API Error", "Error en la solicitud API: " + error.toString());
                        callback.onError(error.toString());
                    }
                }
        ) {
            // Sobrescribir el método getHeaders para añadir el token Bearer
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token); // Añadir el token en el encabezado
                headers.put("Content-Type", "application/json"); // Opcional, dependiendo de la API
                return headers;
            }
        };

        // Añadir la solicitud a la RequestQueue
        queue.add(jsonArrayRequest);
    }

    private static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("saitemp", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        return token;
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


}
