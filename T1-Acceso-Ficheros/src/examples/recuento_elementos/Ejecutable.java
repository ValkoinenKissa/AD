package examples.recuento_elementos;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Ejecutable {
    public static void main(String[] args) {

        /*
        Se debe de pasar la ruta de un archivo como argumento, se puede hacer de forma sencilla editando
        la configuración de ejecución del IDE, si no se recibe el argumento por parámetro la ejecución finaliza
         */

        if (args.length == 0) {
            System.out.println("Debes especificar la ruta de un fichero como argumento al ejecutar el programa");
            System.exit(1);
        }

        String ruta = args[0];

        File f = new File(ruta);

        if (!f.exists()) {
            System.out.println("El archivo no existe");
            System.exit(1);
        }
        if (f.isDirectory()) {
            System.out.println("La ruta indicada es un directorio");
            System.exit(1);
        }


        try (FileReader fr = new FileReader(f)) {
            int a, e, i, o, u;
            a = e = i = o = u = 0;
            int c;

            while ((c = fr.read()) != -1) {
                char letra = (char) c;
                if (letra == 'a' || letra == 'A') {
                    a++;
                }
                if (letra == 'e' || letra == 'E') {
                    e++;
                }
                if (letra == 'i' || letra == 'I') {
                    i++;
                }
                if (letra == 'o' || letra == 'O') {
                    o++;
                }
                if (letra == 'u' || letra == 'U') {
                    u++;
                }
            }

            System.out.println("El número de aes es " + a);
            System.out.println("El número de es es " + e);
            System.out.println("El número de íes es " + i);
            System.out.println("El número de oes es " + o);
            System.out.println("El número de úes es " + u);

        } catch (IOException ex) {
            System.out.println("Error de entrada/salida");
        }
    }

}
