package Example_Screen.View.Administrador;

import AsignacionInstructor.AsignacionGUI;
import Empresas.Vista.EmpresaGUI;
import Example_Screen.View.Aprendiz.AprendizGUI;
import Example_Screen.View.Login.LoginGUI;
import Example_Screen.View.Usuarios_Registrados.VerUsuariosRegistrados;
import Prueba3.Modelo.GUI.CodigoGUI;

import static Example_Screen.View.Login.LoginGUI.cofigBotonInicioSegunRol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private JTable table1;
    private JFrame frame;


    private VerUsuariosRegistrados verUsuario = new VerUsuariosRegistrados();

    public static int verUsuarioPorRol = 0;
    int anchoCompleto = 255;  // Ancho original del menú
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

        switch (cofigBotonInicioSegunRol) {
            case "1": // Aprendiz
                cargarInicioPaneles();
                break;
            case "2": // Evaluador
                break;
            case "3": // Coevaluador
                break;
            case "4": // Auxiliar
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
                break;
            case "5": // Administrador
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
            case "6": // Administrador del sistema
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
                break;
            default:
                // Rol desconocido: puedes mostrar un mensaje o panel de error
                System.out.println("Rol desconocido: " + cofigBotonInicioSegunRol);
        }


        //this.usuario=usuario;



//        if(usuario.getRol().equalsIgnoreCase("Admin"))
//        {
//            verUsuariosButton.setVisible(true);
//            crearUsuariosButton.setVisible(true);
//            permisosButton.setVisible(true);
//            ajustesButton.setVisible(true);
//        }

        logo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_burguer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton[] botones = {inicio, verUsuariosButton, crearUsuariosButton,AsignarIntructorButton, miPerfil, permisosButton, registrarEmpresa,
                aprendices, evaluadores, coevaluadores, auxiliares};

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
    public void mostrarPanelSeguimiento() {
        String usuario = LoginGUI.getUsuarioActual(); // Obtener usuario logueado
        CodigoGUI codigoGUI = new CodigoGUI(usuario); // Pasar al constructor

        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());
        contenidoPanel.add(codigoGUI.getPanel(), BorderLayout.CENTER);
        contenidoPanel.revalidate();
        contenidoPanel.repaint();
    }
    /**
     * Muestra el panel específico para la vista de Aprendiz.
     * Carga la pantalla de AprendizGUI.
     */
    public void mostrarPanelAprendiz() {
        AprendizGUI aprendizGUI = new AprendizGUI();

        // Muy importante: accede al panel primero para inicializar los componentes del GUI builder
        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());
        contenidoPanel.add(aprendizGUI.getPanel(), BorderLayout.CENTER);
        contenidoPanel.revalidate();
        contenidoPanel.repaint();
    }
    /**
     * Este metodo sirve para ocultar un montón de botones y paneles del menú.
     * para que no vea opciones que no le corresponden.
     */
    public void ocultarComponentesNoAsignado(){
        aprendices.setVisible(false);
        evaluadores.setVisible(false);
        coevaluadores.setVisible(false);
        auxiliares.setVisible(false);
        verUsuariosButton.setVisible(false);
        permisosButton.setVisible(false);
        registrarEmpresa.setVisible(false);
        crearUsuariosButton.setVisible(false);
        verUsuariosButton.setVisible(false);
        pnlBtonAsigInstru.setVisible(false);
        pnlBtonVerUsua.setVisible(false);
        pnlBtonCrearUsua.setVisible(false);
        pnlBtonRegisEmpr.setVisible(false);
        pnlBtonPermiso.setVisible(false);
    }

    /**
     * Carga los paneles iniciales dependiendo del rol del usuario.
     * Por ejemplo, si es un Aprendiz, oculta muchas cosas y muestra el panel de seguimiento.
     */
    public void cargarInicioPaneles(){

        switch (cofigBotonInicioSegunRol) {
            case "1": // Aprendiz
                ocultarComponentesNoAsignado();
                mostrarPanelSeguimiento();
                break;
            case "2": // Evaluador
                break;
            case "3": // Coevaluador
                break;
            case "4": // Auxiliar
                break;
            case "5": // Administrador
            case "6": // Administrador del sistema
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
                anchoCompleto = 255;  // Ancho original del menú
                anchoReducido = 80;   // Ancho reducido (30% aprox)

                visible = aprendices.isVisible();

                miPerfil.setVisible(false);
                inicio.setVisible(false);
                logo.setVisible(false);

                if (menuReducido) {
                    menuPanel.setPreferredSize(new Dimension(anchoCompleto, menuPanel.getHeight()));

                    miPerfil.setVisible(true);
                    inicio.setVisible(true);
                    logo.setVisible(true);

                } else {
                    menuPanel.setPreferredSize(new Dimension(anchoReducido, menuPanel.getHeight()));
                }

                menuPanel.revalidate(); // Refresca el layout
                menuReducido = !menuReducido;
                break;
            case "2": // Evaluador
                break;
            case "3": // Coevaluador
                break;
            case "4": // Auxiliar
                configBotonMenu();
                break;
            case "5": // Administrador
                configBotonMenu();
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
        anchoCompleto = 293;  // Ancho original del menú
        anchoReducido = 80;   // Ancho reducido (30% aprox)

        visible = aprendices.isVisible();

        verUsuariosButton.setVisible(false);
        crearUsuariosButton.setVisible(false);
        permisosButton.setVisible(false);
        registrarEmpresa.setVisible(false);
        miPerfil.setVisible(false);
        inicio.setVisible(false);
        logo.setVisible(false);
        aprendices.setVisible(false);
        evaluadores.setVisible(false);
        coevaluadores.setVisible(false);
        auxiliares.setVisible(false);
        AsignarIntructorButton.setVisible(false);

        if (menuReducido) {
            menuPanel.setPreferredSize(new Dimension(anchoCompleto, menuPanel.getHeight()));

            verUsuariosButton.setVisible(true);
            crearUsuariosButton.setVisible(true);
            permisosButton.setVisible(true);
            registrarEmpresa.setVisible(true);
            AsignarIntructorButton.setVisible(true);
            miPerfil.setVisible(true);
            inicio.setVisible(true);
            logo.setVisible(true);

        } else {            menuPanel.setPreferredSize(new Dimension(anchoReducido, menuPanel.getHeight()));
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
                mostrarPanelSeguimiento();
                break;
            case "2": // Evaluador
                break;
            case "3": // Coevaluador
                break;
            case "4": // Auxiliar
                contenidoPanel.add(img_princi);
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(true);
                break;
            case "5": // Administrador
                contenidoPanel.add(img_princi);
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
            case "6": // Administrador del sistema
                contenidoPanel.add(img_princi);
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
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

        System.out.println(cofigBotonInicioSegunRol);

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
