package com.aca.Gift_It.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.aca.Gift_It.model.Asign;
import com.aca.Gift_It.model.CombinedId;
import com.aca.Gift_It.model.Gift;
import com.aca.Gift_It.model.Party;
import com.aca.Gift_It.model.PartyAssign;
import com.aca.Gift_It.model.PeopleAndGiftName;
import com.aca.Gift_It.model.Person;
import com.aca.Gift_It.model.Relation;

public class Gift_ItDaoImpl implements Gift_ItDao {
		

	private static String selectAllGifts =
			"SELECT gifts.giftId, giftName, price, updateDateTime, createDateTime, link, linkType, category, "
			+ "case when A.giftId is not null then 'Y' else 'N' end as assigned "
			+ "FROM gifts "
			+ "left join (select giftId from asigngifts group by giftId) A on gifts.giftId = A.giftId "
			+ "order by category, giftName";
	private static String selectAllPeople =
			"SELECT people.personId, personName, updateDateTime, createDateTime,personLName, nvl(relation,'NA') as relation, nvl(partyLocation,'NA') as partyLocation,"
			+ "case when partyDate IS NULL then 'NA' ELSE datediff(partyDate, SYSDATE()) END AS partyDays, nvl(A.totalGifts,'NA') as totalGifts "
			+ "FROM people "
			+ "left join relations on people.relationId = relations.relationId "
			+ "left join parties on people.partyId = parties.partyId "
			+ "left join (select personId, count(1) as totalGifts from asigngifts group by personId) A on people.personId = A.personId "
			+ "order by personName, personLName";
	private static String selectGiftByPerson =
				"SELECT people.personName, gifts.giftName "
				+ "FROM people "
				+ "LEFT JOIN peoplegifts ON people.personId = peoplegifts.personId "
				+ "LEFT JOIN gifts ON peoplegifts.giftId = gifts.giftId "
				+ "WHERE people.personName = ? ";
	private static String selectPersonById =
			"SELECT personId, personName, updateDateTime, createDateTime "
			+ "FROM people "
			+ "where personId = ?";
	private static String insertPerson =
			"INSERT INTO people(personName, personLName,relationId,PartyId ) "
			+ "VALUES "
			+ "(?,?,?,?) ";
	private static String insertGift =
			"INSERT into gifts(giftName,price,category,link,linkType) "
			+ "VALUES "
			+ "(?,?,?,?,?)";
	private static String updatePerson =
			"update people set personName = ?, personLName = ? ,relationId = ?,PartyId = ? "
			+ "WHERE personId = ? ";
	private static String selectNewPersonId =
			"SELECT LAST_INSERT_ID() AS 'personId'";
	private static String updatePersonById =
			"UPDATE people "
			+ "SET personName = ? "
			+ "WHERE personId = ?";
	private static String insertGiftByPerson =
			"INSERT INTO peoplegifts(personId,giftId) "
			+ "VALUES "
			+ "(?,?)";
	private static String selectNamesById =
			"SELECT people.personName, gifts.giftName "
			+ "FROM people "
			+ "LEFT JOIN peoplegifts ON people.personId = peoplegifts.personId "
			+ "LEFT JOIN gifts ON peoplegifts.giftId = gifts.giftId "
			+ "WHERE people.personId = ?";
	private static String deletePerson =
			"DELETE FROM people "
			+ "WHERE "
			+ "personId = ?";
	private static String deletePersonXref =
			"DELETE FROM assigngifts "
			+ "WHERE "
			+ "personId = ?";
	
