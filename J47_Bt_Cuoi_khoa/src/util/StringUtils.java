package util;

import static main.QuanLyMonHoc.getTenMh;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.regex.Pattern;

import data.DataIO;
import main.QuanLyBangDiem;
import main.QuanLySinhVien;
import model.Diem;

public class StringUtils {

	public static String removeAccent(String s) {
//		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
//		temp = temp.replace("Đ", "D").replace("đ", "d");
//		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
//		return pattern.matcher(temp).replaceAll("");
		//return boDau(s);
		return s;
	}
	
	// " LAª Tha»‹ Dia»‡u   An          02/04/1998   Na»¯"
	
	private static final String SOURCE_CHARACTERS = 
		"ĐđàáảãạằắẳẵặầấẩẫậèéẻẽẹềếểễệìíỉĩịòóỏõọồốổỗộờớởỡợùúủũụừứửữựỳýỷỹỵÀÁẢÃẠẰẮẲẴẶẦẤẨẪẬÈÉẺẼẸỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌỒỐỔỖỘỜỚỞỠỢÙÚỦŨỤỪỨỬỮỰỲÝỶỸỴăâêưôơĂÂÊƯÔƠ";
    private static final String DESTINATION_CHARACTERS = 
    	"DdaaaaaaaaaaaaaaaeeeeeeeeeeiiiiiooooooooooooooouuuuuuuuuuyyyyyAAAAAAAAAAAAAAAEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOUUUUUUUUUUYYYYYaaeuooAAEUOO";
    
    private static final String NONE_MARKS = "abcdefghijklmnopqrstuvxyzwABCDEFGHIJKLMNOPQRSTUVXYZW";

    public static String boDau(String str) {
    	//str = str.replace("a»¯", "u").replace("a»‡", "e").replace("Aª", "e");
    	StringBuilder sb = new StringBuilder();
    	int n = str.length();
    	for (int i = 0; i < n; i++) {
    		String s = str.substring(i,i+1);
			if(NONE_MARKS.contains(s)) {
				sb.append(s);
			}else {
				int id = SOURCE_CHARACTERS.indexOf(s);
				if(id!=-1) {
					sb.append(DESTINATION_CHARACTERS.charAt(id));
				}else {
					sb.append(s);
				}
			}
		}
    	
        return sb.toString();
    }
    
    
	
	public static String getHodem(String name) {
		int index = name.lastIndexOf(" ");
		if(index==-1)return "";
		return name.substring(0, index);
	}
	
	public static String getTen(String name) {
		int index = name.lastIndexOf(" ");
		if(index==-1)return name;
		return name.substring(index+1);
	}
	
	public static String[] depart(String line){
			String res[] = new String[6];
			String part[] = line.split(";");
			for (int i = 0; i < part.length; i++) {
				res[i] = part[i];
			}
			for (int i = 0; i < res.length; i++) {
				if(res[i] == null) res[i] = "";
			}
			return res;
	}
	
	
}
	