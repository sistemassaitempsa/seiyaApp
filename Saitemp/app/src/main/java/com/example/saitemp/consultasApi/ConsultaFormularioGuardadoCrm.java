package com.example.saitemp.consultasApi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.saitemp.bd.saitemp.DatabaseHelper;
import com.example.saitemp.clases.clases_adaptadores.CrmGuardado;
import com.example.saitemp.clases.clases_adaptadores.Estado;

import java.util.ArrayList;
import java.util.List;

public class ConsultaFormularioGuardadoCrm {

    public static List<CrmGuardado> obtenerFormularios(Context context) {
        List<CrmGuardado> crmGuardados = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, sede, proceso, solicitante, medio_atencion, nit_numero_identificacion, nombre_razon_social FROM usr_app_formulario_ingreso", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String sede = cursor.getString(cursor.getColumnIndexOrThrow("sede"));
                String proceso = cursor.getString(cursor.getColumnIndexOrThrow("proceso"));
                String solicitante = cursor.getString(cursor.getColumnIndexOrThrow("solicitante"));
                String tipo_atencion = cursor.getString(cursor.getColumnIndexOrThrow("medio_atencion"));
                String nit = cursor.getString(cursor.getColumnIndexOrThrow("nit_numero_identificacion"));
                String razon_social = cursor.getString(cursor.getColumnIndexOrThrow("nombre_razon_social"));
                CrmGuardado formulario = new CrmGuardado(id, sede, proceso, solicitante,tipo_atencion, nit, razon_social);
                crmGuardados.add(formulario);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return crmGuardados;
    }
}
