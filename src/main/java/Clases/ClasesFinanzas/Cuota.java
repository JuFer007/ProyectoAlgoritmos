package Clases.ClasesFinanzas;

import java.time.LocalDate;

public class Cuota {
    private int idCuota;
    private int idMatricula;
    private int idPago;
    private String concepto;
    private int numeroCuota;
    private double montoCuota;
    private LocalDate fechaVencimiento;

    public Cuota(int idCuota, int idMatricula, int idPago, String concepto, int numeroCuota, double montoCuota, LocalDate fechaVencimiento) {
        this.idCuota = idCuota;
        this.idMatricula = idMatricula;
        this.idPago = idPago;
        this.concepto = concepto;
        this.numeroCuota = numeroCuota;
        this.montoCuota = montoCuota;
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getIdCuota() {
        return idCuota;
    }

    public void setIdCuota(int idCuota) {
        this.idCuota = idCuota;
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(int numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    public double getMontoCuota() {
        return montoCuota;
    }

    public void setMontoCuota(double montoCuota) {
        this.montoCuota = montoCuota;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public String toString() {
        return "Cuota{" +
                "idCuota=" + idCuota +
                ", idMatricula=" + idMatricula +
                ", idPago=" + idPago +
                ", concepto='" + concepto + '\'' +
                ", numeroCuota=" + numeroCuota +
                ", montoCuota=" + montoCuota +
                ", fechaVencimiento=" + fechaVencimiento +
                '}';
    }

    private Object[] convertir(){
        Object[] cuota = {this.idCuota,this.concepto,this.numeroCuota,this.montoCuota,this.fechaVencimiento};
        return cuota;
    }
}
