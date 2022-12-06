(function() {
 let giftapp = angular.module('giftapp',['ngRoute']);
 
 giftapp.config(function($routeProvider) {
		  $routeProvider
		  .when("/myPeople", {
		    templateUrl : "myPeople.html",
		    controller : "searchController"
		  })
		  .when("/update/:personId", {
		    templateUrl : "update.html",
		    controller: "updateController"
		  })
		  .when("/gifts", {
		    templateUrl : "gifts.html",
		    controller: "giftController"
		  })
		  .when("/myPeopleTest", {
		    templateUrl : "myPeopleTest.html",
		    controller : "peopleController"
		  })
		  .when("/stack", {
		    templateUrl : "stack.html"
		  })
		  .when("/resume", {
		    templateUrl : "resume.html"
		  })
		  .when("/main", {
		    templateUrl : "main.html",
		    controller : "mainController"
		  })
		  .when("/party", {
		    templateUrl : "party.html",
		    controller : "partyController"
		  })
		  .when("/code", {
		    templateUrl : "code.html"
		  })
		  .otherwise({
			  templateUrl: "main.html",
		    controller : "mainController"
		  });
		});
 })()