package com.example.saitemp.consultasApi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.saitemp.bd.saitemp.DatabaseHelper;
import com.example.saitemp.clases.clases_adaptadores.Proceso;
import com.example.saitemp.clases.clases_adaptadores.Sede;
import com.example.saitemp.clases.clases_adaptadores.Solicitante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultaSolicitante {
    public static List<Solicitante> obtenerSolicitantes(Context context) {
        List<Solicitante> solicitantes = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usr_app_solicitante_crm", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                Solicitante solicitante = new Solicitante(id, nombre);
                solicitantes.add(solicitante);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return solicitantes;
    }

    public static long guardarSolicitante(Context context, List<Solicitante> solicitantes){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        ContentValues values = new ContentValues();
        try{
            for (Solicitante solicitante: solicitantes) {
                values.put("id", solicitante.getId());
                values.put("nombre", solicitante.getNombre());
                dbHelper.insertData("usr_app_solicitante_crm", values);
            }
        }catch (Exception e){
            return 0;
        }

        return 1;
    }

    public static void llenarTabla(Context contexto, JSONArray jsonArray) throws JSONException {
        List<Solicitante> solicitantes = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tabla = jsonArray.getJSONObject(i);
            int id = tabla.getInt("id");
            String nombre = tabla.getString("nombre");
            Solicitante solicitante = new Solicitante(id,nombre);
            solicitantes.add(solicitante);
        }
        guardarSolicitante(contexto,solicitantes);

    }
}
