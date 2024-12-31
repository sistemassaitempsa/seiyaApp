package com.saitempsa.saitemp.consultasApi;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saitempsa.saitemp.bd.saitemp.DatabaseHelper;
import com.saitempsa.saitemp.clases.clases_adaptadores.Visitante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultaVisitantes {
    public static List<Visitante> obtenerVisitantes(Context context) {
            List<Visitante> visitantes = new ArrayList<>();
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM usr_app_usuarios_visitante", null);
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                    Visitante visitante = new Visitante(id, nombre, email);
                    visitantes.add(visitante);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return visitantes;
        }

        public static long guardarVisitantes(Context context, List<Visitante> visitantes){
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            ContentValues values = new ContentValues();
            try{
                for (Visitante visitante: visitantes) {
                    values.put("id", visitante.getId());
                    values.put("nombre", visitante.getNombre());
                    values.put("email", visitante.getEmail());
                    dbHelper.insertData("usr_app_usuarios_visitante", values);
                }
            }catch (Exception e){
                return 0;
            }
            return 1;
        }

        public static void llenarTabla(Context contexto, JSONArray jsonArray) throws JSONException {
            List<Visitante> visitantes = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject tabla = jsonArray.getJSONObject(i);
                int id = tabla.getInt("id");
                String nombres = tabla.getString("nombres");
                String apellidos = tabla.getString("apellidos").replace("null","");
                String email = tabla.getString("email");
                Visitante visitante = new Visitante(id,nombres+" "+apellidos,email);
                visitantes.add(visitante);
            }
            guardarVisitantes(contexto,visitantes);

        }
}
