package Controlador;

import java.util.ArrayList;

import Modelo.Evento;
import Modelo.LoginResponse;
import Modelo.Matriculacion;
import Modelo.ConfirmacionResponse;
import Modelo.Usuario;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Umair Adil on 10/12/2016.
 */

public interface RetrofitAPI {

    //TODO Replace with your API's Login Method
    @FormUrlEncoded
    @POST("inicio")
    Call<LoginResponse> login(@Field("nombreUsuario") String nombreusuario, @Field("pass") String pass);

    @FormUrlEncoded
    @POST("usuario")
    Call<Usuario> obtenerDatos(@Field("id") int idUsuario);

    @FormUrlEncoded
    @POST("registro")
    Call<ConfirmacionResponse> registro(@Field("nombreusuario") String nombreusuario, @Field("contrasenia") String pass, @Field("email") String email);

    /*
    @FormUrlEncoded
    @POST("completar_registro.php")
    Call<SignUpResponse> completarRegistro(@Field("nombre") String nombre, @Field("apellidos") String apellidos, @Field("direccion") String direccion,
                                           @Field("cp") String codigoPostal, @Field("provincia") String provincia, @Field("fechaNacimiento") Date fechaNacimiento,
                                           @Field("cuentaIban") String cuentaIban, @Field("socio") int socio));
}*/ @GET("eventos")
    Call<ArrayList<Evento>> obtenerEventos();

    @FormUrlEncoded
    @POST("eventos/inscripcion")
    Call<ConfirmacionResponse> confirmarInscripcion(@Field("idusuario") int idUsuario, @Field("idevento") int idEvento);

    @FormUrlEncoded
    @POST("eventos/miseventos")
    Call<ArrayList<Matriculacion>> miseventos(@Field("idusuario") int idUsuario);

    @FormUrlEncoded
    @POST("eventos/baja")
    Call<ConfirmacionResponse> bajaEvento(@Field("idusuario")int idUsuario, @Field("idmatriculacion") int idMatriculacion);

    @FormUrlEncoded
    @POST("eventos/recuperar")
    Call<ConfirmacionResponse> recuperarEvento(@Field("idmatriculacion") int idMatriculacion);

    @FormUrlEncoded
    @POST("editar")
    Call<ConfirmacionResponse> editarUsuario(@Field("idusuario") int idUsuario, @Field("usuario") String usuario,
                                             @Field("correo") String correo, @Field("pass") String pass, @Field("iban") String iban);

    //metodo utilizado para subir los datos junto con la imagen
    @POST("completar_edicion")
    Call<String> uploadUserData(
        @Part("nombre") RequestBody nombre,
        @Part("apellidos") RequestBody apellidos,
        @Part("direccion") RequestBody direccion,
        @Part("provincia") RequestBody provincia,
        @Part("codigoPostal") RequestBody codigoPostal,
        @Part("fechaNacimiento") RequestBody fechaNacimiento,
        @Part("iban") RequestBody iban,
        @Part MultipartBody.Part imagen
    );
}