package Prueba3.Modelo.GUI;

import Prueba3.Modelo.Codigo;
import Prueba3.Modelo.DAO.CodigoDAO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CodigoGUI extends JFrame {
    private JPanel panelPrincipal;
    private JButton btnSubir;
    private JPanel panelArchivos;
    private JFileChooser fileChooser;
    private CodigoDAO archivoDAO;
    private Timer timer;
    private boolean subidaHabilitada = true;
    private JTextField txtBuscarAprendiz;
    private JButton btnBuscar;
    private JPanel panelBusqueda;
    private List<Codigo> listaArchivosCompleta;

    private JLabel progressImageLabel;


    // Colores y fuentes
    private final Color azul = Color.decode("#007AFF");
    private final Color rojo = Color.decode("#FF3B30");
    private final Color verde = Color.decode("#39A900");
    private final Color blanco = Color.decode("#FFFFFF");
    private final Color naranja = Color.decode("#F39C12");
    private final Font fuenteCalibri = new Font("Calibri", Font.PLAIN, 20);

    public CodigoGUI() {
        archivoDAO = new CodigoDAO();
        listaArchivosCompleta = new ArrayList<>();

        configurarVentana();
        configurarComponentes();
        cargarArchivos();

    }

    private void configurarVentana() {
        setTitle("Gestor de Archivos PDF");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void configurarComponentes() {
        panelPrincipal = new JPanel(new BorderLayout());

        // Panel de archivos con scroll
        panelArchivos = new JPanel();
        panelArchivos.setLayout(new BoxLayout(panelArchivos, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelArchivos);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Panel de búsqueda (imagen a la izquierda y controles arriba)
        panelBusqueda = new JPanel(new BorderLayout());

        // --- Panel para la imagen (izquierda) ---
        ImageIcon icono = cargarImagen("C:\\Users\\Famil\\IdeaProjects\\Seguimiento\\src\\Prueba3\\Modelo\\Imagenes\\Grafico.png");
        JLabel lblImagen = new JLabel(icono);
        lblImagen.setHorizontalAlignment(JLabel.LEFT);
        JPanel panelImagen = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelImagen.add(lblImagen);
        panelBusqueda.add(panelImagen, BorderLayout.WEST);

        // --- Panel para los controles de búsqueda (centro) ---
        JPanel panelControles = new JPanel();
        panelControles.setLayout(new BoxLayout(panelControles, BoxLayout.Y_AXIS));

        // Panel para el campo de búsqueda y botón
        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscarAprendiz = new JTextField(20);
        btnBuscar = new JButton("Buscar Aprendiz");
        estilizarBoton(btnBuscar, azul);
        btnBuscar.addActionListener(e -> filtrarArchivosPorAprendiz(txtBuscarAprendiz.getText().trim()));
        panelBuscar.add(new JLabel("Buscar por Cédula/Nombre:"));
        panelBuscar.add(txtBuscarAprendiz);
        panelBuscar.add(btnBuscar);

        panelControles.add(panelBuscar);
        panelBusqueda.add(panelControles, BorderLayout.CENTER);

        panelPrincipal.add(panelBusqueda, BorderLayout.NORTH);

        // Resto del código (botón de subir)...
        JPanel panelSubir = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSubir = new JButton("Subir PDF");
        estilizarBoton(btnSubir, verde);
        btnSubir.addActionListener(e -> {
            if (subidaHabilitada) subirArchivo();
            else mostrarMensajeEspera();
        });
        panelSubir.add(btnSubir);
        panelPrincipal.add(panelSubir, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void estilizarBoton(JButton boton, Color colorFondo) {
        boton.setBackground(colorFondo);
        boton.setForeground(blanco);
        boton.setFont(fuenteCalibri);
    }

    private void mostrarMensajeEspera() {
        JOptionPane.showMessageDialog(this,
                "Espere 20 segundos antes de subir otro archivo",
                "Espera requerida", JOptionPane.INFORMATION_MESSAGE);
    }

    private void subirArchivo() {
        if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;

        File archivoSeleccionado = fileChooser.getSelectedFile();
        if (!archivoSeleccionado.getName().toLowerCase().endsWith(".pdf")) {
            JOptionPane.showMessageDialog(this, "Solo se permiten archivos PDF", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Seleccionar tipo de formato
        String tipoFormato = seleccionarTipoFormato();
        if (tipoFormato == null) return;

        // Obtener cédula del aprendiz
        String cedulaAprendizStr = JOptionPane.showInputDialog(this,
                "Ingrese la cédula del aprendiz asignado:",
                "Cédula del Aprendiz", JOptionPane.QUESTION_MESSAGE);
        if (cedulaAprendizStr == null || cedulaAprendizStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la cédula del aprendiz", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int cedulaAprendiz = Integer.parseInt(cedulaAprendizStr);
            Map<String, String> infoAprendiz = archivoDAO.obtenerInfoCompletaAprendizPorCedula(cedulaAprendiz);

            if (infoAprendiz.get("id") == null) {
                JOptionPane.showMessageDialog(this, "No se encontró un aprendiz con esa cédula", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idAprendiz = Integer.parseInt(infoAprendiz.get("id"));
            String nombreAprendiz = infoAprendiz.get("nombre");
            String numeroDocumento = infoAprendiz.get("cedula");

            // Obtener observaciones
            String observaciones = JOptionPane.showInputDialog(this,
                    "Ingrese las observaciones (opcional):",
                    "Observaciones", JOptionPane.QUESTION_MESSAGE);
            observaciones = observaciones == null ? "" : observaciones;

            // Guardar archivo localmente
            File destino = guardarArchivoLocalmente(archivoSeleccionado);
            if (destino == null) return;

            // Crear objeto Codigo y guardar en BD
            Codigo archivo = crearObjetoArchivo(
                    archivoSeleccionado, destino, tipoFormato, observaciones,
                    nombreAprendiz, numeroDocumento, idAprendiz
            );

            if (archivoDAO.insertar(archivo)) {
                JOptionPane.showMessageDialog(this, "Archivo subido con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarArchivos();
                deshabilitarSubidaTemporalmente();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cédula debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String seleccionarTipoFormato() {
        String[] formatos = {"147", "023"};
        JComboBox<String> formatoComboBox = new JComboBox<>(formatos);
        int option = JOptionPane.showConfirmDialog(this, formatoComboBox,
                "Seleccione el tipo de formato", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            return (String) formatoComboBox.getSelectedItem();
        }
        return null;
    }

    private File guardarArchivoLocalmente(File archivo) {
        File directorio = new File("pdf_almacenados");
        if (!directorio.exists() && !directorio.mkdir()) {
            JOptionPane.showMessageDialog(this, "No se pudo crear el directorio para guardar archivos",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        File destino = new File(directorio, archivo.getName());
        try {
            Files.copy(archivo.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return destino;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al copiar el archivo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private Codigo crearObjetoArchivo(File archivoSeleccionado, File destino, String tipoFormato,
                                      String observaciones, String nombreAprendiz,
                                      String cedulaAprendiz, int idAprendiz) throws IOException {
        Codigo archivo = new Codigo();
        archivo.setNombreArchivo(archivoSeleccionado.getName());
        archivo.setRutaArchivo(destino.getAbsolutePath());

        // Solo leer el archivo si es menor a 10MB
        if (archivoSeleccionado.length() < 10_000_000) {
            archivo.setArchivo(Files.readAllBytes(destino.toPath()));
        } else {
            archivo.setArchivo(("RUTA:" + destino.getAbsolutePath()).getBytes());
        }

        archivo.setTipoFormato(tipoFormato);
        archivo.setObservaciones(observaciones);
        archivo.setFecha(new Date());
        archivo.setNombreAprendiz(nombreAprendiz);
        archivo.setCedulaAprendiz(cedulaAprendiz);
        archivo.setIdAprendiz(idAprendiz);
        archivo.setIdUsuario(obtenerIdUsuarioActual());
        return archivo;
    }

    private void deshabilitarSubidaTemporalmente() {
        subidaHabilitada = false;
        btnSubir.setEnabled(false);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                subidaHabilitada = true;
                btnSubir.setEnabled(true);
                timer.cancel();
            }
        }, 20000);
    }

    private int obtenerIdUsuarioActual() {
        // Implementar lógica para obtener el ID del usuario logueado
        return 1; // Valor temporal
    }

    private void cargarArchivos() {
        listaArchivosCompleta = archivoDAO.listarTodos();
        mostrarArchivos(listaArchivosCompleta);
    }

    private void mostrarArchivos(List<Codigo> archivos) {
        panelArchivos.removeAll();
        for (Codigo archivo : archivos) {
            agregarArchivoAPanel(archivo);
        }
        panelArchivos.revalidate();
        panelArchivos.repaint();
    }

    private void agregarArchivoAPanel(final Codigo archivo) {
        JPanel panelArchivo = new JPanel(new BorderLayout());
        panelArchivo.setBorder(BorderFactory.createEtchedBorder());
        panelArchivo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // Información del archivo
        JLabel lblNombre = new JLabel("Nombre: " + archivo.getNombreArchivo());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        JLabel lblFecha = new JLabel("Subido el: " + sdf.format(archivo.getFecha()));
        JLabel lblAprendiz = new JLabel("Aprendiz: " + archivo.getNombreAprendiz() +
                " (Cédula: " + archivo.getCedulaAprendiz() + ")");
        JLabel lblObservacion = new JLabel("Observaciones: " + archivo.getObservaciones());

        JPanel panelInfo = new JPanel(new GridLayout(4, 1));
        panelInfo.add(lblNombre);
        panelInfo.add(lblFecha);
        panelInfo.add(lblAprendiz);
        panelInfo.add(lblObservacion);

        // Botones
        JButton btnVer = new JButton("Previsualizar");
        estilizarBoton(btnVer, naranja);
        btnVer.addActionListener(e -> previsualizarArchivo(archivo));

        JButton btnEliminar = new JButton("Eliminar");
        estilizarBoton(btnEliminar, rojo);
        btnEliminar.addActionListener(e -> eliminarArchivo(archivo, panelArchivo));

        JPanel panelBotones = new JPanel(new GridLayout(2, 1));
        panelBotones.add(btnVer);
        panelBotones.add(btnEliminar);

        panelArchivo.add(panelInfo, BorderLayout.CENTER);
        panelArchivo.add(panelBotones, BorderLayout.EAST);

        panelArchivos.add(panelArchivo);
    }

    private void previsualizarArchivo(Codigo archivo) {
        try {
            File archivoParaAbrir = new File(archivo.getRutaArchivo());
            if (archivoParaAbrir.exists()) {
                Desktop.getDesktop().open(archivoParaAbrir);
            } else {
                JOptionPane.showMessageDialog(this,
                        "El archivo no se encuentra en la ruta especificada",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al abrir el archivo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarArchivo(Codigo archivo, JPanel panelArchivo) {
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este archivo?", "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) return;

        if (archivoDAO.eliminar(archivo.getIdSeguimiento())) {
            if (archivo.getRutaArchivo() != null) {
                new File(archivo.getRutaArchivo()).delete();
            }
            panelArchivos.remove(panelArchivo);
            panelArchivos.revalidate();
            panelArchivos.repaint();
        }
    }

    private void filtrarArchivosPorAprendiz(String busqueda) {
        if (busqueda.isEmpty()) {
            mostrarArchivos(listaArchivosCompleta);
            return;
        }

        List<Codigo> archivosFiltrados = new ArrayList<>();
        for (Codigo archivo : listaArchivosCompleta) {
            if (archivo.getCedulaAprendiz().contains(busqueda) ||
                    archivo.getNombreAprendiz().toLowerCase().contains(busqueda.toLowerCase())) {
                archivosFiltrados.add(archivo);
            }
        }
        mostrarArchivos(archivosFiltrados);
    }


    private ImageIcon cargarImagen(String ruta) {
        try {
            ImageIcon icono = new ImageIcon(ruta);
            // Redimensionar la imagen si es necesario (ajusta el tamaño según tus necesidades)
            Image imagen = icono.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH);
            return new ImageIcon(imagen);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CodigoGUI().setVisible(true));
    }
}