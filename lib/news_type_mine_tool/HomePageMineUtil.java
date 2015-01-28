package news_type_mine_tool;

import java.util.List;

import reg.Reg;

import net.HttpPage;

public class HomePageMineUtil {
	
	public static List<String> getNewsList(String homePageUrl,String newsPageReg){
		return Reg.matches(newsPageReg, HttpPage.getPageSource(homePageUrl));
	}
	
	public static String reglizeNewsPageUrl(String newsPageUrl){
		String newsPageReg = "";
		newsPageUrl = newsPageUrl.trim().toLowerCase();
		
		if(newsPageUrl.startsWith("http://")){
			newsPageUrl = newsPageUrl.substring(newsPageUrl.indexOf('/') + 2);
			newsPageUrl = newsPageUrl.substring(newsPageUrl.indexOf('/') + 1);
		}
		
		if(newsPageUrl.contains(".")){
			newsPageReg = '\\' + newsPageUrl.substring(newsPageUrl.indexOf('.'));
			newsPageUrl = newsPageUrl.substring(0 , newsPageUrl.indexOf('.'));
		}
		
		String[] parts = newsPageUrl.split("/");
		for(int i = parts.length - 1 ; i >= 0; i--){
			newsPageReg = "/" + reglize(parts[i]) + newsPageReg;
		}
		
		return newsPageReg.substring(1);
	}
	
	private static int TYPE_CHAR = 1 , TYPE_NUM = 2, TYPE_OTHER = 3 , TYPE_NONE = 0;
	private static String reglize(String data){
		String regStr = "";
		
		int i = 0, count = data.length();
		int charCount = 0, numCount = 0, ch ,type = TYPE_NONE;
		while(i < count){
			ch = data.charAt(i++);
			if(ch >= '0' && ch <= '9'){
				if(type == TYPE_CHAR){
					regStr += "[a-zA-Z]+";
					charCount = 0;
				}
				type = TYPE_NUM;
				numCount++;
			}else if(ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z'){
				if(type == TYPE_NUM){
					regStr += "[0-9]{" + numCount + "}";
					numCount = 0;
				}
				type = TYPE_CHAR;
				charCount++;
			}else{
				if(type == TYPE_CHAR){
					regStr += "[a-zA-Z]+";
					charCount = 0;
				}else if(type == TYPE_NUM){
					regStr += "[0-9]{" + numCount + "}";
					numCount = 0;
				}
				type = TYPE_OTHER;
				if(ch == '?' || ch == '+'){
					regStr += '\\';
				}
				regStr += (char)ch;
			}
		}
		
		if(charCount > 0){
			regStr += "[a-zA-Z]+";
		}
		if(numCount > 0){
			regStr += "[0-9]{" + numCount + "}";
		}
		
		return regStr;
	}
}
