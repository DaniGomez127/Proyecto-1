package logica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataLoader {

    public static Map<String, Integer> cargarTransacciones(String archivo) {
        Map<String, Integer> ventasPorMes = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length > 1) {
                    String fechaStr = datos[1];
                    Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
                    String mes = sdf.format(fecha);
                    ventasPorMes.put(mes, ventasPorMes.getOrDefault(mes, 0) + 1);
                }
            }
        } catch (IOException | java.text.ParseException e) {
            e.printStackTrace();
        }

        return ventasPorMes;
    }
}
