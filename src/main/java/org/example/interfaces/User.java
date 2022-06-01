package org.example.interfaces;

import com.google.zxing.WriterException;
import io.github.shashankn.qrterminal.QRCode;
import org.example.mainMenu;
import org.example.obj.Product;
import org.example.obj.Promo;
import org.example.tools.WriteToLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class User {

    private static ArrayList<Product> products;

    public static void buscarPromociones() {
        String query = mainMenu.readString("[I] Introduce tu busqueda");
        ArrayList<Promo> promos = getPromosMatching(mainMenu.createLocalFile("promo_database","csv"), query);
        System.out.println("[SYS] Imprimiendo promociones encontradas");
        for (Promo p : promos) {
            System.out.println(" " + p.toString());
            if (p.getType().equals(Admin.PROMO_TYPE_QR)) {
                System.out.println(" [SYS] Codigo QR generado");
                qrPrinter(p.getPromoCode());
            } else if (p.getType().equals(Admin.PROMO_TYPE_CODE)) {
                System.out.println(" [SYS] Codigo de promocion: " + p.getPromoCode());
            }
        }

    }

    public static void guardarProductos() {
        String input = "";
        while (!input.toUpperCase().equals("S")) {
            //TODO: CREAR PRODUCTOS
        }

    }

    public static void eliminarProductos() {

    }

    public static void verListaDeLaCompra() {
        //TODO CHECK PRODUCTOS CON PRECIOS EN SUPERMERCADOS
    }

    private static void qrPrinter(String code) {
        try {
            QRCode.from(code).generateHalfBlock();
        } catch (WriterException e) {
            e.printStackTrace();
            WriteToLog.writeLogFile("[ERROR] Creando QR con codigo " + code);
        }
    }

    private static ArrayList<Promo> getPromosMatching(File dbPromos, String promo) {
        ArrayList<Promo> promosArray = new ArrayList<>();
        Set<String> set = new HashSet<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dbPromos));
            String line;
            while ((line = reader.readLine()) != null) {
                if (set.contains(line))
                    continue;
                set.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> aux;

        for (String string : set) {
			if (string.contains(promo)) {
				aux = Arrays.asList(string.split(Admin.PROMO_DATABASE_SEPARATOR));
				for (String a :
					 aux) {
					System.out.println(a);
				}
				promosArray.add(new Promo(aux.get(0), aux.get(1), aux.get(2), aux.get(3), aux.get(4)));
			}
        }
        return promosArray;
    }
}
