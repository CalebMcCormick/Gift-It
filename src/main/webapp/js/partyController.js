(function() {
	var giftapp = angular.module('giftapp');

//get request
	giftapp.controller('partyController', function($scope, $http, $location) {
		$scope.getParty = function() {
			$http.get("/GiftIt/webapi/gifts/party")
				.then(function(response) {
					$scope.parties = response.data;
					console.log('number of parties: ' + $scope.parties.length);
				}, function(response) {
					console.log('error http GET parties: ' + response.status);
				});
		}
		$scope.getPeople = function() {
			$http.get("/GiftIt/webapi/gifts/people")
				.then(function(response) {
					$scope.people = response.data;
					console.log('number of people: ' + $scope.people.length);
				}, function(response) {
					console.log('error http GET people: ' + response.status);
				});
		}
		$scope.partyPopUp = function(partyId) {
		$http.get("/GiftIt/webapi/gifts/party/" + partyId)
					.then(function(response) {
						$scope.party = response.data;
						for(let i = 0; i < $scope.party.length; i++) {
						let obj = $scope.party[i];
						var PnameAsgn = document.getElementById("PnameAsgn");
						PnameAsgn.innerHTML = obj.partyLocation;
						var PartyIdAssign = document.getElementById("PartyIdAssign");
						PartyIdAssign.value =  obj.partyId;
						}
					}, function(response) {
						console.log('error http get party: ' + response.status);

					})

$scope.getPeople();

	}
//post request
		$scope.addParty = function() {
			var PLocation = document.getElementById("PLocation").value;
			var PDate = document.getElementById("PDate").value;
			var obj = {"partyLocation": PLocation, "partyDate" : PDate}
			var json = JSON.stringify(obj);
			console.log(json);
			console.log(obj);
		
			var xhr = new XMLHttpRequest();
				xhr.open("POST", "/GiftIt/webapi/gifts/party", true);
				xhr.setRequestHeader('Content-Type', 'application/json');
					xhr.send(json);
					window.location.reload();
					
}
		$scope.addAsignParty = function() {
			var partyId = document.getElementById("PartyIdAssign").value;
			var personId = document.getElementById("PSelect").value;
			var obj = { "personId": personId, "partyId": partyId}
			var json = JSON.stringify(obj);
			console.log(json);

			var xhr = new XMLHttpRequest();
				xhr.open("POST", "/GiftIt/webapi/gifts/partyassign", true);
				xhr.setRequestHeader('Content-Type', 'application/json');
					xhr.send(json);
					window.location.reload();
		}
//delete request
$scope.deleteParty = function(DelId) {
			var result=confirm("want to delete?")
			if (result){
			$http.delete("/GiftIt/webapi/gifts/party/" + DelId)
				.then(function(response) {
					$scope.party = response.data;
					console.log('number of parties: ' + DelId);
				}, function(response) {
					console.log('error http DELETE party: ' + response.status);
					
					
				});
				window.location.reload();
				}
				}
		$scope.getParty();
	})
})()