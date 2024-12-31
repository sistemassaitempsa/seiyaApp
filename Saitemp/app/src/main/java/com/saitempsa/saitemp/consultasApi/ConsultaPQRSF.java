package com.saitempsa.saitemp.consultasApi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saitempsa.saitemp.bd.saitemp.DatabaseHelper;
import com.saitempsa.saitemp.clases.clases_adaptadores.PQRSF;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultaPQRSF {
    public static List<PQRSF> obtenerPQRSF(Context context) {
        List<PQRSF> pqrsfs = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usr_app_pqrsf_crm", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                PQRSF pqrsf = new PQRSF(id, nombre);
                pqrsfs.add(pqrsf);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return pqrsfs;
    }

    public static long guardarPqrsf(Context context, List<PQRSF> pqrsfs){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        ContentValues values = new ContentValues();
        try{
            for (PQRSF pqrsf: pqrsfs) {
                values.put("id", pqrsf.getId());
                values.put("nombre", pqrsf.getNombre());
                dbHelper.insertData("usr_app_pqrsf_crm", values);
            }
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    public static void llenarTabla(Context contexto, JSONArray jsonArray) throws JSONException {
        List<PQRSF> pqrsfs = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tabla = jsonArray.getJSONObject(i);
            int id = tabla.getInt("id");
            String nombre = tabla.getString("nombre");
            PQRSF pqrsf = new PQRSF(id,nombre);
            pqrsfs.add(pqrsf);
        }
        guardarPqrsf(contexto,pqrsfs);

    }
}
