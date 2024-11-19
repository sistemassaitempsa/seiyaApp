package com.example.saitemp;

import android.text.Editable;
import android.text.TextWatcher;

public class GenericTextWatcher implements TextWatcher {

    private Filterable filterable;

    public GenericTextWatcher(Filterable filterable) {
        this.filterable = filterable;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // No es necesario implementar
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        filterable.filter(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
        // No es necesario implementar
    }

    // Interfaz para definir el comportamiento de filtrado
    public interface Filterable {
        void filter(String query);
    }
}

