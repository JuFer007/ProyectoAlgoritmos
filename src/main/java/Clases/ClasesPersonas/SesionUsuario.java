package Clases.ClasesPersonas;

public class SesionUsuario {
    private static SesionUsuario instancia;
    private String DNIusuario;
    private int idUsuario;
    private String nombreUsuario;
    private String rolUsuario;
    private String codigoTrabajador;

    private SesionUsuario() {}

    public static SesionUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }

    // Setters
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public void setCodigoTrabajador(String codigoTrabajador) {
        this.codigoTrabajador = codigoTrabajador;
    }

    public String getCodigoTrabajador() {
        return codigoTrabajador;
    }

    public String getDNIusuario() {
        return DNIusuario;
    }

    public void setDNIusuario(String DNIusuario) {
        this.DNIusuario = DNIusuario;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void cerrarSesion() {
        idUsuario = 0;
        nombreUsuario = null;
        rolUsuario = null;
        codigoTrabajador = null;
    }
}

