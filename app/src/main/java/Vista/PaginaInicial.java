package Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectopracticas.R;

public class PaginaInicial extends AppCompatActivity {

    TextView inicio;
    Button botonDesconectar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);
        inicio = findViewById(R.id.textoInicioCompletado);
        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.loginPreference), Context.MODE_PRIVATE);
        inicio.setText("Bienvenido, " + sharedPreferences.getString(getResources().getString(R.string.usuarioPreference), null));
        botonDesconectar = findViewById(R.id.botonDesconectar);
        botonDesconectar.setOnClickListener(v -> new AlertDialog.Builder(PaginaInicial.this)
                .setTitle(getResources().getString(R.string.tituloAlertDesconectar))
                .setMessage(getResources().getString(R.string.mensajeAlertDesconectar))
                .setPositiveButton("Si", (dialog, which) -> {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(getResources().getString(R.string.usuarioPreference));
                    editor.commit();
                    Intent i = new Intent(PaginaInicial.this, InicioRegistroView.class);
                    startActivity(i);
                    finish();
                })
                .setNegativeButton("No", null)
                .create()
                .show());
        //
    }
}