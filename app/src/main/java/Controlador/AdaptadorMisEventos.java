package Controlador;

import android.content.Context;
import android.content.SharedPreferences;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectopracticas.R;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import Modelo.ConfirmacionResponse;
import Modelo.Evento;
import Modelo.LoginResponse;
import Modelo.Matriculacion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdaptadorMisEventos extends RecyclerView.Adapter<AdaptadorMisEventos.ViewHolder> implements UrlInterface{
    ArrayList<Matriculacion> misMatriculaciones;
    private int expandedPosition = -1;
    Context contexto;


    public AdaptadorMisEventos(ArrayList<Matriculacion> misMatriculaciones, Context contexto) {
        this.misMatriculaciones = misMatriculaciones;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public AdaptadorMisEventos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mis_eventos_card, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorMisEventos.ViewHolder holder, int position) {

        holder.nombreEvento.setText(misMatriculaciones.get(position).getEvento().getNombreEvento());
        holder.precioEvento.setText(""+misMatriculaciones.get(position).getEvento().getPrecio());
        holder.estadoEvento.setText(misMatriculaciones.get(position).getEvento().getEstadoEvento());
        holder.fechaInicioInscripcion.setText(misMatriculaciones.get(position).getEvento().getFechaInicioInscripcion());
        holder.fechaInicio.setText(misMatriculaciones.get(position).getEvento().getFechaInicio());
        holder.fechaFin.setText(misMatriculaciones.get(position).getEvento().getFechaFin());
        holder.plazas.setText(""+misMatriculaciones.get(position).getEvento().getNumeroParticipantes());
        Picasso.get()
                .load(URL_EVENTOS_IMAGENES + misMatriculaciones.get(position).getEvento().getImagenEvento())
                .placeholder(R.drawable.icono_feliz)
                .error(R.drawable.icono_triste)
                .into(holder.imagen);



        //controlar la expansion de la informacion extra
        boolean isExpanded = position == expandedPosition;
        holder.bind(isExpanded);
        holder.itemView.setOnClickListener(v -> {
            // Notify the previous expanded item to collapse
            int previousExpandedPosition = expandedPosition;
            expandedPosition = isExpanded ? -1 : position;
            notifyItemChanged(previousExpandedPosition);
            notifyItemChanged(position);
        });


    }

    public void eliminarEvento(int position) {
        int idMatriculacion = misMatriculaciones.get(position).getIdMatriculacion();
        // Elimina el evento de la lista
        misMatriculaciones.remove(position);
        notifyItemRemoved(position);

        eliminarEventoEnBD(idMatriculacion);
    }
    private void eliminarEventoEnBD(int idMatriculacion) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(contexto.getResources().getString(R.string.loginPreference), Context.MODE_PRIVATE);
        int idUsuario = sharedPreferences.getInt(contexto.getResources().getString(R.string.idPreference), 0);
        // Llama al método de Retrofit para eliminar el evento
        Call<ConfirmacionResponse> call = RetrofitClientInstance
                .getRetrofitInstance(contexto)
                .create(RetrofitAPI.class)
                .bajaEvento(idUsuario, idMatriculacion);
                call.enqueue(new Callback<ConfirmacionResponse>() {
                    @Override
                    public void onResponse(Call<ConfirmacionResponse> call, Response<ConfirmacionResponse> response) {
                        Toast.makeText(contexto, response.body().getMensaje(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ConfirmacionResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return misMatriculaciones.size();
    }
    public void actualizarEventos(ArrayList<Matriculacion> nuevosEventos) {
        this.misMatriculaciones = nuevosEventos;
        notifyDataSetChanged();
    }

    public ArrayList<Matriculacion> getMisMatriculaciones() {
        return misMatriculaciones;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imagen;
        TextView nombreEvento;
        TextView precioEvento;
        TextView estadoEvento;
        TextView fechaInicioInscripcion;
        TextView fechaInicio;
        TextView fechaFin;
        TextView plazas;
        CardView informacionExtra;
        ViewGroup container;
        MaterialCardView tarjeta;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagenMisEventos);
            nombreEvento = itemView.findViewById(R.id.tituloMisEventos);
            precioEvento = itemView.findViewById(R.id.precioMisEventos);
            estadoEvento = itemView.findViewById(R.id.estadoMisEventos);
            fechaInicioInscripcion = itemView.findViewById(R.id.fechaInscripcionMisEventos);
            fechaInicio = itemView.findViewById(R.id.fechaInicioMisEventos);
            fechaFin = itemView.findViewById(R.id.fechaFinMisEventos);
            plazas = itemView.findViewById(R.id.plazasMisEventos);
            informacionExtra = itemView.findViewById(R.id.informacionExtra);
            container = (ViewGroup) itemView;
            tarjeta = itemView.findViewById(R.id.tarjetaMisEventos);

        }
        //metodo para hacer visible o invisible la información extra de los eventos
        public void bind(boolean isExpanded) {
            informacionExtra.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

            // Animate the change
            TransitionManager.beginDelayedTransition(container, new AutoTransition());
        }
    }
}