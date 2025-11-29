package utils;

import java.util.Scanner;

public class ScannerUtil {
    private static Scanner sc;

    private ScannerUtil(){

    }

    public static Scanner getScanner(){
        if (sc == null){
            sc = new Scanner(System.in);
        }

        return sc;
    }
}
