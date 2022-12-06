 (function() {
 var giftapp = angular.module('giftapp');
 
	
	giftapp.controller('peopleController',function($scope, $http, $location){
		$scope.getPeople = function() {
$http.get("/GiftIt/webapi/gifts/people")
.then(function(response) {
$scope.people = response.data;
console.log('number of people: ' + $scope.people.length);
}, function(response) {
console.log('error http GET people: ' + response.status);
});
}
$scope.getGiftByPerson = function() {
$http.get("/GiftIt/webapi/gifts/user/{personName}")
.then(function(response) {
$scope.peopleGifts = response.data;
console.log('number of people: ' + $scope.people.length);
}, function(response) {
console.log('error http GET people: ' + response.status);
});
}

$scope.goToUpdateView = function(personId){
	console.log('go to update row');
	console.log('personId: ' + personId);
	$location.path('/update/' + personId);
}
 })
})()