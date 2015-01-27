package test;

import java.util.List;

import news_type_mine_tool.HomePageMineUtil;

public class TestHomePageMineUtil {
	public static void main(String[] args) {
		System.setProperty("http.proxyHost","10.10.10.78");
		System.setProperty("http.proxyPort","8080");
		
//		String newsPageUrl = "http://www.banyuetan.org/chcontent/sz/szgc/2015121/123434.html";
//		String homePageUrl = "http://www.banyuetan.org/chcontent/sz/szgc/index.html";
		
//		String newsPageUrl = "http://urakanji.com/wp/5051/";
//		String homePageUrl = "http://urakanji.com/";
		
//		String newsPageUrl = "http://developer.51cto.com/art/201501/464174.htm";
//		String homePageUrl = "http://www.51cto.com/";
		
		String newsPageUrl = "http://www.moonmile.net/blog/archives/6782";
		String homePageUrl = "http://www.moonmile.net/blog/";
		
//		String newsPageUrl = "http://opinion.people.com.cn/n/2015/0127/c1003-26459377.html";
//		String homePageUrl = "http://opinion.people.com.cn/";
		
		System.out.println(newsPageUrl);
		
		String newsPageReg = HomePageMineUtil.reglizeNewsPageUrl(newsPageUrl);
		System.out.println(newsPageReg);
		
		List<String> newsList = HomePageMineUtil.getNewsList(homePageUrl, newsPageReg);
		for(String newsUrl : newsList){
			System.out.println(newsUrl);
		}
	}
}
