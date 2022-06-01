package org.example.obj;

import org.example.interfaces.Admin;

public class Promo {

    private String type;
    private String supermercado;
    private String promoCode;
    private String descuento;
    private String fechaCaducidad;

    public Promo(String type, String supermercado, String promoCode, String descuento, String fechaCaducidad) {
        this.type = type;
        this.supermercado = supermercado;
        this.promoCode = promoCode;
        this.descuento = descuento;
        this.fechaCaducidad = fechaCaducidad;
    }

    public Promo() {

    }

    public String getType(){
        return type;
    }

    public String getPromoCode(){
        return promoCode;
    }

    public String getSupermercado() {
        return supermercado;
    }

    @Override
    public String toString() {
        return "[PROMO] Supermercado: '" + supermercado + '\'' +
                ", Descuento: '" + descuento + '\'' + " // Codigo: " + promoCode;
    }

    public String toStringAdmin() {
        return "[PROMO](" + type + ") Supermercado: '" + supermercado + '\'' +
                ", Descuento: '" + descuento + '\'';
    }

    public String toStringDBStore() {
        return type + Admin.PROMO_DATABASE_SEPARATOR + supermercado + Admin.PROMO_DATABASE_SEPARATOR
                + promoCode + Admin.PROMO_DATABASE_SEPARATOR + descuento + Admin.PROMO_DATABASE_SEPARATOR + fechaCaducidad;
    }


}
