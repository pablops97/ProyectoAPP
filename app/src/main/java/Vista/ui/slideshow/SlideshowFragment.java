package Vista.ui.slideshow;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectopracticas.R;
import com.example.proyectopracticas.databinding.FragmentSlideshowBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Controlador.ClaseComprobar;
import Controlador.RetrofitAPI;
import Controlador.RetrofitClientInstance;
import Controlador.UrlInterface;
import Modelo.ConfirmacionResponse;
import Modelo.LoginResponse;
import Modelo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlideshowFragment extends Fragment implements UrlInterface {

    private FragmentSlideshowBinding binding;
    ImageView desplegableInfoPass;
    ImageView foto;
    EditText nombreUsuario;
    EditText emailUsuario;
    EditText pass;
    EditText confirmarPass;
    EditText iban;
    Button botonEnviar;
    Usuario usuario;
    SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedPreferences = root.getContext().getSharedPreferences(getResources().getString(R.string.loginPreference), Context.MODE_PRIVATE);

        iniciarComponentes(root);

        // llamada de la api para obtener los datos del usuario actualizados
        Call<Usuario> call = RetrofitClientInstance
                .getRetrofitInstance(root.getContext())
                .create(RetrofitAPI.class)
                .obtenerDatos(sharedPreferences.getInt(getResources().getString(R.string.idPreference), 0));
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                usuario = response.body();
                if (usuario != null) {
                    actualizarUIConDatosUsuario(usuario);
                } else {
                    Toast.makeText(root.getContext(), "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(root.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void iniciarComponentes(View v) {
        foto = v.findViewById(R.id.imagenEditarUsuario);
        Picasso.get()
                .load(URL_USUARIO_IMAGENES + sharedPreferences.getString(getResources().getString(R.string.imagenUsuarioPreference), null))
                .placeholder(R.drawable.icono_feliz)
                .error(R.drawable.icono_triste)
                .into(foto);
        desplegableInfoPass = v.findViewById(R.id.desplegableInfoContrasenia);
        nombreUsuario = v.findViewById(R.id.editarUsuario);
        emailUsuario = v.findViewById(R.id.editarCorreo);
        pass = v.findViewById(R.id.editarPass);
        confirmarPass = v.findViewById(R.id.confirmarPassEditada);
        iban = v.findViewById(R.id.editarIban);
        botonEnviar = v.findViewById(R.id.botonEnviarEdicion);

        desplegableInfoPass.setClickable(true);
        desplegableInfoPass.setOnClickListener(v1 -> {
            Dialog settingsDialog = new Dialog(v1.getContext());
            settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.informacion_pass, null));
            settingsDialog.show();
        });

        botonEnviar.setOnClickListener(v1 -> {
            if (comprobarDatos()) {
                Call<ConfirmacionResponse> call = RetrofitClientInstance
                        .getRetrofitInstance(v1.getContext())
                        .create(RetrofitAPI.class)
                        .editarUsuario(sharedPreferences.getInt(getResources().getString(R.string.idPreference), 0),
                                nombreUsuario.getText().toString(),
                                emailUsuario.getText().toString(),
                                pass.getText().toString(),
                                iban.getText().toString());
                call.enqueue(new Callback<ConfirmacionResponse>() {
                    @Override
                    public void onResponse(Call<ConfirmacionResponse> call, Response<ConfirmacionResponse> response) {
                        Toast.makeText(v1.getContext(), response.body().getMensaje(), Toast.LENGTH_LONG).show();
                        if (response.body().getCodigo() == 1) {
                            getActivity().onBackPressed(); // Volver al fragmento anterior
                        }
                    }

                    @Override
                    public void onFailure(Call<ConfirmacionResponse> call, Throwable t) {
                        Toast.makeText(v1.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(v1.getContext(), "Compruebe los datos, hay errores", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarUIConDatosUsuario(Usuario usuario) {
        nombreUsuario.setHint(usuario.getNombreUsuario());
        emailUsuario.setHint(usuario.getCorreo());
        iban.setHint(usuario.getIban());
    }

    public boolean comprobarDatos() {
        boolean usuarioCorrecto = true;
        boolean correoCorrecto = true;
        boolean contraseniaCorrecta = true;

        if (!nombreUsuario.getText().toString().isEmpty()) {
            usuarioCorrecto = ClaseComprobar.isValidNickName(nombreUsuario.getText().toString());
        }
        if (!emailUsuario.getText().toString().isEmpty()) {
            correoCorrecto = ClaseComprobar.isEmailValid(emailUsuario.getText().toString()); // Corregido aquí
        }
        if (!pass.getText().toString().isEmpty()) {
            if(comprobarPass()){
                contraseniaCorrecta = ClaseComprobar.isValidPassword(pass.getText().toString()); // Corregido aquí
            }

        }
        return usuarioCorrecto && correoCorrecto && contraseniaCorrecta;
    }
    public boolean comprobarPass() {
        return pass.getText().toString().equals(confirmarPass.getText().toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}