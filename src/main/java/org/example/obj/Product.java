package org.example.obj;

import java.util.ArrayList;

public class Product {

    private String itemDescription;
    private String nombre;
    private ArrayList<Prices> pricesArrayList;

    @Override
    public String toString() {
        return "[PRODUCTO] " +
                " '" + nombre + "'" +
                ", [CANTIDAD] '" + itemDescription + "'";
    }

    public Product(String itemDescription, String nombre, ArrayList<Prices> pricesArrayList) {
        this.itemDescription = itemDescription;
        this.nombre = nombre;
        this.pricesArrayList = pricesArrayList;
    }

    public Product() {}

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Prices> getPricesArrayList() {
        return pricesArrayList;
    }

    public void setPricesArrayList(ArrayList<Prices> pricesArrayList) {
        this.pricesArrayList = pricesArrayList;
    }
}
