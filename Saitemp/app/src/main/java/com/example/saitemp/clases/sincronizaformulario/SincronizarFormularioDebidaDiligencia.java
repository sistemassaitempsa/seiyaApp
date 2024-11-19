package com.example.saitemp.clases.sincronizaformulario;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.saitemp.NavegacionActivity;
import com.example.saitemp.bd.saitemp.DatabaseHelper;
import com.example.saitemp.clases.consultaformularioguardado.ConsultaFormularioGuardado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SincronizarFormularioDebidaDiligencia {
    private Context context;
    private RequestQueue requestQueue;
    private SQLiteDatabase database;
    private JSONObject jsonObject;

    public SincronizarFormularioDebidaDiligencia(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    @SuppressLint("Range")
    public void consultarFormulario(String id) {
        ConsultaFormularioGuardado formulario = new ConsultaFormularioGuardado(context);
        JSONObject formulario1 = formulario.consultarFormulario(id);

        try {
            // Asumiendo que "procesos" es el array dentro del objeto formulario1
            JSONArray asistentes = formulario1.getJSONArray("asistentes");
            JSONArray evidencias = formulario1.getJSONArray("evidencias");
            formulario1.remove("asistentes");
            formulario1.remove("evidencias");

            JSONObject arrayAsistente;
            int medio_atencion_id = formulario1.getInt("medio_atencion_id");
            if (asistentes.length() <= 0 && (medio_atencion_id == 5 || medio_atencion_id == 6)) {
                Toast.makeText(context, "Debe agregar minimo un asistente.", Toast.LENGTH_SHORT).show();
                return;
            }
            for (int i = 0; i < asistentes.length(); i++) {
                try {
                    JSONObject asistente = asistentes.getJSONObject(i);
                    String cargo = asistente.getString("cargo");
                    String nombre = asistente.getString("nombre");
                    String correo = asistente.getString("correo");
                    String firma = asistente.getString("firma");
                    arrayAsistente = new JSONObject();
                    arrayAsistente.put("cargo", cargo);
                    arrayAsistente.put("nombre", nombre);
                    arrayAsistente.put("correo", correo);
                    arrayAsistente.put("firma", firma);
                    asistentes.put(i, arrayAsistente);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            JSONObject arrayEvidencia;
            for (int i = 0; i < evidencias.length(); i++) {
                try {
                    JSONObject evidencia = evidencias.getJSONObject(i);
                    String nombre = evidencia.getString("nombre");
                    String descripcion = evidencia.getString("descripcion");
                    String archivoBase64 = preparaImagenUri(Uri.parse(evidencia.getString("path")));
                    arrayEvidencia = new JSONObject();
                    arrayEvidencia.put("nombre", nombre);
                    arrayEvidencia.put("descripcion", descripcion);
                    arrayEvidencia.put("path", archivoBase64);
                    evidencias.put(i, arrayEvidencia);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            formulario1.put("evidencias", evidencias);
            formulario1.put("asistentes", asistentes);


            if (isNetworkAvailable()) {
                enviarAlServidor("https://debidadiligencia.saitempsa.com:8484/aplicaciones/api/api/public/api/v1/seguimientocrm2", formulario1);
//                esrtructuraCorreos();
            } else {
                Toast.makeText(context.getApplicationContext(), "No tiene conexión a internet", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("TAG", "Error al procesar el JSON: " + e.getMessage());
        }
    }

    public String preparaImagenRuta(String rutaImagen) {
        byte[] imageData = obtenerByteArrayDesdeRutaDeArchivo(rutaImagen);

        if (imageData != null) {
            // Convierte el array de bytes a una cadena Base64
            return Base64.encodeToString(imageData, Base64.DEFAULT);
        }
        return null; // Retorna null si no se pudo obtener los bytes
    }

    private byte[] obtenerByteArrayDesdeRutaDeArchivo(String ruta) {
        byte[] byteArray = null;

        try {
            // Crear un FileInputStream desde la ruta del archivo
            FileInputStream fis = new FileInputStream(new File(ruta));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            // Leer el archivo en bloques y escribir en el ByteArrayOutputStream
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            // Convertir el contenido del ByteArrayOutputStream a un array de bytes
            byteArray = bos.toByteArray();

            // Cerrar los flujos
            fis.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de errores (puedes lanzar una excepción o manejarlo de otra manera)
        }

        return byteArray; // Retorna el array de bytes
    }

    public String preparaImagenUri(Uri uriImagen) {
        byte[] imageData = obtenerByteArrayDesdeUri(uriImagen);

        if (imageData != null) {
            String imagenBase64 = Base64.encodeToString(imageData, Base64.DEFAULT);
            return imagenBase64;
        }
        return null;
    }


    public byte[] obtenerByteArrayDesdeUri(Uri uri) {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        // Tamaño del buffer
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        try (InputStream inputStream = context.getContentResolver().openInputStream(uri)) {
            if (inputStream != null) {
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    byteBuffer.write(buffer, 0, len);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // Convertir a byte array
        return byteBuffer.toByteArray();
    }


    private String getToken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("saitemp", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        return token;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void enviarAlServidor(String url, JSONObject formulario) {

        String token = getToken();
        if (token == "") {
            Toast.makeText(context, "Por favor inicie sesión" + token, Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(context, "Enviando formulario", Toast.LENGTH_SHORT).show();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, formulario,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject correos = response.getJSONObject("correos");
                            String formulario_id = response.getString("formulario_id");
                            Toast.makeText(context, "Formulario guardado.", Toast.LENGTH_SHORT).show();
                            DatabaseHelper dbHelper = new DatabaseHelper(context); // "this" es el contexto
                            dbHelper.eliminarRegistro("usr_app_formulario_ingreso", "id", formulario.getInt("id"));
                            enviarCorreo(formulario_id, correos);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Error al guardar formulario"; // Mensaje por defecto

                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                // Convierte los datos de respuesta a una cadena JSON
                                String jsonString = new String(error.networkResponse.data, "UTF-8");
                                JSONObject jsonError = new JSONObject(jsonString);

                                // Extrae el mensaje de error si está presente
                                errorMessage = jsonError.optString("message", errorMessage); // Mensaje personalizado
                                Log.d("TAG", "Mensaje de error de la API: " + errorMessage);

                            } catch (UnsupportedEncodingException | JSONException e) {
                                Log.e("TAG", "Error al procesar el mensaje de error JSON: " + e.getMessage());
                            }
                        }
                        // Muestra el mensaje de error en un Toast
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "Error de red: " + errorMessage);
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() {
                // Establece los encabezados, incluyendo el token
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        requestQueue.add(request);
    }

    private void enviarCorreo(String formulario_id, JSONObject correos) {
        String url = "https://debidadiligencia.saitempsa.com:8484/aplicaciones/api2/api/public/api/v1/seguimientocrmpdf/" + formulario_id + "/1";
        String token = getToken();
        Toast.makeText(context, "Enviando correos", Toast.LENGTH_SHORT).show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, correos,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("mensaje del servidor", response.toString());

                        Toast.makeText(context.getApplicationContext(), "Correos enviados.", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Correos enviados."; // Mensaje por defecto

                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                // Convierte los datos de respuesta a una cadena JSON
                                String jsonString = new String(error.networkResponse.data, "UTF-8");
                                JSONObject jsonError = new JSONObject(jsonString);

                                // Extrae el mensaje de error si está presente
                                errorMessage = jsonError.optString("message", errorMessage); // Mensaje personalizado
                                Log.d("TAG", "Mensaje de error de la API: " + errorMessage);

                            } catch (UnsupportedEncodingException | JSONException e) {
                                Log.e("TAG", "Error al procesar el mensaje de error JSON: " + e.getMessage());
                            }
                        }

                        // Muestra el mensaje de error en un Toast
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "Error de red: " + errorMessage);
                        Intent intent = new Intent(context, NavegacionActivity.class);
                        context.startActivity(intent);
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() {
                // Establece los encabezados, incluyendo el token
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        requestQueue.add(request);
    }
}
