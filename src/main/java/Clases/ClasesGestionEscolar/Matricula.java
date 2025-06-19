package Clases.ClasesGestionEscolar;

import java.time.LocalDate;

public class Matricula {
    private int idMatricula;
    private int idGrado;
    private int idSeccion;
    private int idAñoE;
    private int idAlumno;
    private int idTrabajador;
    private LocalDate fechaMatricula;
    private String estadoMatricula;

    public Matricula(int idMatricula, int idGrado, int idSeccion, int idAñoE, int idAlumno, int idTrabajador, LocalDate fechaMatricula, String estadoMatricula) {
        this.idMatricula = idMatricula;
        this.idGrado = idGrado;
        this.idSeccion = idSeccion;
        this.idAñoE = idAñoE;
        this.idAlumno = idAlumno;
        this.idTrabajador = idTrabajador;
        this.fechaMatricula = fechaMatricula;
        this.estadoMatricula = estadoMatricula;
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }

    public int getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(int idGrado) {
        this.idGrado = idGrado;
    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public int getIdAñoE() {
        return idAñoE;
    }

    public void setIdAñoE(int idAñoE) {
        this.idAñoE = idAñoE;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
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
                "idMatricula=" + idMatricula +
                ", idGrado=" + idGrado +
                ", idSeccion=" + idSeccion +
                ", idAñoE=" + idAñoE +
                ", idAlumno=" + idAlumno +
                ", idTrabajador=" + idTrabajador +
                ", fechaMatricula=" + fechaMatricula +
                ", estadoMatricula='" + estadoMatricula + '\'' +
                '}';
    }

    public Object[] convertir(){
        Object[] matricula = {this.fechaMatricula, this.estadoMatricula};
        return matricula;
    }

}
