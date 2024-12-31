package com.saitempsa.saitemp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.saitempsa.saitemp.R;
import com.google.android.material.navigation.NavigationView;
import com.saitempsa.saitemp.databinding.ActivityNavegacionBinding;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

public class NavegacionActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavegacionBinding binding;

    private RequestQueue requestQueue;

    private static final int TIME_INTERVAL = 2000; // Intervalo de tiempo en milisegundos
    private long mBackPressedTime; // Variable para almacenar el tiempo de la última pulsación
    private DrawerLayout drawerLayout;
    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavegacionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        requestQueue = Volley.newRequestQueue(this);

        drawerLayout = findViewById(R.id.drawer_layout);


        setSupportActionBar(binding.appBarNavegacion.toolbar);
//        binding.appBarNavegacion.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.crm, R.id.crm, R.id.crm_guardados)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navegacion);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//        Esta función permite redirigir la navegación a un fragment especifico
//        navigateBasedOnIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navegacion, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navegacion);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            goToMainActivity();
            return true;
        }else if (id == R.id.action_settings2) {
            goToMainActivity(true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START); // Si el DrawerLayout está abierto, ciérralo
        } else {
            // Si el DrawerLayout está cerrado, implementa la funcionalidad de "pulsar dos veces atrás para salir"
            if (doubleBackToExitPressedOnce) {
                goToMainActivity(); // Llamar a la función goToMainActivity si se presiona dos veces hacia atrás
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Presiona de nuevo para salir", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, TIME_INTERVAL);
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToMainActivity(Boolean borrar) {
        SharedPreferences sharedPreferences = getSharedPreferences("saitemp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token"); // Reemplaza "nombre_token" con el nombre de tu token
        editor.remove("usuario"); // Reemplaza "nombre_token" con el nombre de tu token
        editor.remove("contrasena"); // Reemplaza "nombre_token" con el nombre de tu token
        editor.apply(); // o editor.commit() si necesitas asegurarte de que los cambios se apliquen de inmediato

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}