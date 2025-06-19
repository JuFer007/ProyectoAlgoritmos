package Clases.ClasesGestionEscolar;

public class Seccion {
    private int idSeccion;
    private int idGrado;
    private char seccion;
    private byte numeroAlumnos;

    public Seccion(int idSeccion, int idGrado, char seccion, byte numeroAlumnos) {
        this.idSeccion = idSeccion;
        this.idGrado = idGrado;
        this.seccion = seccion;
        this.numeroAlumnos = numeroAlumnos;
    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public int getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(int idGrado) {
        this.idGrado = idGrado;
    }

    public char getSeccion() {
        return seccion;
    }

    public void setSeccion(char seccion) {
        this.seccion = seccion;
    }

    public byte getNumeroAlumnos() {
        return numeroAlumnos;
    }

    public void setNumeroAlumnos(byte numeroAlumnos) {
        this.numeroAlumnos = numeroAlumnos;
    }

    @Override
    public String toString() {
        return "Seccion{" +
                "idSeccion=" + idSeccion +
                ", idGrado=" + idGrado +
                ", seccion=" + seccion +
                ", numeroAlumnos=" + numeroAlumnos +
                '}';
    }

    public Object[] convertir(){
        Object[] seccion = {this.seccion, this.numeroAlumnos};
        return seccion;
    }

}
