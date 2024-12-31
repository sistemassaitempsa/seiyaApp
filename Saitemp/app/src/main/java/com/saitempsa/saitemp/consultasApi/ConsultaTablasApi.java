package com.saitempsa.saitemp.consultasApi;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.saitempsa.saitemp.interfaz.ApiCallbackTablas;
import com.saitempsa.saitemp.interfaz.ApiCallbackTablas2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class ConsultaTablasApi {

    public static void consultaTablasApi(Context context, String url_consulta, final ApiCallbackTablas.ApiCallback<JSONArray> callback) {
        String url = url_consulta;
        String token = getToken(context);

        // Deshabilitar la verificación del certificado SSL (si es autofirmado)
        ConsultaActualizacionTabla SSLHelper = null;
        SSLHelper.disableSSLCertificateChecking();

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
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
                        Log.e("API Error", "Error en la solicitud API: " + error.toString());
                        callback.onError(error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token); // Añadir el token en el encabezado
                headers.put("Content-Type", "application/json"); // Opcional, dependiendo de la API
                return headers;
            }
        };

        queue.add(jsonArrayRequest);
    }

    public static void consultaTablasApi2(Context context, String url_consulta, final ApiCallbackTablas2.ApiCallback<JSONObject> callback) {
        String url = url_consulta;
        String token = getToken(context);

        // Deshabilitar la verificación del certificado SSL (si es autofirmado)
        ConsultaActualizacionTabla SSLHelper = null;
        SSLHelper.disableSSLCertificateChecking();

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("API Error", "Error en la solicitud API: " + error.toString());
                        callback.onError(error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token); // Añadir el token en el encabezado
                headers.put("Content-Type", "application/json"); // Opcional, dependiendo de la API
                return headers;
            }
        };

        queue.add(jsonObjectRequest);
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
