package util;

import java.io.IOException;
import java.util.Scanner;

import data.DataIO;

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

	public static void printMenu(int n) {
		if(n==1) {
			System.out.println("\n\n----------------------------------");
			System.out.format("|  %15s %14s|\n"," MENU","");
			System.out.println("|--------------------------------|");
			System.out.format("| %-30s |\n","1. Cap nhat danh sach");
			System.out.format("| %-30s |\n","2. Hien thi bang diem");
			System.out.format("| %-30s |\n","3. Tim kiem");
			System.out.format("| %-30s |\n","0. Thoat");
			System.out.println("----------------------------------\n\n");
		}else if(n==2) {
			System.out.println("\n\n------------------------------------");
			System.out.format("|  %18s %12s|\n"," Cap nhat danh sach","");
			System.out.println("|----------------------------------|");
			System.out.format("| %-32s |\n","1. Cap nhat danh sach sinh vien");
			System.out.format("| %-32s |\n","2. Cap nhat danh sach mon hoc");
			System.out.format("| %-32s |\n","3. Cap nhat bang diem");
			System.out.format("| %-32s |\n","0. Tro ve menu truoc");
			System.out.println("------------------------------------\n\n");
		}else if(n==3) {
			System.out.println("\n\n------------------------------------");
			System.out.format("|  %25s %6s|\n"," DANH SACH SINH VIEN ","");
			System.out.println("|----------------------------------|");
			System.out.format("| %-32s |\n","1. Them sinh vien");
			System.out.format("| %-32s |\n","2. Sua thong tin sv");
			System.out.format("| %-32s |\n","3. Xoa sinh vien");
			System.out.format("| %-32s |\n","4. Hien thi danh sach");
			System.out.format("| %-32s |\n","0. Tro ve menu truoc");
			System.out.println("------------------------------------\n\n");
		}else if(n==4) {
			System.out.println("\n\n------------------------------------");
			System.out.format("|  %25s %6s|\n"," DANH SACH MON HOC ","");
			System.out.println("|----------------------------------|");
			System.out.format("| %-32s |\n","1. Them mon hoc");
			System.out.format("| %-32s |\n","2. Sua mon hoc");
			System.out.format("| %-32s |\n","3. Xoa mon hoc");
			System.out.format("| %-32s |\n","4. Hien thi danh sach");
			System.out.format("| %-32s |\n","0. Tro ve menu truoc");
			System.out.println("------------------------------------\n\n");
		}else if(n==5) {
			System.out.println("\n\n------------------------------------");
			System.out.format("|  %-25s %6s|\n"," BANG DIEM ","");
			System.out.println("|----------------------------------|");
			System.out.format("| %-32s |\n","1. Them diem vao ds");
			System.out.format("| %-32s |\n","2. Sua diem trong ds");
			System.out.format("| %-32s |\n","3. Xoa diem trong ds");
			System.out.format("| %-32s |\n","4. Hien thi bang diem");
			System.out.format("| %-32s |\n","0. Tro ve menu truoc");
			System.out.println("------------------------------------\n\n");
		}
	}
	
	public static void inThongBao(String tb) {
		System.out.println(tb);
	}
	
	public static void inThongBao(String tb, String mode) {
		String[] part = tb.split(",");
		
		if(mode.equals("border")) {
			if(DataIO.suportAscii == false) {
				System.out.println("┌──────────────────────────────────────────┐");
				for (int i = 0; i < part.length; i++) {
					System.out.format("│   %-38s │\n",part[i].trim());
				}
				System.out.println("└──────────────────────────────────────────┘");
			}else {
				System.out.println("--------------------------------------------");
				for (int i = 0; i < part.length; i++) {
					System.out.format("|   %-38s |\n",part[i].trim());
				}
				System.out.println("--------------------------------------------");
			}
		}else {
			System.out.println(tb);
		}
	}
	
}
