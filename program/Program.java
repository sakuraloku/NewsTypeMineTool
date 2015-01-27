package program;

import java.io.File;
import java.util.List;

import net.HttpPage;
import news_type_mine_tool.HomePageMineUtil;
import news_type_mine_tool.NewsPageMineUtil;

import file.Reader;
import file.Writer;

public class Program {
	private static void showHelp(){
		
	}
	
	public static void main(String[] args) {
		System.setProperty("http.proxyHost","10.10.10.78");
		System.setProperty("http.proxyPort","8080");
		
		String batPath = "click_to_download_image.bat";
		String customFileName = "created_by_news_type_mine_tool";
		String newsPageReg = "";
		String newsPageDivId = "";
		String site = "";
		
		if(args.length < 5){
			showHelp();
			return;
		}
		
		String rootDir = args[0];
		String homePageUrl = args[1];
		String referNewsPageUrl = args[2];
		String newsPageStartText = args[3];
		String newsPageEndText = args[4];
		
		if((!homePageUrl.startsWith("http://")) && (referNewsPageUrl.startsWith("http://"))){
			showHelp();
			return;
		}
		
		int flagIndex = homePageUrl.indexOf(':') + 3;
		site = homePageUrl.substring(0, homePageUrl.indexOf('/', flagIndex)) + '/';
		
		if((!rootDir.endsWith("/")) && (!rootDir.endsWith("\\"))){
			rootDir += "/";
		}
		
		// check root directory
		File root = new File(rootDir);
		if(!root.exists()){
			root.mkdirs();
		}
		
		// get newsPageReg and newsPageDivId
		File customFile = new File(rootDir + customFileName);
		if(customFile.exists()){
			String content = Reader.read(rootDir + customFileName);
			String[] customItems = content.split("[ENTER]");
			newsPageReg = customItems[0];
			newsPageDivId = customItems[1];
		}else{
			newsPageReg = HomePageMineUtil.reglizeNewsPageUrl(referNewsPageUrl);
			newsPageDivId = NewsPageMineUtil.getDivIdByText(HttpPage.getPageSource(referNewsPageUrl), newsPageStartText, newsPageEndText);
			Writer.write((newsPageReg+"[ENTER]"+newsPageDivId).getBytes(), rootDir + customFileName);
		}
		
		List<String> newsPageList = HomePageMineUtil.getNewsList(homePageUrl, newsPageReg);
		
		String batContent = "";
		
		for(String newsPageUrl : newsPageList){
			
			String newsName = newsPageUrl.replace('/', '_').replace('.', '_');
			
			//create directory
			File dir = new File(rootDir + newsName);
			if(!dir.exists()){
				dir.mkdir();
			}
			
			File titleFile = new File(rootDir + newsName + "/title.txt");
			File textFile = new File(rootDir + newsName + "/text.txt");
			
			if(titleFile.exists() && textFile.exists()){
				continue;
			}
			
			String pageContent = HttpPage.getPageSource(site + newsPageUrl);
			String title = NewsPageMineUtil.getTitle(pageContent);
			String text = NewsPageMineUtil.findTextByDivId(pageContent, newsPageDivId);
			
			
			
			// get image
			int imageStartIndex, imageEndIndex = 0;
			int srcStartIndex, srcEndIndex;
			int imageIndex = 0;
			
			while(true){
				imageStartIndex = text.indexOf("<img",imageEndIndex);
				if(imageStartIndex == -1){
					break;
				}
				
				srcStartIndex = text.indexOf("src",imageStartIndex) + 5;
				srcEndIndex = text.indexOf('"',srcStartIndex);
				
				imageIndex++;
				
				String imageUrl = text.substring(srcStartIndex, srcEndIndex);
				if(!imageUrl.startsWith("http://")){
					imageUrl = site + imageUrl.substring(1);
				}
				
				String imageFileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
				batContent += "wget " + imageUrl + System.lineSeparator();
				batContent += "move " + imageFileName + " " + rootDir + newsName + "/" + imageIndex + ".jpg" + System.lineSeparator();
				
				text = text.substring(0, srcStartIndex) + imageIndex + ".jpg" + text.substring(srcEndIndex);
				imageEndIndex = text.indexOf('>', imageStartIndex);
			}
			
			Writer.write(title.getBytes(), rootDir + newsName + "/title.txt");
			Writer.write(text.getBytes(), rootDir + newsName + "/text.txt");
		}
		
		Writer.write(batContent.getBytes(), batPath);
	}
}
