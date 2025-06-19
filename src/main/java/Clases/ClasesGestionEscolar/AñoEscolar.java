package Clases.ClasesGestionEscolar;

public class AñoEscolar {
    private int idAñoEscolar;
    private String añoEscolar;

    public AñoEscolar(int idAñoEscolar, String añoEscolar) {
        this.idAñoEscolar = idAñoEscolar;
        this.añoEscolar = añoEscolar;
    }

    public int getIdAñoEscolar() {
        return idAñoEscolar;
    }

    public void setIdAñoEscolar(int idAñoEscolar) {
        this.idAñoEscolar = idAñoEscolar;
    }

    public String getAñoEscolar() {
        return añoEscolar;
    }

    public void setAñoEscolar(String añoEscolar) {
        this.añoEscolar = añoEscolar;
    }

    @Override
    public String toString() {
        return "AñoEscolar{" +
                "idAñoEscolar=" + idAñoEscolar +
                ", añoEscolar='" + añoEscolar + '\'' +
                '}';
    }

    public Object[] convertir() {
        Object[] añoEscolar = {this.añoEscolar};
        return añoEscolar;
    }

}
