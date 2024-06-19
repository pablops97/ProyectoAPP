package Utilidad;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utilidad {

    //metodo para comprobar si la fecha del evento es mayor que la fecha actual del sistema
    public static boolean comprobarFecha(String fecha) throws ParseException {
        //Formato
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        //obtener la fecha actual
        LocalDateTime now = LocalDateTime.now();


        Date fechaActual = format.parse(now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        Date fechaFormateada=new SimpleDateFormat("yyyy/MM/dd").parse(fecha);
        if(fechaActual.compareTo(fechaFormateada) > 0 || fechaActual.compareTo(fechaFormateada) == 0)
        {
            return true;
        }
        return false;
    }
}
