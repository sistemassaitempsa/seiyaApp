package com.saitempsa.saitemp.consultasApi;

import static java.lang.Integer.parseInt;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.saitempsa.saitemp.bd.saitemp.DatabaseHelper;
import com.saitempsa.saitemp.clases.TablaPrincipal;
import com.saitempsa.saitemp.interfaz.ApiCallbackTablas;

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

public class ConsultaActualizacionTabla {

    public static List<TablaPrincipal> tablas = new ArrayList<>();
    private static String nombreTabla;

    public static List<TablaPrincipal> consultaListaTablas(Context context) {
        List<TablaPrincipal> actualizacionesList = new ArrayList<>();

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM usr_app_tablas_android", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nombreTabla = cursor.getString(cursor.getColumnIndexOrThrow("nombre_tabla"));
                int version = cursor.getInt(cursor.getColumnIndexOrThrow("version"));

                TablaPrincipal actualizacion = new TablaPrincipal(id, nombreTabla, version);
                actualizacionesList.add(actualizacion);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return actualizacionesList;
    }


    public static long guardarTablaActualizaciones(Context context, List<TablaPrincipal> actualizacionTablas) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        ContentValues values = new ContentValues();
        try {
            for (TablaPrincipal actualizacionTabla : actualizacionTablas) {
                values.put("id", actualizacionTabla.getId());
                values.put("nombre_tabla", actualizacionTabla.getNombre());
                values.put("version", actualizacionTabla.getVersion());
                dbHelper.insertData("usr_app_tablas_android", values);
            }
            dbHelper.close();
        } catch (Exception e) {
            Toast.makeText(context, "error en actualizar atblas", Toast.LENGTH_SHORT).show();
            return 0;
        }

        return 1;
    }

    public static void llenarTabla(Context contexto, JSONArray jsonArray) throws JSONException {
        List<TablaPrincipal> tablasPrincipales = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tabla = jsonArray.getJSONObject(i);
            int id = tabla.getInt("id");
            String nombreTabla = tabla.getString("nombre_tabla");
            int version = tabla.getInt("version");
            TablaPrincipal tablaPrincipal = new TablaPrincipal(id,nombreTabla,version);
            tablasPrincipales.add(tablaPrincipal);
        }
        guardarTablaActualizaciones(contexto,tablasPrincipales);

    }

    public static void consultaTablasApi(Context context, final ApiCallbackTablas.ApiCallback<JSONArray> callback) {
        String url = "https://debidadiligencia.saitempsa.com:8484/aplicaciones/api/public/api/v1/tablasandroid";
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
                        Log.d("API Response", "Response: " + response.toString());
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

//    public static List<Actualizacion_tabla> getTablas(){
//        return tablas;
//    }
}
