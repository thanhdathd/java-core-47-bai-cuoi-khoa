package main;

import static util.StringUtils.depart;
import static util.Utils.inputPage;
import static util.Utils.inThongBao;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;

import data.DataIO;
import model.Diem;
import model.MonHoc;

public class QuanLyMonHoc {
	public static void showListMonHoc(ArrayList<MonHoc> dsMh) {
		sortListMH(dsMh);
		int perPage = 50;
		int totalPage =  (int) Math.ceil(dsMh.size()/(double)perPage);
		int page = 1;
		int chon;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("\n------------------DANH SACH MON HOC------------------");
			System.out.format("--------------------trang %3d/%-3d------------------\n\n", page, totalPage);
			if(DataIO.suportAscii == true) {
				System.out.println("---------------------------------------------");
				System.out.println("|  Ma  |        Ten mon hoc         | he so |");
				System.out.println("---------------------------------------------");
			}else{
				System.out.println("┌──────┬────────────────────────────┬───────┐");
				System.out.println("│  Ma  │        Ten mon hoc         │ he so │");
				System.out.println("├──────┼────────────────────────────┼───────┤");
	    	}
			int start = page*perPage-perPage;
			int end = page*perPage;
			if(page == totalPage) end = dsMh.size()-1;
			for (int i = start; i <= end; i++) {
				System.out.println(dsMh.get(i).getInfo());
			}
			if(DataIO.suportAscii == true) {
				System.out.println("---------------------------------------------");
			}else {
				System.out.println("└──────┴────────────────────────────┴───────┘");
			}
			System.out.format("\n--------------------trang %3d/%-3d------------------\n", page, totalPage);
			System.out.format("\n\n%-30s%-30s%-30s\n","1. Xem trang tiep theo","3. Den trang cuoi","5. Xem trang cu the");
			System.out.format("%-30s%-30s%-30s\n","2. Tro lai trang truoc","4. Den trang dau tien","0. Tro ve menu truoc");
			System.out.println("\nChon:");
			chon = sc.nextInt();
			switch (chon) {
			case 1:
				if(!(page>=totalPage))page++;
				break;
			case 2:
				if(!(page<=1)) page--;
				break;
			case 3:
				page = totalPage;
				break;
			case 4:
				page = 1;
				break;
			case 5:
				page = inputPage(sc, totalPage);
				break;
			case 0:
				System.out.println("[tro ve]");
				break;
			default:
				System.out.println("Chon sai");
				break;
			}
		}while(chon != 0);
	}

	private static void sortListMH(ArrayList<MonHoc> dsMh) {
		Collections.sort(dsMh, (m1,m2)-> {
			Locale vn = new Locale("vn","vi");
			Collator col = Collator.getInstance(vn);
			return col.compare(m1.getName(), m2.getName());
		});
		
	}

	public static void deleteMonHoc(ArrayList<MonHoc> dsMh) {
		Scanner sc = new Scanner(System.in);
		String input;
		do {
			System.out.println("\nNhap ma mon hoc muon xoa:");
			System.out.println("Nhap ... de quay lai");
			System.out.println("Nhap:");
			input = sc.nextLine();
			if(input.equals("...")) continue;
			int ma = Integer.parseInt(input);
			MonHoc target = null;
			for (MonHoc m : dsMh) {
				if (m.getCode() == ma) {
					target = m;
					break;
				}
			}
			if (target != null) {
				boolean daHoc = false;
				for (Diem d : DataIO.dsDiem) {
					if (d.getMaMh().equals(target.getStringCode())) {
						daHoc = true;
						break;
					}
				}
				if (!daHoc) {
					System.out.println("Tim thay:");
					target.showInfo("border");
					System.out.println("\nBan co chac chan muon xoa(c/k)?");
					String select = sc.nextLine();
					if (select.equals("c")) {
						dsMh.remove(target);
						inThongBao("Xoa thanh cong!","border");
					}
				} else {
					System.out.println("Tim thay:");
					target.showInfo("border");
					inThongBao("Mon hoc nay da co sinh vien hoc, khong the xoa","border");
				}
			} else {
				System.out.println("\nKhong tim thay mon hoc");
			}
		}while(!input.equals("..."));
		
	}

	public static void editMonHoc(ArrayList<MonHoc> dsMh) {
		Scanner sc = new Scanner(System.in);
		System.out.println("\nNhap ma mon hoc muon sua:");
		String input;
		do {
			System.out.println("Nhap ... de quay lai");
			System.out.println("\nNhap:");
			input = sc.nextLine();
			if(input.equals("..."))continue;
			int ma = Integer.parseInt(input);
			MonHoc target = null;
			for (MonHoc m : dsMh) {
				if (m.getCode() == ma) {
					target = m;
					break;
				}
			}
			if (target != null) {
				System.out.println("Tim thay mon hoc:");
				target.showInfo("border");
				System.out.println("\nNhap lai thong tin mon hoc theo dang\t[ten mon hoc];[he so diem]");
				System.out.println("Neu khong muon sua muc nao thi bo trong muc do");
				System.out.println("Vi du:Toan cao cap;");
				System.out.println("\nNhap:");
				//sc.nextLine();
				String line = sc.nextLine();
				String part[] = depart(line);
				String name = part[0];
				String hs = part[1];
				if (!name.isEmpty()) {
					target.setName(name);
				}
				if (!hs.isEmpty()) {
					target.setHs(Float.parseFloat(hs));
				}
				System.out.println("\nSua thanh cong:");
				target.showInfo("border");
			} else {
				System.out.println("Khong tim thay mon hoc");
			}
		}while(!input.equals("..."));
		
	}

	public static void addMonHoc(ArrayList<MonHoc> dsMh) {
		Scanner sc = new Scanner(System.in);
		String tb = "Nhap thong tin mon hoc theo dang,[ten mon hoc];[he so],Vi du:Toan cao cap;3.0";
		inThongBao(tb, "border");
		String line;
		do {
			System.out.println("\nNhap ... de quay lai");
			System.out.println("Nhap:");
			line = sc.nextLine();
			if (line.equals("..."))
				continue;
			String part[] = depart(line);
			String name = part[0];
			String heso = part[1];
			MonHoc mh = new MonHoc(name, Float.parseFloat(heso));
			dsMh.add(mh);
			System.out.println("Them thanh cong:");
			mh.showInfo("border");
		} while (!line.equals("..."));
	}

	public static String getTenMh(String ma) {
		for (MonHoc m : DataIO.dsMonHoc) {
			if(m.getStringCode().equals(ma)) return m.getName();
		}
		return "";
	}

	public static float getHeso(String maMh) {
		for (MonHoc m : DataIO.dsMonHoc) {
			if(m.getStringCode().equals(maMh)) return m.getHs();
		}
		return 0;
	}
	// TODO tim kiem theo ma mon hoc
	public static void searchByMaMh(ArrayList<MonHoc> dsMh) {
		System.out.println("Nhap ma mon hoc:");
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		MonHoc target = null;
		for (MonHoc m : dsMh) {
			if(m.getStringCode().equals(line)) {
				target = m;
			}
		}
		if(target!=null) {
			System.out.println("Tim thay:");
			target.showInfo("border");
			
			QuanLyBangDiem.showBangDiemByMH(target);
			int chon;
			do {
				System.out.println("\n1. Xem chi tiet");
				System.out.println("0. tro ve menu truoc");
				chon = sc.nextInt();
				switch (chon) {
				case 1:
					QuanLyBangDiem.chitietBangDiemMH(target.getStringCode());
					break;
				case 0: 
					System.out.println("[Tro ve]");
					break;
				default:
					System.out.println("[Chon sai]");
					break;
				}
			}while(chon!=0);
		}else {
			System.out.println("Khong tim thay mon hoc["+line+"]");
		}
	}

}
