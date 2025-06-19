package Clases.ClasesGestionEscolar;

public class Nivel {
    private int idNivel;
    private String nivel;

    public Nivel(int idNivel, String nivel) {
        this.idNivel = idNivel;
        this.nivel = nivel;
    }

    public int getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(int idNivel) {
        this.idNivel = idNivel;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Nivel{" +
                "idNivel=" + idNivel +
                ", nivel='" + nivel + '\'' +
                '}';
    }

    public Object[] convertir() {
        Object[] nivel = {this.nivel};
        return nivel;
    }

}
