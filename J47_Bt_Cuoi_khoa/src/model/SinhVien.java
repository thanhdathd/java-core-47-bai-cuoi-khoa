package model;

import java.awt.HeadlessException;

import javax.xml.crypto.Data;

import static util.StringUtils.removeAccent;

import data.DataIO;

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
		if(DataIO.engMode) return removeAccent(hodem);
		return hodem;
	}
	public void setHodem(String hodem) {
		this.hodem = hodem;
	}
	public String getTen() {
		if(DataIO.engMode)
			return removeAccent(ten);
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
		if(DataIO.engMode) return removeAccent(gioiTinh);
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
		if(DataIO.engMode) return getHodem()+" "+getTen();
		return hodem+" "+ten;
	}
	
	
}
