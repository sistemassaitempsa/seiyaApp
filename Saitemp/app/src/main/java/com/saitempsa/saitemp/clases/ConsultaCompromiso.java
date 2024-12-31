package com.saitempsa.saitemp.clases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saitempsa.saitemp.bd.saitemp.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ConsultaCompromiso {

    Context context;

    public static List<Compromiso> obtenerCompromisos(Context context) {
        List<Compromiso> compromisos = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usr_app_compromisos", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String formulario_id = cursor.getString(cursor.getColumnIndexOrThrow("formulario_id"));
                String compromiso = cursor.getString(cursor.getColumnIndexOrThrow("compromiso"));
                String responsable = cursor.getString(cursor.getColumnIndexOrThrow("responsable"));
                String estado = cursor.getString(cursor.getColumnIndexOrThrow("estado"));
                String observacion = cursor.getString(cursor.getColumnIndexOrThrow("observacion"));
                Compromiso compromiso1 = new Compromiso(id, compromiso, responsable, estado, observacion);
                compromisos.add(compromiso1);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return compromisos;
    }
}
