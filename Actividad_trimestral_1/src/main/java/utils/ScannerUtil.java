package utils;

import java.util.Scanner;

public class ScannerUtil {
    private static Scanner sc;

    public static Scanner getScanner(){
        if (sc==null){
            createScanner();
        }

        return sc;
    }

    private static void createScanner() {

        new Scanner(System.in);
    }
}
