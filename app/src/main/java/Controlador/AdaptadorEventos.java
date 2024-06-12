package Controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectopracticas.R;

import java.util.ArrayList;

import Modelo.Evento;

public class AdaptadorEventos extends RecyclerView.Adapter<AdaptadorEventos.ViewHolder> {
    ArrayList<Evento> eventos;
    private Context contexto;
    @NonNull
    @Override
    public AdaptadorEventos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.eventos_card, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorEventos.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
