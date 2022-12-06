package com.aca.Gift_It.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Person {

	String personId;
	String personName;
	private LocalDateTime updateDateTime;
	private LocalDateTime createDateTime;
	String personLastName;
	String relation;
	String partyLocation;
	String partyDays;
	String partyId;
	String relationId;
	String totalGifts;
	String totalAssignedGift;
	
	
	
	public String getTotalAssignedGift() {
		return totalAssignedGift;
	}
	public void setTotalAssignedGift(String totalAssignedGift) {
		this.totalAssignedGift = totalAssignedGift;
	}
	public String getPartyDays() {
		return partyDays;
	}
	public void setPartyDays(String partyDays) {
		this.partyDays = partyDays;
	}
	public String getTotalGifts() {
		return totalGifts;
	}
	public void setTotalGifts(String totalGifts) {
		this.totalGifts = totalGifts;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getRelationId() {
		return relationId;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	public String getPartyLocation() {
		return partyLocation;
	}
	public void setPartyLocation(String partyLocation) {
		this.partyLocation = partyLocation;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getPersonLastName() {
		return personLastName;
	}
	public void setPersonLastName(String personLastName) {
		this.personLastName = personLastName;
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
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	
	
	
}
