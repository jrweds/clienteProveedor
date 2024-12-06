package clientesProveedores;
public class Incidencia {
    private String titulo;
    private String descripcion;
    private String fecha;
    private String estado;

    public Incidencia(String titulo, String descripcion, String fecha, String estado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.estado = estado;
    }

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return fecha + " - " + titulo + " |- " + estado + " -|\n" + descripcion;
    }
}
