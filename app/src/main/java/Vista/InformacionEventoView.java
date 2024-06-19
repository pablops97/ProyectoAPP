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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectopracticas.R;
import com.squareup.picasso.Picasso;

import Controlador.RetrofitAPI;
import Controlador.RetrofitClientInstance;
import Controlador.UrlInterface;
import Modelo.ConfirmacionResponse;
import Vista.ui.home.HomeFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformacionEventoView extends AppCompatActivity implements UrlInterface {
    TextView nombreEvento;
    TextView estadoEvento;
    TextView descripcionEvento;
    TextView precioEvento;
    TextView fechaInicioInscripcion;
    TextView fechaInicio;
    TextView fechaFin;
    TextView numeroParticipantes;
    Button botonInscripcion;

    ImageView fotoEvento;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_evento_view);
        Bundle resultado = getIntent().getExtras();
        iniciarComponentes(resultado);
    }

    private void iniciarComponentes(Bundle resultado){
        nombreEvento = findViewById(R.id.tituloEventoInfo);
        estadoEvento = findViewById(R.id.estadoEventoInfo);
        descripcionEvento = findViewById(R.id.descripcionEventoInfo);
        precioEvento = findViewById(R.id.precioEventoInfo);
        fechaInicioInscripcion = findViewById(R.id.fechaInicioInscripcionInfo);
        fechaInicio = findViewById(R.id.fechaInicioInfo);
        fechaFin = findViewById(R.id.fechaFinInfo);
        numeroParticipantes = findViewById(R.id.numeroPlazasInfo);
        fotoEvento = findViewById(R.id.imagenEventoInfo);
        botonInscripcion = findViewById(R.id.botonInscripcion);




        nombreEvento.setText(resultado.getString("nombreEvento"));
        estadoEvento.setText(resultado.getString("estadoEvento"));
        descripcionEvento.setText(resultado.getString("descripcionEvento"));

        double precio = resultado.getDouble("precioEvento");
        if( precio == 0.0){
            precioEvento.setText("GRATIS");
        }else{
            precioEvento.setText(""+resultado.getDouble("precioEvento") + "€");
        }
        precioEvento.setText(""+resultado.getDouble("precioEvento") + "€");
        fechaInicioInscripcion.setText(resultado.getString("fechaInicioInscripcion"));
        fechaInicio.setText(resultado.getString("fechaInicio"));
        fechaFin.setText(resultado.getString("fechaFin"));
        numeroParticipantes.setText(""+resultado.getInt("numParticipantes"));

        Picasso.get()
                .load(URL_EVENTOS_IMAGENES + resultado.getString("imagen"))
                .placeholder(R.drawable.icono_feliz)
                .into(fotoEvento);

        //activar o desactivar boton de inscripcion según si el evento esta lleno o no
        comprobacionLleno(resultado.getBoolean("lleno"));

        //comprobar si el estado es planificado o no
        comprobacionEstado(resultado.getString("estadoEvento"));


        botonInscripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(InformacionEventoView.this)
                        .setTitle(R.string.mensajeAlertInscripcion)
                        .setPositiveButton(R.string.opcionSi, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sharedPreferences = v.getContext().getSharedPreferences(getResources().getString(R.string.loginPreference), Context.MODE_PRIVATE);
                                if(sharedPreferences.getString(getResources().getString(R.string.estadoRegistroPreference),null).equalsIgnoreCase("incompleto")){
                                    Intent registro = new Intent(v.getContext(), CompletarRegistroView.class);
                                    startActivity(registro);

                                }else{
                                    Call<ConfirmacionResponse> call = RetrofitClientInstance
                                            .getRetrofitInstance(v.getContext())
                                            .create(RetrofitAPI.class)
                                            .confirmarInscripcion(sharedPreferences.getInt(getResources().getString(R.string.idPreference), 0),resultado.getInt("idEvento"));
                                    call.enqueue(new Callback<ConfirmacionResponse>() {
                                        @Override
                                        public void onResponse(Call<ConfirmacionResponse> call, Response<ConfirmacionResponse> response) {
                                            Toast.makeText(InformacionEventoView.this, response.body().getMensaje(), Toast.LENGTH_SHORT).show();
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(Call<ConfirmacionResponse> call, Throwable t) {
                                            Toast.makeText(InformacionEventoView.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }
                        })
                        .setNegativeButton(R.string.opcionNo, null)
                        .create()
                        .show();
            }
        });
    }

    private void comprobacionLleno(boolean resultado){
        if(resultado){
            botonInscripcion.setVisibility(View.INVISIBLE);
        }else{
            botonInscripcion.setVisibility(View.VISIBLE);
        }
    }

    public void comprobacionEstado(String estado){
        if(estado.equalsIgnoreCase("Planificado")){
            botonInscripcion.setVisibility(View.VISIBLE);
        }else{
            botonInscripcion.setVisibility(View.INVISIBLE);
        }
    }


}