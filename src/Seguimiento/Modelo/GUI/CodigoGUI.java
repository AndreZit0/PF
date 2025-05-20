package Seguimiento.Modelo.GUI;

import Example_Screen.View.Login.LoginGUI;
import Seguimiento.Modelo.Codigo;
import Seguimiento.Modelo.DAO.CodigoDAO;
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

/**
 * Interfaz gráfica para la gestión de archivos PDF con formato 023.
 * Proporciona funcionalidad para subir, visualizar, buscar y eliminar archivos PDF
 * asociados a aprendices, mostrando solo los archivos del tipo 023.
 */
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

    private String email;
    private int idUsuario;
    private JLabel progressImageLabel;

    private final Color azul = Color.decode("#007AFF");
    private final Color rojo = Color.decode("#FF3B30");
    private final Color verde = Color.decode("#39A900");
    private final Color blanco = Color.decode("#FFFFFF");
    private final Color naranja = Color.decode("#F39C12");
    private final Font fuenteCalibri = new Font("Calibri", Font.PLAIN, 20);

    /**
     * Constructor principal que inicializa la interfaz para un usuario específico.
     * @param email Correo electrónico del usuario que inicia sesión
     */
    public CodigoGUI(String email) {
        this.email = email;
        this.archivoDAO = new CodigoDAO();

        Map<String, String> infoUsuario = archivoDAO.obtenerInfoCompletaAprendiz(email);
        this.idUsuario = Integer.parseInt(infoUsuario.get("id_usuario"));

        configurarVentana();
        configurarComponentes();
        cargarArchivos();

        btnSubir.setCursor(new Cursor(Cursor.HAND_CURSOR));


    }

    /**
     * Constructor vacío para compatibilidad, utiliza un email vacío.
     */
    private CodigoGUI() {
        this("");
    }

    /**
     * Obtiene el panel principal de la interfaz.
     * @return JPanel que contiene todos los componentes de la interfaz
     */
    public JPanel getPanel() {
        return panelPrincipal;
    }

    /**
     * Configura las propiedades básicas de la ventana principal.
     */
    private void configurarVentana() {
        setTitle("Gestor de Archivos PDF - Formato 023");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Configura y organiza todos los componentes gráficos de la interfaz.
     */
    private void configurarComponentes() {
        panelPrincipal = new JPanel(new BorderLayout());

        panelArchivos = new JPanel();
        panelArchivos.setLayout(new BoxLayout(panelArchivos, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelArchivos);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        ImageIcon icono = cargarImagen("C:\\Users\\Famil\\IdeaProjects\\Seguimiento\\src\\Prueba3\\Modelo\\Imagenes\\Grafico.png");
        JLabel lblImagen = new JLabel(icono);
        lblImagen.setHorizontalAlignment(JLabel.LEFT);
        JPanel panelImagen = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelImagen.add(lblImagen);

        JPanel panelControles = new JPanel();
        panelControles.setLayout(new BoxLayout(panelControles, BoxLayout.Y_AXIS));

        txtBuscarAprendiz = new JTextField(20);
        btnBuscar = new JButton("Buscar Aprendiz");
        estilizarBoton(btnBuscar, azul);
        btnBuscar.addActionListener(e -> filtrarArchivosPorAprendiz(txtBuscarAprendiz.getText().trim()));

        JPanel panelSubir = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSubir = new JButton("Subir PDF");
        estilizarBoton(btnSubir, verde);
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivo PDF", "pdf"));
        btnSubir.addActionListener(e -> {
            if (subidaHabilitada) subirArchivo();
            else mostrarMensajeEspera();
        });
        panelSubir.add(btnSubir);
        panelPrincipal.add(panelSubir, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    /**
     * Aplica estilos visuales a un botón.
     * @param boton Componente JButton a estilizar
     * @param colorFondo Color de fondo para el botón
     */
    private void estilizarBoton(JButton boton, Color colorFondo) {
        boton.setBackground(colorFondo);
        boton.setForeground(blanco);
        boton.setFont(fuenteCalibri);
    }

    /**
     * Muestra un mensaje indicando que se debe esperar antes de subir otro archivo.
     */
    private void mostrarMensajeEspera() {
        JOptionPane.showMessageDialog(this,
                "Espere 20 segundos antes de subir otro archivo",
                "Espera requerida", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Maneja el proceso completo de subida de un archivo PDF.
     * Incluye selección de archivo, validaciones y almacenamiento en la base de datos.
     */
    private void subirArchivo() {
        if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;

        File archivoSeleccionado = fileChooser.getSelectedFile();
        if (!archivoSeleccionado.getName().toLowerCase().endsWith(".pdf")) {
            JOptionPane.showMessageDialog(this, "Solo se permiten archivos PDF", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tipoFormato = "023";

        try {
            Map<String, String> infoAprendiz = archivoDAO.obtenerInfoCompletaAprendiz(this.email);

            if (infoAprendiz.get("id") == null) {
                JOptionPane.showMessageDialog(this, "No se encontró información del usuario", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idAprendiz = Integer.parseInt(infoAprendiz.get("id"));
            String nombreAprendiz = infoAprendiz.get("nombre");
            String numeroDocumento = infoAprendiz.get("cedula");

            String observaciones = JOptionPane.showInputDialog(this,
                    "Ingrese las observaciones (opcional):",
                    "Observaciones", JOptionPane.QUESTION_MESSAGE);
            observaciones = observaciones == null ? "" : observaciones;

            File destino = guardarArchivoLocalmente(archivoSeleccionado);
            if (destino == null) return;

            Codigo archivo = crearObjetoArchivo(
                    archivoSeleccionado, destino, tipoFormato, observaciones,
                    nombreAprendiz, numeroDocumento, this.idUsuario, idAprendiz
            );

// Inicializar los campos de validación
            archivo.setVal1("No Aprobado");
            archivo.setVal2("No Aprobado");
            archivo.setVal3("No Aprobado");

            if (archivoDAO.insertar(archivo)) {
                JOptionPane.showMessageDialog(this, "Archivo subido con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarArchivos();
                deshabilitarSubidaTemporalmente();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Guarda una copia local del archivo PDF en el sistema de archivos.
     * @param archivo Archivo PDF a guardar localmente
     * @return Referencia al archivo guardado, o null si ocurrió un error
     */
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

    /**
     * Crea un objeto Codigo a partir de un archivo PDF.
     * @param archivoSeleccionado Archivo PDF seleccionado
     * @param destino Ubicación donde se guardó el archivo
     * @param tipoFormato Tipo de formato del documento (023)
     * @param observaciones Comentarios sobre el archivo
     * @param nombreAprendiz Nombre completo del aprendiz
     * @param cedulaAprendiz Número de documento del aprendiz
     * @param idUsuario Identificador del usuario que sube el archivo
     * @param idAprendiz Identificador del aprendiz asociado
     * @return Objeto Codigo configurado con toda la información
     * @throws IOException Si ocurre un error al leer el archivo
     */
    private Codigo crearObjetoArchivo(File archivoSeleccionado, File destino, String tipoFormato,
                                      String observaciones, String nombreAprendiz,
                                      String cedulaAprendiz, int idUsuario, int idAprendiz) throws IOException {
        Codigo archivo = new Codigo();
        archivo.setNombreArchivo(archivoSeleccionado.getName());
        archivo.setRutaArchivo(destino.getAbsolutePath());

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
        archivo.setIdUsuario(idUsuario);
        archivo.setIdAprendiz(idAprendiz);
        return archivo;
    }

    /**
     * Deshabilita temporalmente el botón de subir archivos por 20 segundos.
     */
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

    /**
     * Carga los archivos del usuario actual con formato 023 desde la base de datos.
     */
    private void cargarArchivos() {
        listaArchivosCompleta = archivoDAO.listarPorUsuarioYTipo(this.idUsuario, "023");
        mostrarArchivos(listaArchivosCompleta);
    }

    /**
     * Muestra la lista de archivos en el panel principal.
     * @param archivos Lista de objetos Codigo a mostrar
     */
    private void mostrarArchivos(List<Codigo> archivos) {
        panelArchivos.removeAll();
        for (Codigo archivo : archivos) {
            agregarArchivoAPanel(archivo);
        }
        panelArchivos.revalidate();
        panelArchivos.repaint();
    }

    /**
     * Agrega un archivo al panel de visualización con su información y controles.
     * @param archivo Objeto Codigo que representa el archivo a mostrar
     */
    private void agregarArchivoAPanel(final Codigo archivo) {
        JPanel panelArchivo = new JPanel(new BorderLayout());
        panelArchivo.setBorder(BorderFactory.createEtchedBorder());
        panelArchivo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));


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

        JButton btnVer = new JButton("Visualizar");
        estilizarBoton(btnVer, naranja);
        btnVer.addActionListener(e -> previsualizarArchivo(archivo));

        JButton btnEliminar = new JButton("Eliminar");
        estilizarBoton(btnEliminar, rojo);
        btnEliminar.addActionListener(e -> eliminarArchivo(archivo, panelArchivo));

        // Botón de validación
        JButton btnValidar = new JButton("Validar");
        estilizarBoton(btnValidar, Color.BLUE);
        btnValidar.addActionListener(e -> validarArchivo(archivo, btnValidar, btnEliminar));

        // Verificar si ya está validado por los 3 roles
        boolean validadoCompletamente = archivo.getVal1().equals("Aprobado") ||
                archivo.getVal3().equals("Aprobado");

        // Deshabilitar botones si está completamente validado
        if (validadoCompletamente) {
            btnEliminar.setEnabled(false);
            btnValidar.setEnabled(false);
            btnValidar.setText("Validado");
        } else {
            // Verificar si el rol actual ya validó
            String rolActual = obtenerRolUsuario();

            // Deshabilitar validación si ya fue validado por este rol
            boolean yaValidado = (rolActual.equals("1") && archivo.getVal1().equals("Aprobado")) ||
                    (rolActual.equals("2") && archivo.getVal2().equals("Aprobado")) ||
                    (rolActual.equals("3") && archivo.getVal3().equals("Aprobado"));

            if (yaValidado) {
                btnValidar.setEnabled(false);
                btnValidar.setText("Validado");

                // Inhabilitar eliminar si fue validado por aprendiz o coevaluador
                if ((archivo.getVal1().equals("Aprobado") && rolActual.equals("1")) ||
                        (archivo.getVal3().equals("Aprobado") && rolActual.equals("3"))) {
                    btnEliminar.setEnabled(false);
                }
            }
        }

        JPanel panelBotones = new JPanel(new GridLayout(3, 1));
        panelBotones.add(btnVer);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnValidar);

        panelArchivo.add(panelInfo, BorderLayout.CENTER);
        panelArchivo.add(panelBotones, BorderLayout.EAST);

        panelArchivos.add(panelArchivo);
        btnVer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    /**
     * Abre un archivo PDF en el visor predeterminado del sistema.
     * @param archivo Objeto Codigo que contiene la ruta del archivo a abrir
     */
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

    /**
     * Elimina un archivo tanto de la base de datos como del sistema de archivos.
     * @param archivo Objeto Codigo que representa el archivo a eliminar
     * @param panelArchivo Componente JPanel que contiene la visualización del archivo
     */
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

    /**
     * Filtra los archivos mostrados según la cédula o nombre del aprendiz.
     * @param busqueda Texto a buscar en cédula o nombre del aprendiz
     */
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

    /**
     * Carga una imagen desde una ruta específica y la escala al tamaño adecuado.
     * @param ruta Ruta completa del archivo de imagen
     * @return ImageIcon con la imagen cargada, o null si hubo error
     */
    private ImageIcon cargarImagen(String ruta) {
        try {
            ImageIcon icono = new ImageIcon(ruta);
            Image imagen = icono.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH);
            return new ImageIcon(imagen);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void validarArchivo(Codigo archivo, JButton btnValidar, JButton btnEliminar) {
        String rolActual = obtenerRolUsuario();
        String campoAValidar = "";

        switch(rolActual) {
            case "1": // Aprendiz
                campoAValidar = "val1";
                break;
            case "2": // Evaluador
                campoAValidar = "val2";
                break;
            case "3": // Coevaluador
                campoAValidar = "val3";
                break;
            default:
                JOptionPane.showMessageDialog(this,
                        "Su rol no tiene permisos para validar documentos",
                        "Error de permisos", JOptionPane.ERROR_MESSAGE);
                return;
        }

        if (archivoDAO.validarArchivo(archivo.getIdSeguimiento(), campoAValidar)) {
            JOptionPane.showMessageDialog(this,
                    "Documento validado exitosamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Actualizar estado local
            switch(rolActual) {
                case "1":
                    archivo.setVal1("Aprobado");
                    btnEliminar.setEnabled(false); // Inhabilitar eliminar si es aprendiz
                    break;
                case "2":
                    archivo.setVal2("Aprobado");
                    // Evaluador puede seguir eliminando (no se deshabilita)
                    break;
                case "3":
                    archivo.setVal3("Aprobado");
                    btnEliminar.setEnabled(false); // Inhabilitar eliminar si es coevaluador
                    break;
            }

            btnValidar.setEnabled(false);
            btnValidar.setText("Validado");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al validar el documento",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String obtenerRolUsuario() {
        return LoginGUI.cofigBotonInicioSegunRol;
    }


    /**
     * Punto de entrada principal para ejecutar esta interfaz de forma independiente.
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CodigoGUI().setVisible(true));
    }
}