package clientesProveedores;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DaoCliente implements InterfaceDao<Cliente> {
    private List<Cliente> clientes = new ArrayList<>();
    private static final String CLIENTES_GUARGADOS = "clientes.txt";


    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CLIENTES_GUARGADOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 4) {
                    clientes.add(new Cliente(partes[0], partes[1], partes[2], partes[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("error al cargar los clientes: " + e.getMessage());
        }
        return clientes;
    }

    public void guardarTodos(List<Cliente> clientes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CLIENTES_GUARGADOS))) {
            for (Cliente cliente : clientes) {
                writer.write(cliente.getNombre() + "," + cliente.getApellido() + "," + cliente.getTelefono() + ": -" + cliente.getNif());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("error al guardar los clientes: " + e.getMessage());
        }
    }



    @Override
    public void agregar(Cliente cliente) {
        clientes.add(cliente);
    }

    @Override
    public void eliminar(Cliente cliente) {
        clientes.removeIf(c -> c.getNif().equals(cliente.getNif()));
    }

    @Override
    public void guardar(String archivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (Cliente cliente : clientes) {
                writer.write(cliente.getNombre() + "," + cliente.getApellido() + "," + cliente.getTelefono() + "," + cliente.getNif());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("error al guardar los clientes: " + e.getMessage());
        }
    }

    @Override
    public void cargar(String archivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {
                    clientes.add(new Cliente(datos[0], datos[1], datos[2], datos[3]));
                }
            }
        } catch (IOException e) {
            System.err.println("error al cargar los clientes: " + e.getMessage());
        }
    }
}
