package clientesProveedores;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestorDocumentos {

    private final String rutaDirectorioBase;
    private List<Documento> documentos;

    public GestorDocumentos(String rutaDirectorioBase) {
        this.rutaDirectorioBase = rutaDirectorioBase;
        this.documentos = new ArrayList<>();
    }

    public void cargarArchivos(DefaultTableModel tableModel) {
        File directorioBase = new File(rutaDirectorioBase);
        if (!directorioBase.exists()) {
            directorioBase.mkdir();
        }

        File[] archivos = directorioBase.listFiles();
        if (archivos != null) {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

            for (File archivo : archivos) {
                if (archivo.isFile()) {
                    String nombre = archivo.getName();
                    String tipo = obtenerExtension(nombre);
                    String fecha = formatoFecha.format(new Date(archivo.lastModified()));

                    Documento documento = new Documento(nombre, tipo, fecha);
                    documentos.add(documento);
                    tableModel.addRow(new Object[]{nombre, tipo, fecha});
                }
            }
        }
    }

    public void subirArchivos() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Documentos y Archivos", "pdf", "docx", "xlsx", "txt"));

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File[] archivosSeleccionados = fileChooser.getSelectedFiles();
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            String fechaActual = formatoFecha.format(new Date());

            for (File archivo : archivosSeleccionados) {
                try {
                    File archivoDestino = new File(rutaDirectorioBase, archivo.getName());
                    copiarArchivo(archivo, archivoDestino);

                    Documento documento = new Documento(archivo.getName(), obtenerExtension(archivo.getName()), fechaActual);
                    documentos.add(documento);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error al subir el archivo: " + archivo.getName());
                }
            }
        }
    }

    public void descargarArchivos(JTable tablaDocumentos) {
        int[] filasSeleccionadas = tablaDocumentos.getSelectedRows();
        if (filasSeleccionadas.length > 0) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File directorioDestino = fileChooser.getSelectedFile();

                for (int fila : filasSeleccionadas) {
                    Documento documento = documentos.get(fila);
                    File archivoFuente = new File(rutaDirectorioBase, documento.getNombre());
                    File archivoDestino = new File(directorioDestino, documento.getNombre());

                    try {
                        copiarArchivo(archivoFuente, archivoDestino);
                        JOptionPane.showMessageDialog(null, "Archivo descargado: " + archivoDestino.getPath());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error al descargar el archivo: " + documento.getNombre());
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione al menos un archivo para descargar.");
        }
    }

    public void eliminarArchivos(JTable tablaDocumentos) {
        int[] filasSeleccionadas = tablaDocumentos.getSelectedRows();
        if (filasSeleccionadas.length > 0) {
            int confirmacion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Está seguro de que desea eliminar los archivos seleccionados?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                for (int i = filasSeleccionadas.length - 1; i >= 0; i--) {
                    int fila = filasSeleccionadas[i];
                    Documento documento = documentos.get(fila);
                    File archivo = new File(rutaDirectorioBase, documento.getNombre());

                    if (archivo.exists() && archivo.delete()) {
                        documentos.remove(fila);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo eliminar el archivo: " + documento.getNombre(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                JOptionPane.showMessageDialog(null, "Archivos eliminados correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione al menos un archivo para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void copiarArchivo(File fuente, File destino) throws IOException {
        try (InputStream in = new FileInputStream(fuente);
             OutputStream out = new FileOutputStream(destino)) {
            byte[] buffer = new byte[1024];
            int longitud;
            while ((longitud = in.read(buffer)) > 0) {
                out.write(buffer, 0, longitud);
            }
        }
    }

    private String obtenerExtension(String nombreArchivo) {
        int puntoIndex = nombreArchivo.lastIndexOf(".");
        return (puntoIndex == -1) ? "" : nombreArchivo.substring(puntoIndex + 1);
    }
}
