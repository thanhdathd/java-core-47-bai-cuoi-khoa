package main;
import static data.DataIO.saveAllData;
import static main.QuanLyMonHoc.addMonHoc;
import static main.QuanLyMonHoc.deleteMonHoc;
import static main.QuanLyMonHoc.editMonHoc;
import static main.QuanLyMonHoc.getTenMh;
import static main.QuanLyMonHoc.showListMonHoc;
import static main.QuanLySinhVien.addSv;
import static main.QuanLySinhVien.deleteSv;
import static main.QuanLySinhVien.editSv;
import static main.QuanLySinhVien.showList;
import static main.QuanLySinhVien.sortByMa;
import static util.Utils.inputPage;
import static main.QuanLyBangDiem.addDiem;
import static main.QuanLyBangDiem.editDiem;
import static main.QuanLyBangDiem.deleteDiem;
import static main.QuanLyBangDiem.showBangDiem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;


import data.DataIO;
import model.Diem;
import model.MonHoc;
import model.SinhVien;
import util.StringUtils;
import util.Utils;

public class Main {

	public static void main(String[] args) {
		if(args.length != 0) {
			System.out.println(Arrays.toString(args));
			for (int i = 0; i < args.length; i++) {
				if(args[i].equals("-en")) {
					//DataIO.engMode = true;
					DataIO.setEngMode(true);
				}
				if(args[i].equals("-ascii")) DataIO.suportAscii = true;
			}
		}
		
		if(DataIO.suportAscii == false) {
			System.out.println("\n\n\t\t\t┌──────────────────────────────────────────┐");
			System.out.println("\t\t\t│          QUAN LY SINH VIEN 1.0.0         │");
			System.out.println("\t\t\t└──────────────────────────────────────────┘\n\n");
		}else {
			System.out.println("\n\n\t\t\t--------------------------------------------");
			System.out.println("\t\t\t|          QUAN LY SINH VIEN 1.0.0         |");
			System.out.println("\t\t\t--------------------------------------------\n\n");
		}
		ArrayList<SinhVien> listSv = DataIO.listSV;
		ArrayList<MonHoc> dsMh = DataIO.dsMonHoc;
		ArrayList<Diem> dsDiem = DataIO.dsDiem;
		Scanner sc = new Scanner(System.in);
		int select1;
		do {
			try {
				printMenu(1);
				System.out.println("Chon:");
				select1 = sc.nextInt();
				switch (select1) {
				case 1:
					capNhatDanhSach(listSv, dsMh, dsDiem);
					break;
				case 2:
					hienThiBangDiem(listSv,dsMh,dsDiem);
					break;
				case 3:
					timKiem(listSv,dsMh,dsDiem);
					break;
				case 0:break;
				default:
					System.out.println("Chon sai");
					break;
				}
			}catch (InputMismatchException e) {
				System.out.println("Nhap sai");
				select1 = 5;
				sc.nextLine();
				continue;
			}catch (Exception e) {
				System.out.println("Loi:"+e.getClass().getCanonicalName());
				select1 = 5;
				continue;
			}
		} while (select1 != 0);

		if (select1 == 0) {
			saveAllData(listSv, dsMh, dsDiem);
		}
	}

	//TODO III menu tim kiem
	private static void timKiem(ArrayList<SinhVien> listSv, ArrayList<MonHoc> dsMh, ArrayList<Diem> dsDiem) {
		Scanner sc = new Scanner(System.in);
		int select1;
		do {
			//clrscr();
			printMenu(7);
			System.out.println("Chon:");
			select1 = sc.nextInt();
			switch (select1) {
			case 1:
				QuanLySinhVien.searchByMaSV(listSv);
				break;
			case 2:
				QuanLySinhVien.searchByName(listSv);
				break;
			case 3:
				QuanLyMonHoc.searchByMaMh(dsMh);
				break;
			case 0: System.out.println("Tro ve");break;
			default:
				System.out.println("Chon sai");
				break;
			}
		} while (select1 != 0);
	}

	
	

