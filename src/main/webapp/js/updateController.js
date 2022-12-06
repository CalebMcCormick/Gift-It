  (function() {
 var giftapp = angular.module('giftapp');
 
	
	giftapp.controller('updateController',  function($scope, $http, $routeParams){
		
	let personId = $routeParams.personId;
	alert('id of people to update: ' + personId);
	});
})()