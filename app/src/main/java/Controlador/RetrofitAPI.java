package Controlador;

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
    @POST("login.php")
    Call<LoginResponse> login(@Field("nombreusuario") String nombreusuario, @Field("pass") String pass);

    @FormUrlEncoded
    @POST("insertar_usuario.php")
    Call<SignUpResponse> registro(@Field("nombreusuario") String nombreusuario, @Field("contrasenia") String pass, @Field("email") String email, @Field("telefono") int telefono);

}