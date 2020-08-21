package model;

import data.Data;
import data.DataIO;

public class SinhVien implements Data{
	private String maSV, hodem, ten, ngaySinh, gioiTinh;
	public static int currentCode = 0;
	public SinhVien(String hodem, String ten, String ngaySinh, String gioiTinh) {
		String code = String.format("%05d", currentCode+1);
		this.maSV = "SV"+code; currentCode++;
		this.hodem = hodem;
		this.ten = ten;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
	}
	
	public SinhVien(String maSV, String hodem, String ten,
			String ngaySinh, String gioiTinh) {
		this.maSV = maSV;
		this.hodem = hodem;
		this.ten = ten;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		updateCurr(maSV);
	}
	
	private void updateCurr(String ma){
		String numb = ma.replace("SV", "");
		try {
			int n = Integer.parseInt(numb);
			if(n > currentCode) currentCode = n;
		} catch (NumberFormatException e) {
			//e.printStackTrace();
		}
	}

	public String getMaSV() {
		return maSV;
	}
	public void setMaSV(String maSV) {
		this.maSV = maSV;
	}
	public String getHodem() {
		return hodem.trim();
	}
	public void setHodem(String hodem) {
		this.hodem = hodem;
	}
	public String getTen() {
		return ten.trim();
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(String ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	
	@Override
	public String getLine() {
		String line = maSV+";"+hodem+";"+ten+";"+ngaySinh+";"+gioiTinh;
		return line;
	}
	
	@Override
	public String getColumns() {
		// TODO Auto-generated method stub
		return "# ma sv; ho dem; ten; ngay sinh; gioi tinh";
	}

	public void showInfo() {
		String out = String.format("[%8s  %s %s  %s  %s]",  maSV,getHodem(),getTen(),ngaySinh, getGioiTinh());
		System.out.println(out);
	}

	public String getInfo() {
		String out;
		if(DataIO.suportAscii == true) {
			out = String.format("|%-9s| %-21s | %-9s | %-11s| %-6s  |", maSV,getHodem(),getTen(),ngaySinh, getGioiTinh());
		}else {
			out = String.format("│%-9s│ %-21s │ %-9s │ %-11s│ %-6s  │", maSV,getHodem(),getTen(),ngaySinh, getGioiTinh());
		}
		
		return out;
	}

	public String getFullName() {
		return getHodem()+" "+getTen();
	}
	
	
}
