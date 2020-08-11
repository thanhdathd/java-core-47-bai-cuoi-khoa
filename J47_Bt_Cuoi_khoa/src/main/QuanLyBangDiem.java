package main;

import static util.StringUtils.depart;
import static util.Utils.inputPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import static main.QuanLyMonHoc.getTenMh;
import static main.QuanLyMonHoc.getHeso;
import static main.QuanLySinhVien.sortByMa;

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
			ArrayList<Diem> bDiem = showBangDiemBySV(matchSV, false);
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

	/**
	 * @param matchSV 
	 * @return 
	 */
	//TODO hien thi bang diem cua mot sinh vien
	public static ArrayList<Diem> showBangDiemBySV(SinhVien matchSV, boolean showDetail) {
		String maSV = matchSV.getMaSV();
		ArrayList<Diem> bDiem = new ArrayList<>();
		
		for (Diem d : DataIO.dsDiem) {
			if (d.getMaSv().equals(maSV)) {
				bDiem.add(d);
			}
		}
		if(DataIO.suportAscii == true) {
			System.out.println("\n--------------------------------------------");
			System.out.format("| %s  %30s  |\n",maSV,matchSV.getFullName());
			if(showDetail) {
				System.out.format("| %-30s     %4s  |\n",matchSV.getNgaySinh(), matchSV.getGioiTinh());
				System.out.println("|                                          |");
			}
			System.out.format("| %-30s     %3.2f  |\n","DTK:", getDiemTK(bDiem));
			System.out.println("--------------------------------------------");
			for (Diem d : bDiem) {
				System.out.format("| %s %27s  %6s  |\n",d.getMaMh(),getTenMh(d.getMaMh()),d.getDiem());
			}
			if(bDiem.size() == 0) System.out.format("| %39s  |\n","Chua co diem");
			System.out.println("--------------------------------------------\n");
		}else {
			System.out.println("\n┌──────────────────────────────────────────┐");
			System.out.format("│ %s  %30s  │\n",maSV,matchSV.getFullName());
			System.out.format("│ %-30s     %3.2f  │\n","DTK:", getDiemTK(bDiem));
			System.out.println("├──────────────────────────────────────────┤");
			for (Diem d : bDiem) {
				System.out.format("│ %s %27s  %6s  │\n",d.getMaMh(),getTenMh(d.getMaMh()),d.getDiem());
			}
			if(bDiem.size() == 0) System.out.format("│ %39s  │\n","Chua co diem");
			System.out.println("└──────────────────────────────────────────┘\n");
		}
		return bDiem;
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
		// FIXME add something
	}
	
	public static float getDiemTK(ArrayList<Diem> bDiem) {
		float tongDiem = 0, tongSoMon = 0;
		if(bDiem.size() == 0) return 0;
		for (Diem d : bDiem) {
			tongDiem+=Float.parseFloat(d.getDiem())*QuanLyMonHoc.getHeso(d.getMaMh());
			tongSoMon += QuanLyMonHoc.getHeso(d.getMaMh()); 
		}
		return tongDiem/tongSoMon;
	}

	
	// TODO hien thi bang diem theo mon hoc
	public static void dsDiemByMonHoc(ArrayList<SinhVien> listSv,
			ArrayList<MonHoc> dsMh, ArrayList<Diem> dsDiem) {
		int perPage = 10;
		int totalPage =  (int) Math.ceil(dsMh.size()/(double)perPage);
		int page = 1;
		int chon;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("\n-----------------DANH SACH MON HOC-------------------");
			System.out.format("--------------------trang %3d/%-3d------------------\n", page, totalPage);
			int start = page*perPage-perPage;
			int end = page*perPage;
			if(page == totalPage) end = dsMh.size()-1;
			for (int i = start; i <= end; i++) {
				MonHoc match = dsMh.get(i);
				QuanLyBangDiem.showBangDiemByMH(match);
			}
			System.out.format("\n--------------------trang %3d/%-3d------------------\n", page, totalPage);
			System.out.format("\n\n%-30s%-30s%-30s\n","1. Xem trang tiep theo","3. Den trang cuoi","5. Xem trang cu the");
			System.out.format("%-30s%-30s%-30s\n","2. Tro lai trang truoc","4. Den trang dau tien","6. Xem chi tiet mon hoc");
			System.out.println("0. Tro ve menu truoc");
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
			case 6:
				chitietBangDiemMH(null);
				break;
			case 0:
				System.out.println("[tro ve]");
				break;
			default:
				System.out.println("Chon sai");
				break;
			}
		}while(chon!=0);
	}

	
	// TODO hien thi bang diem chi tiet cua mot mon hoc
	public static void chitietBangDiemMH(String mMh) {
		Scanner sc = new Scanner(System.in);
		if(mMh == null) {
			System.out.println("Nhap ma mon hoc:");
			mMh = sc.nextLine();
		}
		String name = QuanLyMonHoc.getTenMh(mMh);
		ArrayList<Diem> bDiem = new ArrayList<>();
		for (Diem d : DataIO.dsDiem) {
			if (d.getMaMh().equals(mMh)) {
				bDiem.add(d);
			}
		}
		QuanLyBangDiem.sortBy(bDiem, "diem");
		String line;
		int perPage = 30;
		int totalPage =  (int) Math.ceil(bDiem.size()/(double)perPage);
		int page = 1;
		int chon;
		do {
			if(DataIO.suportAscii) {
				System.out.println("\n-----------------------------------------------");
				System.out.format("| %s  %37s  |\n",mMh,getTenMh(mMh));
				System.out.format("| %s  %34s  |\n","He so:",getHeso(mMh));
				System.out.println("|                                             |");
				System.out.format("| %-33s     %3.2f  |\n","Diem trung binh mon hoc:", QuanLyBangDiem.getDiemTBMH(bDiem));
				System.out.println("-----------------------------------------------");
			}else {
				System.out.println("\n┌─────────────────────────────────────────────┐");
				System.out.format("│ %s  %37s  │\n",mMh,getTenMh(mMh));
				System.out.println("│                                             │");
				System.out.format("│ %-33s     %3.2f  │\n","Diem trung binh mon hoc:", QuanLyBangDiem.getDiemTBMH(bDiem));
				System.out.println("├─────────────────────────────────────────────┤");	
			}
			
			
			String v = DataIO.suportAscii ? "|" : "│"; //vertical line
			if(bDiem.size() > 0) {
				int start = page*perPage-perPage;
				int end = page*perPage;
				if(page == totalPage) end = bDiem.size()-1;
				for (int i = start; i < end; i++) {
					Diem d = bDiem.get(i);
					System.out.format(v+" %s %26s  %6s  "+v+"\n",d.getMaSv(),QuanLySinhVien.getTenSV(d.getMaSv()),d.getDiem());
				}
				System.out.println(v+"                                             "+v);
				String section = String.format("Tu %d den %d tren tong so %d", start+1, end, bDiem.size());
				System.out.format(v+" %42s  "+v+"\n", section);
			}else
				System.out.format(v+" %42s  "+v+"\n","Chua co diem");
			if(DataIO.suportAscii) {
				System.out.println("-----------------------------------------------\n");
			}else {
				System.out.println("└─────────────────────────────────────────────┘\n");
			}
			
			
			System.out.format("\n--------------------trang %3d/%-3d------------------\n", page, totalPage);
			System.out.format("\n\n%-30s%-30s%-30s\n","1. Xem trang tiep theo","3. Den trang cuoi","5. Xem trang cu the");
			System.out.format("%-30s%-30s%-30s\n","2. Tro lai trang truoc","4. Den trang dau tien","0. Tro ve menu truoc");
			
			System.out.println("Nhap:");
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
		}while(chon!=0);
	}

	// TODO hien thi bang diem theo ds sinh vien
	public static void dsDiemBySV(ArrayList<SinhVien> listSv, ArrayList<MonHoc> dsMh, ArrayList<Diem> dsDiem) {
		sortByMa(listSv);
		int perPage = 15;
		int totalPage =  (int) Math.ceil(listSv.size()/(double)perPage);
		int page = 1;
		int chon;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("\n-----------------DANH SACH SINH VIEN-------------------");
			System.out.format("--------------------trang %3d/%-3d------------------\n", page, totalPage);
			
			
			int start = page*perPage-perPage;
			int end = page*perPage;
			if(page == totalPage) end = listSv.size()-1;
			for (int i = start; i <= end; i++) {
				SinhVien match = listSv.get(i);
				showBangDiemBySV(match, false);
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
		}while(chon!=0);
	}


	
	
	
	
	// TODO hien thi bang diem theo mon hoc
	public static void showBangDiemByMH(MonHoc match) {
		String maMH = match.getStringCode();
		ArrayList<Diem> bDiem = new ArrayList<>();
		
		for (Diem d : DataIO.dsDiem) {
			if (d.getMaMh().equals(maMH)) {
				bDiem.add(d);
			}
		}
		if(DataIO.suportAscii == true) {
	    	System.out.println("\n--------------------------------------------");
			System.out.format("| %s  %34s  |\n",maMH,getTenMh(maMH));
			System.out.format("| %-30s     %3.2f  |\n","Diem trung binh mon hoc:", getDiemTBMH(bDiem));
			System.out.println("--------------------------------------------");
			if(bDiem.size() > 0) {
				for (int i = 0; i < 10; i++) {
					Diem d = bDiem.get(i);
					System.out.format("| %s %23s  %6s  |\n",d.getMaSv(),QuanLySinhVien.getTenSV(d.getMaSv()),d.getDiem());
				}
				System.out.println("| ...                                      |");
				System.out.format("| Con %3s sinh vien nua...                 |\n", bDiem.size()-10);
			}else
				System.out.format("| %39s  |\n","Chua co diem");
			System.out.println("--------------------------------------------\n");
		}else {
			System.out.println("\n┌──────────────────────────────────────────┐");
			System.out.format("│ %s  %34s  │\n",maMH,getTenMh(maMH));
			System.out.format("│ %-30s     %3.2f  │\n","Diem trung binh mon hoc:", getDiemTBMH(bDiem));
			System.out.println("├──────────────────────────────────────────┤");
			if(bDiem.size() > 0) {
				for (int i = 0; i < 10; i++) {
					Diem d = bDiem.get(i);
					System.out.format("│ %s %23s  %6s  │\n",d.getMaSv(),QuanLySinhVien.getTenSV(d.getMaSv()),d.getDiem());
				}
				System.out.println("│ ...                                      │");
				System.out.format("│ Con %3s sinh vien nua...                 │\n", bDiem.size()-10);
			}else
				System.out.format("│ %39s  │\n","Chua co diem");
			System.out.println("└──────────────────────────────────────────┘\n");
		}
	}

	
	
	

	static float getDiemTBMH(ArrayList<Diem> bDiem) {
		if(bDiem.size() == 0) return 0;
		float tong = 0;
		for (Diem d : bDiem) {
			tong+=Float.parseFloat(d.getDiem());
		}
		return tong/bDiem.size();
	}

	// TODO Sap xep danh sach diem
	public static void sortBy(ArrayList<Diem> bDiem, String by) {
		if(by.equals("diem")) {
			bDiem.sort((d1,d2) -> {
				Float diem1 = Float.parseFloat(d1.getDiem());
				Float diem2 = Float.parseFloat(d2.getDiem());
				return diem1 < diem2 ? 1 : -1;
			});
		}else if(by.equals("ma_sv")){
			bDiem.sort((d1,d2) -> {
				String ma1 = d1.getMaSv();
				String ma2 = d2.getMaSv();
				return ma1.compareTo(ma2);
			});
		}
	}
}
