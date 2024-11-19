package com.example.saitemp.consultasApi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.saitemp.bd.saitemp.DatabaseHelper;
import com.example.saitemp.clases.clases_adaptadores.Estado;
import com.example.saitemp.clases.clases_adaptadores.PQRSF;
import com.example.saitemp.clases.clases_adaptadores.Proceso;
import com.example.saitemp.clases.clases_adaptadores.Sede;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultaProcesos {
    public static List<Proceso> obtenerProcesos(Context context) {
        List<Proceso> procesos = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usr_app_procesos", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                Proceso proceso = new Proceso(id, nombre);
                procesos.add(proceso);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return procesos;
    }

    public static long guardarProcesos(Context context, List<Proceso> estados){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        ContentValues values = new ContentValues();
        try{
            for (Proceso proceso: estados) {
                values.put("id", proceso.getId());
                values.put("nombre", proceso.getNombre());
                dbHelper.insertData("usr_app_procesos", values);
            }
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    public static void llenarTabla(Context contexto, JSONArray jsonArray) throws JSONException {
        List<Proceso> procesos = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tabla = jsonArray.getJSONObject(i);
            int id = tabla.getInt("id");
            String nombre = tabla.getString("nombre");
            Proceso proceso = new Proceso(id,nombre);
            procesos.add(proceso);
        }
        guardarProcesos(contexto,procesos);

    }
}
