package com.example.saitemp.interfaz;

import org.json.JSONObject;

public interface ApiCallbackTablas2 {

    interface ApiCallback<T> {
        void onSuccess(JSONObject jsonObject);  // Método para manejar la respuesta exitosa
        void onError(String error);  // Método para manejar los errores
    }
}
