(function() {
	var giftapp = angular.module('giftapp');
	
giftapp.controller('mainController', function($scope, $http, $location) {
		$scope.getParty = function() {
			$http.get("/GiftIt/webapi/gifts/party")
				.then(function(response) {
					$scope.parties = response.data;
					console.log('number of parties: ' + $scope.parties.length);
				}, function(response) {
					console.log('error http GET parties: ' + response.status);
				});
		}
		$scope.getUnassignedGifts = function() {
			$http.get("/GiftIt/webapi/gifts/giftdashboard")
				.then(function(response) {
					$scope.gifts = response.data;
					console.log('number of gifts: ' + $scope.gifts.length);
				}, function(response) {
					console.log('error http GET gifts: ' + response.status);
				});
		}
		
		
		
		
		$scope.getParty();
		$scope.getUnassignedGifts();
			})
})()