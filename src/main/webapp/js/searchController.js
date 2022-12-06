(function() {
	var giftapp = angular.module('giftapp');

//get request
	giftapp.controller('searchController', function($scope, $http, $location) {
		$scope.getPeople = function() {
			$http.get("/GiftIt/webapi/gifts/people")
				.then(function(response) {
					$scope.people = response.data;
					console.log('number of people: ' + $scope.people.length);
				}, function(response) {
					console.log('error http GET people: ' + response.status);
				});
		}
		$scope.getRelations = function() {
			$http.get("/GiftIt/webapi/gifts/relation")
				.then(function(response) {
					$scope.relations = response.data;
					console.log('number of relations: ' + $scope.relation.length);
				}, function(response) {
					console.log('error http GET relation: ' + response.status);
				});
		}
		$scope.getParties = function() {
			$http.get("/GiftIt/webapi/gifts/party")
				.then(function(response) {
					$scope.parties = response.data;
					console.log('number of parties: ' + $scope.party.length);
				}, function(response) {
					console.log('error http GET parties: ' + response.status);
				});
		}
		$scope.personGiftPopUp = function(personId) {
			var unassignPersonId = document.getElementById("unassignPersonId").innerHTML= personId;
			$http.get("/GiftIt/webapi/gifts/giftsbyperson/" + personId)
			.then(function(response) {
					$scope.gifts = response.data;
					console.log('number of gifts: ' + $scope.gifts.length);
				}, function(response) {
					console.log('error http GET gifts: ' + response.status);

					});
		$scope.unassignGift = function(giftId) {
			var unassignPersonId = document.getElementById("unassignPersonId").textContent;
			var obj = {"personId" : unassignPersonId, "giftId" : giftId}
			var json = JSON.stringify(obj);
			var xhr = new XMLHttpRequest();
				xhr.open("POST", "/GiftIt/webapi/gifts/unassign", true);
				xhr.setRequestHeader('Content-Type', 'application/json');
					xhr.send(json);
			console.log(json);
			window.location.reload();
		}
		};
//put request
		$scope.addPerson = function() {
			var personName = document.getElementById("Fname").value;
			var personLName = document.getElementById("Lname").value;
			var relationId = document.getElementById("Rinput").value;
			var partyId = document.getElementById("PLinput").value;
			var obj = {"personName": personName, "personLastName" : personLName, "relationId" : relationId, "partyId" : partyId}
			var json = JSON.stringify(obj);
			console.log(json);
		
			var xhr = new XMLHttpRequest();
				xhr.open("POST", "/GiftIt/webapi/gifts/people", true);
				xhr.setRequestHeader('Content-Type', 'application/json');
					xhr.send(json);
					window.location.reload();
					$scope.getPeople();
}
//delete request
$scope.deletePerson = function(DelId) {
			var result=confirm("want to delete?")
			if (result){
			$http.delete("/GiftIt/webapi/gifts/people/" + DelId)
				.then(function(response) {
					$scope.person = response.data;
					console.log('number of person: ' + DelId);
				}, function(response) {
					console.log('error http DELETE person: ' + response.status);
					
					
				});
				window.location.reload();
				}
				}


 
	
		$scope.getParties();
		$scope.getRelations();
		$scope.getPeople();

		
	})
})()