package Clases.ClasesPersonas;
import java.time.LocalDate;
import java.time.Period;
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

    public Profesor(String dnipersona, String primernombre, String segundonombre, String apellidopaterno, String apellidomaterno, Date fechanacimiento, String genero, String codigoProfesor, int idPersona, String especialidad, String gradoAcademico, int horasSemanales, String correoElectronico, String telefono) {
        super(dnipersona, primernombre, segundonombre, apellidopaterno, apellidomaterno, fechanacimiento, genero);
        this.codigoProfesor = codigoProfesor;
        this.idPersona = idPersona;
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

}
