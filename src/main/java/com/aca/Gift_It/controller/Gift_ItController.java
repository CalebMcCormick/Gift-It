package com.aca.Gift_It.controller;

import java.util.List;

import com.aca.Gift_It.model.Asign;
import com.aca.Gift_It.model.CombinedId;
import com.aca.Gift_It.model.Gift;
import com.aca.Gift_It.model.Party;
import com.aca.Gift_It.model.PartyAssign;
import com.aca.Gift_It.model.PeopleAndGiftName;
import com.aca.Gift_It.model.Person;
import com.aca.Gift_It.model.Relation;
import com.aca.Gift_It.service.Gift_ItService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/gifts")
public class Gift_ItController {

	private Gift_ItService service = new Gift_ItService();
	
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Gift> getGifts() {
        return service.getGifts();
    }
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Gift createGift(Gift newGift) {
		return service.createGift(newGift);
	}
    @DELETE
    @Path("/{giftId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Gift deleteGift(@PathParam("giftId")String giftId) {
    	return service.deleteGift(giftId);
    }
    @GET
    @Path("/{giftId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Gift> getGiftById(@PathParam("giftId")String giftId) {
    	return service.getGiftById(giftId);
    }
    @POST
    @Path("/asign")
	@Consumes(MediaType.APPLICATION_JSON)
	public Asign createAsign(Asign newAsign) {
		return service.createAsign(newAsign);
	}
    
    @GET
    @Path("giftsbyperson/{personId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Gift> getGiftByPersonId(@PathParam("personId")String personId) {
        return service.getGiftByPersonId(personId);
    }
    @GET
    @Path("/people")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPeople() {
        return service.getPeople();
    }
    @GET
    @Path("/user/{personName}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PeopleAndGiftName> getGiftByPerson(@PathParam("personName") String personName) {
        return service.getGiftByPerson(personName);
    }
    @POST
    @Path("/people")
	@Consumes(MediaType.APPLICATION_JSON)
	public Person createPerson(Person newPerson) {
		return service.createPerson(newPerson);
	}
    @PUT
    @Path("/people")
	@Consumes(MediaType.APPLICATION_JSON)
	public Person updatePerson(Person updatePerson) {
		return service.updatePerson(updatePerson);
	}
    @POST
    @Path("/people/addgift")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<PeopleAndGiftName> addGiftToPerson(CombinedId newIds) {
		return service.addGiftToPerson(newIds);
    }
    @DELETE
    @Path("/people/{personId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Person deletePerson(@PathParam("personId")String personId) {
    	return service.deletePerson(personId);
    }
    @GET
    @Path("/relation")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Relation> GetRelation() {
    	return service.getRelation();
    }
    @GET
    @Path("/party")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Party> GetParty() {
    	return service.getParty();
    }
    @DELETE
    @Path("/party/{partyId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Party deleteParty(@PathParam("partyId")String partyId) {
    	return service.deleteParty(partyId);
    }
    @GET
    @Path("/party/{partyId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Party> getPartyById(@PathParam("partyId")String partyId) {
    	return service.getPartyById(partyId);
    }
    @POST
    @Path("/party")
	@Consumes(MediaType.APPLICATION_JSON)
	public Party createParty(Party newParty) {
		return service.createParty(newParty);
	}
    @POST
    @Path("/unassign")
	@Consumes(MediaType.APPLICATION_JSON)
	public Asign unassignGift(Asign unassign) {
		return service.unassignGift(unassign);
	}
    @GET
    @Path("/giftdashboard")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Gift> getUnassignedGifts() {
        return service.getUnassignedGifts();
    }
    @POST
    @Path("/partyassign")
	@Consumes(MediaType.APPLICATION_JSON)
	public PartyAssign partyAssign(PartyAssign assign) {
		return service.partyAssign(assign);
	}
    @GET
    @Path("/peoplebygifts/{giftId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Person> peopleByGifts(@PathParam("giftId") String giftId){
    	return service.peopleByGifts(giftId);
    }

    


}
