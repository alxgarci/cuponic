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

    public String getSupermercado() {
        return supermercado;
    }

    @Override
    public String toString() {
        return "Promo{" +
                "type='" + type + '\'' +
                ", supermercado='" + supermercado + '\'' +
                ", promoCode='" + promoCode + '\'' +
                ", descuento='" + descuento + '\'' +
                '}';
    }

    public String toStringDBStore() {
        return type + Admin.PROMO_DATABASE_SEPARATOR + supermercado + Admin.PROMO_DATABASE_SEPARATOR
                + promoCode + Admin.PROMO_DATABASE_SEPARATOR + descuento + Admin.PROMO_DATABASE_SEPARATOR + fechaCaducidad;
    }


}