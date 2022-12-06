package com.aca.Gift_It.service;

import java.util.List;

import com.aca.Gift_It.dao.Gift_ItDaoImpl;
import com.aca.Gift_It.dao.Gift_ItDao;
import com.aca.Gift_It.dao.Gift_ItDaoMock;
import com.aca.Gift_It.model.Asign;
import com.aca.Gift_It.model.CombinedId;
import com.aca.Gift_It.model.Gift;
import com.aca.Gift_It.model.Party;
import com.aca.Gift_It.model.PartyAssign;
import com.aca.Gift_It.model.PeopleAndGiftName;
import com.aca.Gift_It.model.Person;
import com.aca.Gift_It.model.Relation;

public class Gift_ItService {

	//private ProjectDao projectDao = new ProjectDaoMock();
	private Gift_ItDao Gift_ItDao = new Gift_ItDaoImpl();
	
	public List<Gift> getGifts(){
		
		return Gift_ItDao.getGifts();
	}

	public List<Person> getPeople() {
		
		return Gift_ItDao.getPeople();
	}

	public List<PeopleAndGiftName> getGiftByPerson(String personName) {
		return Gift_ItDao.getGiftByPerson(personName);
	}

	public Person createPerson(Person newPerson) {
		return Gift_ItDao.createPerson(newPerson);
	}

	public Person updatePerson(Person updatePerson) {
		return Gift_ItDao.updatePerson(updatePerson);
	}

	public List<PeopleAndGiftName> addGiftToPerson(CombinedId newIds) {
		return Gift_ItDao.addGiftToPerson(newIds);
	}

	public Person deletePerson(String personId) {
		// TODO Auto-generated 
		return Gift_ItDao.deletePerson(personId);
	}

	public List<Relation> getRelation() {
		return Gift_ItDao.getRelation();
	}

	public List<Party> getParty() {
		return Gift_ItDao.getParty();
	}

	public Gift createGift(Gift newGift) {
		return Gift_ItDao.createGift(newGift);
	}

	public Gift deleteGift(String giftId) {
		return Gift_ItDao.deleteGift(giftId);
	}

	public List<Gift> getGiftById(String giftId) {
		return Gift_ItDao.getGiftById(giftId);
	}

	public Asign createAsign(Asign newAsign) {
		return Gift_ItDao.createAsign(newAsign);
	}

	public List<Gift> getGiftByPersonId(String personId) {
		return Gift_ItDao.getGiftByPersonId(personId);
	}

	public Party deleteParty(String partyId) {
		return Gift_ItDao.deleteParty(partyId);
	}

	public Asign unassignGift(Asign unassign) {
		return Gift_ItDao.unassignGift(unassign);
	}

	public Party createParty(Party newParty) {
		return Gift_ItDao.createParty(newParty);
	}

	public List<Gift> getUnassignedGifts() {
		return Gift_ItDao.getUnassignedGifts();
	}

	public PartyAssign partyAssign(PartyAssign assign) {
		return Gift_ItDao.partyAssign(assign);
	}

	public List<Party> getPartyById(String partyId) {
		return Gift_ItDao.getPartyById(partyId);
	}

	public List<Person> peopleByGifts(String giftId) {
		return Gift_ItDao.peopleByGifts(giftId);
	}
	
}
