package util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtils {

	public static String removeAccent(String s) {
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
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