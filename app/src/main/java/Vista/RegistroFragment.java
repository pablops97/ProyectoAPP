package Vista;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectopracticas.R;

import java.util.HashMap;
import java.util.Map;

import Controlador.ClaseComprobar;
import Controlador.RetrofitAPI;
import Controlador.RetrofitClientInstance;
import Modelo.LoginResponse;
import Modelo.SignUpResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroFragment extends Fragment {
    private EditText nombreUsuario;
    private EditText password;
    private EditText confirmarPassword;
    private EditText correo;
    private EditText telefono;
    private Button registrar;
    private ImageView checkConfirmarPass;

    /*VARIABLES PARA EL CONTROL DE LOS ESTADOS(correcto o incorrecto)*/
    HashMap<String, Boolean> estados = new HashMap<>();

    HashMap<String, String> errores = new HashMap<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.fragment_registro, container, false);
        iniciarComponentes(vista);
        return vista;
    }

    private void iniciarComponentes(View view){
        //INICIALIZAR VARIABLES
        errores.put("estadoUsuario", "El usuario no cumple los requisitos necesarios");
        errores.put("estadoPass", "La contraseña no es válida");
        errores.put("estadoConfirmarPass", "Las contraseñas no son iguales");
        errores.put("estadoCorreo", "El correo es incorrecto, por favor, modifiquelo");

        estados.put("estadoUsuario", false);
        estados.put("estadoPass", false);
        estados.put("estadoConfirmarPass", false);
        estados.put("estadoCorreo", false);

        nombreUsuario = view.findViewById(R.id.nombreRegistroField);
        password = view.findViewById(R.id.passRegistroField);
        confirmarPassword = view.findViewById(R.id.confirmarPassField);
        correo = view.findViewById(R.id.correoRegistroField);
        telefono = view.findViewById(R.id.telefonoRegistroField);
        registrar = view.findViewById(R.id.botonRegistro);
        checkConfirmarPass = view.findViewById(R.id.checkConfirmarPass);

        checkConfirmarPass.setVisibility(View.INVISIBLE);


        //EVENTOS DE COMPROBACIONES
        nombreUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(ClaseComprobar.isValidNickName(nombreUsuario.getText().toString())){
                    estados.put("estadoUsuario", true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(ClaseComprobar.isValidPassword(password.getText().toString())){
                    estados.put("estadoPass", true);
                }else{
                    estados.put("estadoPass", false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmarPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                confirmarPassword.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(confirmarPassword.getText().toString().isEmpty()){
                            checkConfirmarPass.setVisibility(View.INVISIBLE);
                            estados.put("estadoConfirmarPass", false);
                        }
                        if(confirmarPassword.getText().toString().equals(password.getText().toString())){
                            checkConfirmarPass.setVisibility(View.VISIBLE);
                            checkConfirmarPass.setImageDrawable(view.getResources().getDrawable(R.drawable.baseline_check_24));
                            confirmarPassword.setBackgroundColor(view.getResources().getColor(R.color.fondoConfirmacion));
                            estados.put("estadoConfirmarPass", true);
                        } else if (!confirmarPassword.getText().toString().equals(password.getText().toString())) {
                            checkConfirmarPass.setVisibility(View.VISIBLE);
                            checkConfirmarPass.setImageDrawable(view.getResources().getDrawable(R.drawable.baseline_close_24));
                            confirmarPassword.setBackgroundColor(view.getResources().getColor(R.color.fondoDenegado));
                            estados.put("estadoConfirmarPass", false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }else{
                confirmarPassword.setBackgroundColor(v.getResources().getColor(R.color.transparente));
                checkConfirmarPass.setVisibility(View.INVISIBLE);

            }
        });
        correo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(ClaseComprobar.isEmailValid(correo.getText().toString())){
                    estados.put("estadoCorreo", true);
                }else{
                    estados.put("estadoCorreo", false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String errores = informarError();
                if(!errores.isEmpty()){
                    Toast.makeText(v.getContext(), errores, Toast.LENGTH_SHORT).show();
                }else{
                    //LOGICA DE REGISTRO EN BASE DE DATOS
                    Call<SignUpResponse> call = RetrofitClientInstance
                        .getRetrofitInstance(v.getContext())
                        .create(RetrofitAPI.class)
                        .registro(nombreUsuario.getText().toString(), password.getText().toString()
                        , correo.getText().toString()
                        , Integer.parseInt(telefono.getText().toString()));
                    //LLAMADA A LA API DE LA BASE DE DATOS PARA RECIBIR UN MENSAJE SI EL REGISTRO HA SIDO EXITOSO O NO
                    call.enqueue(new Callback<SignUpResponse>() {
                        @Override
                        public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                            View usuarioRegistrado = null;
                            //ASIGNAMOS DISTINTAS VIEWS SEGUN EL CODIGO QUE RECIBIMOS DE LA RESPUESTA DE LA API
                            switch(response.body().getCodigo()){
                                case 0:
                                    usuarioRegistrado = getLayoutInflater().inflate(R.layout.alerta_usuario_registrado, null);
                                    TextView textoRegistrado = usuarioRegistrado.findViewById(R.id.usuarioRegistradoExito);
                                    textoRegistrado.setText(response.body().getMensaje());
                                    break;
                                default:
                                    usuarioRegistrado = getLayoutInflater().inflate(R.layout.alert_usuario_no_registrado, null);
                                    TextView textoNoRegistrado = usuarioRegistrado.findViewById(R.id.mensajeNoRegistro);
                                    textoNoRegistrado.setText(response.body().getMensaje());
                                    break;
                            }
                            //GENERAMOS UN CUADRO DE ALERTA CON DICHA VISTA
                            new AlertDialog.Builder(v.getContext())
                                    .setView(usuarioRegistrado)
                                    .setPositiveButton("Aceptar", (dialog, which) -> {
                                        nombreUsuario.setText("");
                                        password.setText("");
                                        confirmarPassword.setText("");
                                        correo.setText("");
                                        telefono.setText("");
                                    })
                                    .create().show();
                        }

                        @Override
                        public void onFailure(Call<SignUpResponse> call, Throwable t) {
                            Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("error registro", t.getMessage());
                        }
                    });
                }
            }
        });

    }


    //METODO PARA INFORMAR DE LOS ERRORES, RECORRIENDO LOS HASHMAP QUE HEMOS MODIFICADO SEGUN LA COMPROBACIÓN DE LOS CAMPOS
    private String informarError(){
        String error = "";
        for (HashMap.Entry<String, Boolean> estados : estados.entrySet()) {
            if(estados.getValue() == false){
                for(Map.Entry<String, String> errores : errores.entrySet()){
                    if(estados.getKey() == errores.getKey()){
                        error += errores.getValue() + "\n";
                    }
                }
            }
        }
        return error;
    }
}