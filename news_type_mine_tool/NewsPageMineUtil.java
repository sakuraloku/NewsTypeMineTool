package news_type_mine_tool;

import java.util.List;

import reg.Reg;

public class NewsPageMineUtil {
	
	public static String findTextByDivId(String content,String divId){
		String ret = "";
		content = content.toLowerCase();
		
		// find the start index of div with id named divId
		int theDivStartIndex = 0 , divEndIndex = 0;
		while(true){
			theDivStartIndex = content.indexOf("<div", theDivStartIndex + 1);
			if(theDivStartIndex == -1)break;
			
			divEndIndex = content.indexOf('>', theDivStartIndex);
			if(content.substring(theDivStartIndex, divEndIndex).contains(divId)){
				break;
			}
		}
		
		// find the end of div with id named divId
		if(theDivStartIndex >= 0){
			
			int theDivEndIndex = theDivStartIndex + 1,theOldDivEndIndex;
			int divCount = 1, slashDivCount = 0;
			while(divCount != slashDivCount){
				theOldDivEndIndex = theDivEndIndex;
				theDivEndIndex = content.indexOf("</div",theOldDivEndIndex + 1);
				if(theDivEndIndex == -1) break;
				
				slashDivCount++;
				List<String> divList = Reg.matches("<div", content.substring(theOldDivEndIndex, theDivEndIndex));
				divCount += divList.size();
			}
			
			// get content of div with id named divId
			if(theDivEndIndex >= 0){
				ret = content.substring(divEndIndex + 1, theDivEndIndex);
			}
		}
		
		return ret;
	}
	
	/*
	 * get title of http page
	 */
	public static String getTitle(String content){
		String ret = "";
		content = content.toLowerCase();
		
		int titleStartIndex = content.indexOf("<title>") + "<title>".length();
		if(titleStartIndex >=0 ){
			int titleEndIndex = content.indexOf("</title>", titleStartIndex);
			if(titleEndIndex >= 0){
				ret = content.substring(titleStartIndex, titleEndIndex);
			}
		}
		return ret;
	}
	
	public static String getDivIdByText(String content, String startText, String endText){
		String divId = "";
		content = content.toLowerCase();
		startText = startText.toLowerCase();
		endText = endText.toLowerCase();
		
		int startTextIndex = content.indexOf(startText);
		int endTextIndex = content.indexOf(endText);
		
		if(startTextIndex == -1 || endTextIndex == -1) return divId;
		
		Range range = new Range();
		if(startTextIndex > endTextIndex){
			range.setStartIndex(endTextIndex);
			range.setEndIndex(startTextIndex + startText.length());
		}else{
			range.setStartIndex(startTextIndex);
			range.setEndIndex(endTextIndex + endText.length());
		}
		
		range = findMinDiv(content, range);
		
		/*
		 * sample:
		 * 	<div ***** id=**"____"*******>********</div>
		 */
		if(range != null){
//			System.out.println(content.substring(range.getStartIndex(), range.getEndIndex()));
			int endDivIndex = content.indexOf(">", range.getStartIndex());
			if(endDivIndex >= 0){
				int idIndex = content.substring(range.getStartIndex(), endDivIndex).indexOf("id=");
				
				if(idIndex >= 0){
					int divIdStartIndex = content.indexOf('"',idIndex + range.getStartIndex());
					
					if(divIdStartIndex >= 0){
						int divIdEndIndex = content.indexOf('"', divIdStartIndex + 1);
						divId = content.substring(divIdStartIndex + 1,divIdEndIndex);
					}
				}else{// a whole div as a divId, bad choice
					divId = content.substring(range.getStartIndex(), endDivIndex);
				}
			}
		}
		
		return divId;
	}
	
	private static Range findMinDiv(String content, Range range){
		Range retRange = null;
		
		String interContent = content.substring(range.getStartIndex(), range.getEndIndex());
		
		String slashDivContent = interContent , divContent = interContent;
		int lastSlashDivIndex = interContent.lastIndexOf("</div");
		int firstDivIndex = interContent.indexOf("<div");
		if(lastSlashDivIndex >= 0){
			slashDivContent = interContent.substring(0, lastSlashDivIndex+ "</div".length());
		}
		if(firstDivIndex >= 0){
			divContent = interContent.substring(firstDivIndex);
		}

		List<String> divList, slashDivList;
		
		// get inter div count
		divList = Reg.matches("<div", divContent);
		slashDivList = Reg.matches("</div", divContent);
		int divCount = divList.size() - slashDivList.size();
		divCount = divCount < 0 ? 0 : divCount;
		divCount++;
		
		// get inter slash div count
		divList = Reg.matches("<div", slashDivContent);
		slashDivList = Reg.matches("</div", slashDivContent);
		int slashDivCount = slashDivList.size() - divList.size();
		slashDivCount = slashDivCount < 0 ? 0 : slashDivCount;
		slashDivCount++;
		
		int divIndex = range.getStartIndex(),slashDivIndex = range.getEndIndex() - 1,oldDivIndex,oldSlashDivIndex;
		int nowDivCount = 0,nowSlashDivCount = 0;
		
		// get div index
		while(slashDivCount != nowDivCount){
			oldDivIndex = divIndex;
			divIndex = content.lastIndexOf("<div", oldDivIndex - 1);
			if(divIndex == -1) break;
			
			nowDivCount++;
			slashDivList = Reg.matches("</div", content.substring(divIndex, oldDivIndex));
			slashDivCount += slashDivList.size();
		}
		
		if(divIndex >= 0){
			// get slash div index
			while(divCount != nowSlashDivCount){
				oldSlashDivIndex = slashDivIndex;
				slashDivIndex = content.indexOf("</div", oldSlashDivIndex + 1);
				if(slashDivIndex == -1) break;
				
				nowSlashDivCount++;
				divList = Reg.matches("<div", content.substring(oldSlashDivIndex, slashDivIndex));
				divCount += divList.size();
			}
			
			if(slashDivIndex >= 0){
				retRange = new Range();
				retRange.setStartIndex(divIndex);
				retRange.setEndIndex(content.indexOf('>',slashDivIndex) + 1);
			}
		}
		
		return retRange;
	}
}
