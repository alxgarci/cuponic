package org.example.interfaces;

import org.example.mainMenu;
import org.example.obj.Promo;
import org.example.tools.WriteToLog;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Admin {

    private static final String PROMO_DATABASE_CHARSET = "UTF-8";
    public static final String PROMO_DATABASE_SEPARATOR = ";";
    public static final String PROMO_TYPE_QR = "QR";
    public static final String PROMO_TYPE_CODE = "CODE";

    public static void buscarPromociones() {

    }
    public static void altaPromociones() {
        File f = mainMenu.createLocalFile("promo_database","csv");

        String type = mainMenu.readString("[I] Introduce el tipo de promo (QR/CODIGO)").trim().toUpperCase();
        type = (type.equals(PROMO_TYPE_QR))? PROMO_TYPE_QR : PROMO_TYPE_CODE;

        String supermercado = mainMenu.readString("[I] Introduce el supermercado");
        String codigo = mainMenu.readString("[I] Introduce el codigo de la promocion");
        String descuento = mainMenu.readString("[I] Introduce el descuento");
        String fechaCaducidad = mainMenu.readString("[I] Introduce fecha de caducidad");
        Promo p = new Promo(type, supermercado, codigo, descuento, fechaCaducidad);

        promoDBWrite(f, p);

    }
    public static void bajaPromociones() {
        File f = mainMenu.createLocalFile("promo_database","csv");

        String codigo = mainMenu.readString("[I] Introduce el codigo de la promocion a eliminar");
        System.out.println(promoDBRemove(f, codigo));


    }
    public static void consultarLog() {
        File f = WriteToLog.getFile();
        System.out.println("[SYSTEM] Imprimiendo LOG " + f.getAbsolutePath());

        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            int c = 0;
            while ((line = reader.readLine()) != null) {
                System.out.println("Linea-" + c + "->" + line);
                c++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[SYSTEM] Pulsa ENTER para continuar");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();

    }

    private static void promoDBWrite(File dbCsv, Promo promo) {

        try {
            //Check if the line being written is already in the database
            //lines are stored in the 'set' and are compared later with the
            //one going to be written
            Set<String> set = new HashSet<>();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(dbCsv));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (set.contains(line))
                        continue;
                    set.add(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            FileOutputStream fos = new FileOutputStream(dbCsv, true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, PROMO_DATABASE_CHARSET);
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter pw = new PrintWriter(bw, true);

			if (!set.contains(promo.toStringDBStore())) {
				pw.write(promo.toStringDBStore());
				pw.write("\n");
			}
            pw.close();
            WriteToLog.writeLogFile("[SYSTEM] Promocion '" + promo.getPromoCode() + "' registrada correctamente");
			System.out.println("[SYSTEM] Promocion para '" + promo.getSupermercado() + "' registrada correctamente");

        } catch (IOException e) {
            System.out.println("[ERROR] No se ha podido guardar el registro en '" + dbCsv.getPath() + "'");
            WriteToLog.writeLogFile("[ERROR] No se ha podido guardar el registro en '" + dbCsv.getPath() + "'");
            e.printStackTrace();
        }
    }

    private static String promoDBRemove(File dbCsv, String codigo) {

        try {
            if (!dbCsv.isFile()) {
                return "[ERROR] No existe el fichero";
            }

            File tempFile = new File(dbCsv.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new FileReader(dbCsv.getPath()));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            String line = null;

            while ((line = br.readLine()) != null) {
                if (!line.trim().contains(codigo)) {
                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();

            if (!dbCsv.delete()) {
                return "[ERROR] No se ha podido eliminar " + dbCsv.getPath();
            }

            if (!tempFile.renameTo(dbCsv))
                return "[ERROR] No se ha podido renombrar el nuevo fichero";
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        WriteToLog.writeLogFile("[SYSTEM] Se ha eliminado la promocion " + codigo + " correctamente");
        return "[SYSTEM] Se ha eliminado la promocion correctamente";


    }
}
