'use strict';

// Declare app level module which depends on views, and components
var app = angular.module(
		'myApp',
		[ 'ngRoute', 'ngMaterial', 'ngAnimate', 'ngAria','toaster',
				'angular-loading-bar', 'isteven-multi-select', 'ngMessages',
				'ngStorage' ]).config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.otherwise({
				redirectTo : '/login'
			});
		} ]);

app.run(function($rootScope, $location, $localStorage,toaster) {
	var userInfo;
	$rootScope.$on('$routeChangeStart', function(event, next) {
		userInfo = $localStorage.userInfo;// Check if the user is logged in */
		// alert(!userInfo);
		// alert(next.isLogin);
		console.log(userInfo);
		if (!userInfo && ($location.path() === "/post")) {
			toaster.pop('error', "Oooooops!!!", "You aren't authenticated please signin !!!!");
			/*
			 * You can save the user's location to take him back to the same
			 * page after he has logged-in
			 */
			// $rootScope.savedLocation = $location.url();
			$location.path('/login');
		}else if(userInfo){
			$location.path('/post');
		}

		// if (userInfo && userInfo.isAuth && !next.isLogin) {
		// $location.path('/dashboard');
		// }

	});

});
