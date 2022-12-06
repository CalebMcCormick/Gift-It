package com.aca.Gift_It.dao;

import java.util.List;

import com.aca.Gift_It.model.Asign;
import com.aca.Gift_It.model.CombinedId;
import com.aca.Gift_It.model.Gift;
import com.aca.Gift_It.model.Party;
import com.aca.Gift_It.model.PartyAssign;
import com.aca.Gift_It.model.PeopleAndGiftName;
import com.aca.Gift_It.model.Person;
import com.aca.Gift_It.model.Relation;

public interface Gift_ItDao {
	
	public List<Gift> getGifts();
	public List<Person> getPeople();
	public List<PeopleAndGiftName> getGiftByPerson(String personName);
	public Person createPerson(Person newPerson);
	public Person updatePerson(Person updatePerson);
	public List<PeopleAndGiftName> addGiftToPerson(CombinedId newIds);
	public Person deletePerson(String personId);
	public List<Relation> getRelation();
	public List<Party> getParty();
	public Gift createGift(Gift newGift);
	public Gift deleteGift(String giftId);
	public List<Gift> getGiftById(String giftId);
	public Asign createAsign(Asign newAsign);
	public List<Gift> getGiftByPersonId(String personId);
	public Party deleteParty(String partyId);
	public Asign unassignGift(Asign unassign);
	public Party createParty(Party newParty);
	public List<Gift> getUnassignedGifts();
	public PartyAssign partyAssign(PartyAssign assign);
	public List<Party> getPartyById(String partyId);
	public List<Person> peopleByGifts(String giftId);
	
	
}
