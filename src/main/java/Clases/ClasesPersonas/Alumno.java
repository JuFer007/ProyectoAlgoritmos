package Clases.ClasesPersonas;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class Alumno extends Persona{
    private int idAlumno;
    private String codigoAlumno;
    private int idPersona;
    private int idApoderado;

    public Alumno(String dnipersona, String primernombre, String segundonombre, String apellidopaterno, String apellidomaterno, Date fechanacimiento, String genero, String codigoAlumno, int idApoderado) {
        super(dnipersona, primernombre, segundonombre, apellidopaterno, apellidomaterno, fechanacimiento, genero);
        this.codigoAlumno = codigoAlumno;
        this.idApoderado = idApoderado;
    }

    public Alumno(String dnipersona, String primernombre, String segundonombre, String apellidopaterno, String apellidomaterno, Date fechanacimiento, String genero) {
        super(dnipersona, primernombre, segundonombre, apellidopaterno, apellidomaterno, fechanacimiento, genero);
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getCodigoAlumno() {
        return codigoAlumno;
    }

    public void setCodigoAlumno(String codigoAlumno) {
        this.codigoAlumno = codigoAlumno;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getIdApoderado() {
        return idApoderado;
    }

    public void setIdApoderado(int idApoderado) {
        this.idApoderado = idApoderado;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "idAlumno=" + idAlumno +
                ", codigoAlumno='" + codigoAlumno + '\'' +
                ", idPersona=" + idPersona +
                ", idApoderado=" + idApoderado +
                '}';
    }

    //Metodo para obtener la edad y algunos datos a mostrar de alumno
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
                this.codigoAlumno,
                this.getDnipersona(),
                this.getPrimernombre() + " " + this.getSegundonombre(),
                this.getApellidopaterno(),
                this.getApellidomaterno(),
                edad,
                this.getGenero()
        };
    }
}
