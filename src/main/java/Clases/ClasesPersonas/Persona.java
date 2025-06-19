package Clases.ClasesPersonas;

import java.time.LocalDate;

public class Persona {
    private int idpersona;
    private int dnipersona;
    private String primernombre;
    private String segundonombre;
    private String apellidopaterno;
    private String apellidomaterno;
    private LocalDate fechanacimiento;
    private String genero;

    public Persona(int idpersona, int dnipersona, String primernombre, String segundonombre, String apellidopaterno, String apellidomaterno, LocalDate fechanacimiento, String genero) {
        this.idpersona = idpersona;
        this.dnipersona = dnipersona;
        this.primernombre = primernombre;
        this.segundonombre = segundonombre;
        this.apellidopaterno = apellidopaterno;
        this.apellidomaterno = apellidomaterno;
        this.fechanacimiento = fechanacimiento;
        this.genero = genero;
    }

    public int getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(int idpersona) {
        this.idpersona = idpersona;
    }

    public int getDnipersona() {
        return dnipersona;
    }

    public void setDnipersona(int dnipersona) {
        this.dnipersona = dnipersona;
    }

    public String getPrimernombre() {
        return primernombre;
    }

    public void setPrimernombre(String primernombre) {
        this.primernombre = primernombre;
    }

    public String getSegundonombre() {
        return segundonombre;
    }

    public void setSegundonombre(String segundonombre) {
        this.segundonombre = segundonombre;
    }

    public String getApellidopaterno() {
        return apellidopaterno;
    }

    public void setApellidopaterno(String apellidopaterno) {
        this.apellidopaterno = apellidopaterno;
    }

    public String getApellidomaterno() {
        return apellidomaterno;
    }

    public void setApellidomaterno(String apellidomaterno) {
        this.apellidomaterno = apellidomaterno;
    }

    public LocalDate getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(LocalDate fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "idpersona=" + idpersona +
                ", dnipersona=" + dnipersona +
                ", primernombre='" + primernombre + '\'' +
                ", segundonombre='" + segundonombre + '\'' +
                ", apellidopaterno='" + apellidopaterno + '\'' +
                ", apellidomaterno='" + apellidomaterno + '\'' +
                ", fechanacimiento=" + fechanacimiento +
                ", genero=" + genero +
                '}';
    }

    public Object[] Convertir(){
        Object[] persona = {this.dnipersona,this.primernombre,this.segundonombre,this.apellidopaterno,this.apellidomaterno,this.fechanacimiento,this.genero};
        return persona;
    }

}
