package com.example.saitemp.bd.saitemp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "saitemp";
    private static final int DATABASE_VERSION = 1;

    // Crear la tabla actualizacion de tablas
    private static final String CREATE_TABLE_ACTUALIZACION =
            "CREATE TABLE usr_app_tablas_android (" +
                    "id INTEGER PRIMARY KEY," +
                    "nombre_tabla TEXT NOT NULL," +
                    "version INTEGER NOT NULL" +
                    ");";

//     Crear la tabla formulario
    private static final String CREATE_TABLE_FORMULARIOS =
            "CREATE TABLE usr_app_formulario_ingreso (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "sede TEXT NOT NULL," +
                    "sede_id TEXT NOT NULL," +
                    "proceso TEXT NOT NULL," +
                    "proceso_id TEXT NOT NULL," +
                    "solicitante TEXT NOT NULL," +
                    "solicitante_id TEXT NOT NULL," +
                    "medio_atencion TEXT NOT NULL," +
                    "medio_atencion_id TEXT NOT NULL," +
                    "nit_numero_identificacion TEXT NOT NULL," +
                    "nombre_razon_social TEXT NOT NULL," +
                    "cierra_radicado TEXT NOT NULL," +
                    "telefono_contacto TEXT NOT NULL," +
                    "correo_contacto TEXT NOT NULL," +
                    "visitante TEXT NOT NULL," +
                    "visitante_id TEXT NOT NULL," +
                    "cargo_visitante TEXT NOT NULL," +
                    "cargo_visitante_id TEXT NOT NULL," +
                    "visitado TEXT NOT NULL," +
                    "cargo_visitado TEXT NOT NULL," +
                    "objetivo TEXT NOT NULL," +
                    "alcance TEXT NOT NULL," +
                    "tema TEXT NOT NULL," +
                    "estado TEXT NOT NULL," +
                    "estado_id TEXT NOT NULL," +
                    "pqrsf TEXT NOT NULL," +
                    "pqrsf_id TEXT NOT NULL," +
                    "responsable TEXT NOT NULL," +
                    "correo_responsable TEXT NOT NULL," +
                    "responsable_id TEXT NOT NULL," +
                    "hora_incio TEXT NOT NULL," +
                    "observaciones TEXT NOT NULL," +
                    "longitud TEXT NOT NULL," +
                    "latitud TEXT NOT NULL" +
                    //                    "formulario_id INTEGER NOT NULL," +
//                    "FOREIGN KEY(formulario_id) REFERENCES formulario(id) ON DELETE CASCADE" +
                    ");";

    // Crear la tabla Evidencia
    private static final String CREATE_TABLE_EVIDENCIAS =
            "CREATE TABLE usr_app_evidencias (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "formulario_id INTEGER NOT NULL," +
                    "nombre TEXT NOT NULL," +
                    "path TEXT NOT NULL," +
                    "descripcion TEXT" +
                    ");";

    // Crear la tabla Asistente
    // Crear la tabla Order con clave foránea
    private static final String CREATE_TABLE_ASISTENTES =
            "CREATE TABLE usr_app_asistentes (" + // Cambié el nombre de la tabla a 'asistentes'
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "cargo TEXT NOT NULL," +
                    "formulario_id INTEGER NOT NULL," +
                    "nombre TEXT NOT NULL," +
                    "correo TEXT NOT NULL," +
                    "firma TEXT NOT NULL" +
                    ");";


    // Crear la tabla Estado
    private static final String CREATE_TABLE_ESTADOS_PQRSF =
            "CREATE TABLE usr_app_estado_cierre_crm (" +
                    "id INTEGER PRIMARY KEY," +
                    "nombre TEXT NOT NULL" +
                    ");";

    // Crear la tabla Estado
    private static final String CREATE_TABLE_ESTADOS_COMPROMISO =
            "CREATE TABLE usr_app_estado_compromiso_crm (" +
                    "id INTEGER PRIMARY KEY," +
                    "nombre TEXT NOT NULL" +
                    ");";


    // Crear la tabla Proceso
    private static final String CREATE_TABLE_PROCESOS =
            "CREATE TABLE usr_app_procesos (" +
                    "id INTEGER PRIMARY KEY," +
                    "nombre TEXT NOT NULL" +
                    ");";


 // Crear la tabla Pqrsf
    private static final String CREATE_TABLE_PQRSF =
            "CREATE TABLE usr_app_pqrsf_crm (" +
                    "id INTEGER PRIMARY KEY," +
                    "nombre TEXT NOT NULL" +
                    ");";

 // Crear la tabla Responsable
    private static final String CREATE_TABLE_RESPONSABLE =
            "CREATE TABLE usr_app_usuarios_responsable (" +
                    "id INTEGER PRIMARY KEY," +
                    "nombre TEXT NOT NULL," +
                    "email TEXT NOT NULL" +
                    ");";

    // Crear la tabla Responsable
    private static final String CREATE_TABLE_VISITANTE=
            "CREATE TABLE usr_app_usuarios_visitante (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "email TEXT NOT NULL" +
                    ");";



    // Crear la tabla Cargo
    private static final String CREATE_TABLE_CARGOS =
            "CREATE TABLE usr_app_cargos_crm (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL" +
                    ");";


 // Crear la tabla Sede
    private static final String CREATE_TABLE_SEDES =
            "CREATE TABLE usr_app_sedes_saitemp (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL" +
                    ");";

 // Crear la tabla Solicitante
    private static final String CREATE_TABLE_SOLICITANTES =
            "CREATE TABLE usr_app_solicitante_crm (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL" +
                    ");";

