package Clases.ClasesPersonas;

public class Alumno {
    private int idAlumno;
    private String codigoAlumno;
    private int idPersona;
    private int idApoderado;

    public Alumno(int idAlumno, String codigoAlumno, int idPersona, int idApoderado) {
        this.idAlumno = idAlumno;
        this.codigoAlumno = codigoAlumno;
        this.idPersona = idPersona;
        this.idApoderado = idApoderado;
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

    public Object[] convertir() {
        Object[] alumno = {this.codigoAlumno};
        return alumno;
    }
}
