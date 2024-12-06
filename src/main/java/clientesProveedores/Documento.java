package clientesProveedores;

class Documento {
    private String nombre;
    private String tipo;
    private String fecha;

    public Documento(String nombre, String tipo, String fecha) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getFecha() {
        return fecha;
    }
}