// Crear la tabla Tipo_atencion
    private static final String CREATE_TABLE_TIPO_ATENCION =
            "CREATE TABLE usr_app_atencion_interacion (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL" +
                    ");";

    private static final String CREATE_TABLE_COMPROMISOS =
            "CREATE TABLE usr_app_compromisos (" + // Cambié el nombre de la tabla a 'asistentes'
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "formulario_id INTEGER NOT NULL," +
                    "compromiso TEXT NOT NULL," +
                    "responsable TEXT NOT NULL," +
                    "responsable_id TEXT NOT NULL," +
                    "estado TEXT NOT NULL," +
                    "estado_id TEXT NOT NULL," +
                    "observacion TEXT NOT NULL" +
                    ");";

    // Crear la tabla cliente debida diligencia
    private static final String CREATE_TABLE_CLIENTE_DEBIDA_DILIGENCIA =
            "CREATE TABLE usr_app_cliente_debida_diligencia (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "razon_social TEXT NOT NULL," +
                    "nit TEXT NOT NULL" +
                    ");";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ACTUALIZACION);
        db.execSQL(CREATE_TABLE_SEDES);
        db.execSQL(CREATE_TABLE_PROCESOS);
        db.execSQL(CREATE_TABLE_SOLICITANTES);
        db.execSQL(CREATE_TABLE_TIPO_ATENCION);
        db.execSQL(CREATE_TABLE_VISITANTE);
        db.execSQL(CREATE_TABLE_CARGOS);
        db.execSQL(CREATE_TABLE_ESTADOS_PQRSF);
        db.execSQL(CREATE_TABLE_ESTADOS_COMPROMISO);
        db.execSQL(CREATE_TABLE_COMPROMISOS);
        db.execSQL(CREATE_TABLE_PQRSF);
        db.execSQL(CREATE_TABLE_FORMULARIOS);
        db.execSQL(CREATE_TABLE_EVIDENCIAS);
        db.execSQL(CREATE_TABLE_ASISTENTES);
        db.execSQL(CREATE_TABLE_RESPONSABLE);
        db.execSQL(CREATE_TABLE_CLIENTE_DEBIDA_DILIGENCIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_ACTUALIZACION);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_FORMULARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_EVIDENCIAS);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_ASISTENTES);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_ESTADOS_PQRSF);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_ESTADOS_COMPROMISO);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_COMPROMISOS);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_PROCESOS);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_PQRSF);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_RESPONSABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_VISITANTE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_CARGOS);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_SEDES);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_SOLICITANTES);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_TIPO_ATENCION);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_CLIENTE_DEBIDA_DILIGENCIA);
        onCreate(db);
    }

    public int insertData(String tabla, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowId = (int) db.insert(tabla, null, values);
        db.close();
        return rowId;
    }

    public long actualizarRegistro(String tabla, ContentValues valores, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        long rowId = db.update(tabla, valores, whereClause, whereArgs);
        db.close();
        return rowId;
    }

    public boolean eliminarRegistro(String tabla, String campo, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM " + tabla + " WHERE " + campo + " = '" + id + "'";

        try {
            db.execSQL(sql);
            return true; // Devuelve true si la eliminación fue exitosa
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Devuelve false si hay algún error
        } finally {
            db.close();
        }
    }
}


