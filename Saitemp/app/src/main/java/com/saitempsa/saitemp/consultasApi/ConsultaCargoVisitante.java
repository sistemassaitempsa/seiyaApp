package com.saitempsa.saitemp.consultasApi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saitempsa.saitemp.bd.saitemp.DatabaseHelper;
import com.saitempsa.saitemp.clases.clases_adaptadores.CargoVisitante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultaCargoVisitante {
    public static List<CargoVisitante> obtenerCargoVisitante(Context context) {
        List<CargoVisitante> cargosVisitantes = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usr_app_cargos_crm", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                CargoVisitante cargoVisitante = new CargoVisitante(id, nombre);
                cargosVisitantes.add(cargoVisitante);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cargosVisitantes;
    }

    public static long guardarCargos(Context context, List<CargoVisitante> cargoVisitantes){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        ContentValues values = new ContentValues();
        try{
            for (CargoVisitante cargo: cargoVisitantes) {
                values.put("id", cargo.getId());
                values.put("nombre", cargo.getNombre());
                dbHelper.insertData("usr_app_cargos_crm", values);
            }
        }catch (Exception e){
            return 0;
        }

        return 1;
    }

    public static void llenarTabla(Context contexto, JSONArray jsonArray) throws JSONException {
        List<CargoVisitante> cargoVisitantes = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tabla = jsonArray.getJSONObject(i);
            int id = tabla.getInt("id");
            String cargo = tabla.getString("cargo");
            CargoVisitante cargoVisitante = new CargoVisitante(id,cargo);
            cargoVisitantes.add(cargoVisitante);
        }
        guardarCargos(contexto,cargoVisitantes);

    }

}
