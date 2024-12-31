package com.saitempsa.saitemp.consultasApi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saitempsa.saitemp.bd.saitemp.DatabaseHelper;
import com.saitempsa.saitemp.clases.clases_adaptadores.ClienteDebidaDiligencia;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultaDebidaDiligencia {
    public static List<ClienteDebidaDiligencia> obtenerClientesDebidaDiligencia(Context context) {
        List<ClienteDebidaDiligencia> clientes = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usr_app_cliente_debida_diligencia", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("razon_social"));
                String nit = cursor.getString(cursor.getColumnIndexOrThrow("nit"));
                ClienteDebidaDiligencia cliente = new ClienteDebidaDiligencia(id, nombre, nit);
                clientes.add(cliente);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return clientes;
    }

    public static long guardarClientes(Context context, List<ClienteDebidaDiligencia> clientes){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        ContentValues values = new ContentValues();
        try{
            for (ClienteDebidaDiligencia cliente: clientes) {
                values.put("id", cliente.getId());
                values.put("razon_social", cliente.getNombre());
                values.put("nit", cliente.getNit_numero_documento());
                dbHelper.insertData("usr_app_cliente_debida_diligencia", values);
            }
        }catch (Exception e){
            return 0;
        }

        return 1;
    }

    public static void llenarTabla(Context contexto, JSONArray jsonArray) throws JSONException {
        List<ClienteDebidaDiligencia> clientes = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tabla = jsonArray.getJSONObject(i);
            int id = tabla.getInt("id");
            String nombre = tabla.getString("razon_social");
            String nit = tabla.getString("nit");
            ClienteDebidaDiligencia cliente = new ClienteDebidaDiligencia(id,nombre, nit);
            clientes.add(cliente);
        }
        guardarClientes(contexto,clientes);

    }
}
