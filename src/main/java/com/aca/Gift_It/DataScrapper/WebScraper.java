package com.aca.Gift_It.DataScrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WebScraper {
	
	WebClient webClient = new WebClient();
	HtmlPage page;

	public WebScraper() throws IOException{
		String url = "https://www.bestbuy.com/site/microsoft-xbox-series-s-512-gb-all-digital-disc-free-gaming-holiday-console-white/6510817.p?skuId=6510817";
		page = getWebPage(url);
		
		
	}

	private HtmlPage getWebPage(String url) throws IOException {
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);
		return webClient.getPage(url);
	}
	
	public List<String> extractInformation(){
		List<DomText> bestBuyItems = page.getByXPath("//strong/text()");
		List<DomText> targetItems = page.getByXPath("//h3/text()");
		List<DomText> walmartItems = page.getByXPath("//button/text()");
		List<String> games = new ArrayList<>();
		
//best buy
		for (DomText domText: bestBuyItems) {
			String text = domText.toString();
			if (text.equals("Sold Out")) {
				System.out.println("out of stock");
			}else if(text.equals("Pickup") || text.equals("Shipping")) {
				System.out.println("In stock");
			}
			}
//Target
		for (DomText domText: targetItems) {
			String text = domText.toString();
			if (text.equals("Out of stock")) {
				System.out.println("out of stock");
			}
			//else {
			//	System.out.println("In stock");
			//}
			}
//Walmart
		for (DomText domText: walmartItems) {
			String text = domText.toString();
			if (text.equals("Add to cart")) {
				System.out.println("in stock");
			}else {
				System.out.println("out of stock");
			}
			}

		
		
		return games;
	}
	
	public static void main(String[] args) throws IOException{
		WebScraper webScraper = new WebScraper();
		System.out.println(webScraper.extractInformation());
	}
	
}
