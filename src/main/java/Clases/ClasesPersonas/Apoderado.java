package Clases.ClasesPersonas;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class Apoderado extends Persona{
    private int idApoderado;
    private String correoElectronico;
    private String  numeroTelefono;
    private String parentesco_relacion;

    public Apoderado(String dnipersona, String primernombre, String segundonombre, String apellidopaterno, String apellidomaterno, Date fechanacimiento, String genero, String correoElectronico, String numeroTelefono, String parentesco_relacion) {
        super(dnipersona, primernombre, segundonombre, apellidopaterno, apellidomaterno, fechanacimiento, genero);
        this.correoElectronico = correoElectronico;
        this.numeroTelefono = numeroTelefono;
        this.parentesco_relacion = parentesco_relacion;
    }

    public int getIdApoderado() {
        return idApoderado;
    }

    public void setIdApoderado(int idApoderado) {
        this.idApoderado = idApoderado;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getParentesco_relacion() {
        return parentesco_relacion;
    }

    public void setParentesco_relacion(String parentesco_relacion) {
        this.parentesco_relacion = parentesco_relacion;
    }

    //Metodo para convetir datos y mostrarlos
    public Object[] convertir() {
        int edad = 0;
        if (this.getFechanacimiento() != null) {
            try {
                LocalDate fechaNac = ((java.sql.Date) this.getFechanacimiento()).toLocalDate();
                edad = Period.between(fechaNac, LocalDate.now()).getYears();
            } catch (ClassCastException e) {
                edad = 0;
            }
        }

        return new Object[] {
                this.getDnipersona(),
                this.getPrimernombre() + " " + this.getSegundonombre(),
                this.getApellidopaterno(),
                this.getApellidomaterno(),
                this.correoElectronico,
                this.numeroTelefono,
        };
    }
}
