package main;

import static util.StringUtils.depart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import static main.QuanLyMonHoc.getTenMh;

import data.DataIO;
import model.Diem;
import model.MonHoc;
import model.SinhVien;
import util.Utils;


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
				System.out.println("┌──────────────────────────────────────┐");
				System.out.format("│ %s %22s       │\n",masv,matchSV.getFullName());
				System.out.println("├──────────────────────────────────────┤");
				System.out.format("│ %-27s %s      │\n",matchMh.getName(),d.getDiem());
				System.out.println("└──────────────────────────────────────┘");
				
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
	

	public static void editDiem(ArrayList<Diem> dsDiem) {
		System.out.println("Nhap ma sinh vien:");
		String line;
		Scanner sc = new Scanner(System.in);
		line = sc.nextLine();
		line = line.trim();
		boolean ok = validate(line, "sv");
		if(ok) {
			ArrayList<Diem> bDiem = new ArrayList<>();
			for (Diem d : dsDiem) {
				if (d.getMaSv().equals(line)) {
					bDiem.add(d);
				}
			}
			System.out.println("\n┌──────────────────────────────────────────┐");
			System.out.format("│ %s  %27s     │\n",line,matchSV.getFullName());
			System.out.println("├──────────────────────────────────────────┤");
			for (Diem d : bDiem) {
				System.out.format("│ %s %27s  %6s  │\n",d.getMaMh(),getTenMh(d.getMaMh()),d.getDiem());
			}
			System.out.println("└──────────────────────────────────────────┘\n\n");
			
			if(bDiem.size() > 0) {
				System.out.println("Nhap diem muon sua theo dang:");
				System.out.println("[ma mon hoc];[diem]");
				System.out.println("Vi du:003;8.5");
			}else {
				System.out.println("Sinh vien chua co diem mon nao");
			}
			do {
				System.out.println("\nNhap ... de quay lai");
				System.out.println("Nhap:");	
				line = sc.nextLine();
				if(line.equals("..."))continue;
				String part[] = depart(line);
				if(!part[0].isEmpty()&&!part[1].isEmpty()) {
					boolean ok2 = checkMhCode(part[0], bDiem);
					Diem target = null;
					if(ok2){
						for(Diem d : dsDiem) {
							if(d.getMaSv().equals(matchSV.getMaSV())
									&&d.getMaMh().equals(part[0])) {
								target = d;
								break;
							}
								
						}
					}
					if(target!=null) {
						try {
							Float diem = Float.parseFloat(part[1]);
							if(diem>10||diem<0) throw new InputMismatchException("diem theo thang 10");
							target.setDiem(part[1]);
							System.out.println("Sua diem thanh cong:");
							System.out.println("┌──────────────────────────────────────┐");
							System.out.format("│ %s %22s       │\n",matchSV.getMaSV(),matchSV.getFullName());
							System.out.println("├──────────────────────────────────────┤");
							System.out.format("│ %s  %-27s %s │\n",part[0],getTenMh(part[0]),target.getDiem());
							System.out.println("└──────────────────────────────────────┘");
						}catch (InputMismatchException|NumberFormatException e) {
							System.out.println("Diem khong dung dinh dang:"+e.getMessage());
						}
					}else {
						if(!ok2)System.out.format("[Loi]:Khong tim thay mon hoc [%s]",part[0]);

					}
				}
			}while(!line.equals("..."));
			
		}else {
			System.out.format("[Loi]:Khong tim thay sinh vien [%s]",line);
		}
	}

	private static boolean checkMhCode(String code, ArrayList<Diem> bDiem) {
		for (Diem d : bDiem) {
			if(d.getMaMh().equals(code)) return true;
		}
		return false;
	}

	
	
	public static void deleteDiem(ArrayList<Diem> dsDiem) {
		System.out.println("Nhap ma sinh vien:");
		String line;
		Scanner sc = new Scanner(System.in);
		line = sc.nextLine();
		line = line.trim();
		boolean ok = validate(line, "sv");
		if(ok) {
			ArrayList<Diem> bDiem = new ArrayList<>();
			for (Diem d : dsDiem) {
				if (d.getMaSv().equals(line)) {
					bDiem.add(d);
				}
			}
			System.out.println("\n┌──────────────────────────────────────────┐");
			System.out.format("│ %s  %27s     │\n",line,matchSV.getFullName());
			System.out.println("├──────────────────────────────────────────┤");
			for (Diem d : bDiem) {
				System.out.format("│ %s %27s  %6s  │\n",d.getMaMh(),getTenMh(d.getMaMh()),d.getDiem());
			}
			System.out.println("└──────────────────────────────────────────┘");
			
			if(bDiem.size() > 0) {
				System.out.println("Nhap ma cua mon hoc muon xoa diem:");
				System.out.println("Vi du:003");
			}else {
				System.out.println("Sinh vien chua co diem mon nao");
			}
			//
			do {
				System.out.println("\nNhap ... de quay lai");
				System.out.println("Nhap:");	
				line = sc.nextLine();
				if(line.equals("..."))continue;
				
				if(!line.isEmpty()) {
					boolean ok2 = checkMhCode(line, bDiem);
					Diem target = null;
					if(ok2){
						for(Diem d : dsDiem) {
							if(d.getMaSv().equals(matchSV.getMaSV())
									&&d.getMaMh().equals(line)) {
								target = d;
								break;
							}
								
						}
					}
					if(target!=null) {
						System.out.println("Tim thay:");
						System.out.println("┌──────────────────────────────────────┐");
						System.out.format("│ %s %22s       │\n",matchSV.getMaSV(),matchSV.getFullName());
						System.out.println("├──────────────────────────────────────┤");
						System.out.format("│ %s  %-27s %s│\n",line,getTenMh(line),target.getDiem());
						System.out.println("└──────────────────────────────────────┘");
						System.out.println("Ban co chac chan muon xoa (c/k) ?");
						String answer = sc.nextLine();
						if(answer.equals("c")) {
							dsDiem.remove(target);
							Utils.inThongBao("Xoa thanh cong", "border");
						}
					}else {
						if(!ok2)System.out.format("[Loi]:Khong tim thay mon hoc [%s]",line);
					}
				}
			}while(!line.equals("..."));
			//
		}else {
			System.out.format("[Loi]:Khong tim thay sinh vien [%s]",line);
		}
	}

	
	public static void showBangDiem(ArrayList<Diem> dsDiem, String by) {
		
	}
}
