package test;

import net.HttpPage;
import news_type_mine_tool.NewsPageMineUtil;

public class TestFindTextByDivId {
	
	public static void main(String[] args) {
		System.setProperty("http.proxyHost","10.10.10.78");
		System.setProperty("http.proxyPort","8080");
		
//		String url = "http://www.banyuetan.org/chcontent/sz/szgc/2015126/123839.html";
//		String divId = "neirong";
		
//		String url = "http://opinion.people.com.cn/n/2015/0127/c1003-26457847.html";
//		String divId = "p_content";
//		HttpPage.CODE_MODE = "GB2312";
		
//		String url = "http://d.hatena.ne.jp/TipsMemo+computer-technology/20150124/p1";
//		String divId = "<div class=\"section\"";
//		HttpPage.CODE_MODE = "euc-jp";
		
//		String url = "http://developer.51cto.com/art/201501/464174.htm";
//		String divId = "content";
//		HttpPage.CODE_MODE = "GB2312";
		
//		String url = "http://www.moonmile.net/blog/archives/6778";
//		String divId = "<div class=\"entry-content\"";
//		HttpPage.CODE_MODE = "utf-8";
		
		String url = "http://urakanji.com/wp/4910/";
		String divId = "<div class=\"pf-content\"";
		
		String httpPage = HttpPage.getPageSource(url);
		System.out.println(NewsPageMineUtil.findTextByDivId(httpPage, divId));
	}
}
