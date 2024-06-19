package Vista.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.proyectopracticas.R;
import com.example.proyectopracticas.databinding.FragmentHomeBinding;

import java.util.ArrayList;

import Controlador.AdaptadorEventos;
import Controlador.RetrofitAPI;
import Controlador.RetrofitClientInstance;
import Modelo.Evento;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private RecyclerView rv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AdaptadorEventos adaptadorEventos;
    private ArrayList<Evento> eventos = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rv = root.findViewById(R.id.rvEventos);
        rv.setLayoutManager(new LinearLayoutManager(root.getContext()));

        // Inicializar el adaptador y configurarlo en el RecyclerView
        adaptadorEventos = new AdaptadorEventos(eventos, root.getContext());
        rv.setAdapter(adaptadorEventos);

        // Configurar SwipeRefreshLayout
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Llama al método para actualizar los datos
                fetchDataFromApi();
            }
        });

        // Obtener los datos iniciales
        fetchDataFromApi();

        return root;
    }

    private void fetchDataFromApi() {
        // Realizar la llamada a Retrofit
        RetrofitAPI retrofitAPI = RetrofitClientInstance
                .getRetrofitInstance(getContext())
                .create(RetrofitAPI.class);

        Call<ArrayList<Evento>> call = retrofitAPI.obtenerEventos();
        call.enqueue(new Callback<ArrayList<Evento>>() {
            @Override
            public void onResponse(Call<ArrayList<Evento>> call, Response<ArrayList<Evento>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adaptadorEventos.actualizarEventos(response.body());
                }
                swipeRefreshLayout.setRefreshing(false); // Detiene la animación de actualización
            }

            @Override
            public void onFailure(Call<ArrayList<Evento>> call, Throwable t) {
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