	private static void printMenu(int n) {
		if(DataIO.suportAscii) {
			Utils.printMenu(n);
			return;
		}
		if(n==1) {
			System.out.println("\n\n┌────────────────────────────────┐");
			System.out.format("│  %15s %14s│\n"," MENU","");
			System.out.println("├────────────────────────────────┤");
			System.out.format("│ %-30s │\n","1. Cap nhat danh sach");
			System.out.format("│ %-30s │\n","2. Hien thi bang diem");
			System.out.format("│ %-30s │\n","3. Tim kiem");
			System.out.format("│ %-30s │\n","0. Thoat");
			System.out.println("└────────────────────────────────┘\n\n");
		}else if(n==2) {
			System.out.println("\n\n┌──────────────────────────────────┐");
			System.out.format("│  %18s %12s│\n"," Cap nhat danh sach","");
			System.out.println("├──────────────────────────────────┤");
			System.out.format("│ %-32s │\n","1. Cap nhat danh sach sinh vien");
			System.out.format("│ %-32s │\n","2. Cap nhat danh sach mon hoc");
			System.out.format("│ %-32s │\n","3. Cap nhat bang diem");
			System.out.format("│ %-32s │\n","0. Tro ve menu truoc");
			System.out.println("└──────────────────────────────────┘\n\n");
		}else if(n==3) {
			System.out.println("\n\n┌──────────────────────────────────┐");
			System.out.format("│  %25s %6s│\n"," DANH SACH SINH VIEN ","");
			System.out.println("├──────────────────────────────────┤");
			System.out.format("│ %-32s │\n","1. Them sinh vien");
			System.out.format("│ %-32s │\n","2. Sua thong tin sv");
			System.out.format("│ %-32s │\n","3. Xoa sinh vien");
			System.out.format("│ %-32s │\n","4. Hien thi danh sach");
			System.out.format("│ %-32s │\n","0. Tro ve menu truoc");
			System.out.println("└──────────────────────────────────┘\n\n");
		}else if(n==4) {
			System.out.println("\n\n┌──────────────────────────────────┐");
			System.out.format("│  %25s %6s│\n"," DANH SACH MON HOC ","");
			System.out.println("├──────────────────────────────────┤");
			System.out.format("│ %-32s │\n","1. Them mon hoc");
			System.out.format("│ %-32s │\n","2. Sua mon hoc");
			System.out.format("│ %-32s │\n","3. Xoa mon hoc");
			System.out.format("│ %-32s │\n","4. Hien thi danh sach");
			System.out.format("│ %-32s │\n","0. Tro ve menu truoc");
			System.out.println("└──────────────────────────────────┘\n\n");
		}else if(n==5) {
			System.out.println("\n\n┌──────────────────────────────────┐");
			System.out.format("│  %-25s %6s│\n"," BANG DIEM ","");
			System.out.println("├──────────────────────────────────┤");
			System.out.format("│ %-32s │\n","1. Them diem vao ds");
			System.out.format("│ %-32s │\n","2. Sua diem trong ds");
			System.out.format("│ %-32s │\n","3. Xoa diem trong ds");
			System.out.format("│ %-32s │\n","0. Tro ve menu truoc");
			System.out.println("└──────────────────────────────────┘\n\n");
		}else if(n==6) {
			System.out.println("\n\n┌──────────────────────────────────┐");
			System.out.format("│  %-25s %6s│\n"," BANG DIEM ","");
			System.out.println("├──────────────────────────────────┤");
			System.out.format("│ %-32s │\n","1. Bang diem theo ds sinh vien");
			System.out.format("│ %-32s │\n","2. Bang diem theo ds mon hoc");
			System.out.format("│ %-32s │\n","0. Tro ve menu truoc");
			System.out.println("└──────────────────────────────────┘\n\n");
		}else if(n==7) {
			System.out.println("\n\n┌──────────────────────────────────┐");
			System.out.format("│  %-25s %6s│\n"," TIM KIEM ","");
			System.out.println("├──────────────────────────────────┤");
			System.out.format("│ %-32s │\n","1. Tim kiem theo ma sinh vien");
			System.out.format("│ %-32s │\n","2. Tim kiem theo ten sinh vien");
			System.out.format("│ %-32s │\n","3. Tim kiem ma mon hoc");
			System.out.format("│ %-32s │\n","0. Tro ve menu truoc");
			System.out.println("└──────────────────────────────────┘\n\n");
		}
	}
	
	
	// TODO I Cap nhat danh sach
	private static void capNhatDanhSach(ArrayList<SinhVien> listSv,
			ArrayList<MonHoc> dsMh, ArrayList<Diem> dsDiem) {
		Scanner sc = new Scanner(System.in);
		int select1;
		do {
			//clrscr();
			printMenu(2);
			System.out.println("Chon:");
			select1 = sc.nextInt();
			switch (select1) {
			case 1:
				updateListSV(listSv);
				break;
			case 2:
				updateDsMonHoc(dsMh);
				break;
			case 3:
				updateDsDiem(dsDiem);
				break;
			case 0: System.out.println("Tro ve");break;
			default:
				System.out.println("Chon sai");
				break;
			}
		} while (select1 != 0);
		
	}

