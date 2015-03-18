package br.com.controlepontosimples.util;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

/**
 * Created by estudos on 09/03/15.
 */
public class PontoUtil {

    public static LocalTime convertHoraMinutoJodaTime(String horaMinuto) {
        String[] partes = horaMinuto.split(":");
        LocalTime lt = new LocalTime(Integer.valueOf(partes[0]), Integer.valueOf(partes[1]));

        return lt;
    }

}
