package Clases.ClasesGestionEscolar;

import java.time.LocalDate;

public class Matricula {
    private String grado;
    private String seccion;
    private String añoE;
    private String codigoAlumno;
    private String trabajador;
    private LocalDate fechaMatricula;
    private String estadoMatricula;

    public Matricula(String grado, String seccion, String añoE, String codigoAlumno, String trabajador, LocalDate fechaMatricula, String estadoMatricula) {
        this.grado = grado;
        this.seccion = seccion;
        this.añoE = añoE;
        this.codigoAlumno = codigoAlumno;
        this.trabajador = trabajador;
        this.fechaMatricula = fechaMatricula;
        this.estadoMatricula = estadoMatricula;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getAñoE() {
        return añoE;
    }

    public void setAñoE(String añoE) {
        this.añoE = añoE;
    }

    public String getCodigoAlumno() {
        return codigoAlumno;
    }

    public void setCodigoAlumno(String codigoAlumno) {
        this.codigoAlumno = codigoAlumno;
    }

    public String getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(String trabajador) {
        this.trabajador = trabajador;
    }

    public LocalDate getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(LocalDate fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public String getEstadoMatricula() {
        return estadoMatricula;
    }

    public void setEstadoMatricula(String estadoMatricula) {
        this.estadoMatricula = estadoMatricula;
    }

    @Override
    public String toString() {
        return "Matricula{" +
                ", grado='" + grado + '\'' +
                ", seccion='" + seccion + '\'' +
                ", añoE='" + añoE + '\'' +
                ", codigoAlumno='" + codigoAlumno + '\'' +
                ", trabajador='" + trabajador + '\'' +
                ", fechaMatricula=" + fechaMatricula +
                ", estadoMatricula='" + estadoMatricula + '\'' +
                '}';
    }

    public Object[] convertir(){
        Object[] matricula = {this.fechaMatricula, this.estadoMatricula};
        return matricula;
    }

}
