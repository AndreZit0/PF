package Example_Screen.View.Administrador;

import AsignacionInstructor.AsignacionGUI;
import Empresas.Vista.EmpresaGUI;
import Example_Screen.Model.Aprendiz;
import Example_Screen.Model.AprendizDAO;
import Example_Screen.View.AprendicesAsignados;
import Example_Screen.View.Aprendiz.AprendizGUI;
import Example_Screen.View.GraficoCircular;
import Example_Screen.View.Login.LoginGUI;
import Example_Screen.View.Usuarios_Registrados.VerUsuariosRegistrados;
import Example_Screen.View.VisualizarPerfilGUI;
import Seguimiento.Modelo.GUI.CodigoGUI;
import Seguimiento.Modelo.GUI.CodigoGUI2;

import static Example_Screen.View.Login.LoginGUI.cofigBotonInicioSegunRol;
import static Example_Screen.View.Login.LoginGUI.traerIDusuario;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
/**
 * Esta es la clase principal para la pantalla del Administrador.
 * Es como el centro de control desde donde el admin puede ir a diferentes partes del programa.
 * Básicamente, maneja qué se muestra y qué botones hacen qué cosa.
 */
public class Administrador {
    private JPanel main;
    private JButton verUsuariosButton;
    private JButton crearUsuariosButton;
    private JButton permisosButton;
    private JButton miPerfil;
    private JButton aprendices;
    private JButton evaluadores;
    private JButton coevaluadores;
    private JButton auxiliares;
    private JButton inicio;
    private JLabel logo;
    private JLabel menu_burguer;
    private JPanel menuPanel;
    private JLabel img_princi;
    private JPanel contenidoPanel;
    private JButton registrarEmpresa;
    private JLabel tituloBienvenido;
    private JPanel pnlBtonInicio;
    private JPanel pnlBtonVerUsua;
    private JPanel pnlBtonCrearUsua;
    private JPanel pnlBtonRegisEmpr;
    private JPanel pnlBtonPermiso;
    private JPanel pnlBtonPerfil;
    private JLabel CerrarSesion;
    private JPanel pnlBtonAsigInstru;
    private JButton AsignarIntructorButton;
    private JButton botonAprendizContratado;
    private JPanel pnlBtonAprenContrat;
    private JLabel separadorInvisible;
    private JPanel PanelFormato;
    private JButton FormatoBoton;
    private JButton f147;
    private JButton f023;
    private JPanel panelAsigna;
    private JButton asignaBoton;
    private JTable table1;
    private JFrame frame;


    private VerUsuariosRegistrados verUsuario = new VerUsuariosRegistrados();

    public static int verUsuarioPorRol = 0;
    int anchoCompleto = 280;  // Ancho original del menú
    int anchoReducido = 80;   // Ancho reducido (30% aprox)

    boolean visible = Boolean.parseBoolean(null);



    private boolean menuReducido = false; // Estado inicial

