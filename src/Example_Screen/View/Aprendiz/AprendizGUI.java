package Example_Screen.View.Aprendiz;

public class AprendizGUI {
/*

    static class DatabaseManager {
        public static Usuario autenticar(String usuario, String contraseña) {
            String sql = "SELECT * FROM usuarios WHERE email = ? AND clave = ?";

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, usuario);
                pstmt.setString(2, contraseña);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        Rol rol = Rol.fromId(rs.getInt("id_rol"));
                        return new Usuario(
                                rs.getInt("ID_usuarios"),
                                rs.getString("email"),
                                rs.getString("clave"),
                                rs.getString("nombres"),
                                rs.getString("numero"),
                                rol
                        );
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }


            public void LoginWindow() {

                btnLogin.addActionListener(e -> {


                    Usuario u = DatabaseManager.autenticar(usuario, contraseña);
                    if (u != null) {
                        JOptionPane.showMessageDialog(this, "Bienvenido, " + u.getNombre());
                        dispose();
                        abrirPanelSegunRol(u);
                    } else {
                        JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
            }

            private void abrirPanelSegunRol(Usuario u) {
                switch (u.getRol()) {
                    case ADMINISTRADOR:
                    case ADMIN_SISTEMA:
                        new PanelAdmin(u).setVisible(true);
                        break;
                    case APRENDIZ:
                        new PanelAprendiz(u).setVisible(true);
                        break;
                    case EVALUADOR:
                        new PanelInstructor(u).setVisible(true);
                        break;
                    default:
                        new PanelBasico(u).setVisible(true);
                }
            }
        }

 */
}
