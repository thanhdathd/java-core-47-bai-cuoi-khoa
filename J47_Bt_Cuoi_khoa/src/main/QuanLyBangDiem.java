package main;

import static util.StringUtils.depart;

import java.util.ArrayList;
import java.util.Scanner;

import data.DataIO;
import model.Diem;
import model.MonHoc;
import model.SinhVien;


public class QuanLyBangDiem {
	public static void addDiem(ArrayList<Diem> dsDiem) {
		Scanner sc = new Scanner(System.in);
		System.out.println("\nNhap diem theo dang: \t  [ma Sv];[ma mon hoc];diem");
		System.out.println("Vi du:SV00015;003;8.2");
		String line;
		do {
			System.out.println("\nNhap exit de quay lai");
			System.out.println("Nhap:");
			line = sc.nextLine();
			if (line.equals("exit"))
				continue;
			String part[] = depart(line);
			String masv = part[0];
			String mamh = part[1];
			String diem = part[2];
			boolean ok1 = validate(masv,"sv"); // true neu ma sv co trong ds sv
			boolean ok2 = validate(mamh,"mh"); // true neu ma mh co trong ds mh
			boolean conflict = checkConflict(masv, mamh); // true neu sv da co diem mon nay
			
			if(ok1&&ok2&&!conflict) {
				Diem d = new Diem(masv, mamh, diem);
				dsDiem.add(d);
				System.out.println("Them thanh cong:");
//				d.showInfo(); SV00015;019;8.2
				System.out.println("┌──────────────────────────────────────┐");
				System.out.format("│ %s %22s       │\n",masv,matchSV.getFullName());
				System.out.println("├──────────────────────────────────────┤");
				System.out.format("│ %-27s %s      │\n",matchMh.getName(),d.getDiem());
				System.out.println("└──────────────────────────────────────┘");
//				System.out.println("2500──");
//				System.out.println("2504┄┄");
//				System.out.println("2505┅┅");─────
				
			}
			if(!ok1) {
				System.out.println("\n[Loi]:Ma sinh vien khong ton tai:["+masv+"]");
			}
			if(!ok2) {
				System.out.println("\n[Loi]:Ma mon hoc khong ton tai: ["+mamh+"]");
			}
			if(conflict) {
				System.out.println("\n[Loi]:Sinh vien nay da co diem cua mon hoc nay");
			}
		} while (!line.equals("exit"));
	}

	private static boolean checkConflict(String masv, String mamh) {
		for (Diem d : DataIO.dsDiem) {
			if(d.getMaSv().equals(masv)&&
					d.getMaMh().equals(mamh))
				return true;
		}
		return false;
	}

	private static SinhVien matchSV;
	private static MonHoc matchMh;
	private static boolean validate(String code, String type) {
		if(type.equals("sv")) {
			for (SinhVien s : DataIO.listSV) {
				if(code.equals(s.getMaSV())) {
					matchSV = s;
					return true;
				}
			}
			return false;
		}else if(type.equals("mh")) {
			for (MonHoc m : DataIO.dsMonHoc) {
				if(code.equals(m.getStringCode())) {
					matchMh = m;
					return true;
				}
			}
			return false;
		}
		//wrong type
		return false;
	}
}
