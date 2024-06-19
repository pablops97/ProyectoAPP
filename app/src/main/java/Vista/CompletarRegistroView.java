package Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectopracticas.R;

import java.io.File;
import java.io.IOException;

import Utilidad.FileUtils;
import Controlador.RetrofitAPI;
import Controlador.RetrofitClientInstance;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class CompletarRegistroView extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    ImageView imagen;
    EditText nombre;
    EditText apellidos;
    EditText direccion;
    EditText provincia;
    EditText codigoPostal;
    EditText fechaNacimiento;
    EditText iban;
    Button botonEnviar;
    Button botonSalir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completar_registro_view);
        iniciarComponentes();
    }
    public void iniciarComponentes(){
        imagen = findViewById(R.id.imagenCompletarRegistro);
        nombre = findViewById(R.id.nombreCompletarRegistro);
        apellidos = findViewById(R.id.apellidosCompletarRegistro);
        direccion = findViewById(R.id.direccionCompletarRegistro);
        provincia = findViewById(R.id.provinciaCompletarRegistro);
        codigoPostal = findViewById(R.id.cpCompletarRegistro);
        fechaNacimiento = findViewById(R.id.fechaNacimientoCompletarRegistro);
        iban = findViewById(R.id.ibanCompletarRegistro);
        botonEnviar = findViewById(R.id.botonEnviarCompletarRegistro);
        botonSalir = findViewById(R.id.botonSalirCompletarRegistro);
        imagen.setOnClickListener(v -> openFileChooser());

        botonEnviar.setOnClickListener(v -> uploadData());
        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.loginPreference), Context.MODE_PRIVATE);
                sharedPreferences.edit().clear();
                sharedPreferences.edit().apply();
                Intent i = new Intent(CompletarRegistroView.this, InicioRegistroView.class);
                startActivity(i);
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imagen.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadData() {
        if (imageUri != null) {
            File file = new File(FileUtils.getPath(this, imageUri));
            RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("imagen", file.getName(), requestFile);

            RequestBody nombrePart = createPartFromString(nombre.getText().toString());
            RequestBody apellidosPart = createPartFromString(apellidos.getText().toString());
            RequestBody direccionPart = createPartFromString(direccion.getText().toString());
            RequestBody provinciaPart = createPartFromString(provincia.getText().toString());
            RequestBody codigoPostalPart = createPartFromString(codigoPostal.getText().toString());
            RequestBody fechaNacimientoPart = createPartFromString(fechaNacimiento.getText().toString());
            RequestBody ibanPart = createPartFromString(iban.getText().toString());

            Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance(this);
            RetrofitAPI apiService = retrofit.create(RetrofitAPI.class);

            Call<String> call = apiService.uploadUserData(
                    nombrePart, apellidosPart, direccionPart, provinciaPart, codigoPostalPart, fechaNacimientoPart, ibanPart, body);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(CompletarRegistroView.this, "Datos enviados con éxito", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CompletarRegistroView.this, "Error al enviar los datos", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(CompletarRegistroView.this, "Fallo en la comunicación: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }


            });
        }else{
            Toast.makeText(this, "Introduce una imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MultipartBody.FORM, descriptionString);
    }
}