	private static void updateDsDiem(ArrayList<Diem> dsDiem) {
		Scanner sc = new Scanner(System.in);
		int chon;
		do {
			printMenu(5);
			System.out.println("Chon:");
			chon = sc.nextInt();
			switch (chon) {
			case 1:
				addDiem(dsDiem);
				break;
			case 2:
				editDiem(dsDiem);
				break;
			case 3:
				deleteDiem(dsDiem);
				break;
			case 0: System.out.println("[tro ve]");break;
			default:
				System.out.println("Chon sai");
				break;
			}
		}while(chon!=0);
	}




	//TODO II hien thi bang diem
	private static void hienThiBangDiem(ArrayList<SinhVien> listSv, ArrayList<MonHoc> dsMh, ArrayList<Diem> dsDiem) {
		Scanner sc = new Scanner(System.in);
		int chon;
		do {
			printMenu(6);
			System.out.println("Chon:");
			chon = sc.nextInt();
			switch (chon) {
			case 1:
				QuanLyBangDiem.dsDiemBySV(listSv, dsMh, dsDiem);
				break;
			case 2:
				QuanLyBangDiem.dsDiemByMonHoc(listSv, dsMh, dsDiem);
				break;
			case 0: System.out.println("[tro ve]");break;
			default:
				System.out.println("Chon sai");
				break;
			}
		}while(chon!=0);
		
	}

	

	private static void updateListSV(ArrayList<SinhVien> listSv) {
		Scanner sc = new Scanner(System.in);
		int chon;
		do {
			printMenu(3);
			System.out.println("Chon:");
			chon = sc.nextInt();
			switch (chon) {
			case 1:// them
				addSv(listSv);
				break;
			case 2:// sua
				editSv(listSv);
				break;
			case 3://xoa
				deleteSv(listSv);
				break;
			case 4:// hien ds
				showList(listSv, 50, "DANH SACH SINH VIEN");
				break;
			case 0: System.out.println("[Tro ve]");break;
			default:
				System.out.println("Chon sai");
				break;
			}
		} while (chon != 0);
		
	}

	// TODO Cap nhat danh sach mon hoc
	private static void updateDsMonHoc(ArrayList<MonHoc> dsMh) {
		Scanner sc = new Scanner(System.in);
		int chon;
		do {
			printMenu(4);
			System.out.println("Chon:");
			chon = sc.nextInt();
			switch (chon) {
			case 1:
				addMonHoc(dsMh);
				break;
			case 2:
				editMonHoc(dsMh);
				break;
			case 3:
				deleteMonHoc(dsMh);
				break;
			case 4:
				showListMonHoc(dsMh);
				break;
			case 0: System.out.println("[tro ve]");break;
			default:
				System.out.println("Chon sai");
				break;
			}
		}while(chon!=0);
	}

	
	
	

}
