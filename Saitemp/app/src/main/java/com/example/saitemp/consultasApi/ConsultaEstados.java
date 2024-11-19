package com.example.saitemp.consultasApi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.saitemp.bd.saitemp.DatabaseHelper;
import com.example.saitemp.clases.clases_adaptadores.Estado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultaEstados {
    Context context;
    public static List<Estado> obtenerEstados(Context context) {
        List<Estado> estados = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usr_app_estado_cierre_crm", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                Estado estado = new Estado(id, nombre);
                estados.add(estado);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return estados;
    }

    public static long guardarEstados(Context context, List<Estado> estados){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        ContentValues values = new ContentValues();
        try{
            for (Estado estado: estados) {
                values.put("id", estado.getId());
                values.put("nombre", estado.getNombre());
                dbHelper.insertData("usr_app_estado_cierre_crm", values);
            }
        }catch (Exception e){
            return 0;
        }

        return 1;
    }

    public static void llenarTabla(Context contexto, JSONArray jsonArray) throws JSONException {
        List<Estado> estados = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tabla = jsonArray.getJSONObject(i);
            int id = tabla.getInt("id");
            String nombre = tabla.getString("nombre");
            Estado estado = new Estado(id,nombre);
            estados.add(estado);
        }
        guardarEstados(contexto,estados);

    }



}
