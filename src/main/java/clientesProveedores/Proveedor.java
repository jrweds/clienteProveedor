package clientesProveedores;


public class Proveedor {
    private String nombre;
    private String nif;

    public Proveedor(String nombre, String nif) {
        this.nombre = nombre;
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNif() {
        return nif;
    }

    @Override
    public String toString() {
        return nombre + " - NIF: " + nif;
    }
}
