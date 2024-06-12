package Controlador;

import android.media.Image;

import java.util.Date;

import Modelo.LoginResponse;
import Modelo.SignUpResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Umair Adil on 10/12/2016.
 */

public interface RetrofitAPI {

    //TODO Replace with your API's Login Method
    @FormUrlEncoded
    @POST("inicio")
    Call<LoginResponse> login(@Field("nombreUsuario") String nombreusuario, @Field("pass") String pass);

    @FormUrlEncoded
    @POST("insertar_usuario.php")
    Call<SignUpResponse> registro(@Field("nombreusuario") String nombreusuario, @Field("contrasenia") String pass, @Field("email") String email, @Field("telefono") int telefono);

    /*
    @FormUrlEncoded
    @POST("completar_registro.php")
    Call<SignUpResponse> completarRegistro(@Field("nombre") String nombre, @Field("apellidos") String apellidos, @Field("direccion") String direccion,
                                           @Field("cp") String codigoPostal, @Field("provincia") String provincia, @Field("fechaNacimiento") Date fechaNacimiento,
                                           @Field("cuentaIban") String cuentaIban, @Field("socio") int socio));
}*/
}