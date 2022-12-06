package com.aca.Gift_It.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Gift {

	BigDecimal price;
	String giftId;
	String giftName;
	private LocalDateTime updateDateTime;
	private LocalDateTime createDateTime;
	String link;
	String linkType;
	String category;
	String assigned;
	
	public String getAssigned() {
		return assigned;
	}
	public void setAssigned(String assigned) {
		this.assigned = assigned;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getLinkType() {
		return linkType;
	}
	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
	public String getGiftId() {
		return giftId;
	}
	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(LocalDateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}
	
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String name) {
		this.giftName = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
	
	
}
