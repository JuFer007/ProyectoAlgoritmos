package Clases.ClasesFinanzas;

import java.time.LocalDate;

public class Pago {
    private int idPago;
    private LocalDate fechaPago;
    private String estadoPago;
    private double Monto;
    private String metodoPago;

    public Pago(int idPago, LocalDate fechaPago, String estadoPago, double monto, String metodoPago) {
        this.idPago = idPago;
        this.fechaPago = fechaPago;
        this.estadoPago = estadoPago;
        Monto = monto;
        this.metodoPago = metodoPago;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public double getMonto() {
        return Monto;
    }

    public void setMonto(double monto) {
        Monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "idPago=" + idPago +
                ", fechaPago=" + fechaPago +
                ", estadoPago='" + estadoPago + '\'' +
                ", Monto=" + Monto +
                ", metodoPago='" + metodoPago + '\'' +
                '}';
    }

    public Object[] convertir (){
        Object[] pago={this.idPago,this.fechaPago,this.estadoPago,this.Monto};
        return pago;
    }
}
