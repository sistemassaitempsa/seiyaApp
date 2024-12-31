package com.saitempsa.saitemp.interfaz;

import org.json.JSONArray;
import org.json.JSONException;

public interface ApiCallbackTablas {

    public interface ApiCallback<T> {
        void onSuccess(JSONArray result) throws JSONException;
        void onError(String error);
    }

//    interface ApiCallback<T> {
//        void onSuccess(JSONArray result);  // Método para manejar la respuesta exitosa
//        void onError(String error);  // Método para manejar los errores
//    }
}
