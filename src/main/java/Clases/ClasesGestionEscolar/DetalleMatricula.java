package Clases.ClasesGestionEscolar;

public class DetalleMatricula {
    private int idDetalleMatricula;
    private int idMatricula;
    private int idCurso;

    public DetalleMatricula(int idDetalleMatricula, int idMatricula, int idCurso) {
        this.idDetalleMatricula = idDetalleMatricula;
        this.idMatricula = idMatricula;
        this.idCurso = idCurso;
    }

    public int getIdDetalleMatricula() {
        return idDetalleMatricula;
    }

    public void setIdDetalleMatricula(int idDetalleMatricula) {
        this.idDetalleMatricula = idDetalleMatricula;
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


}
