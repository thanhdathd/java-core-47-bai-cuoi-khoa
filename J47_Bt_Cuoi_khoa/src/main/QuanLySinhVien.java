package main;

import static util.StringUtils.depart;
import static util.StringUtils.getHodem;
import static util.StringUtils.getTen;
import static util.Utils.inputPage;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;

import data.DataIO;
import model.Diem;
import model.SinhVien;

public class QuanLySinhVien {
	
	// TODO xoa sinh vien chua hoc khoi danh sach
	public static void deleteSv(ArrayList<SinhVien> listSv) {
		System.out.println("\nNhap ma sinh vien muon xoa:");
		System.out.println("Nhap:");
		Scanner sc = new Scanner(System.in);
		String ma = sc.nextLine();
		SinhVien target = null;
		for (SinhVien s : listSv) {
			if (s.getMaSV().equals(ma)) {
				target = s;
				break;
			}
		}
		if (target != null) {
			boolean coDiem = false;
			for (Diem d : DataIO.dsDiem) {
				if (d.getMaSv().equals(target.getMaSV())) {
					coDiem = true;
					break;
				}
			}
			if (!coDiem) {
				System.out.println("Tim thay:");
				target.showInfo();
				System.out.println("\nBan co chac chan muon xoa(c/k)?");
				String select = sc.nextLine();
				if (select.equals("c")) {
					listSv.remove(target);
					System.out.println("Xoa thanh cong!");
				}
			} else {
				System.out.println("Tim thay:");
				target.showInfo();
				System.out.println("\nSinh vien nay da co diem, khong the xoa");
			}
		} else {
			System.out.println("\nKhong tim thay sinh vien");
		}
	}

	// TODO sua thong tin sinh vien
	public static void editSv(ArrayList<SinhVien> listSv) {
		System.out.println("\nNhap ma sinh vien muon sua");
		System.out.println("Nhap:");
		Scanner sc = new Scanner(System.in);
		String ma = sc.nextLine();
		SinhVien target = null;
		for (SinhVien s : listSv) {
			if (s.getMaSV().equals(ma)) {
				target = s;
				break;
			}
		}
		if (target != null) {
			System.out.println("Tim thay sinh vien:");
			target.showInfo();
			System.out.println("\nNhap lai thong tin sinh vien theo dang");
			System.out.println("[ho va ten];[ngay sinh(dd/MM/yyyy)];[gioi tinh(Nam/Nu)]");
			System.out.println("Neu khong muon sua muc nao thi bo trong muc do");
			System.out.println("Vi du:Nguyen Van A;;Nam");
			System.out.println("Nhap:");
			String line = sc.nextLine();
			String part[] = depart(line);
			System.out.println(Arrays.toString(part));
			String name = part[0];
			String date = part[1];
			String gender = part[2];
			if (!name.isEmpty()) {
				target.setHodem(getHodem(name));
				target.setTen(getTen(name));
			}
			if (!date.isEmpty()) {
				target.setNgaySinh(date);
			}
			if (!gender.isEmpty()) {
				target.setGioiTinh(gender);
			}
			System.out.println("\nSua thanh cong:");
			target.showInfo();
		} else {
			System.out.println("Khong tim thay sinh vien");
		}

	}

	public static void addSv(ArrayList<SinhVien> listSv) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Nhap thong tin sinh vien theo dang");
		System.out.println("[ho va ten];[ngay sinh(dd/MM/yyyy)];[gioi tinh(Nam/Nu)]");
		System.out.println("Vi du:Nguyen Van A;14/06/1996;Nam");
		String line;
		do {
			System.out.println("Nhap exit de quay lai");
			System.out.println("Nhap:");
			line = sc.nextLine();
			if (line.equals("exit"))
				continue;
			String part[] = line.split(";");
			String name = part[0];
			String date = part[1];
			String gender = part[2];
			String hodem = getHodem(name);
			String ten = getTen(name);
			SinhVien sv = new SinhVien(hodem, ten, date, gender);
			listSv.add(sv);
			System.out.println("Them thanh cong:");
			sv.showInfo();
		} while (!line.equals("exit"));
	}
	
	
	// TODO hien thi danh sach sv
		public static void showList(ArrayList<SinhVien> listSv) {
			sortListSV(listSv);
			int perPage = 50;
			int totalPage =  (int) Math.ceil(listSv.size()/(double)perPage);
			int page = 1;
			int chon;
			Scanner sc = new Scanner(System.in);
			do {
				System.out.println("\n----------------------------------------------------");
				System.out.format("--------------------trang %3d/%-3d------------------\n", page, totalPage);
				int start = page*perPage-perPage;
				int end = page*perPage;
				if(page == totalPage) end = listSv.size()-1;
				for (int i = start; i <= end; i++) {
					System.out.println(listSv.get(i).getInfo());
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

		private static void sortListSV(ArrayList<SinhVien> listSv) {
			Collections.sort(listSv, (s1,s2) -> {
				Locale vn = new Locale("vn", "vi");
				Collator col = Collator.getInstance(vn);
				return col.compare(s1.getTen(), s2.getTen());
			});
		}

		

}
