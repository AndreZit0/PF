package Example_Screen.View.Administrador;

import Empresas.Vista.AdministrarGUI;
import Empresas.Vista.CrearGUI;
import AsignacionInstructor.AsignacionGUI;
import Example_Screen.Connection.DBConnection;
import Example_Screen.Model.Aprendiz;
import Example_Screen.Model.AprendizDAO;
import Example_Screen.View.AprendicesAsignados;
import Example_Screen.View.AprendicesContratados;
import Example_Screen.View.GraficoCircular;
import Example_Screen.View.Login.LoginGUI;
import Example_Screen.View.Usuarios_Registrados.VerUsuariosRegistrados;

import Example_Screen.View.VisualizarPerfilGUI;
import Seguimiento.Modelo.GUI.CodigoGUI;
import Seguimiento.Modelo.GUI.CodigoGUI2;
import Usuarios.*;


import static Example_Screen.View.Login.LoginGUI.cofigBotonInicioSegunRol;
import static Example_Screen.View.Login.LoginGUI.traerIDusuario;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private JButton botonCrearUsuario;
    private JButton botonCrearFicha;
    private JButton botonCrearPrograma;
    private JButton botonCrearSede;
    private JButton botonCrearModalidad;
    private JButton botonCrearEmpresa;
    private JButton botonAdministrarEmpresa;
    private JButton verFichasButton;
    private JButton verProgramasButton;
    private JButton verSedesButton;

    private JTable table1;
    private JFrame frame;


    private VerUsuariosRegistrados verUsuario = new VerUsuariosRegistrados();

    public static int verUsuarioPorRol = 0;
    int anchoCompleto = 280;  // Ancho original del menú
    int anchoReducido = 80;   // Ancho reducido (30% aprox)

    String tipoDeFormulario = "";

    boolean visible = Boolean.parseBoolean(null);



    private boolean menuReducido = false; // Estado inicial


    private JButton submenuActivo = null;
    private Color colorSubmenuNormal = new Color(57, 169, 0);

    private static final String ICONO_FLECHA_DERECHA = "     ▸";
    private static final String ICONO_FLECHA_ABAJO = "     ▾";
    private static final Color COLOR_FLECHA = Color.WHITE;
    /**
     * Constructor de la clase Administrador.
     * Aquí es donde se arma toda la interfaz y se configuran los botones y eventos.
     * También ajusta algunas cosas dependiendo del rol del usuario que entró.
     */
    public Administrador() {
        configurarFlechasBotones();
        cambiarTituloSegunRol();
        tamañoCompletoMenu();
        pnlBtonPermiso.setVisible(false);
        botonCrearModalidad.setVisible(false);


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
                botonCrearUsuario.setVisible(false);
                botonCrearModalidad.setVisible(false);
                botonCrearFicha.setVisible(false);
                botonCrearPrograma.setVisible(false);
                botonCrearSede.setVisible(false);
                botonCrearEmpresa.setVisible(false);
                botonAdministrarEmpresa.setVisible(false);
                verFichasButton.setVisible(false);
                verProgramasButton.setVisible(false);
                verSedesButton.setVisible(false);
                f023.setVisible(false);
                f147.setVisible(false);
                cargarInicioPaneles();
                break;
            case "5": // Administrador
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
                botonCrearUsuario.setVisible(false);
                botonCrearModalidad.setVisible(false);
                botonCrearFicha.setVisible(false);
                botonCrearPrograma.setVisible(false);
                botonCrearSede.setVisible(false);
                botonCrearEmpresa.setVisible(false);
                botonAdministrarEmpresa.setVisible(false);
                verFichasButton.setVisible(false);
                verProgramasButton.setVisible(false);
                verSedesButton.setVisible(false);
                f023.setVisible(false);
                f147.setVisible(false);
                cargarInicioPaneles();
                break;
            case "6": // Administrador del sistema
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
                botonCrearUsuario.setVisible(false);
                botonCrearModalidad.setVisible(false);
                botonCrearFicha.setVisible(false);
                botonCrearPrograma.setVisible(false);
                botonCrearSede.setVisible(false);
                botonCrearEmpresa.setVisible(false);
                botonAdministrarEmpresa.setVisible(false);
                verFichasButton.setVisible(false);
                verProgramasButton.setVisible(false);
                verSedesButton.setVisible(false);
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
                aprendices, evaluadores, coevaluadores, auxiliares, FormatoBoton, f147, f023,asignaBoton, botonAprendizContratado, botonCrearSede,
                botonCrearPrograma, botonCrearModalidad, botonCrearUsuario, botonCrearFicha, botonCrearEmpresa, botonAdministrarEmpresa,verSedesButton,verProgramasButton,verFichasButton
        };


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
        aplicarEfectoHover(botonAdministrarEmpresa, colorHover, colorBase);
        aplicarEfectoHover(botonCrearEmpresa, colorHover, colorBase);
        aplicarEfectoHover(botonCrearFicha, colorHover, colorBase);
        aplicarEfectoHover(botonCrearModalidad, colorHover, colorBase);
        aplicarEfectoHover(botonCrearSede, colorHover, colorBase);
        aplicarEfectoHover(botonCrearUsuario, colorHover, colorBase);
        aplicarEfectoHover(botonCrearPrograma, colorHover, colorBase);
        aplicarEfectoHover(aprendices, colorHover, colorBase);
        aplicarEfectoHover(auxiliares, colorHover, colorBase);
        aplicarEfectoHover(coevaluadores, colorHover, colorBase);
        aplicarEfectoHover(evaluadores, colorHover, colorBase);



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
                botonCrearUsuario.setVisible(!visible);
                String textoBase = verUsuariosButton.getText().replace(ICONO_FLECHA_DERECHA, "").replace(ICONO_FLECHA_ABAJO, "").trim();
                verUsuariosButton.setText(textoBase + "  " + (visible ? ICONO_FLECHA_DERECHA : ICONO_FLECHA_ABAJO));
                if (visible) {
                    resaltarSubmenu(null);
                }

            }
        });

        /**
         * Este ActionListener controla el comportamiento del botón cuando se hace clic en él.
         * Su función es com tal alternar la visibilidad de dos componentes (f147 y f023) y
         */
        FormatoBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean visible = f147.isVisible();
                f147.setVisible(!visible);
                f023.setVisible(!visible);
                String textoBase = FormatoBoton.getText().replace(ICONO_FLECHA_DERECHA, "").replace(ICONO_FLECHA_ABAJO, "").trim();
                FormatoBoton.setText(textoBase + "  " + (visible ? ICONO_FLECHA_DERECHA : ICONO_FLECHA_ABAJO));
                if (visible) {
                    resaltarSubmenu(null);
                }
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
                resaltarSubmenu(aprendices);
                mostrarPanelUsuarios();
            }
        });
        evaluadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verUsuarioPorRol = 2;
                resaltarSubmenu(evaluadores);
                mostrarPanelUsuarios();

            }
        });
        coevaluadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verUsuarioPorRol = 3;
                resaltarSubmenu(coevaluadores);
                mostrarPanelUsuarios();
            }
        });
        auxiliares.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verUsuarioPorRol = 4;
                resaltarSubmenu(auxiliares);
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

        /**
         * Este evento se ejecuta cuando el usuario hace clic en el botón.
         * Esta pues Cambia la visibilidad de los botones botonCrearEmpresa y botonAdministrarEmpresa.
         */
        registrarEmpresa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean visible = botonCrearEmpresa.isVisible();;
                botonCrearEmpresa.setVisible(!visible);
                botonAdministrarEmpresa.setVisible(!visible);
                String textoBase = registrarEmpresa.getText().replace(ICONO_FLECHA_DERECHA, "").replace(ICONO_FLECHA_ABAJO, "").trim();
                registrarEmpresa.setText(textoBase + "  " + (visible ? ICONO_FLECHA_DERECHA : ICONO_FLECHA_ABAJO));
                if (visible) {
                    resaltarSubmenu(null);
                }
            }
        });
        AsignarIntructorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanelAsignarInstructor();
            }
        });

        /**
         * Este evento como tal se activa cuando el usuario hace clic en el botón.
         * Al presionar el botón, se muestra u oculta un conjunto de botones relacionados
         * con la creación de usuarios (como crear ficha, programa y sede).
         */
        crearUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean visible = botonCrearFicha.isVisible();;
                botonCrearFicha.setVisible(!visible);
                //botonCrearModalidad.setVisible(!visible);;
                botonCrearPrograma.setVisible(!visible);
                botonCrearSede.setVisible(!visible);
                verFichasButton.setVisible(!visible);
                verProgramasButton.setVisible(!visible);
                verSedesButton.setVisible(!visible);
                String textoBase = crearUsuariosButton.getText().replace(ICONO_FLECHA_DERECHA, "").replace(ICONO_FLECHA_ABAJO, "").trim();
                crearUsuariosButton.setText(textoBase + "  " + (visible ? ICONO_FLECHA_DERECHA : ICONO_FLECHA_ABAJO));
                if (visible) {
                    resaltarSubmenu(null);
                }
            }
        });
        f147.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resaltarSubmenu(f147);
                mostrarPanelSeguimiento147();
            }
        });
        f023.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resaltarSubmenu(f023);
                mostrarPanelSeguimiento023();
            }
        });
        botonCrearUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resaltarSubmenu(botonCrearUsuario);
                mostrarPanelCrearUsuario();
            }
        });

        botonCrearFicha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resaltarSubmenu(botonCrearFicha);
                mostrarPanelCrearFichas();
            }
        });
        botonCrearPrograma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resaltarSubmenu(botonCrearPrograma);
                mostrarPanelCrearProgramas();
            }
        });
        botonCrearSede.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resaltarSubmenu(botonCrearSede);
                mostrarPanelCrearSedes();
            }
        });

        botonCrearModalidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resaltarSubmenu(botonCrearModalidad);
                mostrarPanelCrearModalidad();
            }
        });

        botonCrearEmpresa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resaltarSubmenu(botonCrearEmpresa);
                mostrarPanelCrearEmpresa();

            }
        });
        botonAdministrarEmpresa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resaltarSubmenu(botonAdministrarEmpresa);
                mostrarPanelAdministrarEmpesa();
            }

        });


        asignaBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTablaAprendicesAsignados();

            }
        });

        botonAprendizContratado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTablaAprendicesContratados();

            }
        });
        verFichasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanelverFichas();
                resaltarSubmenu(verFichasButton);
            }
        });
        verProgramasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanelverProgramas();
                resaltarSubmenu(verProgramasButton);
            }
        });
        verSedesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanelverSedes();
                resaltarSubmenu(verSedesButton);
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

        int idUsuario = LoginGUI.idUsuarioActual; // o traerIDusuario
        int idRol = traerIDusuario; // ← CAMBIO AQUÍ: usar el rol real del usuario
        // O si no tienes rolUsuarioActual, usa el método que tengas para obtener el rol:
        // int idRol = obtenerRolUsuario(idUsuario);

        Editar_Admin editarAdmin = new Editar_Admin(idUsuario, idRol);

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
                if (!boton.equals(submenuActivo)) {
                    boton.setBackground(colorHover);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!boton.equals(submenuActivo)) {
                    boton.setBackground(colorBase);
                }
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
     * Muestra el panel para crear empresas.
     * Carga la pantalla de AdministrarGUI.
     */
    public void mostrarPanelCrearEmpresa() {
        CrearGUI crearGUI = new CrearGUI();

        // Muy importante: accede al panel primero para inicializar los componentes del GUI builder
        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());
        contenidoPanel.add(crearGUI.getPanel(), BorderLayout.CENTER);
        contenidoPanel.revalidate();
        contenidoPanel.repaint();
    }

    /**
     * Muestra el panel para Administrar  y ver empresas.
     * Carga la pantalla de AdministrarGUI.
     */

    public void mostrarPanelAdministrarEmpesa() {
        AdministrarGUI administrarGUI = new AdministrarGUI();

        // Muy importante: accede al panel primero para inicializar los componentes del GUI builder
        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());
        contenidoPanel.add(administrarGUI.getPanel(), BorderLayout.CENTER);
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
    /**
     * Muestra en pantalla el panel correspondiente al Formato 147 - Bitácoras.
     * Este metodo obtiene el usuario que ha iniciado sesión, crea una instancia
     * del panel de seguimiento 147 usando ese usuario, y lo muestra con el título adecuado.
     */
    public void mostrarPanelSeguimiento147() {
        String usuario = LoginGUI.getUsuarioActual();
        CodigoGUI2 codigoGUI = new CodigoGUI2(usuario);

        mostrarPanel("Formato 147 - Bitácoras", codigoGUI.getPanel());
    }
    /**
     * Muestra en pantalla el panel correspondiente al Formato 023 - Seguimiento.
     * Al igual que el anterior, este método consigue el nombre del usuario
     * que está usando el sistema, crea el panel del Formato 023, y lo muestra en la interfaz.
     */
    public void mostrarPanelSeguimiento023() {
        String usuario = LoginGUI.getUsuarioActual();
        CodigoGUI codigoGUI = new CodigoGUI(usuario);

        mostrarPanel("Formato 023 - Seguimiento", codigoGUI.getPanel());
    }

    /**
     * Muestra el panel gráfico de inicio para el usuario actual (aprendiz).
     * Este metodo se encarga de limpiar el panel principal y mostrar un resumen visual
     * del progreso del aprendiz que ha iniciado sesión.
     */
    public void mostrarPanelGraficoInicio() {
        contenidoPanel.setBackground(new Color(246, 246, 246)); // Color verde #39A900

        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout(20, 0));
        contenidoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        AprendizDAO dao = new AprendizDAO();
        Aprendiz aprendiz = dao.obtenerAprendiz(LoginGUI.idUsuarioActual);
        int progreso = aprendiz != null ? aprendiz.calcularProgreso() : 0;

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));


        JLabel titulo = new JLabel("Progreso de "+aprendiz.getNombre());
        titulo.setFont(new Font("Calibri", Font.BOLD, 25));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        GraficoCircular grafico = new GraficoCircular(progreso);
        grafico.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel fechaInicio = new JLabel("Fecha Inicio: " + (aprendiz != null ? aprendiz.getFecha_fin_lec() : ""));
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
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Crear y configurar botón Visualizar Perfil
        JButton botonPerfil = new JButton("Ver Información");
        botonPerfil.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonPerfil.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonPerfil.setPreferredSize(new Dimension(200, 40));
        botonPerfil.setMaximumSize(new Dimension(200, 40));
        botonPerfil.setBackground(new Color(0x39A900)); // Verde
        botonPerfil.setForeground(Color.WHITE);
        botonPerfil.setFont(new Font("Calibri", Font.BOLD, 20));
        botonPerfil.setFocusPainted(false);

        // Acción del botón Perfil
        botonPerfil.addActionListener(e -> {
            try {
                // Obtener el rol del usuario actual desde la base de datos
                int rolUsuario = obtenerRolUsuario(LoginGUI.idUsuarioActual);

                JDialog perfilDialog = new JDialog(frame, "Ver Información", true);
                // Usar el ID del usuario actual y su rol correcto
                VisualizarPerfilGUI perfilGUI = new VisualizarPerfilGUI(LoginGUI.idUsuarioActual, rolUsuario);
                perfilDialog.setContentPane(perfilGUI.panel1);
                perfilDialog.pack();
                perfilDialog.setLocationRelativeTo(frame);
                perfilDialog.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame,
                        "Error al cargar el perfil: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });


        // Efecto hover
        botonPerfil.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botonPerfil.setBackground(new Color(0, 120, 50));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botonPerfil.setBackground(new Color(0x39A900));
            }
        });

        // CÓDIGO COMENTADO - Botón Bitácoras
        /*
        // Crear y configurar botón Bitácoras
        JButton botonBitacoras = new JButton("Ver Bitácoras");
        botonBitacoras.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonBitacoras.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonBitacoras.setPreferredSize(new Dimension(200, 40));
        botonBitacoras.setMaximumSize(new Dimension(200, 40));
        botonBitacoras.setBackground(new Color(0x007BFF)); // Azul
        botonBitacoras.setForeground(Color.WHITE);
        botonBitacoras.setFont(new Font("Calibri", Font.BOLD, 20));
        botonBitacoras.setFocusPainted(false);

        // Hover efecto
        botonBitacoras.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botonBitacoras.setBackground(new Color(0x339EFF));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botonBitacoras.setBackground(new Color(0x007BFF));
            }
        });
        */

        // CÓDIGO COMENTADO - Botón Seguimiento
        /*
        // Crear y configurar botón Seguimiento
        JButton botonSeguimiento = new JButton("Ver Seguimiento");
        botonSeguimiento.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonSeguimiento.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSeguimiento.setPreferredSize(new Dimension(200, 40));
        botonSeguimiento.setMaximumSize(new Dimension(200, 40));
        botonSeguimiento.setBackground(new Color(0x003366)); // Azul oscuro
        botonSeguimiento.setForeground(Color.WHITE);
        botonSeguimiento.setFont(new Font("Calibri", Font.BOLD, 20));
        botonSeguimiento.setFocusPainted(false);

        // Hover efecto
        botonSeguimiento.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botonSeguimiento.setBackground(new Color(0x1A4D80));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botonSeguimiento.setBackground(new Color(0x003366));
            }
        });
        */

        // Añadir solo el botón de perfil centrado verticalmente
        panelDerecho.add(Box.createVerticalGlue());
        panelDerecho.add(botonPerfil);
        panelDerecho.add(Box.createVerticalGlue());

        // CÓDIGO COMENTADO - Agregado de botones adicionales
        /*
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 10)));
        panelDerecho.add(botonBitacoras);
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 10)));
        panelDerecho.add(botonSeguimiento);
        */


        contenidoPanel.add(panelIzquierdo, BorderLayout.CENTER);
        contenidoPanel.add(panelDerecho, BorderLayout.EAST);

        contenidoPanel.revalidate();
        contenidoPanel.repaint();
    }

    /**
     * Como tal lo que hace es que resalta visualmente un submenú cuando se selecciona.
     * Este metodo cambia el color de fondo del botón del submenú seleccionado para que
     * el usuario vea cuál está activo. También restaura el color del submenú anterior
     * si era diferente.
     */


    private int obtenerRolUsuario(int idUsuario) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT ID_rol FROM usuarios WHERE ID_usuarios = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("ID_rol");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 1; // Valor por defecto (Aprendiz) si no se encuentra
    }


    public void resaltarSubmenu(JButton botonSubmenu) {
        if (submenuActivo != null && !submenuActivo.equals(botonSubmenu)) {
            submenuActivo.setBackground(colorSubmenuNormal);
        }


        if (botonSubmenu != null) {
            botonSubmenu.setBackground(new Color(30, 150, 75));
            submenuActivo = botonSubmenu;
        } else {
            submenuActivo = null;
        }
    }





    /**
     * Agrega una flecha al texto de ciertos botones del menú y cambia su color.
     * Este metodo se usa para que los botones principales del sistema tengan una flecha
     * (→) al lado de su texto,este indicando que se puede desplegar un submenú.
     */
    public void configurarFlechasBotones() {

        verUsuariosButton.setText(verUsuariosButton.getText() + "    " + ICONO_FLECHA_DERECHA);
        crearUsuariosButton.setText(crearUsuariosButton.getText() + "        " + ICONO_FLECHA_DERECHA);
        registrarEmpresa.setText(registrarEmpresa.getText() + " " + ICONO_FLECHA_DERECHA);
        FormatoBoton.setText(FormatoBoton.getText() + "  " + ICONO_FLECHA_DERECHA);


        verUsuariosButton.setForeground(COLOR_FLECHA);
        crearUsuariosButton.setForeground(COLOR_FLECHA);
        registrarEmpresa.setForeground(COLOR_FLECHA);
        FormatoBoton.setForeground(COLOR_FLECHA);
    }

    /**
     * Muestra en pantalla la tabla con los aprendices que están asignados a un usuario.
     * Este metodo se encarga de obtener los aprendices relacionados con el usuario actual,
     * y luego mostrar esa información en una tabla dentro del panel principal de la interfaz.
     */
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
     * Muestra en el panel principal una tabla con los aprendices que han sido contratados por un usuario específico.
     * Este metodo obtiene los datos de los aprendices contratados y los muestra en una vista tipo tabla.
     */
    public void mostrarTablaAprendicesContratados() {

        VerUsuariosRegistrados vista = new VerUsuariosRegistrados();
        vista.mostrarRol("Aprendices Contratados");

        // 2. Obtener y mostrar los datos de aprendices contratados
        AprendicesContratados aprendicesContratados = new AprendicesContratados();
        aprendicesContratados.obtenerAprendicesContratados(traerIDusuario, vista);

        // 3. Limpiar y configurar el panel principal
        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());
        contenidoPanel.add(vista.getPanel(), BorderLayout.CENTER);

        // 4. Actualizar la interfaz
        contenidoPanel.revalidate();
        contenidoPanel.repaint();
    }

    /**
     * Muestra en el panel principal la interfaz gráfica para crear un nuevo usuario.
     * Este metodo crea una nueva instancia de la pantalla para crear usuarios,
     * limpia el contenido actual del panel principal y agrega el panel de creación de usuario.
     */
    public void mostrarPanelCrearUsuario() {
        CrearUsuarioGUI crearUsuarioGUI = new CrearUsuarioGUI();

        // Muy importante: accede al panel primero para inicializar los componentes del GUI builder
        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());
        contenidoPanel.add(crearUsuarioGUI.getPanel(), BorderLayout.CENTER);
        contenidoPanel.revalidate();
        contenidoPanel.repaint();
    }
    /**
     * Muestra en el panel principal la interfaz gráfica para crear una nueva modalidad.
     * Este metodo crea una nueva instancia de la pantalla para crear modalidades,
     * limpia el contenido actual del panel principal y agrega el panel de creación de modalidad.
     */
    public void mostrarPanelCrearModalidad() {
        CrearModalidadGUI modalidadGUI = new CrearModalidadGUI();

        // Muy importante: accede al panel primero para inicializar los componentes del GUI builder
        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());
        contenidoPanel.add(modalidadGUI.getPanel(), BorderLayout.CENTER);
        contenidoPanel.revalidate();
        contenidoPanel.repaint();
    }
    /**
     * Muestra en el panel principal la interfaz gráfica para crear un nuevo programa.
     * Este metodo crea una nueva instancia de la pantalla para crear programas,
     * limpia el contenido actual del panel principal y agrega el panel de creación de programa.
     */
    public void mostrarPanelCrearProgramas() {
        CrearProgramaGUI crearProgramaGUI = new CrearProgramaGUI();

        // Muy importante: accede al panel primero para inicializar los componentes del GUI builder
        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());
        contenidoPanel.add(crearProgramaGUI.getPanel(), BorderLayout.CENTER);
        contenidoPanel.revalidate();
        contenidoPanel.repaint();
    }
    /**
     * Muestra en el panel principal la interfaz para crear nuevas fichas.
     * Este metodo crea una nueva instancia de la pantalla para crear fichas,
     * limpia el contenido actual del panel principal y agrega el panel de creación de fichas.
     */
    public void mostrarPanelCrearFichas() {
        CrearFichasGUI crearFichasGUI = new CrearFichasGUI();

        // Muy importante: accede al panel primero para inicializar los componentes del GUI builder
        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());
        contenidoPanel.add(crearFichasGUI.getPanel(), BorderLayout.CENTER);
        contenidoPanel.revalidate();
        contenidoPanel.repaint();
    }
    /**
     * Muestra en el panel principal la interfaz para crear nuevas sedes.
     * Este metodo crea una nueva instancia del formulario para crear sedes,
     * limpia el contenido actual del panel principal y agrega el panel de creación de sedes.
     */
    public void mostrarPanelCrearSedes() {
        CrearSedesGUI crearSedesGUI = new CrearSedesGUI();

        // Muy importante: accede al panel primero para inicializar los componentes del GUI builder
        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());
        contenidoPanel.add(crearSedesGUI.getPanel(), BorderLayout.CENTER);
        contenidoPanel.revalidate();
        contenidoPanel.repaint();
    }
    public void mostrarPanelverFichas() {
        AdminFichasGUI adminFichasGUI = new AdminFichasGUI();

        // Muy importante: accede al panel primero para inicializar los componentes del GUI builder
        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());
        contenidoPanel.add(adminFichasGUI.getPanel(), BorderLayout.CENTER);
        contenidoPanel.revalidate();
        contenidoPanel.repaint();
    }
    public void mostrarPanelverProgramas() {
        AdminProgramaGUI adminProgramaGUI = new AdminProgramaGUI();

        // Muy importante: accede al panel primero para inicializar los componentes del GUI builder
        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());
        contenidoPanel.add(adminProgramaGUI.getPanel(), BorderLayout.CENTER);
        contenidoPanel.revalidate();
        contenidoPanel.repaint();
    }
    public void mostrarPanelverSedes() {
       AdminSedesGUI adminSedesGUI = new AdminSedesGUI();

        // Muy importante: accede al panel primero para inicializar los componentes del GUI builder
        contenidoPanel.removeAll();
        contenidoPanel.setLayout(new BorderLayout());
        contenidoPanel.add(adminSedesGUI.getPanel(), BorderLayout.CENTER);
        contenidoPanel.revalidate();
        contenidoPanel.repaint();
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
                botonCrearUsuario.setVisible(false);
                botonCrearModalidad.setVisible(false);
                botonCrearFicha.setVisible(false);
                botonCrearPrograma.setVisible(false);
                botonCrearSede.setVisible(false);
                botonCrearEmpresa.setVisible(false);
                botonAdministrarEmpresa.setVisible(false);
                pnlBtonAsigInstru.setVisible(false);
                pnlBtonVerUsua.setVisible(false);
                pnlBtonCrearUsua.setVisible(false);
                pnlBtonRegisEmpr.setVisible(false);
                pnlBtonPermiso.setVisible(false);
                pnlBtonAprenContrat.setVisible(false);
                verFichasButton.setVisible(false);
                verProgramasButton.setVisible(false);
                verSedesButton.setVisible(false);
                panelAsigna.setVisible(false);
                f147.setVisible(false);
                f023.setVisible(false);
                break;
            case "2": // Evaluador
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
                botonCrearUsuario.setVisible(false);
                botonCrearModalidad.setVisible(false);
                botonCrearFicha.setVisible(false);
                botonCrearPrograma.setVisible(false);
                botonCrearSede.setVisible(false);
                botonCrearEmpresa.setVisible(false);
                botonAdministrarEmpresa.setVisible(false);
                pnlBtonAsigInstru.setVisible(false);
                pnlBtonVerUsua.setVisible(false);
                pnlBtonCrearUsua.setVisible(false);
                pnlBtonRegisEmpr.setVisible(false);
                pnlBtonPermiso.setVisible(false);
                pnlBtonAprenContrat.setVisible(false);
                verFichasButton.setVisible(false);
                verProgramasButton.setVisible(false);
                verSedesButton.setVisible(false);
                PanelFormato.setVisible(false);
                f147.setVisible(false);
                f023.setVisible(false);

                break;
            case "3": // Coevaluador
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
                botonCrearUsuario.setVisible(false);
                botonCrearModalidad.setVisible(false);
                botonCrearFicha.setVisible(false);
                botonCrearPrograma.setVisible(false);
                botonCrearSede.setVisible(false);
                botonCrearEmpresa.setVisible(false);
                botonAdministrarEmpresa.setVisible(false);
                pnlBtonAsigInstru.setVisible(false);
                pnlBtonVerUsua.setVisible(false);
                pnlBtonCrearUsua.setVisible(false);
                pnlBtonRegisEmpr.setVisible(false);
                pnlBtonPermiso.setVisible(false);
                panelAsigna.setVisible(false);
                PanelFormato.setVisible(false);
                verFichasButton.setVisible(false);
                verProgramasButton.setVisible(false);
                verSedesButton.setVisible(false);
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
                //mostrarTablaAprendicesAsignados();
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
        botonCrearUsuario.setVisible(false);
        botonCrearModalidad.setVisible(false);
        botonCrearFicha.setVisible(false);
        botonCrearPrograma.setVisible(false);
        botonCrearSede.setVisible(false);
        botonCrearEmpresa.setVisible(false);
        botonAdministrarEmpresa.setVisible(false);
        separadorInvisible.setVisible(false);
        verFichasButton.setVisible(false);
        verProgramasButton.setVisible(false);
        verSedesButton.setVisible(false);

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
                tituloBienvenido.setText("Bienvenido Funcionario");
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
        if (submenuActivo != null) {
            submenuActivo.setBackground(colorSubmenuNormal);
            submenuActivo = null;
        }

        verUsuariosButton.setText(verUsuariosButton.getText().replace(ICONO_FLECHA_ABAJO, ICONO_FLECHA_DERECHA));
        crearUsuariosButton.setText(crearUsuariosButton.getText().replace(ICONO_FLECHA_ABAJO, ICONO_FLECHA_DERECHA));
        registrarEmpresa.setText(registrarEmpresa.getText().replace(ICONO_FLECHA_ABAJO, ICONO_FLECHA_DERECHA));
        FormatoBoton.setText(FormatoBoton.getText().replace(ICONO_FLECHA_ABAJO, ICONO_FLECHA_DERECHA));
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
                botonCrearUsuario.setVisible(false);
                botonCrearFicha.setVisible(false);
                botonCrearSede.setVisible(false);
                botonCrearPrograma.setVisible(false);
                botonCrearModalidad.setVisible(false);
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
                botonCrearEmpresa.setVisible(false);
                botonAdministrarEmpresa.setVisible(false);
                verFichasButton.setVisible(false);
                verProgramasButton.setVisible(false);
                verSedesButton.setVisible(false);
                f147.setVisible(false);
                f023.setVisible(false);
                break;
            case "5": // Administrador
                contenidoPanel.add(img_princi);
                botonCrearUsuario.setVisible(false);
                botonCrearFicha.setVisible(false);
                botonCrearSede.setVisible(false);
                botonCrearPrograma.setVisible(false);
                botonCrearModalidad.setVisible(false);
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
                PanelFormato.setVisible(false);
                botonCrearEmpresa.setVisible(false);
                botonAdministrarEmpresa.setVisible(false);
                verFichasButton.setVisible(false);
                verProgramasButton.setVisible(false);
                verSedesButton.setVisible(false);
                f147.setVisible(false);
                f023.setVisible(false);
                break;
            case "6": // Administrador del sistema
                contenidoPanel.add(img_princi);
                botonCrearUsuario.setVisible(false);
                botonCrearFicha.setVisible(false);
                botonCrearSede.setVisible(false);
                botonCrearPrograma.setVisible(false);
                botonCrearModalidad.setVisible(false);
                aprendices.setVisible(false);
                evaluadores.setVisible(false);
                coevaluadores.setVisible(false);
                auxiliares.setVisible(false);
                PanelFormato.setVisible(false);
                botonCrearEmpresa.setVisible(false);
                botonAdministrarEmpresa.setVisible(false);
                verFichasButton.setVisible(false);
                verProgramasButton.setVisible(false);
                verSedesButton.setVisible(false);
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
        URL iconoURL = Administrador.class.getClassLoader().getResource("img/SENA.png");
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

