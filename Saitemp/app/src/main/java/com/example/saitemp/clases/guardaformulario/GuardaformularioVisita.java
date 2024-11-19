package com.example.saitemp.clases.guardaformulario;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.saitemp.bd.saitemp.DatabaseHelper;
import com.example.saitemp.clases.Compromiso;
import com.example.saitemp.clases.Formulario_visitas;
import com.example.saitemp.clases.clases_adaptadores.Asistente;
import com.example.saitemp.clases.clases_adaptadores.Evidencia;
import com.example.saitemp.interfaz.EliminaFirmasCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GuardaformularioVisita {

    public static long guardaFormulario(Context context, Formulario_visitas formulario) {

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        ContentValues values = new ContentValues();
        List<Asistente> asistentes = formulario.getAsistentes();
        List<Compromiso> compromisos = formulario.getCompromisos();
        List<Evidencia> evidencias = formulario.getEvidencias();
        int idFormulario;
        try {
            values.put("sede", formulario.getSede());
            values.put("sede_id", formulario.getSede_id());
            values.put("proceso", formulario.getProceso());
            values.put("proceso_id", formulario.getProceso_id());
            values.put("solicitante", formulario.getSolicitante());
            values.put("solicitante_id", formulario.getSolicitante_id());
            values.put("medio_atencion", formulario.getMedio_atencion());
            values.put("medio_atencion_id", formulario.getMedio_atencion_id());
            values.put("nit_numero_identificacion", formulario.getNumero_documento());
            values.put("nombre_razon_social", formulario.getRazon_social());
            values.put("cierra_radicado", formulario.getCierra_radicado());
            values.put("telefono_contacto", formulario.getTelefono());
            values.put("correo_contacto", formulario.getCorreo());
            values.put("visitante", formulario.getVisitante());
            values.put("visitante_id", formulario.getVisitante_id());
            values.put("cargo_visitante", formulario.getCargo_visitante());
            values.put("cargo_visitante_id", formulario.getVisitante_id());
            values.put("visitado", formulario.getVisitado());
            values.put("cargo_visitado", formulario.getCargo_visitado());
            values.put("objetivo", formulario.getObjetivo());
            values.put("alcance", formulario.getAlcance());
            values.put("tema", formulario.getTema());
            values.put("estado", formulario.getEstado());
            values.put("estado_id", formulario.getEstado_id());
            values.put("pqrsf", formulario.getPqrsf());
            values.put("pqrsf_id", formulario.getPqrsf_id());
            values.put("responsable", formulario.getResponsable());
            values.put("correo_responsable", formulario.getCorreo_responsable());
            values.put("responsable_id", formulario.getResponsable_id());
            values.put("hora_incio", formulario.getHora_inicio());
            values.put("observaciones", formulario.getObservacion());
            values.put("latitud", formulario.getLatitud());
            values.put("longitud", formulario.getLongitud());
            idFormulario = dbHelper.insertData("usr_app_formulario_ingreso", values);


            if (asistentes != null && !asistentes.isEmpty()) {
                manejoAsistentes(context, asistentes, 1, (int) idFormulario);
            }

            if (compromisos != null && !compromisos.isEmpty()) {
                manejoCompromisos(context, compromisos, 1, idFormulario);
            }

            if (evidencias != null && !evidencias.isEmpty()) {
                manejoEvidencias(context, evidencias, 1, idFormulario);
            }

            dbHelper.close();
        } catch (Exception e) {
            Toast.makeText(context, "error al insertar el registro", Toast.LENGTH_SHORT).show();
            Log.d("Error", "error al insertar el registro" + e);
            return 0;
        }
        Toast.makeText(context, "Formulario guardado exitosamente", Toast.LENGTH_SHORT).show();
        return idFormulario;
    }

    public static Long actualizaRegistro(Context context, String tabla, ContentValues values, String id) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        String whereClause = "id = ?";
        String[] whereArgs = new String[]{String.valueOf(id)};

        Long idFormulario = dbHelper.actualizarRegistro(tabla, values, whereClause, whereArgs);
        Toast.makeText(context, "Datos actualizados con éxito", Toast.LENGTH_SHORT).show();
        return idFormulario;

    }

    public static void manejoCompromisos(Context context, List<Compromiso> compromisos, int accion, int id_formulario) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        ContentValues campos_compromisos = new ContentValues();
        if (accion == 1) {
            for (Compromiso compromiso : compromisos) {
                if (compromiso.getId() != 0) {
                    campos_compromisos.put("id", compromiso.getId());
                }
                if (id_formulario != 0) {
                    campos_compromisos.put("formulario_id", id_formulario);
                }
                campos_compromisos.put("compromiso", compromiso.getCompromiso());
                campos_compromisos.put("estado", compromiso.getEstado());
                campos_compromisos.put("estado_id", compromiso.getEstado_id());
                campos_compromisos.put("responsable", compromiso.getResponsable());
                campos_compromisos.put("responsable_id", compromiso.getResponsable_id());
                campos_compromisos.put("observacion", compromiso.getObservacion());
                Log.d("compromiso",campos_compromisos.toString());
                dbHelper.insertData("usr_app_compromisos", campos_compromisos);
            }
        } else if (accion == 2) {
            for (Compromiso compromiso : compromisos) {
                int id = compromiso.getId();
                int formulario_id = compromiso.getFormulario_id();
                campos_compromisos.put("compromiso", compromiso.getCompromiso());
                campos_compromisos.put("estado", compromiso.getEstado());
                campos_compromisos.put("estado_id", compromiso.getEstado_id());
                campos_compromisos.put("responsable", compromiso.getResponsable());
                campos_compromisos.put("responsable_id", compromiso.getResponsable_id());
                campos_compromisos.put("observacion", compromiso.getObservacion());
                String whereClause = "id = ? AND formulario_id = ?";
                String[] whereArgs = new String[]{String.valueOf(id), String.valueOf(formulario_id)};
                dbHelper.actualizarRegistro("usr_app_compromisos", campos_compromisos, whereClause, whereArgs);
            }
        }
    }

    public static void manejoEvidencias(Context context, List<Evidencia> evidencias, int accion, int id_formulario) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        ContentValues campos_evidencias = new ContentValues();
        if (accion == 1) {
            for (Evidencia evidencia : evidencias) {
                if (evidencia.getId() != 0) {
                    campos_evidencias.put("id", evidencia.getId());
                }
                if (id_formulario != 0) {
                    campos_evidencias.put("formulario_id", id_formulario);
                }
                campos_evidencias.put("path", evidencia.getPath());
                campos_evidencias.put("descripcion", evidencia.getDescripcion());
                campos_evidencias.put("nombre", evidencia.getNombre());
                dbHelper.insertData("usr_app_evidencias", campos_evidencias);
            }
        } else if (accion == 2) {
            for (Evidencia evidencia : evidencias) {
                int id = evidencia.getId();
                int formulario_id = evidencia.getFormulario_id();
                campos_evidencias.put("path", evidencia.getPath());
                campos_evidencias.put("descripcion", evidencia.getDescripcion());
                campos_evidencias.put("nombre", evidencia.getNombre());
                String whereClause = "id = ? AND formulario_id = ?";
                String[] whereArgs = new String[]{String.valueOf(id), String.valueOf(formulario_id)};
                dbHelper.actualizarRegistro("usr_app_evidencias", campos_evidencias, whereClause, whereArgs);
            }
        }
    }


    public static void manejoAsistentes(Context context, List<Asistente> asistentes, int accion, long formulario_id) {

        if (asistentes != null && !asistentes.isEmpty()) {
            String directorioImagenes = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/saitemp/ImagenesFirmas/";

            File carpeta = new File(directorioImagenes);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            for (Asistente asistente : asistentes) {
                Bitmap firmaBitmap = asistente.getFirma();
                if (firmaBitmap != null) {
                    String nombreArchivo = "firma_" + System.currentTimeMillis() + ".png";
                    String rutaCompleta = directorioImagenes + nombreArchivo;

                    try {
                        FileOutputStream stream = new FileOutputStream(rutaCompleta);
                        firmaBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        stream.flush();
                        stream.close();

                        guardaActualizaAsistente(context, asistente, accion, rutaCompleta, formulario_id);

                    } catch (IOException e) {
                        Toast.makeText(context, "error al insertar el registro", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public static void guardaActualizaAsistente(Context context, Asistente asistente, int accion, String rutaCompleta, long id_formulario) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        ContentValues campo_asistente = new ContentValues();

        if (accion == 1) {
            if (asistente.getId() != 0) {
                campo_asistente.put("id", asistente.getId());
            } else {
                campo_asistente.put("formulario_id", id_formulario);
            }
            campo_asistente.put("cargo", asistente.getCargo());
            campo_asistente.put("nombre", asistente.getNombre());
            campo_asistente.put("correo", asistente.getCorreo());
            campo_asistente.put("firma", rutaCompleta);
            dbHelper.insertData("usr_app_asistentes", campo_asistente);
        } else if (accion == 2) {
            int id = asistente.getId();
            int formulario_id = asistente.getFormulario_id();
            campo_asistente.put("cargo", asistente.getCargo());
            campo_asistente.put("nombre", asistente.getNombre());
            campo_asistente.put("correo", asistente.getCorreo());
            String whereClause = "id = ? AND formulario_id = ?";
            String[] whereArgs = new String[]{String.valueOf(id), String.valueOf(formulario_id)};
            dbHelper.actualizarRegistro("usr_app_asistentes", campo_asistente, whereClause, whereArgs);
        } else if (accion == 3) {
            int id = asistente.getId();
            int formulario_id = asistente.getFormulario_id();
//            campo_asistente.put("id", asistente.getFormulario_id());
            campo_asistente.put("formulario_id", asistente.getFormulario_id());
            campo_asistente.put("cargo", asistente.getCargo());
            campo_asistente.put("nombre", asistente.getNombre());
            campo_asistente.put("correo", asistente.getCorreo());
            campo_asistente.put("firma", rutaCompleta);
            dbHelper.insertData("usr_app_asistentes", campo_asistente);
            String whereClause = "id = ? AND formulario_id = ?";
            String[] whereArgs = new String[]{String.valueOf(id), String.valueOf(formulario_id)};
           long actaulizar = dbHelper.actualizarRegistro("usr_app_asistentes", campo_asistente, whereClause, whereArgs);
        }
    }

    public static void eliminaFirmasAsync(Context context, long id_formulario, EliminaFirmasCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    eliminaFirmas(context, id_formulario);  // Llama a la función que ya tienes
                    // Si la operación fue exitosa
                    if (callback != null) {
                        callback.onSuccess();
                    }
                } catch (Exception e) {
                    // Si ocurre algún error, se envía al callback
                    if (callback != null) {
                        callback.onFailure(e);
                    }
                }
            }
        });

        executor.shutdown();  // Opcional: apagar el executor después de completar la tarea
    }

    public static void eliminaFirmas(Context context, long id_formulario) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usr_app_asistentes WHERE formulario_id = ?", new String[]{String.valueOf(id_formulario)});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String firma = cursor.getString(cursor.getColumnIndex("firma"));
                    if (firma != null && !firma.isEmpty()) {
                        File imagenFile = new File(firma);
                        if (imagenFile.exists()) {
                            boolean deleted = imagenFile.delete();
                        }
                    }
                } while (cursor.moveToNext());
                dbHelper.eliminarRegistro("usr_app_asistentes", "formulario_id", (int) id_formulario);
            } else {
                Log.d("firmas", "No hay registros en el cursor");
            }
            cursor.close();
        }

    }

}
