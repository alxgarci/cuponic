package org.example.obj;

public class ItemListaCompra {
    private String nombre;
    private String cantidad;
    private String mejorSuper;
    private String mejorPrecio;
    private String cantidadMejorPrecio;

    public ItemListaCompra(String nombre, String cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public ItemListaCompra(String nombre, String cantidad, String mejorSuper, String mejorPrecio, String cantidadMejorPrecio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.mejorSuper = mejorSuper;
        this.mejorPrecio = mejorPrecio;
        this.cantidadMejorPrecio = cantidadMejorPrecio;
    }

    @Override
    public String toString() {
        return "  [ITEM] " + nombre + " - CANTIDAD: " + cantidad +
                " [MEJOR PRECIO] " + mejorSuper + " a " + mejorPrecio + "€ " + cantidadMejorPrecio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
