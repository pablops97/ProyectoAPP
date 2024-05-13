package Vista;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.proyectopracticas.R;

import Controlador.RetrofitAPI;
import Controlador.RetrofitClientInstance;
import Modelo.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InicioSesionFragment extends Fragment {

    private EditText usuario;
    private EditText pass;
    private Button botonEnviar;
    private ImageButton botonVerPass;
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {
            verificarCamposCompletos();
        }
    };

    private boolean usuarioConfirmado = false;
    private boolean passConfirmada = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.fragment_inicio_sesion, container, false);
        iniciarComponentes(vista);
        return vista;

    }

    private void iniciarComponentes(View view){
        usuario = view.findViewById(R.id.usuarioField);
        pass = view.findViewById(R.id.passField);
        botonEnviar = view.findViewById(R.id.botonInicioSesion);
        botonVerPass = view.findViewById(R.id.imageButton);
        botonEnviar.setEnabled(false);
        usuario.addTextChangedListener(textWatcher);
        pass.addTextChangedListener(textWatcher);

        botonVerPass.setOnTouchListener((v, event) -> {
            //EVENTO PARA ACTIVAR Y DESACTIVAR LA POSIBILIDAD DE VER LA CONTRASEÑA
            switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN:
                    pass.setInputType(InputType.TYPE_CLASS_TEXT);
                    break;
                case MotionEvent.ACTION_UP:
                    pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    break;
            }
            return true;
        });

        botonEnviar.setOnClickListener(v -> {
            //CREAMOS UNA LLAMADA A LA API
            Call<LoginResponse> call = RetrofitClientInstance
                    .getRetrofitInstance(v.getContext())
                    .create(RetrofitAPI.class)
                    .login(usuario.getText().toString(), pass.getText().toString());
            //NOS DEVUELVE UN OBJETO DEL TIPO LOGINRESPONSE CON LOS DATOS QUE NOS INTERESA
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    SharedPreferences sharedPreferences = v.getContext().getSharedPreferences(v.getResources().getString(R.string.loginPreference), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if(response.body().getNombreUsuario() !=null){
                        editor.putString("nombreUsuario", response.body().getNombreUsuario());
                        editor.apply();
                        Intent i = new Intent(v.getContext(), PaginaInicial.class);
                        startActivity(i);

                    }else{
                        Toast.makeText(v.getContext(), "No existe este usuario", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });



        });


    }


    private void verificarCamposCompletos() {
        String user = usuario.getText().toString().trim();
        String contra = pass.getText().toString().trim();

        // Habilitar el botón de enviar datos solo si los campos no están vacíos
        botonEnviar.setEnabled(!user.isEmpty() && !contra.isEmpty());
    }


}