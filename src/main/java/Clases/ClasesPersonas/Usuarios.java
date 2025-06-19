package Clases.ClasesPersonas;


public class Usuarios {
    private int idUsuario;
    private int idPersona;
    private String nombreUsuario;
    private String contraseñaUsuario;
    private String rolUsuario ;

    public Usuarios(int idUsuario, int idPersona, String nombreUsuario, String contraseñaUsuario, String rolUsuario) {
        this.idUsuario = idUsuario;
        this.idPersona = idPersona;
        this.nombreUsuario = nombreUsuario;
        this.contraseñaUsuario = contraseñaUsuario;
        this.rolUsuario = rolUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseñaUsuario() {
        return contraseñaUsuario;
    }

    public void setContraseñaUsuario(String contraseñaUsuario) {
        this.contraseñaUsuario = contraseñaUsuario;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    @Override
    public String toString() {
        return "Usuarios{" +
                "idUsuario=" + idUsuario +
                ", idPersona=" + idPersona +
                ", nombreUsuario='" + nombreUsuario +
                ", rolUsuario=" + rolUsuario +
                '}';
    }
}
