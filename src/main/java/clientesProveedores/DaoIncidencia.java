package clientesProveedores;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DaoIncidencia {
    private static final String INCIDENCIAS_GUARDADAS = "incidencias.txt";

    public List<Incidencia> obtenerListadoInci() {
        List<Incidencia> incidencias = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INCIDENCIAS_GUARDADAS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 4) {
                    incidencias.add(new Incidencia(partes[0], partes[1], partes[2], partes[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("error al cargar  las incidencias : " + e.getMessage());
        }
        return incidencias;
    }

    public void guardarIncidencias(List<Incidencia> incidencias) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INCIDENCIAS_GUARDADAS))) {
            for (Incidencia incidencia : incidencias) {
                writer.write(incidencia.getTitulo() + "," + incidencia.getDescripcion() + "," + incidencia.getFecha() + ",--" + incidencia.getEstado());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("error al guardar las incidencias: " + e.getMessage());
        }
    }
}
