'use strict';

app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/login', {
		templateUrl : 'login/login.html',
		controller : 'loginCtrl'
	});
} ])

app.controller('loginCtrl', function($scope, authService, $location,
		BIOTConstant, $localStorage,toaster) {
	
	$scope.postPageBG=false;

	$scope.signIn = function() {

		$scope.loading = true;
		$scope.info = false;
		$scope.muted = false;
		$scope.isProcessing = true;

		authService.login($scope.Login).then(function(response) {
			$localStorage.userInfo = response;
			$scope.isProcessing = false;
			$scope.loading = false;
			$location.path('/post');
		}, function(err) {
			toaster.pop('error', "Oooooops!!!", err);
			$scope.loading = false;
			$scope.info = true;
			$scope.muted = true;
			$scope.isProcessing = false;
		});
	};

});

app.directive('capitalize', function() {
	return {
		require : 'ngModel',
		link : function(scope, element, attrs, modelCtrl) {
			var capitalize = function(inputValue) {
				if (inputValue == undefined)
					inputValue = '';
				var capitalized = inputValue.toUpperCase();
				if (capitalized !== inputValue) {
					modelCtrl.$setViewValue(capitalized);
					modelCtrl.$render();
				}
				return capitalized;
			}
			modelCtrl.$parsers.push(capitalize);
			capitalize(scope[attrs.ngModel]); // capitalize initial value
		}
	};
});

app.factory('authService', [
		'$http',
		'$q',
		'userInformationService',
		'BIOTConstant',
		function($http, $q, userInformationService, BIOTConstant) {

			console.log(userInformationService);
			var authServiceFactory = {};

			var _login = function(Login) {

				var deferred = $q.defer();

				$http.post(BIOTConstant.url + '/biot/rest/admin/user', Login,
						{}).success(
						function(response) {
							console.log(response);
							if (response.status.code != 'BIOK') {
								console.log(response.status.description);
								deferred.reject(response.status.description);

							} else {
								userInformationService.setUserInfo(
										response.currentUser.name,
										response.currentProfile.name,
										response.currentUser.code,
										BIOTConstant.url
												+ response.currentUser.image);
								deferred.resolve(userInformationService);
							}
						}).error(function(err, status) {
					userInformationService.destroy();
					deferred.reject("Technical Problem");
				});

				return deferred.promise;
			};

			authServiceFactory.login = _login

			return authServiceFactory;
		} ]);