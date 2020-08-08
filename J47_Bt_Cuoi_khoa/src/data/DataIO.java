package data;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import model.Diem;
import model.MonHoc;
import model.SinhVien;

public class DataIO {
	static File _monhoc = null;
	static File _sinhvien = null;
	static File _diem = null;
	public static boolean engMode = false;
	
	public static ArrayList<SinhVien> listSV;
	public static ArrayList<MonHoc> dsMonHoc;
	public static ArrayList<Diem> dsDiem;
	public static boolean suportAscii = false;
	static {
		_monhoc = new File("data\\monhoc.txt");
		_sinhvien = new File("data\\sinhvien.txt");
		_diem = new File("data\\diem.txt");
		listSV = loadSinhVien();
		dsMonHoc = loadMonHoc();
		dsDiem = loadDiem();
	}
	
	public static ArrayList<SinhVien> loadSinhVien(){
		DataTemplate<SinhVien> template = s -> {
			String part[] = s.split(";");
			SinhVien sv = new SinhVien(part[0], part[1], part[2], part[3],part[4]);
			return sv;
		};
		ArrayList<SinhVien> list = loadData(_sinhvien, template);
		return list;
	}
	
	public static ArrayList<MonHoc> loadMonHoc() {
		DataTemplate<MonHoc> template = s -> {
			String part[] = s.split(";");
			MonHoc mh = new MonHoc(Integer.parseInt(part[0]),
					part[1], Float.parseFloat(part[2]));
			return mh;
		};
		ArrayList<MonHoc> list = loadData(_monhoc, template);
		return list;
	}
	
	public static ArrayList<Diem> loadDiem() {
		DataTemplate<Diem> template = s -> {
			String part[] = s.split(";");
			Diem d = new Diem(part[0],part[1],part[2]);
			return d;
		};
		ArrayList<Diem> list = loadData(_diem, template);
		return list;
	}

	
	private static <T> ArrayList<T> loadData(File file, DataTemplate<T> template){
		ArrayList<T> list = new ArrayList<T>();
		FileReader frd = null;
		BufferedReader bufR = null;
		try {
			if(file.exists()) {
				frd = new FileReader(file);
				bufR = new BufferedReader(frd);
			}else {
				InputStream in = DataIO.class.getResourceAsStream(file.getName());
				bufR = new BufferedReader(new InputStreamReader(in));
			}
			String line;
			while ((line = bufR.readLine()) != null) {
				//System.out.println(line);
				if(line.startsWith("#")) continue;
				T data = template.retrieve(line);
				list.add(data);
			}
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(bufR!=null) bufR.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	
	public static void saveAllData(ArrayList<SinhVien> ls1, ArrayList<MonHoc> ls2, ArrayList<Diem> ls3) {
		
	}
}
