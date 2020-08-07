package model;

import java.awt.HeadlessException;

public class SinhVien {
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
		int n = Integer.parseInt(numb);
		if(n > currentCode) currentCode = n;
	}

	public String getMaSV() {
		return maSV;
	}
	public void setMaSV(String maSV) {
		this.maSV = maSV;
	}
	public String getHodem() {
		return hodem;
	}
	public void setHodem(String hodem) {
		this.hodem = hodem;
	}
	public String getTen() {
		return ten;
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
	
	
	public String getLine() {
		String line = maSV+";"+hodem+";"+ten+";"+ngaySinh+";"+gioiTinh;
		return line;
	}

	public void showInfo() {
		String out = String.format("[%8s  %s %s  %s  %s]", maSV, hodem, ten, ngaySinh, gioiTinh);
		System.out.println(out);
	}

	public String getInfo() {
		String out = String.format("%-11s %-18s %-11s %-12s %-6s", maSV,hodem,ten,ngaySinh, gioiTinh);
		return out;
	}

	public String getFullName() {
		return hodem+" "+ten;
	}
	
	
}
