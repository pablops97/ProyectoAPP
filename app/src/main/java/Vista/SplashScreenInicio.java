package Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.proyectopracticas.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_inicio);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Ocultamos la barra de titulo
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen_inicio);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // Arrancamos la siguiente actividad
                Intent mainIntent = new Intent().setClass(SplashScreenInicio.this, InicioRegistroView.class);
                startActivity(mainIntent);
                // Cerramos esta actividad para que el usuario no pueda volver a ella mediante botón de volver
                finish();
            }
        };

        // Simulamos un tiempo en el proceso de carga durante el cual mostramos el SplashScreen
        Timer timer = new Timer();
        timer.schedule(task, 2000); // Tiempo de espera del temporizador en milisegundos (2 sg)
    }
}