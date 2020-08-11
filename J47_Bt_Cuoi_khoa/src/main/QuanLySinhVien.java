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
import util.StringUtils;

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
	public static void showList(ArrayList<SinhVien> listSv, int perPage, String title) {
		sortListSV(listSv);
		int totalPage = (int) Math.ceil(listSv.size() / (double) perPage);
		int page = 1;
		int chon;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("\n-----------------"+title+"-------------------");
			System.out.format("--------------------trang %3d/%-3d------------------\n", page, totalPage);
			if (DataIO.suportAscii == true) {
				System.out.println("----------------------------------------------------------------------");
				System.out.println("|   Ma    |  Ho dem               |  Ten      | ngay sinh  |Gioi tinh|");
				System.out.println("----------------------------------------------------------------------");
			} else {
				System.out.println("┌─────────┬───────────────────────┬───────────┬────────────┬─────────┐");
				System.out.println("│   Ma    │  Ho dem               │  Ten      │ ngay sinh  │Gioi tinh│");
				System.out.println("├─────────┼───────────────────────┼───────────┼────────────┼─────────┤");
			}
			int start = page * perPage - perPage;
			int end = page * perPage;
			if (page == totalPage)
				end = listSv.size() - 1;
			for (int i = start; i <= end; i++) {
				System.out.println(listSv.get(i).getInfo());
			}
			if (DataIO.suportAscii == true) {
				System.out.println("----------------------------------------------------------------------");
			} else {
				System.out.println("└─────────┴───────────────────────┴───────────┴────────────┴─────────┘");
			}
			System.out.format("\n--------------------trang %3d/%-3d------------------\n", page, totalPage);
			if(totalPage > 1) {
				System.out.format("\n\n%-30s%-30s%-30s\n", "1. Xem trang tiep theo", "3. Den trang cuoi",
						"5. Xem trang cu the");
				System.out.format("%-30s%-30s%-30s\n", "2. Tro lai trang truoc", "4. Den trang dau tien",
						"0. Tro ve menu truoc");
				System.out.println("6. Xem chi tiet bang diem");
			}else {
				System.out.println("6. Xem chi tiet bang diem");
				System.out.println("0. tro ve menu truoc");
			}
			System.out.println("\nChon:");
			chon = sc.nextInt();
			switch (chon) {
			case 1:
				if (!(page >= totalPage))
					page++;
				break;
			case 2:
				if (!(page <= 1))
					page--;
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
			case 6:
				xemChiTietBangDiem(listSv);
				break;
			case 0:
				System.out.println("[tro ve]");
				break;
			default:
				System.out.println("Chon sai");
				break;
			}
		} while (chon != 0);
	}

	private static void xemChiTietBangDiem(ArrayList<SinhVien> listSv) {
		Scanner sc = new Scanner(System.in);
		SinhVien target = null;
		String line = "";
		if(listSv.size() > 1) {
			System.out.println("Nhap ma sinh vien:");
			 line = sc.nextLine();
			for (SinhVien s : DataIO.listSV) {
				if(s.getMaSV().equals(line)) {
					target = s;
				}
			}
		}else {
			target = listSv.get(0); // neu ds chi co 1 sv duy nhat, auto chon sv do
		}
		
		if(target != null) {
			QuanLyBangDiem.showBangDiemBySV(target, true);
		}else {
			System.out.println("Khong tim thay sinh vien ["+line+"]");
		}
		
	}

	public static void sortListSV(ArrayList<SinhVien> listSv) {
		Collections.sort(listSv, (s1, s2) -> {
			Locale vn = new Locale("vn", "vi");
			Collator col = Collator.getInstance(vn);
			return col.compare(s1.getTen(), s2.getTen());
		});
	}

	public static void sortByMa(ArrayList<SinhVien> listSv) {
		Collections.sort(listSv, (s1, s2) -> {
			return s1.getMaSV().compareTo(s2.getMaSV());
		});

	}

	public static String getTenSV(String maSv) {
		for (SinhVien s : DataIO.listSV) {
			if (s.getMaSV().equals(maSv)) {
				return s.getFullName();
			}
		}
		return "";
	}

	// TODO tim kiem theo ma sinh vien
	public static void searchByMaSV(ArrayList<SinhVien> listSv) {
		System.out.println("Nhap ma sinh vien:");
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		SinhVien target = null;
		for (SinhVien s : listSv) {
			if (s.getMaSV().equals(line)) {
				target = s;
				break;
			}
		}
		if(target!=null) {
			QuanLyBangDiem.showBangDiemBySV(target, true);
		}else {
			System.out.println("Khong tim thay sinh vien ["+line+"]");
		}

	}
	
	// TODO tim kiem theo ten sinh vien
	public static void searchByName(ArrayList<SinhVien> listSv) {
		System.out.println("Nhap ten sinh vien:");
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		ArrayList<SinhVien> dsKetQua = new ArrayList<SinhVien>();
		for (SinhVien s : listSv) {
			String name = StringUtils.boDau(s.getFullName()).toLowerCase();
			String keyword = StringUtils.boDau(line).toLowerCase();
			if(s.getMaSV().startsWith("SV0013")) {
				System.out.println("name:"+name);
				System.out.println(" -> key:"+keyword);
			}
			if (name.contains(keyword)) {
				dsKetQua.add(s);
			}
		}
		if(dsKetQua.size() > 0) {
			showList(dsKetQua, 20, "KET QUA TIM KIEM");
		}else {
			System.out.println("Khong tim thay sinh vien ["+line+"]");
		}
	}

}
