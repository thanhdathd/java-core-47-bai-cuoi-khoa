package util;

import java.io.IOException;
import java.util.Scanner;

public class Utils {
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static void clrscr() {
		// Clears Screen in java
		try {
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			else
				Runtime.getRuntime().exec("clear");
		} catch (IOException | InterruptedException ex) {
		}
	}
	
	public static int inputPage(Scanner sc, int total) {
		int trang = 0;
		do {
			System.out.println("Nhap so trang:");
			trang = sc.nextInt();
			if(trang > total || trang < 1)
				System.out.println("\nSo trang khong hop le");
		}while(trang < 1 || trang > total);
		if(trang==0) trang = 1;
		return trang;
	}
}
