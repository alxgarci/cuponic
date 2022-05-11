package org.example;

import org.example.obj.Usuario;

import java.io.*;
import java.util.*;

public class mainMenu {

	private static final Scanner sc = new Scanner(System.in);

	public static final String DB_SEPARATOR = ";";
	public static final String DB_TYPEUSER_SEPARATOR = ">";
	public static final String TYPE_ADMIN = "ADM";
	public static final String TYPE_USER = "USER";
	public static final String TYPE_ADMIN_TAG = TYPE_ADMIN+"_TAG";
	public static final String TYPE_USER_TAG = TYPE_USER+"_TAG";
	private static final String DATABASES_CHARSET = "UTF-8";
	private static final String DB_USERS = "userDatabase";

	public static String LOGGED_USERNAME = "";
	public static String LOGGED_TYPE = "";

	public static void main(String[] args) {
		System.out.println("Bienvenido a CUPONIC...");
		printMiniLogo();
		System.out.println("[SYSTEM] Selecciona usuario\n" +
				"  1 - Usuario de la aplicacion\n"+
				"  2 - Administrador");

		Usuario usuarioActivo = logIn(readType());


		boolean continuarOpc = true;
		while (continuarOpc) {
			if (usuarioActivo.getType().equals(TYPE_USER)) {
				boolean continuar = true;
				while(continuar) {
					//TODO: probar en cmd el clear console
					clrscr();
					switch (menu_trabajador()) {
					case 1:
						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
						break;
					case 5:
						break;
					case 6:
						break;
					case 7:
						break;
					case 0:
						System.out.println("[SYSTEM] Saliendo de la aplicación...");
						continuar = false;
						continuarOpc = false;
						break;
					default:
						System.out.println("[ERROR] Valor no listado. Volviendo al menu...");
						break;
					}
				}

			} else if (usuarioActivo.getType().equals(TYPE_ADMIN)) {
				boolean continuar = true;
				while(continuar) {
					switch (menu_admin()) {    //Menu interactivo
					case 1:
						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
						break;
					case 5:
						break;
					case 6:
						break;
					case 7:
						break;
					case 0:
						System.out.println("[SYSTEM] Saliendo de la aplicación...");
						continuar = false;
						continuarOpc = false;
						break;
					default:
						System.out.println("[ERROR] Valor no listado. Volviendo al menu...");
						break;
					}
				}
			} else {
				System.out.println("[SYSTEM] Debes introducir un valor listado en el menu");
				continuarOpc = false;
			}
		}
    }

	private static String readType() {
		boolean datoNotOk = true;
		while (datoNotOk) {
			System.out.print(">>: ");
			if (sc.hasNextInt()) {
				datoNotOk = false;
			}
			else {
				sc.next(); // Vaciar la basura (no int) del teclado...
				System.out.println("[ERROR] Introduce un numero (INT)");
			}
		}
		int input = sc.nextInt();
		if (input == 1) {
			return TYPE_USER;
		} else if (input == 2){
			return TYPE_ADMIN;
		} else {
			return "";
		}
	}

	private static File createLocalFile(String dbName, String extension) {
        String dbFormed;
        if (extension.equals("")) {
            dbFormed = dbName + ".db";
        } else {
            dbFormed = dbName + "." + extension;
        }

        File dir = new File("tmp/");
        dir.mkdirs();
        File creator = new File(dir, dbFormed);
        try {
            creator.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String path = "tmp/" + dbFormed;

        return new File(path);
    }

	private static void userDBWrite(File dbUsers, Usuario usuario) {

        try {
            //Check if the line being written is already in the database
            //lines are stored in the 'set' and are compared later with the
            //one going to be written
            Set<String> set = new HashSet<>();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(dbUsers));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (set.contains(line))
                        continue;
                    set.add(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            FileOutputStream fos = new FileOutputStream(dbUsers, true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, DATABASES_CHARSET);
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter pw = new PrintWriter(bw, true);

			if (!set.contains(usuario.toString())) {
				pw.write(usuario.toString());
				pw.write("\n");
			}
            pw.close();
			System.out.println("[SYSTEM] Usuario '" + usuario.getUsername() + "' registrado correctamente");

        } catch (IOException e) {
            System.out.println("[ERROR] No se ha podido guardar el registro en '" + dbUsers.getPath() + "'");
            e.printStackTrace();
        }
    }

	private static ArrayList<Usuario> userDBRead(File dbUsers, String typeUser) {
        ArrayList<Usuario> usersArray = new ArrayList<>();
        Set<String> set = new HashSet<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dbUsers));
            String line;
            while ((line = reader.readLine()) != null) {
                if (set.contains(line))
                    continue;
                set.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		String dbType = "";
		if (typeUser.equals(TYPE_ADMIN)) {
			dbType = TYPE_ADMIN_TAG;
		} else if (typeUser.equals(TYPE_USER)) {
			dbType = TYPE_USER_TAG;
		}
        List<String> aux;

        for (String string : set) {
			if (string.contains(dbType)) {
				aux = Arrays.asList(string.replace(DB_TYPEUSER_SEPARATOR, "").split(DB_SEPARATOR));
				for (String a :
					 aux) {
					System.out.println(a);
				}
				usersArray.add(new Usuario(typeUser, aux.get(1), aux.get(2)));
			}
        }
        return usersArray;
    }

