package Vista.ui.eventos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.proyectopracticas.R;
import com.example.proyectopracticas.databinding.FragmentGalleryBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import Controlador.AdaptadorEventos;
import Controlador.AdaptadorMisEventos;
import Controlador.RetrofitAPI;
import Controlador.RetrofitClientInstance;
import Modelo.ConfirmacionResponse;
import Modelo.Evento;
import Modelo.LoginResponse;
import Modelo.Matriculacion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventosFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private RecyclerView rv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AdaptadorMisEventos adaptadorMisEventos;
    ArrayList<Matriculacion> misMatriculaciones = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rv = root.findViewById(R.id.rvMisEventos);
        rv.setLayoutManager(new LinearLayoutManager(root.getContext()));

        // Inicializar el adaptador y configurarlo en el RecyclerView
        adaptadorMisEventos = new AdaptadorMisEventos(misMatriculaciones, root.getContext());
        rv.setAdapter(adaptadorMisEventos);

        // Configurar SwipeRefreshLayout
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayoutMisEventos);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Llama al método para actualizar los datos
                fetchDataFromApi(root);
            }
        });

        // Obtener los datos iniciales
        fetchDataFromApi(root);
        misMatriculaciones = adaptadorMisEventos.getMisMatriculaciones();

        //METODO PARA PODER ELIMINAR EVENTOS DESLIZANDO
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
                0, // No drag and drop
                ItemTouchHelper.RIGHT // Swipe direction (can be LEFT, RIGHT, START, END, or combination using bitwise OR)
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // No action when dragged
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Implementar la acción cuando se deslice
                int position = viewHolder.getAdapterPosition();
                adaptadorMisEventos.eliminarEvento(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rv);


        return root;
    }

    private void fetchDataFromApi(View v) {
        // Realizar la llamada a Retrofit
        RetrofitAPI retrofitAPI = RetrofitClientInstance
                .getRetrofitInstance(getContext())
                .create(RetrofitAPI.class);
        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences(v.getResources().getString(R.string.loginPreference), Context.MODE_PRIVATE);
        int idUsuario = sharedPreferences.getInt("idUsuario", 0);
        Call<ArrayList<Matriculacion>> call = retrofitAPI.miseventos(idUsuario);
        call.enqueue(new Callback<ArrayList<Matriculacion>>() {
            @Override
            public void onResponse(Call<ArrayList<Matriculacion>> call, Response<ArrayList<Matriculacion>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adaptadorMisEventos.actualizarEventos(response.body());
                }
                swipeRefreshLayout.setRefreshing(false); // Detiene la animación de actualización
            }

            @Override
            public void onFailure(Call<ArrayList<Matriculacion>> call, Throwable t) {
                // Manejar la falla de la llamada aquí
                swipeRefreshLayout.setRefreshing(false); // Detiene la animación de actualización
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}