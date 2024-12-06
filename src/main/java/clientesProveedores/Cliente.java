package clientesProveedores;


public class Cliente {
    private String nombre;
    private String apellido;
    private String telefono;
    private String nif;

    public Cliente(String nombre, String apellido, String telefono, String nif) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getNif() {
        return nif;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " - Telefono: " + telefono + " - NIF: " + nif;
    }
}
