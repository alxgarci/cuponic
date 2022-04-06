package org.example.obj;

public class Prices {

    private String supermercado;
    private double precio;

    public Prices(String supermercado, double precio) {
        this.supermercado = supermercado;
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "[SUPERMERCADO] '" + supermercado + "'" +
                ", [€] " + precio;
    }

    public Prices() {}

    public String getSupermercado() {
        return supermercado;
    }

    public void setSupermercado(String supermercado) {
        this.supermercado = supermercado;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
