package Controlador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectopracticas.R;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.A;
import org.w3c.dom.Text;

import java.util.ArrayList;

import Modelo.Evento;
import Modelo.Usuario;
import Vista.InformacionEventoView;

public class AdaptadorEventos extends RecyclerView.Adapter<AdaptadorEventos.ViewHolder> implements UrlInterface{
    ArrayList<Evento> eventos;
    private Context contexto;




    public AdaptadorEventos(ArrayList<Evento> eventos, Context contexto) {
        this.eventos = eventos;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public AdaptadorEventos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.eventos_card, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorEventos.ViewHolder holder, int position) {
        Picasso.get()
            .load(UrlInterface.URL_EVENTOS_IMAGENES + eventos.get(position).getImagenEvento())
            .placeholder(R.drawable.icono_feliz)
            .error(R.drawable.icono_triste)
            .into(holder.imagenPrincipal);

        holder.tituloEvento.setText(eventos.get(position).getNombreEvento());
        holder.barraNumeroParticipantes.setMax(eventos.get(position).getNumeroParticipantes());
        holder.barraNumeroParticipantes.setProgress(eventos.get(position).getUsuarios().size());
        holder.plazasEvento.setText(""+eventos.get(position).getNumeroParticipantes() + " plazas");
        holder.plazasRestantes.setText(""+ (eventos.get(position).getNumeroParticipantes() - eventos.get(position).getUsuarios().size()) + " restantes");

        //UTILIZO ESTE BUCLE PARA CARGAR LAS IMAGENES DE LOS USUARIOS
        int numUsuarios = eventos.get(position).getUsuarios().size();
        for (int i = 0; i < holder.usuarios.size(); i++) {
            if (i < numUsuarios) {
                // Mostrar imagen del usuario
                Picasso.get()
                        .load(URL_USUARIO_IMAGENES + eventos.get(position).getUsuarios().get(i).getImagen())
                        .placeholder(R.drawable.icono_feliz)
                        .into(holder.usuarios.get(i));
                holder.usuarios.get(i).setVisibility(View.VISIBLE);
            } else {
                // Ocultar las imágenes de usuario adicionales
                holder.usuarios.get(i).setVisibility(View.GONE);
            }
        }
        if(numUsuarios > 4){
            holder.numeroMiembros.setText(" + " + (eventos.get(position).getUsuarios().size() - holder.usuarios.size()));
        }
        comprobarEventoLleno(holder.imagenLleno, position);


        holder.contenedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(contexto, InformacionEventoView.class);
                i.putExtra("idEvento", eventos.get(position).getIdEvento());
                i.putExtra("nombreEvento", eventos.get(position).getNombreEvento());
                i.putExtra("estadoEvento", eventos.get(position).getEstadoEvento());
                i.putExtra("descripcionEvento", eventos.get(position).getDescripcionEvento());
                i.putExtra("numParticipantes", eventos.get(position).getNumeroParticipantes());
                i.putExtra("fechaInicioInscripcion", eventos.get(position).getFechaInicioInscripcion());
                i.putExtra("fechaInicio", eventos.get(position).getFechaInicio());
                i.putExtra("fechaFin", eventos.get(position).getFechaFin());
                i.putExtra("imagen", eventos.get(position).getImagenEvento());
                i.putExtra("lleno", eventos.get(position).isLleno());
                contexto.startActivity(i);
            }
        });





    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }
    public void actualizarEventos(ArrayList<Evento> nuevosEventos) {
        this.eventos = nuevosEventos;
        notifyDataSetChanged();
    }

    //METODO PARA COMPROBAR SI EL EVENTO ESTÁ LLENO
    public void comprobarEventoLleno(ImageView imagenLleno, int position){
        //Comprobar si el evento está lleno o no
        if(eventos.get(position).getNumeroParticipantes() == eventos.get(position).getUsuarios().size()){
            eventos.get(position).setLleno(true);
            //Superponer la imagen de lleno cuando el evento lo está
            imagenLleno.setVisibility(View.VISIBLE);

        }else{
            eventos.get(position).setLleno(false);
            imagenLleno.setVisibility(View.INVISIBLE);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imagenPrincipal;
        private ImageView imagenLleno;
        private TextView tituloEvento;
        private ProgressBar barraNumeroParticipantes;
        private TextView plazasEvento;
        private TextView plazasRestantes;
        private ImageView usuario1;
        private ImageView usuario2;
        private ImageView usuario3;
        private ImageView usuario4;
        private TextView numeroMiembros;
        private MaterialCardView contenedor;
        private ArrayList<ImageView> usuarios = new ArrayList<>();
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenPrincipal = itemView.findViewById(R.id.imagenEvento);
            tituloEvento = itemView.findViewById(R.id.tituloEvento);
            barraNumeroParticipantes = itemView.findViewById(R.id.progressBar);
            plazasEvento = itemView.findViewById(R.id.plazasEvento);
            plazasRestantes = itemView.findViewById(R.id.plazasRestantes);
            usuario1 =  itemView.findViewById(R.id.primerMiembro);
            usuario2 = itemView.findViewById(R.id.segundoMiembro);
            usuario3 = itemView.findViewById(R.id.tercerMiembro);
            usuario4 = itemView.findViewById(R.id.cuartoMiembro);
            contenedor = itemView.findViewById(R.id.tarjeta);
            numeroMiembros = itemView.findViewById(R.id.numeroMiembros);
            usuarios.add(usuario1);
            usuarios.add(usuario2);
            usuarios.add(usuario3);
            usuarios.add(usuario4);
            imagenLleno = itemView.findViewById(R.id.llenoImagen);

        }
    }
}
