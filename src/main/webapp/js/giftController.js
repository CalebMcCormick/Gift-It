(function() {
	var giftapp = angular.module('giftapp');


	giftapp.controller('giftController', function($scope, $http, $location) {
		$scope.getGifts = function() {
			$http.get("/GiftIt/webapi/gifts")
				.then(function(response) {
					$scope.gifts = response.data;
					console.log('number of gifts: ' + $scope.gifts.length);
				}, function(response) {
					console.log('error http GET gifts: ' + response.status);
				});
		}
		$scope.addGift = function() {
			console.log("test");
			var giftName = document.getElementById("Gname").value;
			var category = document.getElementById("Gcat").value;
			var price = document.getElementById("Price").value;
			var linkType = document.getElementById("Ltype").value;
			var link = document.getElementById("Glink").value;
			var obj = { "giftName": giftName, "price": price, "category": category, "link": link, "linkType": linkType }
			var json = JSON.stringify(obj);
			console.log(json);

			var xhr = new XMLHttpRequest();
			xhr.open("POST", "/GiftIt/webapi/gifts", true);
			xhr.setRequestHeader('Content-Type', 'application/json');
			xhr.send(json);
			window.location.reload();
			$scope.getGifts();
		}
		$scope.deleteGift = function(DelId) {
			var result = confirm("want to delete?")
			if (result) {
				$http.delete("/GiftIt/webapi/gifts/" + DelId)
					.then(function(response) {
						$scope.gift = response.data;
						console.log('number of gift: ' + DelId);
					}, function(response) {
						console.log('error http DELETE gift: ' + response.status);

					});
				window.location.reload();
			}
		}

		$scope.getGifts();

		$scope.giftPopUp = function(giftId) {
		$http.get("/GiftIt/webapi/gifts/" + giftId)
					.then(function(response) {
						$scope.gift = response.data;
						for(let i = 0; i < $scope.gift.length; i++) {
						let obj = $scope.gift[i];
						var GnameAsgn = document.getElementById("GnameAsgn");
						GnameAsgn.innerHTML = obj.giftName;
						var GpriceAsgn = document.getElementById("GpriceAsgn");
						GpriceAsgn.innerHTML = "$" + obj.price;
						var GIdAssign = document.getElementById("GIdAssign");
						GIdAssign.value = obj.giftId;
						}
					}, function(response) {
						console.log('error http get gift: ' + response.status);

					})

$scope.getPeople();
$scope.getPeopleByGift(giftId);

	}
	$scope.getPeopleByGift = function(giftId) {
			$http.get("/GiftIt/webapi/gifts/peoplebygifts/" + giftId)
				.then(function(response) {
					$scope.PGA = response.data;
					console.log('number of people: ' + $scope.people.length);
				}, function(response) {
					console.log('error http GET people: ' + response.status);
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
$scope.addAsign = function() {
			var giftId = document.getElementById("GIdAssign").value;
			var personId = document.getElementById("GPSelect").value;
			var obj = { "personId": personId, "giftId": giftId}
			var json = JSON.stringify(obj);
			console.log(json);

			var xhr = new XMLHttpRequest();
			xhr.open("POST", "/GiftIt/webapi/gifts/asign", true);
			xhr.setRequestHeader('Content-Type', 'application/json');
			xhr.send(json);
			window.location.reload();
			$scope.getGifts();
		}

$scope.goToUpdateView = function(giftId) {
			console.log('go to update row');
			console.log('giftId: ' + giftId);
			$location.path('/update/' + giftId);
		}
 })
}) ()