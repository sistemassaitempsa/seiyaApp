package com.saitempsa.saitemp.consultasApi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saitempsa.saitemp.bd.saitemp.DatabaseHelper;
import com.saitempsa.saitemp.clases.clases_adaptadores.Tipo_atencion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultaTipoAtencion {
    public static List<Tipo_atencion> obtenerTipoAtencion(Context context) {
        List<Tipo_atencion> tipo_atencion = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usr_app_atencion_interacion", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                Tipo_atencion tipoAtencion = new Tipo_atencion(id, nombre);
                tipo_atencion.add(tipoAtencion);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tipo_atencion;
    }

    public static long guardarTiposAtencion(Context context, List<Tipo_atencion> tiposAtencion){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        ContentValues values = new ContentValues();
        try{
            for (Tipo_atencion tipoAtencion: tiposAtencion) {
                values.put("id", tipoAtencion.getId());
                values.put("nombre", tipoAtencion.getNombre());
                dbHelper.insertData("usr_app_atencion_interacion", values);
            }
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    public static void llenarTabla(Context contexto, JSONArray jsonArray) throws JSONException {
        List<Tipo_atencion> tiposAtencion = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tabla = jsonArray.getJSONObject(i);
            int id = tabla.getInt("id");
            String nombre = tabla.getString("nombre");
            Tipo_atencion tipoAtencion = new Tipo_atencion(id,nombre);
            tiposAtencion.add(tipoAtencion);
        }
        guardarTiposAtencion(contexto,tiposAtencion);

    }
}
