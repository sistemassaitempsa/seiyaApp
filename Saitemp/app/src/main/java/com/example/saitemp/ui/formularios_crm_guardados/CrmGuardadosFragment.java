package com.example.saitemp.ui.formularios_crm_guardados;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.saitemp.R;
import com.example.saitemp.adaptadores.AdapterFormularioCrmGuardado;
import com.example.saitemp.clases.Formulario_visitas;
import com.example.saitemp.clases.clases_adaptadores.CrmGuardado;
import com.example.saitemp.consultasApi.ConsultaFormularioGuardadoCrm;

import java.util.ArrayList;
import java.util.List;

public class CrmGuardadosFragment extends Fragment {

    private List<CrmGuardado> formulariosGuardados = new ArrayList<>();
    private RecyclerView formulariosGuardadosRecycler;
    private AdapterFormularioCrmGuardado adapterFormularioCrmGuardado;

    private TextView tv_res_sede, tv_res_proceso, tv_res_solicitante, tv_res_nit, tv_res_razon_social;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crm_guardados, container, false);

        formulariosGuardadosRecycler = (RecyclerView) view.findViewById(R.id.rv_crm_guardados);
        formulariosGuardadosRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        List<CrmGuardado> formularios = ConsultaFormularioGuardadoCrm.obtenerFormularios(getContext());
        adapterFormularioCrmGuardado = new AdapterFormularioCrmGuardado(formularios);
        formulariosGuardadosRecycler.setAdapter(adapterFormularioCrmGuardado);

        adapterFormularioCrmGuardado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la posición del ítem clicado
                int position = formulariosGuardadosRecycler.getChildAdapterPosition(v);
                if (position != RecyclerView.NO_POSITION) {
                    // Obtener el objeto en esa posición
                    CrmGuardado item = adapterFormularioCrmGuardado.getItemAtPosition(position);
                    // Mostrar el id y la razón social (o realizar otra acción)

                    Formulario_visitas formulario = new Formulario_visitas();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", item.getId());
                    bundle.putBoolean("actualizar", true);

                    // Obtener el NavController
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_navegacion);

                    // Navegar al nuevo fragmento pasando el bundle
                    navController.navigate(R.id.crm, bundle);

                }
            }
        });


        return view;
    }
}