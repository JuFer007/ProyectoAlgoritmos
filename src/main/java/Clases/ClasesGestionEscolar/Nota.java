package Clases.ClasesGestionEscolar;

public class Nota {
    private int idNota;
    private int idMatricula;
    private int idCurso;
    private byte nota;
    private String tipoNota;

    public Nota(int idNota, int idMatricula, int idCurso, byte nota, String tipoNota) {
        this.idNota = idNota;
        this.idMatricula = idMatricula;
        this.idCurso = idCurso;
        this.nota = nota;
        this.tipoNota = tipoNota;
    }

    public int getIdNota() {
        return idNota;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public byte getNota() {
        return nota;
    }

    public void setNota(byte nota) {
        this.nota = nota;
    }

    public String getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(String tipoNota) {
        this.tipoNota = tipoNota;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "idNota=" + idNota +
                ", idMatricula=" + idMatricula +
                ", idCurso=" + idCurso +
                ", nota=" + nota +
                ", tipoNota='" + tipoNota + '\'' +
                '}';
    }

    public Object[] convertir(){
        Object[] notas = {this.nota, this.tipoNota};
        return notas;
    }

}
