package test;

import net.HttpPage;
import news_type_mine_tool.NewsPageMineUtil;

public class TestGetTitle {
	
	public static void main(String[] args) {
		System.setProperty("http.proxyHost","10.10.10.78");
		System.setProperty("http.proxyPort","8080");
		
//		String url = "http://www.banyuetan.org/chcontent/sz/szgc/2015126/123839.html";
		
//		String url = "http://opinion.people.com.cn/n/2015/0127/c1003-26457847.html";
//		HttpPage.CODE_MODE = "GB2312";
		
		String url = "http://d.hatena.ne.jp/TipsMemo+computer-technology/20150124/p1";
//		HttpPage.CODE_MODE = "euc-jp";
		
//		String url = "http://developer.51cto.com/art/201501/464174.htm";
//		HttpPage.CODE_MODE = "GB2312";
		
//		String url = "http://www.moonmile.net/blog/archives/6778";
		
//		String url = "http://urakanji.com/wp/4910/";
		
		String content = HttpPage.getPageSource(url);
		System.out.println(NewsPageMineUtil.getTitle(content));
	}
}
