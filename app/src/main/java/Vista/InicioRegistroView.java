package Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.example.proyectopracticas.R;

import Controlador.ClaseComprobar;

public class InicioRegistroView extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager2 viewPager = findViewById(R.id.paginador);
        FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        //Comprobar si hay un usuario en sesion para redireccionar automaticamente a la pagina inicial
        if(ClaseComprobar.comprobarUsuarioConectado(getResources().getString(R.string.loginPreference), getResources().getString(R.string.usuarioPreference), this)){
            Intent i = new Intent(this, PaginaInicial.class);
            startActivity(i);
            finish();
        }

    }

    private static class ScreenSlidePagerAdapter extends FragmentStateAdapter {

        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }
        //ADAPTADOR PARA TRANSICIONAR ENTRE LOS DISTINTOS FRAGMENTS
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0  : fragment = new InicioSesionFragment();
                    break;
                case 1  : fragment = new RegistroFragment();
                    break;
                default : fragment = null;
            }
            return fragment;
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }



}