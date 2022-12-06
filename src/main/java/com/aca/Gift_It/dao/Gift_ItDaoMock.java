package com.aca.Gift_It.dao;

import java.util.ArrayList;
import java.util.List;

import com.aca.Gift_It.model.CombinedId;
import com.aca.Gift_It.model.Gift;
import com.aca.Gift_It.model.PeopleAndGiftName;
import com.aca.Gift_It.model.Person;

public class Gift_ItDaoMock implements Gift_ItDao {

	private static List<Gift> gifts = new ArrayList<Gift>();
	private static List<Integer> giftIdList = new ArrayList<Integer>();
	private static List<Person> people = new ArrayList<Person>();
	
	//gift list
	static {
		Gift keyboard = new Gift();
		keyboard.setGiftId(1);
		keyboard.setGiftName("Corsiar Keyboard");
		keyboard.setPrice(30);
		
		Gift Mouse = new Gift();
		Mouse.setGiftId(2);
		Mouse.setGiftName("Corsiar Mouse");
		Mouse.setPrice(20);
		
		Gift HeadSet = new Gift();
		HeadSet.setGiftId(3);
		HeadSet.setGiftName("Headset");
		HeadSet.setPrice(25);
		
		gifts.add(keyboard);
		gifts.add(Mouse);
		gifts.add(HeadSet);
		
	}
	//Person list
	static {
		Person Kyle = new Person();
		Kyle.setPersonName("Kyle");
		Kyle.setPersonId(1);
		Kyle.setGiftIdList(giftIdList);
		
		Person Jessica = new Person();
		Jessica.setPersonName("Jessica");
		Jessica.setPersonId(2);
		Jessica.setGiftIdList(giftIdList);
		
		people.add(Kyle);
		people.add(Jessica);
	}
	//giftIdList
	static {
		giftIdList.add(1);
		giftIdList.add(2);
		for (Integer gift : giftIdList) {
			if (gift.toString() == gifts.toString()) {
				
			}
		}
	}
	
	@Override
	public List<Gift> getGifts() {
		List<Gift> myGifts = new ArrayList<Gift>();
		myGifts.addAll(gifts);
		return myGifts;
	}

	@Override
	public List<Person> getPeople() {
		List<Person> myPeople = new ArrayList<Person>();
		myPeople.addAll(people);
		return myPeople;
	}

	@Override
	public List<PeopleAndGiftName> getGiftByPerson(String personName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person createPerson(Person newPerson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person updatePerson(Person updatePerson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PeopleAndGiftName> addGiftToPerson(CombinedId newIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person deletePerson(Integer personId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person deletePerson(String personId) {
		// TODO Auto-generated method stub
		return null;
	}

}
