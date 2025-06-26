package Clases.ClasesPersonas;
import java.util.Date;

public class Profesor extends Persona {
    private int idProfesor;
    private String codigoProfesor;
    private int idPersona;
    private String especialidad;
    private String gradoAcademico;
    private int horasSemanales;
    private String correoElectronico;
    private String telefono;

    public Profesor(String dnipersona, String primernombre, String segundonombre, String apellidopaterno, String apellidomaterno, Date fechanacimiento, String genero, String codigoProfesor, String especialidad, String gradoAcademico, int horasSemanales, String correoElectronico, String telefono) {
        super(dnipersona, primernombre, segundonombre, apellidopaterno, apellidomaterno, fechanacimiento, genero);
        this.codigoProfesor = codigoProfesor;
        this.especialidad = especialidad;
        this.gradoAcademico = gradoAcademico;
        this.horasSemanales = horasSemanales;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
    }

    public Profesor(String dnipersona, String primernombre, String segundonombre, String apellidopaterno, String apellidomaterno, Date fechanacimiento, String genero, String especialidad, String gradoAcademico, int horasSemanales, String correoElectronico, String telefono) {
        super(dnipersona, primernombre, segundonombre, apellidopaterno, apellidomaterno, fechanacimiento, genero);
        this.especialidad = especialidad;
        this.gradoAcademico = gradoAcademico;
        this.horasSemanales = horasSemanales;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "idProfesor=" + idProfesor +
                ", codigoProfesor='" + codigoProfesor + '\'' +
                ", idPersona=" + idPersona +
                ", especialidad='" + especialidad + '\'' +
                ", gradoAcademico='" + gradoAcademico + '\'' +
                ", horasSemanales=" + horasSemanales +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }

    //Retornar datos para mostrar en la tabla profesor
    public Object[] Convertir(){
        return new Object[] {
                this.codigoProfesor,
                this.getDnipersona(),
                this.getPrimernombre() + " " + this.getSegundonombre(),
                this.getApellidopaterno(),
                this.getApellidomaterno(),
                this.especialidad,
                this.getGenero(),
        };
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getCodigoProfesor() {
        return codigoProfesor;
    }

    public void setCodigoProfesor(String codigoProfesor) {
        this.codigoProfesor = codigoProfesor;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getGradoAcademico() {
        return gradoAcademico;
    }

    public void setGradoAcademico(String gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }

    public int getHorasSemanales() {
        return horasSemanales;
    }

    public void setHorasSemanales(int horasSemanales) {
        this.horasSemanales = horasSemanales;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