	private static Usuario logIn(String tipo) {
		Usuario user = new Usuario();
		String usuario;
		String pass;
		if (tipo.equals(TYPE_ADMIN)) {
			boolean cont = true;
			while (cont) {
				switch (readOpcion("[MENU] Selecciona opcion (" + TYPE_ADMIN + ")\n"+
						" 1 - Inicio sesion\n"+
						" 2 - Registrarse")){
				case 1:
					System.out.println("[SYSTEM] LogIn " + TYPE_ADMIN);
					usuario = readString("[SYSTEM] Introduce un usuario");
					pass = readString("[SYSTEM] Introduce password");

					boolean loginOk = checkUserAndPass(TYPE_ADMIN, new Usuario(TYPE_ADMIN, usuario, pass));
					if (loginOk) {
						user = new Usuario(TYPE_ADMIN, usuario, pass);
						cont = false;
						System.out.println("CONT IF LOGINOK =" + cont);
					} else {
						System.out.println("[SYSTEM] Usuario y/o password incorrectas");
						cont = true;
					}

					break;
				case 2:
					System.out.println("[SYSTEM] Registro " + TYPE_ADMIN);
					usuario = readString("[SYSTEM] Introduce un usuario");
					pass = readString("[SYSTEM] Introduce password");

					File f = createLocalFile(DB_USERS, "");
					userDBWrite(f, new Usuario(TYPE_ADMIN, usuario, pass));
					cont = true;
					break;
				}
			}
		} else if (tipo.equals(TYPE_USER)) {
			boolean cont = true;
			while (cont) {
				switch (readOpcion("[MENU] Selecciona opcion (" + TYPE_USER + ")\n"+
						" 1 - Inicio sesion\n"+
						" 2 - Registrarse")){
				case 1:
					System.out.println("[SYSTEM] LogIn " + TYPE_USER);
					usuario = readString("[SYSTEM] Introduce un usuario");
					pass = readString("[SYSTEM] Introduce password");

					boolean loginOk = checkUserAndPass(TYPE_USER, new Usuario(TYPE_USER, usuario, pass));
					if (loginOk) {
						user = new Usuario(TYPE_USER, usuario, pass);
						cont = false;
					} else {
						System.out.println("[SYSTEM] Usuario y/o password incorrectas");
						cont = true;
					}
					break;
				case 2:
					System.out.println("[SYSTEM] Registro " + TYPE_USER);
					usuario = readString("[SYSTEM] Introduce un usuario");
					pass = readString("[SYSTEM] Introduce password");

					File f = createLocalFile(DB_USERS, "");
					userDBWrite(f, new Usuario(TYPE_USER, usuario, pass));
					cont = true;
					break;
				}
			}
		}
		return user;
	}

	private static boolean checkUserAndPass(String type, Usuario u) {
		File f = createLocalFile(DB_USERS, "");
		ArrayList<Usuario> usersArray = userDBRead(f, type);
		return usersArray.contains(u);
	}

	private static String readString(String txt) {
		String returnTxt = "";
		if (txt.length() != 0) {
			System.out.println(txt);
		}
		boolean datoNotOk = true;
		while (datoNotOk) {
			System.out.print(">>: ");
			returnTxt = sc.nextLine();
			if (returnTxt.length() != 0) {
				datoNotOk = false;
			} else {
				System.out.println("[ERROR] Introduce un usuario valido");
			}
		}
		return returnTxt;
	}

