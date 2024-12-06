package clientesProveedores;


import java.util.List;

public interface InterfaceDao<T> {
    List<T> obtenerTodos();

    void agregar(T t);

    void eliminar(T t);

    void guardar(String archivo);

    void cargar(String archivo);
}
