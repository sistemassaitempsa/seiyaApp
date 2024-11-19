package com.example.saitemp.consultasApi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.saitemp.bd.saitemp.DatabaseHelper;
import com.example.saitemp.clases.clases_adaptadores.Estado;
import com.example.saitemp.clases.clases_adaptadores.Responsable;
import com.example.saitemp.clases.clases_adaptadores.Sede;
import com.example.saitemp.clases.clases_adaptadores.Visitante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultaResponsable {
    public static List<Responsable> obtenerResponsables(Context context) {
        List<Responsable> responsables = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usr_app_usuarios_responsable", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                Responsable responsable = new Responsable(id, nombre, email);
                responsables.add(responsable);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return responsables;
    }

    public static long guardarResponsables(Context context, List<Responsable> responsables){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        ContentValues values = new ContentValues();
        try{
            for (Responsable responsable: responsables) {
                values.put("id", responsable.getId());
                values.put("nombre", responsable.getNombre());
                values.put("email", responsable.getEmail());
                dbHelper.insertData("usr_app_usuarios_responsable", values);
            }
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    public static void llenarTabla(Context contexto, JSONArray jsonArray) throws JSONException {
        List<Responsable> responsables = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tabla = jsonArray.getJSONObject(i);
            int id = tabla.getInt("id");
            String nombres = tabla.getString("nombres");
            String apellidos = tabla.getString("apellidos").replace("null","");
            String email = tabla.getString("email");
            Responsable responsable = new Responsable(id,nombres+" "+apellidos,email);
            responsables.add(responsable);
        }
        guardarResponsables(contexto,responsables);

    }
}
