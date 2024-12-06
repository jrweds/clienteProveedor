package clientesProveedores;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DaoProveedor implements InterfaceDao<Proveedor> {
    private List<Proveedor> proveedores = new ArrayList<>();


    private static final String PROVEEDORES_GUARDADOS = "proveedores.txt";

    public List<Proveedor> obtenerTodos() {
        List<Proveedor> proveedores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PROVEEDORES_GUARDADOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 2) {
                    proveedores.add(new Proveedor(partes[0], partes[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("error al cargar los proveedores: " + e.getMessage());
        }
        return proveedores;
    }

    public void guardarTodos(List<Proveedor> proveedores) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROVEEDORES_GUARDADOS))) {
            for (Proveedor proveedor : proveedores) {
                writer.write(proveedor.getNombre() + ": " + proveedor.getNif());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("error al guardar los proveedores: " + e.getMessage());
        }
    }



    @Override
    public void agregar(Proveedor proveedor) {
        proveedores.add(proveedor);
    }

    @Override
    public void eliminar(Proveedor proveedor) {
        proveedores.removeIf(p -> p.getNif().equals(proveedor.getNif()));
    }

    @Override
    public void guardar(String archivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (Proveedor proveedor : proveedores) {
                writer.write(proveedor.getNombre() + "," + proveedor.getNif());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("error al guardar los proveedores : " + e.getMessage());
        }
    }

    @Override
    public void cargar(String archivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 2) {
                    proveedores.add(new Proveedor(datos[0], datos[1]));
                }
            }
        } catch (IOException e) {
            System.err.println("error al cargar los proveedores: " + e.getMessage());
        }
    }
}