	private static int readOpcion(String txt) {
		if (txt.length() != 0) {
			System.out.println(txt);
		}
		boolean datoNotOk = true;
		while (datoNotOk) {
			System.out.print(">>: ");
			if (sc.hasNextInt()) {
				datoNotOk = false;
			}
			else {
				sc.next(); // Vaciar la basura (no int) del teclado...
				System.out.println("[ERROR] Introduce un numero (INT)");
			}
		}
		int opcion = sc.nextInt();
		sc.nextLine();
		return opcion;
	}

	public static void clrscr(){
    //Limpiar pantalla en java
    try {
        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            Runtime.getRuntime().exec("clear");
    } catch (InterruptedException | IOException ex) {
		ex.printStackTrace();
	}

	}

	private static void printLogo() {
		System.out.println(
	"<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>\n" +
    "           ▄████▄													\n"+
    "         ██████████\n"+
    "       ██████████████         █████  ██  ██ ██████  █████  ██  ██ ██  █████\n"+
    "     █████        █████▄      ██  ██ ██  ██ ██  ██ ██   ██ ███ ██ ██ ██▌ ██\n"+
    "   █████    ████ ▄███████     ██     ██  ██ ██████ ██   ██ ██████ ██ ██▌   \n"+
    "  █████   ██████████  ████    ██     ██  ██ ██▀    ██   ██ ██ ███ ██ ██▌  \n"+
    "  █████    █████████▄▄████    ██  ██ ██▄▄██ ██     ██   ██ ██ ███ ██ ██▌ ██\n"+
    "   █████    ████ ▀███████▀    █████   ████  ██      █████  ██  ██ ██  █████ \n"+
    "     █████        █████ \n"+
    "       ██████████████         Un proyecto de Daniel, Alejandro, Adrián y\n"+
    "         ▐█████████           Mohamed para la UEM [2022]\n"+
    "            ▀███▀ \n" +
	"<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>\n"
		);
	}

	private static void printMiniLogo() {
		System.out.println(
    "           ▄████▄	         \n"+
    "         ██████████         \n"+
    "       ██████████████       \n"+
    "     █████        █████▄    \n"+
    "   █████    ████ ▄███████   \n"+
    "  █████   ██████████  ████  \n"+
    "  █████    █████████▄▄████  \n"+
    "   █████    ████ ▀███████▀  \n"+
    "     █████        █████     \n"+
    "       ██████████████       \n"+
    "         ▐█████████         \n"+
    "            ▀███▀           \n"
		);
	}

	//MENU DEL TRABAJADOR
	public static int menu_trabajador(){
		int opcion = 0;
		Scanner teclado = new Scanner(System.in);

		//Mostramos menu donde el usuario
		printLogo();
		System.out.print(
				"\n" +
						"[MENU] Menu principal TRABAJADOR\n" +
						"  1 - Registrar cliente\n" +
						"  2 - Alta usuario\n" +
						"  3 - Buscar promociones\n" +
						"  4 - guardar productos\n" +
						"  5 - eliminar productosma\n" +
						"  6 - Mostrar precios de usuario\n" +
						"  7 - Crear lista de la compra\n" +
						"  0 - Salir del programa\n" +
						"UEM" + "\n"

				);
		// recoge la opcion y asegura que se introduce un integer
		if (teclado.hasNextInt()) {
			opcion = teclado.nextInt();
		}
		else {
			teclado.next(); // Vaciar la basura (no int) del teclado...
		}
		//Se devuelve el valor de 'opcion' a main()
		return opcion;
	}

	//MENU DEL ADMINISTRADOR
	public static int menu_admin(){   //FunciÃ³n menu interactivo
		int opcion = 0;
		Scanner teclado = new Scanner(System.in);

		//Mostramos menu del administrador
		printLogo();
		System.out.print(
				"\n" +
						"[MENU] Menu principal ADMINISTRADOR\n" +
						"  1 - Registrar\n" +
						"  2 - Alta administrador\n" +
						"  3 - Buscar promociones\n" +
						"  4 - dar de alta promociones\n" +
						"  5 - Dar de baja promociones\n" +
						"  6 - Consultar log\n" +
						"  0 - Salir del programa\n" +
						"UEM" + "\n"

				);
		// recoge la opciÃ³n y asegura que se introduce un integer
		if (teclado.hasNextInt()) {
			opcion = teclado.nextInt();
		}
		else {
			teclado.next(); // Vaciar la basura (no int) del teclado...
		}


		//Se devuelve el valor de 'opcion' a main()
		return opcion;

	}
}
