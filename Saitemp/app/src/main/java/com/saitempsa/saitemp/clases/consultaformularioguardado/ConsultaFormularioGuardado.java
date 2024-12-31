package com.saitempsa.saitemp.clases.consultaformularioguardado;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.saitempsa.saitemp.bd.saitemp.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ConsultaFormularioGuardado {
    private Context context;
    private RequestQueue requestQueue;
    private SQLiteDatabase database;
    private JSONObject jsonObject;

    public ConsultaFormularioGuardado(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    @SuppressLint("Range")
    public JSONObject consultarFormulario(String id) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            // Crear el JSONObject principal
            JSONObject jsonObject = new JSONObject();
            Cursor cursor = db.rawQuery("SELECT * FROM usr_app_formulario_ingreso WHERE id = ?", new String[]{id});
            // Iterar sobre el cursor
            while (cursor.moveToNext()) {
                // Agregar los datos del cursor al JSONObject principal
                jsonObject.put("id", cursor.getString(cursor.getColumnIndex("id")));
                jsonObject.put("sede", cursor.getString(cursor.getColumnIndex("sede")));
                jsonObject.put("sede_id", cursor.getString(cursor.getColumnIndex("sede_id")));
                jsonObject.put("proceso", cursor.getString(cursor.getColumnIndex("proceso")));
                jsonObject.put("proceso_id", cursor.getString(cursor.getColumnIndex("proceso_id")));
                jsonObject.put("solicitante", cursor.getString(cursor.getColumnIndex("solicitante")));
                jsonObject.put("solicitante_id", cursor.getString(cursor.getColumnIndex("solicitante_id")));
                jsonObject.put("medio_atencion", cursor.getString(cursor.getColumnIndex("medio_atencion")));
                jsonObject.put("medio_atencion_id", cursor.getString(cursor.getColumnIndex("medio_atencion_id")));
                jsonObject.put("nit", cursor.getString(cursor.getColumnIndex("nit_numero_identificacion")));
                jsonObject.put("razon_social", cursor.getString(cursor.getColumnIndex("nombre_razon_social")));
                jsonObject.put("cierra_radicado", cursor.getString(cursor.getColumnIndex("cierra_radicado")));
                jsonObject.put("telefono", cursor.getString(cursor.getColumnIndex("telefono_contacto")));
                jsonObject.put("correo", cursor.getString(cursor.getColumnIndex("correo_contacto")));
                jsonObject.put("visitante", cursor.getString(cursor.getColumnIndex("visitante")));
                jsonObject.put("visitante_id", cursor.getString(cursor.getColumnIndex("visitante_id")));
                jsonObject.put("cargo_visitante", cursor.getString(cursor.getColumnIndex("cargo_visitante")));
                jsonObject.put("cargo_visitante_id", cursor.getString(cursor.getColumnIndex("cargo_visitante_id")));
                jsonObject.put("visitado", cursor.getString(cursor.getColumnIndex("visitado")));
                jsonObject.put("cargo_visitado", cursor.getString(cursor.getColumnIndex("cargo_visitado")));
                jsonObject.put("objetivo", cursor.getString(cursor.getColumnIndex("objetivo")));
                jsonObject.put("alcance", cursor.getString(cursor.getColumnIndex("alcance")));
                jsonObject.put("tema", cursor.getString(cursor.getColumnIndex("tema")));
                jsonObject.put("estado", cursor.getString(cursor.getColumnIndex("estado")));
                jsonObject.put("estado_id", cursor.getString(cursor.getColumnIndex("estado_id")));
                jsonObject.put("pqrsf", cursor.getString(cursor.getColumnIndex("pqrsf")));
                jsonObject.put("pqrsf_id", cursor.getString(cursor.getColumnIndex("pqrsf_id")));
                jsonObject.put("responsable", cursor.getString(cursor.getColumnIndex("responsable")));
                jsonObject.put("correo_responsable", cursor.getString(cursor.getColumnIndex("correo_responsable")));
                jsonObject.put("responsable_id", cursor.getString(cursor.getColumnIndex("responsable_id")));
                jsonObject.put("hora_incio", cursor.getString(cursor.getColumnIndex("hora_incio")));
                jsonObject.put("observaciones", cursor.getString(cursor.getColumnIndex("observaciones")));
                jsonObject.put("longitud", cursor.getString(cursor.getColumnIndex("longitud")));
                jsonObject.put("latitud", cursor.getString(cursor.getColumnIndex("latitud")));
            }

            JSONArray evidencias = new JSONArray();
            Cursor cursor_evidencias = db.rawQuery("SELECT * FROM usr_app_evidencias WHERE formulario_id = ?", new String[]{id});
            while (cursor_evidencias.moveToNext()) {
                JSONObject evidencia = new JSONObject();
                evidencia.put("id", cursor_evidencias.getString(cursor_evidencias.getColumnIndex("id")));
                evidencia.put("formulario_id", cursor_evidencias.getString(cursor_evidencias.getColumnIndex("formulario_id")));
                evidencia.put("nombre", cursor_evidencias.getString(cursor_evidencias.getColumnIndex("nombre")));
                evidencia.put("path", cursor_evidencias.getString(cursor_evidencias.getColumnIndex("path")));
                evidencia.put("descripcion", cursor_evidencias.getString(cursor_evidencias.getColumnIndex("descripcion")));
                evidencias.put(evidencia);
            }
            jsonObject.put("evidencias", evidencias);

            JSONArray compromisos = new JSONArray();
            Cursor cursor_compromisos = db.rawQuery("SELECT * FROM usr_app_compromisos WHERE formulario_id = ?", new String[]{id});
            while (cursor_compromisos.moveToNext()) {
                JSONObject compromiso = new JSONObject();
                compromiso.put("id", "");
                compromiso.put("key", cursor_compromisos.getString(cursor_compromisos.getColumnIndex("id")));
                compromiso.put("formulario_id", cursor_compromisos.getString(cursor_compromisos.getColumnIndex("formulario_id")));
                compromiso.put("compromiso", cursor_compromisos.getString(cursor_compromisos.getColumnIndex("compromiso")));
                compromiso.put("responsable", cursor_compromisos.getString(cursor_compromisos.getColumnIndex("responsable")));
                compromiso.put("responsable_id", cursor_compromisos.getString(cursor_compromisos.getColumnIndex("responsable_id")));
                compromiso.put("estado", cursor_compromisos.getString(cursor_compromisos.getColumnIndex("estado")));
                compromiso.put("estado_id", cursor_compromisos.getString(cursor_compromisos.getColumnIndex("estado_id")));
                compromiso.put("observacion", cursor_compromisos.getString(cursor_compromisos.getColumnIndex("observacion")));
                compromisos.put(compromiso);
            }
            jsonObject.put("compromisos", compromisos);


//          Esta consulta no permite realizar un orderby porque se daña la actualización de los campos
            JSONArray asistentes = new JSONArray();
            Cursor cursor_asistentes = db.rawQuery("SELECT * FROM usr_app_asistentes WHERE formulario_id = ?", new String[]{id});
            while (cursor_asistentes.moveToNext()) {
                JSONObject asistente = new JSONObject();
                asistente.put("id", cursor_asistentes.getString(cursor_asistentes.getColumnIndex("id")));
                asistente.put("formulario_id", cursor_asistentes.getString(cursor_asistentes.getColumnIndex("formulario_id")));
                asistente.put("cargo", cursor_asistentes.getString(cursor_asistentes.getColumnIndex("cargo")));
                asistente.put("nombre", cursor_asistentes.getString(cursor_asistentes.getColumnIndex("nombre")));
                asistente.put("correo", cursor_asistentes.getString(cursor_asistentes.getColumnIndex("correo")));
                String firma = preparaImagen(cursor_asistentes.getString(cursor_asistentes.getColumnIndex("firma")));
                asistente.put("firma", firma);
                asistentes.put(asistente);
            }
            jsonObject.put("asistentes", asistentes);

            cursor_evidencias.close();
            cursor_compromisos.close();
            cursor_asistentes.close();
            cursor.close();
            return jsonObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }

    public String preparaImagen(String rutaImagen) {
        byte[] imageData = obtenerByteArrayDesdeRutaDeArchivo(rutaImagen);

        if (imageData != null) {
            String imagenBase64 = Base64.encodeToString(imageData, Base64.DEFAULT);
            return imagenBase64;
        }
        return null;
    }

    private byte[] obtenerByteArrayDesdeRutaDeArchivo(String rutaArchivo) {
        File file = new File(rutaArchivo);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
