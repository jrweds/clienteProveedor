package clientesProveedores;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ClienteProveedorMain extends JFrame {
    private DaoCliente clienteDAO = new DaoCliente();
    private DaoProveedor proveedorDAO = new DaoProveedor();
    private DaoIncidencia incidenciaDAO = new DaoIncidencia();
    private DefaultTableModel tableModel;
    private final String RUTA_DE_DOCUMENTOS_ALMACENADOS = "DocumentosSubidos";

    private GestorDocumentos gestorDocumentos;

    public ClienteProveedorMain() {
        setTitle("Cliente/Proveedor");
        setSize(800, 320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gestorDocumentos = new GestorDocumentos(RUTA_DE_DOCUMENTOS_ALMACENADOS);

        File directorioBase = new File(RUTA_DE_DOCUMENTOS_ALMACENADOS);
        if (!directorioBase.exists()) {
            directorioBase.mkdir();
        }



        JTabbedPane pestanias = new JTabbedPane();
        pestanias.addTab("Clientes", panelClientes());
        pestanias.addTab("Proveedores", panelProveedores());
        pestanias.addTab("Incidencias", panelIncidencias());
        pestanias.addTab("Documentos", panelGestorDocumentos());

        add(pestanias);
    }

    private JPanel panelClientes() {
        JPanel panelCliente = new JPanel(new BorderLayout());

        // Área de visualización
        List<Cliente> clientes = clienteDAO.obtenerTodos();

        // Formulario de entrada
        JPanel formularioDeClientes = new JPanel(new GridLayout(5, 2, 5, 5));
        formularioDeClientes.add(new JLabel("Nombre:"));
        JTextField nombreCliente = new JTextField();
        formularioDeClientes.add(nombreCliente);

        formularioDeClientes.add(new JLabel("Apellido:"));
        JTextField apellidoCliente = new JTextField();
        formularioDeClientes.add(apellidoCliente);

        formularioDeClientes.add(new JLabel("Teléfono:"));
        JTextField telefonoCliente = new JTextField();
        formularioDeClientes.add(telefonoCliente);

        formularioDeClientes.add(new JLabel("DNI:"));
        JTextField dniCliente = new JTextField();
        formularioDeClientes.add(dniCliente);

        // Botones
        JPanel botones = new JPanel();
        JButton botonAnadirCliente = new JButton("Añadir cliente");
        botonAnadirCliente.addActionListener(e -> {
            String nombre = nombreCliente.getText();
            String apellido = apellidoCliente.getText();
            String telefono = telefonoCliente.getText();
            String dni = dniCliente.getText();
            if (!nombre.isEmpty() && !apellido.isEmpty() && !telefono.isEmpty() && !dni.isEmpty()) {
                clientes.add(new Cliente(nombre, apellido, telefono, dni));
                clienteDAO.guardarTodos(clientes);
                limpiarCampos(nombreCliente, apellidoCliente, telefonoCliente, dniCliente);
            } else {
                JOptionPane.showMessageDialog(panelCliente, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton botonVerClientes = new JButton("Ver clientes");
        botonVerClientes.addActionListener(e -> mostrarVentanaListado("Clientes", clientes));

        JButton botonBorrarCLiente = new JButton("Borrar cliente");
        botonBorrarCLiente.addActionListener(e -> {
            String dni = JOptionPane.showInputDialog("Ingrese el DNI del cliente que desea borrar:");
            if (dni != null) {
                clientes.removeIf(c -> c.getNif().equals(dni));
                clienteDAO.guardarTodos(clientes);
            }
        });

        botones.add(botonAnadirCliente);
        botones.add(botonVerClientes);
        botones.add(botonBorrarCLiente);

        panelCliente.add(formularioDeClientes, BorderLayout.NORTH);
        panelCliente.add(botones, BorderLayout.SOUTH);

        return panelCliente;
    }


    private JPanel panelProveedores() {
        JPanel panelProveedor = new JPanel(new BorderLayout());

        // Área de visualización
        List<Proveedor> proveedores = proveedorDAO.obtenerTodos();

        JPanel formularioProveedores = new JPanel(new GridLayout(3, 2, 5, 5));
        formularioProveedores.add(new JLabel("Nombre:"));
        JTextField nombreProveedor = new JTextField();
        formularioProveedores.add(nombreProveedor);

        formularioProveedores.add(new JLabel("NIF:"));
        JTextField NifProveedor = new JTextField();
        formularioProveedores.add(NifProveedor);

        // Botones
        JPanel botones = new JPanel();
        JButton botonAnadir = new JButton("Añadir proveedor");
        botonAnadir.addActionListener(e -> {
            String nombre = nombreProveedor.getText();
            String nif = NifProveedor.getText();
            if (!nombre.isEmpty() && !nif.isEmpty()) {
                proveedores.add(new Proveedor(nombre, nif));
                proveedorDAO.guardarTodos(proveedores);
                limpiarCampos(nombreProveedor, NifProveedor);
            } else {
                JOptionPane.showMessageDialog(panelProveedor, "Rellene todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton botonVerProveedor = new JButton("Ver proveedores");
        botonVerProveedor.addActionListener(e -> mostrarVentanaListado("Proveedores", proveedores));

        JButton botonBorrarProveedor = new JButton("Borrar proveedor");
        botonBorrarProveedor.addActionListener(e -> {
            String nif = JOptionPane.showInputDialog("Ingrese el NIF del proveedor que desea borrar:");
            if (nif != null) {
                proveedores.removeIf(p -> p.getNif().equals(nif));
                proveedorDAO.guardarTodos(proveedores);
            }
        });

        botones.add(botonAnadir);
        botones.add(botonVerProveedor);
        botones.add(botonBorrarProveedor);

        panelProveedor.add(formularioProveedores, BorderLayout.NORTH);
        panelProveedor.add(botones, BorderLayout.SOUTH);

        return panelProveedor;
    }

    private JPanel panelIncidencias() {
        JPanel panelIncidencia = new JPanel(new BorderLayout());

        // Área de visualización
        List<Incidencia> incidencias = incidenciaDAO.obtenerListadoInci();
        JTextArea textoIncidencias = new JTextArea();
        actualizarPanelTextoIncidencias(textoIncidencias, incidencias);

        // Formulario de entrada
        JPanel formularioIncidencias = new JPanel(new GridLayout(5, 2, 5, 5));
        formularioIncidencias.add(new JLabel("Título de la incidencia:"));
        JTextField tituloIncidencia = new JTextField();
        formularioIncidencias.add(tituloIncidencia);

        formularioIncidencias.add(new JLabel("Descripción:"));
        JTextField descripcionInci = new JTextField();
        formularioIncidencias.add(descripcionInci);

        formularioIncidencias.add(new JLabel("Estado:"));
        JTextField estadoInci = new JTextField();
        formularioIncidencias.add(estadoInci);

        formularioIncidencias.add(new JLabel("Fecha:"));
        JTextField fechaInci = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        fechaInci.setEditable(false);
        formularioIncidencias.add(fechaInci);

        // Botones
        JPanel botones = new JPanel();
        JButton botonAnadirIncidencia = new JButton("Añadir Incidencia");
        botonAnadirIncidencia.addActionListener(e -> {
            String titulo = tituloIncidencia.getText();
            String descripcion = descripcionInci.getText();
            String estado = estadoInci.getText();
            String fecha = fechaInci.getText();
            if (!titulo.isEmpty() && !descripcion.isEmpty() && !estado.isEmpty() && !fecha.isEmpty()) {
                incidencias.add(new Incidencia(titulo, descripcion, fecha, estado));
                incidenciaDAO.guardarIncidencias(incidencias);
                actualizarPanelTextoIncidencias(textoIncidencias, incidencias);
                limpiarCampos(tituloIncidencia, descripcionInci, estadoInci);
            } else {
                JOptionPane.showMessageDialog(panelIncidencia, "Rellene todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton botonVerIncidencia = new JButton("Ver incidencias");
        botonVerIncidencia.addActionListener(e -> mostrarVentanaListado("Incidencias", incidencias));

        botones.add(botonAnadirIncidencia);
        botones.add(botonVerIncidencia);

        panelIncidencia.add(formularioIncidencias, BorderLayout.NORTH);
        panelIncidencia.add(new JScrollPane(textoIncidencias), BorderLayout.CENTER);
        panelIncidencia.add(botones, BorderLayout.SOUTH);

        return panelIncidencia;
    }

    private void actualizarPanelTextoIncidencias(JTextArea area, List<Incidencia> incidencias) {
        area.setText("");
        for (Incidencia incidencia : incidencias) {
            area.append(incidencia.toString() + "\n\n");
        }
    }

    private void limpiarCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setText("");
        }
    }

    private <T> void mostrarVentanaListado(String titulo, List<T> elementos) {
        JFrame ventanaListado = new JFrame(titulo);
        ventanaListado.setSize(400, 400);
        ventanaListado.setLocationRelativeTo(null);

        JTextArea areaListado = new JTextArea();
        for (T elemento : elementos) {
            areaListado.append(elemento.toString() + "\n");
        }

        ventanaListado.add(new JScrollPane(areaListado));
        ventanaListado.setVisible(true);
    }

    /////////////////////////////////////////7
    private JPanel panelGestorDocumentos() {
        JPanel panelDocumentos = new JPanel(new BorderLayout());

        // Modelo de la tabla
        String[] columnas = {"Nombre del Archivo", "Tipo", "Fecha"};
        tableModel = new DefaultTableModel(columnas, 0);
        JTable tablaDocumentos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablaDocumentos);

        // Botón para subir archivos
        JButton botonSubirArchivo = new JButton("Subir Archivos");
        botonSubirArchivo.addActionListener(e -> gestorDocumentos.subirArchivos());

        // Botón para descargar archivos
        JButton botonDescargar = new JButton("Descargar Archivos");
        botonDescargar.addActionListener(e -> gestorDocumentos.descargarArchivos(tablaDocumentos));

        // Botón para eliminar archivos
        JButton botonEliminar = new JButton("Eliminar Archivos");
        botonEliminar.addActionListener(e -> gestorDocumentos.eliminarArchivos(tablaDocumentos));

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(botonSubirArchivo);
        panelBotones.add(botonDescargar);
        panelBotones.add(botonEliminar);

        panelDocumentos.add(scrollPane, BorderLayout.CENTER);
        panelDocumentos.add(panelBotones, BorderLayout.SOUTH);

        // Cargar los archivos existentes en la interfaz
        gestorDocumentos.cargarArchivos(tableModel);

        return panelDocumentos;
    }

    public static void main(String[] args) {
        ClienteProveedorMain gestor =  new ClienteProveedorMain();
        gestor.setVisible(true);
    }
}
