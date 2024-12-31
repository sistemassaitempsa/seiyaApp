package com.saitempsa.saitemp.consultasApi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saitempsa.saitemp.bd.saitemp.DatabaseHelper;
import com.saitempsa.saitemp.clases.clases_adaptadores.Sede;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultaSedes {
    public static List<Sede> obtenerSedes(Context context) {
        List<Sede> sedes = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usr_app_sedes_saitemp", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                Sede sede = new Sede(id, nombre);
                sedes.add(sede);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return sedes;
    }

    public static long guardarSedes(Context context, List<Sede> sedes){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        ContentValues values = new ContentValues();
        try{
            for (Sede sede: sedes) {
                values.put("id", sede.getId());
                values.put("nombre", sede.getNombre());
                dbHelper.insertData("usr_app_sedes_saitemp", values);
            }
        }catch (Exception e){
            return 0;
        }

        return 1;
    }

    public static void llenarTabla(Context contexto, JSONArray jsonArray) throws JSONException {
        List<Sede> sedes = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tabla = jsonArray.getJSONObject(i);
            int id = tabla.getInt("id");
            String nombre = tabla.getString("nombre");
            Sede sede = new Sede(id,nombre);
            sedes.add(sede);
        }
        guardarSedes(contexto,sedes);

    }
}
