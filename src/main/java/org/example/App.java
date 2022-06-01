package org.example;

import org.example.obj.ItemListaCompra;
import org.example.obj.Prices;
import org.example.obj.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class App
{

    private static File input;

    private static final String URL_SEARCH_BASE = "https://soysuper.com/search?q=";
    private static final String URL_PRODUCT_BASE = "https://soysuper.com";

    public static void main( String[] args ) throws IOException {

        //Leemos por consola el elemento a buscar (para pruebas)
//        System.out.print("BUSCAR: ");
//        String query = sc.nextLine();

        ArrayList<String> listaCompra = new ArrayList<>();
        listaCompra.add("COCACOLA 2 LITROS");
        listaCompra.add("MAHOU 12 PACK");
        listaCompra.add("TOMATES");
        listaCompra.add("GEL DE BAÑO");
        listaCompra.add("PLÁTANOS DE CANARIAS");

        for (String s : listaCompra) {
            String href = getHrefItem(s);
            Product p = getProductAndPrices(href);
            System.out.println(s + " - " + p.getPricesArrayList().get(0));
        }
//        printProductConsole(p);
    }

    public static ArrayList<ItemListaCompra> getPreciosListaCompra(ArrayList<ItemListaCompra> arrayList) {
        ArrayList<ItemListaCompra> aux = new ArrayList<>();
        for (ItemListaCompra i: arrayList) {
            try {
                String href = getHrefItem(i.getNombre());
                Product p = getProductAndPrices(href);
                String precio = String.valueOf(p.getPricesArrayList().get(0).getPrecio());
                aux.add(new ItemListaCompra(i.getNombre(), i.getCantidad(), p.getPricesArrayList().get(0).getSupermercado(), precio, p.getItemDescription()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return aux;
    }

    private static Product getProductAndPrices(String href) throws IOException {

        Product product = new Product();
        //Obtenemos el precio por supermercado
        Document doc = getPricesDoc(href);

        ArrayList<Prices> pricesArrayList = new ArrayList<>();

//        Añadimos table que esta debajo de una clase '.superstable' y recorremos sus filas.

        Elements tabla = doc.select(".superstable > table");
        for (Element t : tabla) {
            Elements filas = t.select("tbody > tr");
            for (Element f : filas) {
                //<td> cuya clase empieza por 'price'
                double precio = parseDoubleControl(f.select("td[class^=price]").text());
                //obtenemos el 'title=' de <i>
                String supermercado = f.selectFirst("i").attr("title");
                pricesArrayList.add(new Prices(supermercado, precio));
//                System.out.println(f.selectFirst("i").attr("title"));
//                System.out.println(f.select("td[class^=price]").text());

            }
        }

        String nombreProducto = "null";
        if (doc.select(".withpagination > a").first() != null) {
            String nombreProductoToReplace = doc.select(".withpagination > a").first().text();
            if (doc.select("h1.withpagination").first() != null) {
                nombreProducto = doc.select("h1.withpagination").first().text();
                nombreProducto = nombreProducto.replaceFirst(nombreProductoToReplace, "").trim();
            }
        }


        String item = "null";
        if (doc.select("span.price > strong").first() != null) {
            String itemToReplace = doc.select("span.price > strong").first().text();
            if (doc.select("span.price").first() != null) {
                item = doc.select("span.price").first().text();
                item = item.replaceFirst(itemToReplace, "");
                item = item.replaceFirst("/", "");
            }
        }

        return new Product(item.trim(), nombreProducto, pricesArrayList);
    }

    private static String getHrefItem(String query) throws IOException {
        Document doc = getDoc(query);

        //Se filtra por enlaces a productos
        Elements products = doc.select("a[href^=/p/]");
        Element p = products.get(0);
        String pLink = p.attr("href");
//        System.out.println("[S] 1ST ITEM href " + pLink);
        return pLink;
    }

    private static double parseDoubleControl(String text) {
        String res;
        res = text.replace(" ", "").replace("€", "").replace(",", ".");

        if (text.length() != 0) {
            return Double.parseDouble(res);
        } else {
            return Double.NaN;
        }
    }

    private static Document getDoc(String query) throws IOException {
        String url = parseUrlQuery(query);
//        System.out.println("[S] FETCHING QUERY DOC " + url);
        return Jsoup.connect(url).get();
    }

    private static Document getPricesDoc(String query) throws IOException {
        String url = URL_PRODUCT_BASE + query;
//        System.out.println("[S] FETCHING PRODUCT DOC " + url);
        return Jsoup.connect(url).get();
    }

    private static String parseUrlQuery(String query) {
        //Metodo para pasar la query en un formato de enlace determinado,
        //transformandolo a UTF-8 para URL
        String qRev = "";
        try {
            qRev = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return URL_SEARCH_BASE + qRev;
    }
}
