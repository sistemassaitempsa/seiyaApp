package com.saitempsa.saitemp.consultasApi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saitempsa.saitemp.bd.saitemp.DatabaseHelper;
import com.saitempsa.saitemp.clases.clases_adaptadores.EstadoCompromiso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultaEstadoCompromiso {
    public static List<EstadoCompromiso> obtenerEstadosCompromiso(Context context) {
        List<EstadoCompromiso> estadoscompromiso = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usr_app_estado_compromiso_crm", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                EstadoCompromiso estadoCompromiso = new EstadoCompromiso(id, nombre);
                estadoscompromiso.add(estadoCompromiso);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return estadoscompromiso;
    }

    public static long guardarEstadosCompromisos(Context context, List<EstadoCompromiso> estadoCompromiso){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        ContentValues values = new ContentValues();
        try{
            for (EstadoCompromiso estado: estadoCompromiso) {
                values.put("id", estado.getId());
                values.put("nombre", estado.getNombre());
                dbHelper.insertData("usr_app_estado_compromiso_crm", values);
            }
        }catch (Exception e){
            return 0;
        }

        return 1;
    }

    public static void llenarTabla(Context contexto, JSONArray jsonArray) throws JSONException {
        List<EstadoCompromiso> estadoCompromisos = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tabla = jsonArray.getJSONObject(i);
            int id = tabla.getInt("id");
            String nombre = tabla.getString("nombre");
            EstadoCompromiso estadoCompromiso = new EstadoCompromiso(id,nombre);
            estadoCompromisos.add(estadoCompromiso);
        }
        guardarEstadosCompromisos(contexto,estadoCompromisos);

    }
}
