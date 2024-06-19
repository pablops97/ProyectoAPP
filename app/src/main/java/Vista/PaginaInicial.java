package Vista;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectopracticas.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectopracticas.databinding.ActivityPaginaInicialBinding;
import com.squareup.picasso.Picasso;

import Controlador.UrlInterface;

public class PaginaInicial extends AppCompatActivity implements UrlInterface {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityPaginaInicialBinding binding;
    TextView nombreUsuarioSidebar;
    TextView emailUsuarioSidebar;
    ImageView fotoUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPaginaInicialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarPaginaInicial.toolbar);
        binding.appBarPaginaInicial.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(PaginaInicial.this)
                        .setTitle(R.string.tituloBotonAbandonarSesion)
                        .setPositiveButton(R.string.opcionSi, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //PARA SALIR DE LA SESIÓN ELIMINAMOS LAS SHAREDPREFERENCES Y DEVOLVEMOS AL USUARIO AL INICIO DE SESIÓN
                                SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.loginPreference), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                Intent i = new Intent(PaginaInicial.this, InicioRegistroView.class);
                                (PaginaInicial.this).finish();
                                startActivity(i);
                            }
                        })
                        .setNegativeButton(R.string.opcionNo, null)
                        .create().show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_pagina_inicial);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        iniciarComponentes(navigationView);
    }
    private void iniciarComponentes(NavigationView navigationView){
        View headerView = navigationView.getHeaderView(0); // Obtener la vista de cabecera del NavigationView
        nombreUsuarioSidebar = headerView.findViewById(R.id.nombreUsuarioSidebar);
        emailUsuarioSidebar = headerView.findViewById(R.id.emailUsuarioSidebar);
        fotoUsuario = headerView.findViewById(R.id.imagenUsuarioSidebar);

        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.loginPreference), Context.MODE_PRIVATE);
        nombreUsuarioSidebar.setText(sharedPreferences.getString(getResources().getString(R.string.usuarioPreference), null));
        emailUsuarioSidebar.setText(sharedPreferences.getString(getResources().getString(R.string.emailPreference), null));
        Picasso.get()
                .load(URL_USUARIO_IMAGENES + sharedPreferences.getString(getResources().getString(R.string.imagenUsuarioPreference), null))
                .placeholder(R.drawable.icono_feliz)
                .into(fotoUsuario);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pagina_inicial, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_pagina_inicial);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}