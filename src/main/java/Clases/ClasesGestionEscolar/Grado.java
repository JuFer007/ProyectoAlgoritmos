package Clases.ClasesGestionEscolar;

public class Grado {
    private int idGrado;
    private int idNivel;
    private String grado;

    public Grado(int idGrado, int idNivel, String grado) {
        this.idGrado = idGrado;
        this.idNivel = idNivel;
        this.grado = grado;
    }

    public int getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(int idGrado) {
        this.idGrado = idGrado;
    }

    public int getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(int idNivel) {
        this.idNivel = idNivel;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    @Override
    public String toString() {
        return "Grado{" +
                "idGrado=" + idGrado +
                ", idNivel=" + idNivel +
                ", grado='" + grado + '\'' +
                '}';
    }

    public Object[] convertir() {
        Object[] grado = {this.grado};
        return grado;
    }

}
