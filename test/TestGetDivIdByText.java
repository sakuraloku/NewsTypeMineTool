package test;

import net.HttpPage;
import news_type_mine_tool.NewsPageMineUtil;

public class TestGetDivIdByText {
	public static void main(String[] args) {
		System.setProperty("http.proxyHost","10.10.10.78");
		System.setProperty("http.proxyPort","8080");
		
//		String url = "http://www.banyuetan.org/chcontent/sz/szgc/2015126/123839.html";
//		String startText = "国家卫生计生委：今年将制订";
//		String endText = "民政部：继续加快发展养";
		
//		String url = "http://opinion.people.com.cn/n/2015/0127/c1003-26457847.html";
//		String startText = "区区一张照片，竟像一把盐";
//		String endText = "阿拉伯世界的人民与以色";
//		HttpPage.CODE_MODE = "GB2312";
		
//		String url = "http://d.hatena.ne.jp/TipsMemo+computer-technology/20150124/p1";
//		String startText = "「再読み込み」で解決で";
//		String endText = "本語ランゲージパ";
//		HttpPage.CODE_MODE = "euc-jp";
		
//		String url = "http://developer.51cto.com/art/201501/464174.htm";
//		String startText = "这是一篇介绍机器学习历史的文";
//		String endText = "17 个关于机器学习";
//		HttpPage.CODE_MODE = "GB2312";
		
//		String url = "http://www.moonmile.net/blog/archives/6778";
//		String startText = "n.iOS/Android に AllJoyn を";
//		String endText = "結構有力かな";
//		HttpPage.CODE_MODE = "utf8";
		
		String url = "http://urakanji.com/wp/4910/";
		String startText = "サービスのうち";
		String endText = "ず動くようにな";
		
		String httpPage = HttpPage.getPageSource(url);
		System.out.println(NewsPageMineUtil.getDivIdByText(httpPage, startText, endText));
	}
}