	private static String deleteGift = 
			"DELETE FROM gifts "
			+ "WHERE "
			+ "giftId = ?";
	private static String deleteGiftXref = 
			"DELETE FROM asigngifts "
			+ "WHERE "
			+ "giftId = ?";
	private static String selectAllRelations =
			"select relationId, relation "
			+ "from relations "
			+ "order by relation";
	private static String selectAllParty =
			"SELECT partyId, partyLocation, date_format(partyDate,'%m/%d/%Y') as partyDate, "
			+ "case "
			+ "  when partyDate IS NULL then -999 "
			+ "  ELSE datediff(partyDate, SYSDATE()) "
			+ "END AS partyDays "
			+ "FROM parties "
			+ "ORDER BY partyDays";
	private static String selectGiftById =
			"SELECT giftId, giftName, price, updateDateTime, createDateTime, link, linkType, category, assigned "
			+ "FROM gifts "
			+ "Where giftId = ? ";
	private static String insertAsign =
			"INSERT into asignGifts(personId,giftId) "
			+ "VALUES "
			+ "(?,?)";
	private static String selectGiftByPersonId =
			"SELECT gifts.* "
			+ "FROM people "
			+ "inner JOIN asigngifts ON people.personId = asigngifts.personId "
			+ "inner JOIN gifts ON asigngifts.giftId = gifts.giftId "
			+ "WHERE people.personId = ?";
	private static String deleteParty = 
			"DELETE FROM parties "
			+ "WHERE "
			+ "partyId = ?";
	private static String deletePartyPeople = 
			"update people "
			+ "set partyId = null "
			+ "WHERE "
			+ "partyId = ?";
	private static String UnassignGift =
			"delete from asignGifts "
			+ "where personId=? and giftId=? ";
	private static String insertParty =
			"insert into Parties(partyLocation, PartyDate) "
			+ "values "
			+ "(?,Str_to_date(?,'%Y-%m-%d'))";
	private static String updatePeopleByPartyId =
			"UPDATE people "
			+ "SET partyId = ? "
			+ "WHERE personId = ? ";
	private static String selectPartyById =
			"SELECT partyId, partyLocation, date_format(partyDate,'%m/%d/%Y') as partyDate, "
			+ "case "
			+ "when partyDate IS NULL then -999 "
			+ "ELSE datediff(partyDate, SYSDATE()) "
			+ "END AS partyDays "
			+ "from parties "
			+ "where "
			+ "partyId = ? ";
	private static String selectPeopleByGifts =
			"SELECT personName, personLname, COUNT(1) AS totalAssignedGift, people.personId "
			+ "FROM gifts "
			+ "JOIN asigngifts ON gifts.giftId = asigngifts.giftId "
			+ "JOIN people ON asigngifts.personId = people.personId "
			+ "WHERE gifts.giftId = ? "
			+ "GROUP BY people.personId,personName, personLName ";
	
	
	@Override
	public List<Gift> getGifts() {
		List<Gift> myGifts = new ArrayList<Gift>();
		ResultSet result = null;
		Statement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();
		try {
			statement = conn.createStatement();
			result = statement.executeQuery(selectAllGifts);
			myGifts = makeGift(result);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return myGifts;
	}
	
	private List<Gift> makeGift(ResultSet result) throws SQLException {
		List<Gift> myGifts = new ArrayList<Gift>();
		while(result.next()) {
			Gift gift = new Gift();
			
			gift.setGiftId(result.getString("giftId"));
			gift.setGiftName(result.getString("giftName"));
			gift.setPrice(result.getBigDecimal("price"));
			gift.setUpdateDateTime(result.getObject("updateDateTime", LocalDateTime.class));
			gift.setCreateDateTime(result.getObject("createDateTime", LocalDateTime.class));
			gift.setLink(result.getString("link"));
			gift.setLinkType(result.getString("linkType"));
			gift.setAssigned(result.getString("assigned"));
			gift.setCategory(result.getString("category"));
			myGifts.add(gift);
		}
		
		
		return myGifts;
	}
	private List<Person> makePerson(ResultSet result) throws SQLException {
		List<Person> myPeople = new ArrayList<Person>();
		while(result.next()) {
			Person person = new Person();
			
			person.setPersonId(result.getString("personId"));
			person.setPersonName(result.getString("personName"));
			person.setUpdateDateTime(result.getObject("updateDateTime", LocalDateTime.class));
			person.setCreateDateTime(result.getObject("createDateTime", LocalDateTime.class));
			person.setPersonLastName(result.getString("personLName"));
			person.setRelation(result.getString("relation"));
			person.setPartyLocation(result.getString("partyLocation"));
			person.setPartyDays(result.getString("partyDays"));
			person.setTotalGifts(result.getString("totalGifts"));
			myPeople.add(person);
		}
		
		
		return myPeople;
	}
	private List<Person> makePerson2(ResultSet result) throws SQLException {
		List<Person> myPeople = new ArrayList<Person>();
		while(result.next()) {
			Person person = new Person();
			
			person.setPersonId(result.getString("personId"));
			person.setPersonName(result.getString("personName"));
			person.setPersonLastName(result.getString("personLName"));
			person.setTotalAssignedGift(result.getString("totalassignedgift"));
			myPeople.add(person);
		}
		
		
		return myPeople;
	}
	private List<PeopleAndGiftName> makeNames (ResultSet result) throws SQLException{
		List<PeopleAndGiftName> myNames = new ArrayList<PeopleAndGiftName>();
		while (result.next()) {
			PeopleAndGiftName name = new PeopleAndGiftName();
			
			name.setPersonName(result.getString("personName"));
			name.setGiftName(result.getString("giftName"));
			myNames.add(name);
		}
		
		return myNames;
	}

	@Override
	public List<Person> getPeople() {
		List<Person> myPeople = new ArrayList<Person>();
		ResultSet result = null;
		Statement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();
		try {
			statement = conn.createStatement();
			result = statement.executeQuery(selectAllPeople);
			myPeople = makePerson(result);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return myPeople;
	}

	@Override
	public List<PeopleAndGiftName> getGiftByPerson(String personName) {
		List<PeopleAndGiftName> myNames = new ArrayList<PeopleAndGiftName>();
		ResultSet result = null;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();
		try {
			ps = conn.prepareStatement(selectGiftByPerson);
			ps.setString(1, personName);
			result = ps.executeQuery();
			myNames = makeNames(result);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
			
			
		
		
		
		return myNames;
		}

	@Override
	public Person createPerson(Person newPerson) {
		int updateRowCount = 0;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(insertPerson);
			ps.setString(1, newPerson.getPersonName());
			ps.setString(2, newPerson.getPersonLastName());
			ps.setString(3, newPerson.getRelationId());
			ps.setString(4, newPerson.getPartyId());
			updateRowCount = ps.executeUpdate();
			System.out.println("rows affected: " + updateRowCount);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		return newPerson;
	}
	private String getNewPersonId(Connection conn) {
		ResultSet result = null;
		Statement statement = null;
		String newPersonId = "";
		
		try {
			statement = conn.createStatement();
			result = statement .executeQuery(selectNewPersonId);
			
			while(result.next()) {
				newPersonId = result.getString("personId");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newPersonId;

	}

	@Override
	public Person updatePerson(Person updatePerson) {
		List<Person> people = getPeopleById(updatePerson.getPersonId());
		
		if(people.size() > 0) {
			int updateRowCount = 0;
			PreparedStatement ps = null;
			
			Connection conn = MariaDbUtil.getConnection();
			try {
				ps = conn.prepareStatement(updatePersonById);
				ps.setString(1, updatePerson.getPersonName());
				ps.setString(2, updatePerson.getPersonId());
				updateRowCount = ps.executeUpdate();
				System.out.println("rows affected: " + updateRowCount);
			} catch (SQLException e) {
	
				e.printStackTrace();
			}
		}
		return updatePerson;
	}
	private List<Person> getPeopleById(String personId){
		List<Person> myPeople = new ArrayList<Person>();
		ResultSet result = null;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();

		try {
			ps = conn.prepareStatement(selectPersonById);
			ps.setString(1, personId);
			result = ps.executeQuery();
			myPeople = makePerson(result);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return myPeople;
	}

	@Override
	public List<PeopleAndGiftName> addGiftToPerson(CombinedId newIds) {
		List<PeopleAndGiftName> myNames = new ArrayList<PeopleAndGiftName>();
		int updateRowCount = 0;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(insertGiftByPerson);
			ps.setInt(1, newIds.getPersonId());
			ps.setInt(2, newIds.getGiftId());
			myNames = getGiftByPersonById(newIds.getPersonId());
			updateRowCount = ps.executeUpdate();
			System.out.println("rows affected: " + updateRowCount);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return myNames;
	}
	public List<PeopleAndGiftName> getGiftByPersonById(Integer personId){
		List<PeopleAndGiftName> myNames = new ArrayList<PeopleAndGiftName>();
		ResultSet result = null;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();
		try {
			ps = conn.prepareStatement(selectNamesById);
			ps.setInt(1, personId);
			result = ps.executeQuery();
			myNames = makeNames(result);
			} catch (SQLException e) {
			e.printStackTrace();
		}	
		return myNames;
	}

	@Override
	public Person deletePerson(String personId) {
		Person personToDelete = null;
		
		
			int updateRowCount = 0;
			PreparedStatement ps = null;
			
			Connection conn = MariaDbUtil.getConnection();
			
			try {
				ps = conn.prepareStatement(deletePerson);
				ps.setString(1, personId);
				updateRowCount = ps.executeUpdate();
				System.out.println("rows affected: " + updateRowCount);
				ps = conn.prepareStatement(deletePersonXref);
				ps.setString(1, personId);
				updateRowCount = ps.executeUpdate();
				System.out.println("rows affected: " + updateRowCount);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		
		return personToDelete;
			
	}

	@Override
	public List<Relation> getRelation() {
		List<Relation> myRelations = new ArrayList<Relation>();
		ResultSet result = null;
		Statement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();
		try {
			statement = conn.createStatement();
			result = statement.executeQuery(selectAllRelations);
			myRelations = makeRelation(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return myRelations;
		
		
		
	}

	private List<Relation> makeRelation(ResultSet result) throws SQLException {
		List<Relation> myRelations = new ArrayList<Relation>();
		while(result.next()) {
			Relation relation = new Relation();
			
			relation.setRelation(result.getString("relation"));
			relation.setRelationId(result.getString("relationId"));
			myRelations.add(relation);
		}
		
		
		return myRelations;	
		}

	@Override
	public List<Party> getParty() {
		List<Party> myRelations = new ArrayList<Party>();
		ResultSet result = null;
		Statement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();
		try {
			statement = conn.createStatement();
			result = statement.executeQuery(selectAllParty);
			myRelations = makeParty(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return myRelations;
	}

	private List<Party> makeParty(ResultSet result) throws SQLException {
		List<Party> myParties = new ArrayList<Party>();
		while(result.next()) {
			Party party = new Party();
			
			party.setPartyId(result.getString("partyId"));
			party.setPartyLocation(result.getString("partyLocation"));
			party.setPartyDate(result.getString("partyDate"));
			party.setPartyDays(result.getInt("partyDays"));
			myParties.add(party);
		}
		
		
		return myParties;	
	}

	@Override
	public Gift createGift(Gift newGift) {
		int updateRowCount = 0;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(insertGift);
			ps.setString(1, newGift.getGiftName());
			ps.setBigDecimal(2,newGift.getPrice() );
			ps.setString(3, newGift.getCategory());
			ps.setString(4, newGift.getLink());
			ps.setString(5, newGift.getLinkType());
			updateRowCount = ps.executeUpdate();
			System.out.println("rows affected: " + updateRowCount);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		return null;
	}

	@Override
	public Gift deleteGift(String giftId) {
		Gift GiftToDelete = null;
		
		
		int updateRowCount = 0;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(deleteGift);
			ps.setString(1, giftId);
			updateRowCount = ps.executeUpdate();
			System.out.println("rows affected: " + updateRowCount);
			ps = conn.prepareStatement(deleteGiftXref);
			ps.setString(1, giftId);
			updateRowCount = ps.executeUpdate();
			System.out.println("rows affected: " + updateRowCount);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	
	return GiftToDelete;
	}

	@Override
	public List<Gift> getGiftById(String giftId) {
		List<Gift> myGifts = new ArrayList<Gift>();
		
		ResultSet result = null;
		int updateRowCount = 0;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(selectGiftById);
			ps.setString(1, giftId);
			result = ps.executeQuery();
			myGifts = makeGift(result);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	
		return myGifts;
	}

	@Override
	public Asign createAsign(Asign newAsign) {
		int updateRowCount = 0;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(insertAsign);
			ps.setString(1, newAsign.getPersonId());
			ps.setString(2, newAsign.getGiftId());
			updateRowCount = ps.executeUpdate();
			System.out.println("rows affected: " + updateRowCount);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		return newAsign;
	}

	@Override
	public List<Gift> getGiftByPersonId(String personId) {
		List<Gift> myGifts = new ArrayList<Gift>();
		
		ResultSet result = null;
		int updateRowCount = 0;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(selectGiftByPersonId);
			ps.setString(1, personId);
			result = ps.executeQuery();
			myGifts = makeGift(result);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	
		return myGifts;
	}

	@Override
	public Party deleteParty(String partyId) {
Party PartyToDelete = null;
		
		
		int updateRowCount = 0;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(deleteParty);
			ps.setString(1, partyId);
			updateRowCount = ps.executeUpdate();
			System.out.println("rows affected: " + updateRowCount);
			ps = conn.prepareStatement(deletePartyPeople);
			ps.setString(1, partyId);
			updateRowCount = ps.executeUpdate();
			System.out.println("rows affected: " + updateRowCount);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	
	return PartyToDelete;
	}

	@Override
	public Asign unassignGift(Asign unassign) {
		int updateRowCount = 0;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(UnassignGift);
			ps.setString(1, unassign.getPersonId());
			ps.setString(2, unassign.getGiftId());
			updateRowCount = ps.executeUpdate();
			System.out.println("rows affected: " + updateRowCount);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		return unassign;
	}

	@Override
	public Party createParty(Party newParty) {
		int updateRowCount = 0;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(insertParty);
			ps.setString(1, newParty.getPartyLocation());
			ps.setString(2, newParty.getPartyDate());
			updateRowCount = ps.executeUpdate();
			System.out.println("rows affected: " + updateRowCount);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		return newParty;
	}

	@Override
	public List<Gift> getUnassignedGifts() {
		List<Gift> myGifts = new ArrayList<Gift>();
		List<Gift> myGiftsUnassigned = new ArrayList<Gift>();
		ResultSet result = null;
		Statement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();
		try {
			statement = conn.createStatement();
			result = statement.executeQuery(selectAllGifts);
			myGifts = makeGift(result);
			for(Gift gift : myGifts) {
				if (gift.getAssigned().equals("N")) {
					myGiftsUnassigned.add(gift);
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return myGiftsUnassigned;
	}

	@Override
	public PartyAssign partyAssign(PartyAssign assign) {
		int updateRowCount = 0;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(updatePeopleByPartyId);
			ps.setString(1, assign.getPartyId());
			ps.setString(2, assign.getPersonId());
			updateRowCount = ps.executeUpdate();
			System.out.println("rows affected: " + updateRowCount);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		return assign;
	}

	@Override
	public List<Party> getPartyById(String partyId) {
		List<Party> myParty = new ArrayList<Party>();
		
		ResultSet result = null;
		int updateRowCount = 0;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(selectPartyById);
			ps.setString(1, partyId);
			result = ps.executeQuery();
			myParty = makeParty(result);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	
		return myParty;
	}

	@Override
	public List<Person> peopleByGifts(String giftId) {
		List<Person> myPeople = new ArrayList<Person>();
		ResultSet result = null;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();

		try {
			ps = conn.prepareStatement(selectPeopleByGifts);
			ps.setString(1, giftId);
			result = ps.executeQuery();
			myPeople = makePerson2(result);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return myPeople;
	}
	
}