    /**
     * Constructor de la clase Administrador.
     * Aquí es donde se arma toda la interfaz y se configuran los botones y eventos.
     * También ajusta algunas cosas dependiendo del rol del usuario que entró.
     */
    public Administrador() {
        cambiarTituloSegunRol();
        tamañoCompletoMenu();

        switch (cofigBotonInicioSegunRol) {
            case "1": // Aprendiz
                cargarInicioPaneles();
                break;
            case "2": // Evaluador
                cargarInicioPaneles();
                break;
            case "3": // Coevaluador
                cargarInicioPaneles();
                break;
            case "4": // Auxiliar
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
                f023.setVisible(false);
                f147.setVisible(false);
                cargarInicioPaneles();
                break;
            case "5": // Administrador
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
                f023.setVisible(false);
                f147.setVisible(false);
                cargarInicioPaneles();
                break;
            case "6": // Administrador del sistema
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
                f023.setVisible(false);
                f147.setVisible(false);
                cargarInicioPaneles();
                break;
            default:
                // Rol desconocido: puedes mostrar un mensaje o panel de error
                System.out.println("Rol desconocido: " + cofigBotonInicioSegunRol);
        }

        logo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_burguer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton[] botones = {inicio, verUsuariosButton, crearUsuariosButton,AsignarIntructorButton, miPerfil, permisosButton, registrarEmpresa,
                aprendices, evaluadores, coevaluadores, auxiliares, FormatoBoton, f147, f023, botonAprendizContratado};

        for (JButton btn : botones) {
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        Color colorHover = new Color(0, 120, 50);
        Color colorBase = new Color(57, 169, 0);

        aplicarEfectoHover(inicio, colorHover, colorBase);
        aplicarEfectoHover(verUsuariosButton, colorHover, colorBase);
        aplicarEfectoHover(crearUsuariosButton, colorHover, colorBase);
        aplicarEfectoHover(permisosButton, colorHover, colorBase);
        aplicarEfectoHover(AsignarIntructorButton, colorHover, colorBase);
        aplicarEfectoHover(miPerfil, colorHover, colorBase);
        aplicarEfectoHover(registrarEmpresa, colorHover, colorBase);
        aplicarEfectoHover(FormatoBoton, colorHover, colorBase);
        aplicarEfectoHover(botonAprendizContratado, colorHover, colorBase);


        /**
         * Aquí se configuran las acciones de los botones.
         * Es decir, qué pasa cuando alguien hace clic.
         */
        verUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean visible = aprendices.isVisible();
                aprendices.setVisible(!visible);
                evaluadores.setVisible(!visible);
                coevaluadores.setVisible(!visible);
                auxiliares.setVisible(!visible);

            }
        });

        FormatoBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean visible = f147.isVisible();
                f147.setVisible(!visible);
                f023.setVisible(!visible);
            }
        });

        logo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                regresarInicio();
            }
        });
        CerrarSesion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (JOptionPane.showConfirmDialog(frame, "¿Estás seguro de cerrar sesión?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    LoginGUI.main(new String[0]);  // Llama al main para abrir un nuevo login
                }
            }
        });

        inicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                regresarInicio();
            }
        });

        menu_burguer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                configBotonMenuSegunRol();

            }
        });

        aprendices.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verUsuarioPorRol = 1;
                mostrarPanelUsuarios();
            }
        });
        evaluadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verUsuarioPorRol = 2;
                mostrarPanelUsuarios();

            }
        });
        coevaluadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verUsuarioPorRol = 3;
                mostrarPanelUsuarios();
            }
        });
        auxiliares.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verUsuarioPorRol = 4;
                mostrarPanelUsuarios();
            }
        });


        miPerfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanelEditar();
            }
        });
        permisosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configurarBotonPermisos();
            }
        });
        registrarEmpresa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanelEmpresa();
            }
        });
        AsignarIntructorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanelAsignarInstructor();
            }
        });
        f147.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanelSeguimiento147();
            }
        });
        f023.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanelSeguimiento023();
            }
        });
    }


    /**
     * Configura lo que pasa cuando se hace clic en el botón de "Permisos".
     * Básicamente carga el panel de permisos en el área de contenido..
     */
    public void configurarBotonPermisos() {
        permisosButton.addActionListener(e -> {
            contenidoPanel.removeAll();

            PermisosPanel permisosPanel = new PermisosPanel();
            contenidoPanel.setLayout(new BorderLayout());
            contenidoPanel.add(permisosPanel.getMainPanel(), BorderLayout.CENTER);

            contenidoPanel.revalidate();
            contenidoPanel.repaint();
        });
        // También puedes agregar el efecto hover que ya tienes en otros botones
        permisosButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                permisosButton.setBackground(new Color(0, 120, 50));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                permisosButton.setBackground(new Color(57, 169, 0));
            }
        });
    }

    /**
     * Muestra el panel para editar la información del administrador.
     * Limpia el panel de contenido y carga el de "Editar_Admin".
     */
    public void mostrarPanelEditar() {
        Editar_Admin editarAdmin = new Editar_Admin();

        // Muy importante: accede al panel primero para inicializar los componentes del GUI builder
        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());
        contenidoPanel.add(editarAdmin.getPanel(), BorderLayout.CENTER);
        contenidoPanel.revalidate();
        contenidoPanel.repaint();
    }
    /**
     * Le pone un efecto visual a un botón para que cambie de color cuando
     * el mouse pasa por encima.
     *
     * @param boton El botón al que le vamos a poner el efecto.
     * @param colorHover El color que tomará el botón cuando el mouse esté encima.
     * @param colorBase El color original del botón.
     */
    public void aplicarEfectoHover(JButton boton, Color colorHover, Color colorBase) {
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(colorHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(colorBase);
            }
        });
    }
    /**
     * Muestra el panel donde se listan los usuarios (aprendices, evaluadores, etc.).
     * Carga la pantalla de VerUsuariosRegistrados y le pide que muestre los datos
     * y configure sus filtros.
     */
    public void mostrarPanelUsuarios() {
        VerUsuariosRegistrados verUsuarios = new VerUsuariosRegistrados();

    // Muy importante: accede al panel primero para inicializar los componentes del GUI builder
        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());
        contenidoPanel.add(verUsuarios.getPanel(), BorderLayout.CENTER);
        contenidoPanel.revalidate();
        contenidoPanel.repaint();

    // Ahora sí: ya están inicializados los campos como `busqueda` y `table1`
        verUsuarios.obtenerDatosUsuario();
        verUsuarios.inicializarFiltro(verUsuarios.getBusqueda(), verUsuarios.getTable());
        verUsuarios.tipoDeUsuarioRegistrado();
        verUsuarios.componentesPersonalizado();
    }
    /**
     * Muestra el panel para registrar o ver empresas.
     * Carga la pantalla de EmpresaGUI.
     */
    public void mostrarPanelEmpresa() {
            EmpresaGUI empresaGUI = new EmpresaGUI();

        // Muy importante: accede al panel primero para inicializar los componentes del GUI builder
        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());
        contenidoPanel.add(empresaGUI.getPanel(), BorderLayout.CENTER);
        contenidoPanel.revalidate();
        contenidoPanel.repaint();

    }
    /**
     * Muestra el panel para asignar instructores.
     * Carga la pantalla de AsignacionGUI.
     */
    public void mostrarPanelAsignarInstructor() {
        AsignacionGUI asignacionGUI = new AsignacionGUI(frame);

        // Muy importante: accede al panel primero para inicializar los componentes del GUI builder
        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());
        contenidoPanel.add(asignacionGUI.getPanel(), BorderLayout.CENTER);
        contenidoPanel.revalidate();
        contenidoPanel.repaint();

    }

    /**
     * Muestra el panel de seguimiento.
     */
    public void mostrarPanel(String titulo, JPanel panelContenido) {
        // Etiqueta centrada
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Calibri", Font.BOLD, 25));   // tamaño y estilo opcionales
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // margen superior/inferior
        lblTitulo.setForeground(Color.WHITE); // Texto en color blanco
        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());

        contenidoPanel.setBackground(new Color(57, 169, 0)); // Color verde #39A900

        // Agregamos título y contenido
        contenidoPanel.add(lblTitulo, BorderLayout.NORTH);
        contenidoPanel.add(panelContenido, BorderLayout.CENTER);
        contenidoPanel.revalidate();
        contenidoPanel.repaint();
    }

    // Clase para mostrar imagen de fondo
    class ImagePanel extends JPanel {
        private Image backgroundImage;

        public ImagePanel(String imagePath) {
            try {
                backgroundImage = ImageIO.read(getClass().getResource(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
                // Imagen alternativa si no se encuentra la original
                backgroundImage = null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Dibuja la imagen para que se ajuste al tamaño del panel
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }


    // ------------­ LLAMADAS ESPECÍFICAS --------------
    public void mostrarPanelSeguimiento147() {
        String usuario = LoginGUI.getUsuarioActual();
        CodigoGUI2 codigoGUI = new CodigoGUI2(usuario);

        mostrarPanel("Formato 147 - Bitácoras", codigoGUI.getPanel());
    }

    public void mostrarPanelSeguimiento023() {
        String usuario = LoginGUI.getUsuarioActual();
        CodigoGUI codigoGUI = new CodigoGUI(usuario);

        mostrarPanel("Formato 023 - Seguimiento", codigoGUI.getPanel());
    }



    /**
     * Muestra el panel de seguimiento.
     */
    public void mostrarPanelGraficoInicio() {
        contenidoPanel.setBackground(new Color(246, 246, 246)); // Color verde #39A900

        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout(20, 0));
        contenidoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        AprendizDAO dao = new AprendizDAO();
        Aprendiz aprendiz = dao.obtenerAprendiz();
        int progreso = aprendiz != null ? aprendiz.calcularProgreso() : 0;

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JLabel titulo = new JLabel("Tu Progreso");
        titulo.setFont(new Font("Calibri", Font.BOLD, 25));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        GraficoCircular grafico = new GraficoCircular(progreso);
        grafico.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel fechaInicio = new JLabel("Fecha Inicio: " + (aprendiz != null ? aprendiz.getFechaInicio() : ""));
        fechaInicio.setFont(new Font("Arial", Font.BOLD, 17));
        fechaInicio.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel fechaFin = new JLabel("Fecha Final: " + (aprendiz != null ? aprendiz.getFechaFin() : ""));
        fechaFin.setFont(new Font("Arial", Font.BOLD, 17));
        fechaFin.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelIzquierdo.add(titulo);
        panelIzquierdo.add(grafico);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 5)));
        panelIzquierdo.add(fechaInicio);
        panelIzquierdo.add(fechaFin);


        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton botonPerfil = new JButton("Visualizar Perfil");
        botonPerfil.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonPerfil.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonPerfil.setPreferredSize(new Dimension(200, 40)); // Ajusta según necesidad
        botonPerfil.setMaximumSize(new Dimension(200, 40));


        botonPerfil.setBackground(new Color(0, 122, 255));
        botonPerfil.setForeground(Color.WHITE);
        botonPerfil.setFont(new Font("Calibri", Font.BOLD, 20));
        botonPerfil.setFocusPainted(true);
        botonPerfil.setEnabled(true);


        botonPerfil.setHorizontalTextPosition(SwingConstants.RIGHT);
        botonPerfil.setVerticalTextPosition(SwingConstants.CENTER);
        botonPerfil.setIconTextGap(10);


        botonPerfil.addActionListener(e -> {
            JDialog perfilDialog = new JDialog(frame, "Visualizar Perfil", true);
            VisualizarPerfilGUI perfilGUI = new VisualizarPerfilGUI();

            perfilDialog.setContentPane(perfilGUI.panel1);
            perfilDialog.pack();
            perfilDialog.setLocationRelativeTo(frame);
            perfilDialog.setVisible(true);
        });
        botonPerfil.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botonPerfil.setBackground(new Color(0, 100, 220));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botonPerfil.setBackground(new Color(0, 122, 255));
            }
        });

        panelDerecho.add(Box.createVerticalGlue());
        panelDerecho.add(botonPerfil);
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 10)));
        panelDerecho.add(Box.createVerticalGlue());

        contenidoPanel.add(panelIzquierdo, BorderLayout.CENTER);
        contenidoPanel.add(panelDerecho, BorderLayout.EAST);

        contenidoPanel.revalidate();
        contenidoPanel.repaint();
    }


    public void mostrarTablaAprendicesAsignados() {
        VerUsuariosRegistrados vista = new VerUsuariosRegistrados();
        AprendicesAsignados asignados = new AprendicesAsignados();

        asignados.obtenerAprendicesAsignados(traerIDusuario, vista);

// Mostrar la vista en el panel principal
        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());
        contenidoPanel.add(vista.getPanel(), BorderLayout.CENTER);
        contenidoPanel.revalidate();
        contenidoPanel.repaint();

        vista.mostrarRol("Aprendices Asignados");

    }
    /**
     * Este metodo sirve para ocultar un montón de botones y paneles del menú.
     * para que no vea opciones que no le corresponden.
     */
    public void ocultarComponentesNoAsignado(){

        switch (cofigBotonInicioSegunRol) {
            case "1": // Aprendiz
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
                pnlBtonAsigInstru.setVisible(false);
                pnlBtonVerUsua.setVisible(false);
                pnlBtonCrearUsua.setVisible(false);
                pnlBtonRegisEmpr.setVisible(false);
                pnlBtonPermiso.setVisible(false);
                pnlBtonAprenContrat.setVisible(false);
                panelAsigna.setVisible(false);
                f147.setVisible(false);
                f023.setVisible(false);
                break;
            case "2": // Evaluador
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
                pnlBtonAsigInstru.setVisible(false);
                pnlBtonVerUsua.setVisible(false);
                pnlBtonCrearUsua.setVisible(false);
                pnlBtonRegisEmpr.setVisible(false);
                pnlBtonPermiso.setVisible(false);
                pnlBtonAprenContrat.setVisible(false);
                PanelFormato.setVisible(false);
                f147.setVisible(false);
                f023.setVisible(false);

                break;
            case "3": // Coevaluador
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
                pnlBtonAsigInstru.setVisible(false);
                pnlBtonVerUsua.setVisible(false);
                pnlBtonCrearUsua.setVisible(false);
                pnlBtonRegisEmpr.setVisible(false);
                pnlBtonPermiso.setVisible(false);
                panelAsigna.setVisible(false);
                PanelFormato.setVisible(false);
                f147.setVisible(false);
                f023.setVisible(false);

                break;
            case "4": // Auxiliar
                pnlBtonAprenContrat.setVisible(false);
                panelAsigna.setVisible(false);
                PanelFormato.setVisible(false);
                f147.setVisible(false);
                f023.setVisible(false);
                break;
            case "5": // Administrador
                pnlBtonAprenContrat.setVisible(false);
                panelAsigna.setVisible(false);
                PanelFormato.setVisible(false);
                f147.setVisible(false);
                f023.setVisible(false);
                break;
            case "6": // Administrador del sistema
                pnlBtonAprenContrat.setVisible(false);
                panelAsigna.setVisible(false);
                PanelFormato.setVisible(false);
                f147.setVisible(false);
                f023.setVisible(false);
                break;
            default:
                // Rol desconocido: puedes mostrar un mensaje o panel de error
                System.out.println("Rol desconocido: " + cofigBotonInicioSegunRol);
        }

    }

    /**
     * Carga los paneles iniciales dependiendo del rol del usuario.
     * Por ejemplo, si es un Aprendiz, oculta muchas cosas y muestra el panel de seguimiento.
     */
    public void cargarInicioPaneles(){

        switch (cofigBotonInicioSegunRol) {
            case "1": // Aprendiz
                ocultarComponentesNoAsignado();
                mostrarPanelGraficoInicio();
                break;
            case "2": // Evaluador
                ocultarComponentesNoAsignado();
                mostrarTablaAprendicesAsignados();
                break;
            case "3": // Coevaluador
                ocultarComponentesNoAsignado();
                break;
            case "4": // Auxiliar
                ocultarComponentesNoAsignado();
                break;
            case "5": // Administrador
                ocultarComponentesNoAsignado();
                break;
            case "6": // Administrador del sistema
                ocultarComponentesNoAsignado();
                break;
            default:
                // Rol desconocido: puedes mostrar un mensaje o panel de error
                System.out.println("Rol desconocido: " + cofigBotonInicioSegunRol);
        }
    }
    /**
     * Configura cómo se ve y comporta el menú lateral.
     * según el rol del usuario. Decide si el menú se encoge o se expande.
     */
    public void configBotonMenuSegunRol(){
        switch (cofigBotonInicioSegunRol) {
            case "1": // Aprendiz

                visible = aprendices.isVisible();

                PanelFormato.setVisible(false);
                f147.setVisible(false);
                f023.setVisible(false);
                pnlBtonPerfil.setVisible(false);
                pnlBtonInicio.setVisible(false);
                logo.setVisible(false);
                separadorInvisible.setVisible(false);

                if (menuReducido) {
                    tamañoCompletoMenu();

                    PanelFormato.setVisible(true);
                    pnlBtonPerfil.setVisible(true);
                    pnlBtonInicio.setVisible(true);
                    logo.setVisible(true);
                    separadorInvisible.setVisible(true);

                } else {
                    tamañoReducidoMenu();
                }

                menuPanel.revalidate(); // Refresca el layout
                menuReducido = !menuReducido;
                break;
            case "2": // Evaluador

                visible = aprendices.isVisible();

                asignaBoton.setVisible(false);
                pnlBtonPerfil.setVisible(false);
                pnlBtonInicio.setVisible(false);
                logo.setVisible(false);
                separadorInvisible.setVisible(false);

                if (menuReducido) {
                    tamañoCompletoMenu();

                    asignaBoton.setVisible(true);
                    pnlBtonPerfil.setVisible(true);
                    pnlBtonInicio.setVisible(true);
                    logo.setVisible(true);
                    separadorInvisible.setVisible(true);

                } else {
                    tamañoReducidoMenu();
                }

                menuPanel.revalidate(); // Refresca el layout
                menuReducido = !menuReducido;
                break;
            case "3": // Coevaluador
                visible = aprendices.isVisible();

                pnlBtonPerfil.setVisible(false);
                pnlBtonInicio.setVisible(false);
                logo.setVisible(false);
                pnlBtonAprenContrat.setVisible(false);
                separadorInvisible.setVisible(false);

                if (menuReducido) {
                    tamañoCompletoMenu();

                    pnlBtonPerfil.setVisible(true);
                    pnlBtonInicio.setVisible(true);
                    logo.setVisible(true);
                    pnlBtonAprenContrat.setVisible(true);
                    separadorInvisible.setVisible(true);

                } else {
                    tamañoReducidoMenu();
                }

                menuPanel.revalidate(); // Refresca el layout
                menuReducido = !menuReducido;
                break;
            case "4": // Auxiliar
                configBotonMenu();
                break;
            case "5": // Administrador
                configBotonMenu();
                break;
            case "6": // Administrador del sistema
                configBotonMenu();
                break;
            default:
                // Rol desconocido: puedes mostrar un mensaje o panel de error
                System.out.println("Rol desconocido: " + cofigBotonInicioSegunRol);

        }

    }
    /**
     * Configuración general para encoger/expandir el menú lateral.
     * Esto es para roles como Administrador, Auxiliar, etc.
     * Oculta todos los textos de los botones y solo deja iconos si se encoge.
     */
    public void configBotonMenu(){

        visible = aprendices.isVisible();

        verUsuariosButton.setVisible(false);
        crearUsuariosButton.setVisible(false);
        permisosButton.setVisible(false);
        registrarEmpresa.setVisible(false);
        pnlBtonPerfil.setVisible(false);
        pnlBtonInicio.setVisible(false);
        logo.setVisible(false);
        aprendices.setVisible(false);
        evaluadores.setVisible(false);
        coevaluadores.setVisible(false);
        auxiliares.setVisible(false);
        AsignarIntructorButton.setVisible(false);
        separadorInvisible.setVisible(false);

        if (menuReducido) {
            tamañoCompletoMenu();


            verUsuariosButton.setVisible(true);
            crearUsuariosButton.setVisible(true);
            permisosButton.setVisible(true);
            registrarEmpresa.setVisible(true);
            AsignarIntructorButton.setVisible(true);
            pnlBtonPerfil.setVisible(true);
            pnlBtonInicio.setVisible(true);
            logo.setVisible(true);
            separadorInvisible.setVisible(true);

        } else {
            tamañoReducidoMenu();
        }

        menuPanel.revalidate(); // Refresca el layout
        menuReducido = !menuReducido;

    }
    /**
     * Cambia el texto del saludo de bienvenida ("Bienvenido...") según el rol del usuario.
     */
    public void cambiarTituloSegunRol(){
        switch (cofigBotonInicioSegunRol) {
            case "1": // Aprendiz
                tituloBienvenido.setText("Bienvenido Aprendiz");
                break;
            case "2": // Evaluador
                tituloBienvenido.setText("Bienvenido Evaluador");
                break;
            case "3": // Coevaluador
                tituloBienvenido.setText("Bienvenido Coevaluador");
                break;
            case "4": // Auxiliar
                tituloBienvenido.setText("Bienvenido Auxiliar");
                break;
            case "5": // Administrador
                tituloBienvenido.setText("Bienvenido Administrador");
                break;
            case "6": // Administrador del sistema
                tituloBienvenido.setText("Bienvenido Administrador del sistema");
                break;
            default:
                // Rol desconocido: puedes mostrar un mensaje o panel de error
                System.out.println("Rol desconocido: " + cofigBotonInicioSegunRol);
        }
    }
    /**
     * Vuelve a mostrar la pantalla de inicio en el panel de contenido.
     * Para la mayoría de los roles de admin/auxiliar, es una imagen.
     * Para el aprendiz, es su panel de seguimiento.
     */
    public void regresarInicio(){
        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());

        switch (cofigBotonInicioSegunRol) {
            case "1": // Aprendiz
                mostrarPanelGraficoInicio();
                f147.setVisible(false);
                f023.setVisible(false);
                break;
            case "2": // Evaluador
                contenidoPanel.add(img_princi);
                PanelFormato.setVisible(false);
                f147.setVisible(false);
                f023.setVisible(false);
                break;
            case "3": // Coevaluador
                contenidoPanel.add(img_princi);
                PanelFormato.setVisible(false);
                f147.setVisible(false);
                f023.setVisible(false);
                break;
            case "4": // Auxiliar
                contenidoPanel.add(img_princi);
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(true);
                f147.setVisible(false);
                f023.setVisible(false);
                break;
            case "5": // Administrador
                contenidoPanel.add(img_princi);
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
                PanelFormato.setVisible(false);
                f147.setVisible(false);
                f023.setVisible(false);
                break;
            case "6": // Administrador del sistema
                contenidoPanel.add(img_princi);
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
                PanelFormato.setVisible(false);
                f147.setVisible(false);
                f023.setVisible(false);
                break;
            default:
                // Rol desconocido: puedes mostrar un mensaje o panel de error
                System.out.println("Rol desconocido: " + cofigBotonInicioSegunRol);
        }

        contenidoPanel.revalidate();
        contenidoPanel.repaint();
    }
    /**
     * Pone el icono del SENA en la barra de título de la ventana.
     *
     * @param frame La ventana (JFrame) a la que le vamos a poner el ícono.
     */
    public static void setFrameIcon(JFrame frame) {
        URL iconoURL = Administrador.class.getClassLoader().getResource("Example_Screen/img/SENA.png");
        if (iconoURL != null) {
            frame.setIconImage(new ImageIcon(iconoURL).getImage());
        }
    }

    public void tamañoCompletoMenu(){
        menuPanel.setPreferredSize(new Dimension(anchoCompleto, menuPanel.getHeight()));
        menuPanel.setMinimumSize(new Dimension(anchoCompleto, menuPanel.getHeight()));
        menuPanel.setMaximumSize(new Dimension(anchoCompleto, menuPanel.getHeight()));
    }

    public void tamañoReducidoMenu(){
        menuPanel.setPreferredSize(new Dimension(anchoReducido, menuPanel.getHeight()));
        menuPanel.setMinimumSize(new Dimension(anchoReducido, menuPanel.getHeight()));
        menuPanel.setMaximumSize(new Dimension(anchoReducido, menuPanel.getHeight()));
    }


    /**
     * Este es el metodo que hace que se muestre la pantalla del Administrador.
     * Crea la ventana (JFrame), le pone el contenido, la maximiza y la hace visible.
     * También maneja qué pasa cuando el usuario intenta cerrar la ventana.
     */
    public void Admin_Screen() {
        frame = new JFrame("SAEP");
        frame.setContentPane(this.main);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);

        setFrameIcon(frame);
        frame.addWindowListener(new WindowAdapter() {
            /**
             * Se ejecuta justo cuando el usuario está intentando cerrar la ventana.
             * Muestra el diálogo de confirmación para salir del sistema.
             * @param e El evento de cierre de ventana.
             */
            @Override
            public void windowClosing(WindowEvent e) {

                int option = JOptionPane.showConfirmDialog(frame, "¿Está seguro de que desea salir?\nCualquier operación que esté realizando y no haya guardado se perderá.","Confirmar Salida",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                if(option == JOptionPane.YES_OPTION)
                {
                    frame.dispose(); // Cierra la ventana
                    System.exit(0);
                }
            }
        });
    }

}
