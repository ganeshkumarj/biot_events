app.controller('HomeController', [ '$scope', '$location',
		'userInformationService', '$localStorage',
		function($scope, $location, userInformationService, $localStorage) {

			$scope.$storage = $localStorage;

			$scope.signout = function($scope) {
				userInformationService.destroy();
				delete $localStorage.userInfo;
				$location.path("/login");
			}

		} ]);
