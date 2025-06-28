package Clases.ClasesPersonas;
import java.util.Date;

public class Trabajador extends Persona {
    private int idTrabajador;
    private String codigoTrabajador;
    private int idPersona;
    private String tipoTrabajador;
    private String turnoAsignado;
    private String cargo;

    public Trabajador(String dnipersona, String primernombre, String segundonombre, String apellidopaterno, String apellidomaterno, Date fechanacimiento, String genero, String tipoTrabajador, String turnoAsignado, String cargo) {
        super(dnipersona, primernombre, segundonombre, apellidopaterno, apellidomaterno, fechanacimiento, genero);
        this.tipoTrabajador = tipoTrabajador;
        this.turnoAsignado = turnoAsignado;
        this.cargo = cargo;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getCodigoTrabajador() {
        return codigoTrabajador;
    }

    public void setCodigoTrabajador(String codigoTrabajador) {
        this.codigoTrabajador = codigoTrabajador;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getTipoTrabajador() {
        return tipoTrabajador;
    }

    public void setTipoTrabajador(String tipoTrabajador) {
        this.tipoTrabajador = tipoTrabajador;
    }

    public String getTurnoAsignado() {
        return turnoAsignado;
    }

    public void setTurnoAsignado(String turnoAsignado) {
        this.turnoAsignado = turnoAsignado;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Trabajador{" +
                "idTrabajador=" + idTrabajador +
                ", codigoTrabajador='" + codigoTrabajador + '\'' +
                ", idPersona=" + idPersona +
                ", tipoTrabajador='" + tipoTrabajador + '\'' +
                ", turnoAsignado='" + turnoAsignado + '\'' +
                ", cargo='" + cargo + '\'' +
                '}';
    }

    public Object[] convertir(){
        Object[] trabajador = {
                this.codigoTrabajador,
                this.getDnipersona(),
                this.getPrimernombre() + " " + this.getSegundonombre(),
                this.getApellidopaterno(),
                this.getApellidomaterno(),
                this.tipoTrabajador,
                this.cargo
        };
        return trabajador;
    }

}
