'use strict';

app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/post', {
		templateUrl : 'post/post.html',
		controller : 'postCtrl'
	});
} ])

app.directive("ngFileSelect", function() {

	return {
		link : function($scope, el) {

			el.bind("change", function(e) {

				$scope.file = (e.srcElement || e.target).files[0];
				$scope.getFile();
			})

		}

	}

})

app.controller('postCtrl', function($scope, $timeout, $q, $location, $filter,
		postMessageService, userInformationService, fileReader, sendMessageService,
		$mdDialog, $animate, usersOnMyLocation, $interval, BIOTConstant,$localStorage,toaster) {
    
    $scope.submitVaue="That's it Post it";
    $scope.submitting = false;
    $scope.postPageBG=true;
	$scope.getFile = function() {
		$scope.progress = 0;
		fileReader.readAsDataUrl($scope.file, $scope).then(function(result) {
			$scope.post.imageSrc = result;
		});
	};

	$scope.$on("fileProgress", function(e, progress) {
		$scope.progress = progress.loaded / progress.total;
	});

	postMessageService.postMessage().then(
			function(response) {

				var usersDetail = [];

				var profileDetail = [];

				$scope.post = {};

				_.each(response.users, function(user, key, list) {
					var userTemp = {};
					userTemp.icon = "<img src=" + BIOTConstant.url + user.image
							+ ">";
					userTemp.name = user.name;
					userTemp.id = user.code;
					userTemp.ticked = false;
					usersDetail.push(userTemp);
				});

				_.each(response.profiles, function(profile, key, list) {
					var profileTemp = {};
					profileTemp.icon = "<img src=" + BIOTConstant.url
							+ profile.id + ".png>";
					profileTemp.name = profile.name;
					profileTemp.id = profile.id;
					profileTemp.ticked = false;
					profileDetail.push(profileTemp);
				});

				// $scope.profileName=userInformationService.getUserInfo().userName;
				// $scope.profileDept=userInformationService.getUserInfo().profile;
				// $scope.userImage=BIOTConstant.url+userInformationService.getUserInfo().image;
				$scope.users = usersDetail;
				$scope.profile = profileDetail;
				$scope.post.startDate = new Date();
				$scope.post.endDate = new Date(new Date().getTime()
						+ (30 * 24 * 60 * 60 * 1000));

			}, function(err) {
				console.log(err);

			});

	
	  var usersFetchInterval = $interval( function(){
	  usersOnMyLocation.users($localStorage.userInfo).then(function(response) {
	  $scope.usersOnMyLocation= response.users;
	  console.log($scope.usersOnMyLocation); }, function(err) {
	  console.log(err); }); }, 10000);
	  
	  $scope.$on('$destroy',function(){ $interval.cancel(usersFetchInterval);
	  });
	 

	$scope.postMessage = function() {
         $scope.submitVaue="Posting !!!!!";
        
      //  $scope.submitting = true;
		var Message = {};
		Message.start = $filter('date')($scope.post.startDate,
				"yyyy-MM-dd HH:mm:ss");
		Message.end = $filter('date')($scope.post.endDate,
				"yyyy-MM-dd HH:mm:ss");
		Message.created = $localStorage.userInfo.code;
		Message.profiles = [];
		Message.users = [];
		Message.messages = [];
		_.each($scope.post.selectedProfiles, function(profile, key, list) {
		console.log(profile.id);
			Message.profiles.push(profile.id);
		});

		_.each($scope.post.selectedUsers, function(usr, key, list) {
		console.log(usr.id);
			Message.users.push(usr.id);
		});
		var msgContent = {};
		msgContent.content = $scope.post.message;
		msgContent.image = $scope.post.imageSrc
		Message.messages.push(msgContent);
	$scope.messageContent = Message;
		console.log(Message);

		console.log($scope.post);
		sendMessageService.sendMessage($scope.messageContent).then(function(response) {
                    console.log("isnide submit");
					 console.log($scope.submitVaue);
					$scope.post.message=null;
					//$scope.post.selectedProfiles=[];
					//$scope.post.selectedUsers=[];
					$scope.post.imageSrc=null;
                     $scope.submitVaue="Posted !!!!!";
					  $timeout(function(){
						$scope.submitVaue="That's it Post it";
						 }, 3000);
                    $scope.submitting = false;
                    toaster.pop('success', "Awesome!!!", "Congratulation Successfully Posted");
                   
					console.log(response);
				}, function(err) {
                    $scope.post.message=null;
					//$scope.post.selectedProfiles=[];
					//$scope.post.selectedUsers=[];
					$scope.post.imageSrc=null;
						 $timeout(function(){
						$scope.submitVaue="That's it Post it";
						 }, 3000);
                        $scope.submitting = false;
                   
					console.log(err);
				});

	}

});

app.factory('postMessageService', [
		'$http',
		'$q',
		'userInformationService',
		'BIOTConstant',
		function($http, $q, userInformationService, BIOTConstant) {

			var postMessageFactory = {};

			var _postMessage = function(Login) {

				var deferred = $q.defer();

				$http.get(BIOTConstant.url + '/biot/rest/admin/message')
						.success(function(response) {
							console.log(response);
							if (response.status.code != 'BIOK') {
								console.log(response.status.description);
								deferred.reject(response.status.description);

							} else {
								deferred.resolve(response);
							}
						}).error(function(err, status) {
							deferred.reject("Technical Problem");
						});

				return deferred.promise;
			};

			postMessageFactory.postMessage = _postMessage

			return postMessageFactory;
		} ]);

app.factory('usersOnMyLocation', [
		'$http',
		'$q',
		'userInformationService',
		'BIOTConstant',
		function($http, $q, userInformationService, BIOTConstant) {
			console.log(BIOTConstant.url);
			var usersOnMyLocationFactory = {};
			var _users = function(userInformationService) {
				var deferred = $q.defer();
				$http.get(
						BIOTConstant.url + '/biot/rest/admin/user?userCode='
								+ userInformationService.code).success(
						function(response) {
							console.log(response);
							if (response.status.code != 'BIOK') {
								console.log(response.status.description);
								deferred.reject(response.status.description);

							} else {
								deferred.resolve(response);
							}
						}).error(function(err, status) {
					deferred.reject("Technical Problem");
				});

				return deferred.promise;
			};

			usersOnMyLocationFactory.users = _users

			return usersOnMyLocationFactory;
		} ]);

app.factory('sendMessageService', [
		'$http',
		'$q',
		'BIOTConstant',
		function($http, $q, BIOTConstant) {

			var sendMessageServiceFactory = {};

			var _sendMessage = function(sendMessage) {

				var deferred = $q.defer();

				$http.post(BIOTConstant.url + '/biot/rest/admin/message',
						sendMessage, {}).success(function(response) {
					deferred.resolve(response);
				}).error(function(err, status) {
					deferred.reject("Technical Problem");
				});

				return deferred.promise;
			};

			sendMessageServiceFactory.sendMessage = _sendMessage

			return sendMessageServiceFactory;
		} ]);

var fileReader = function($q, $log) {

	var onLoad = function(reader, deferred, scope) {
		return function() {
			scope.$apply(function() {
				deferred.resolve(reader.result);
			});
		};
	};

	var onError = function(reader, deferred, scope) {
		return function() {
			scope.$apply(function() {
				deferred.reject(reader.result);
			});
		};
	};

	var onProgress = function(reader, scope) {
		return function(event) {
			scope.$broadcast("fileProgress", {
				total : event.total,
				loaded : event.loaded
			});
		};
	};

	var getReader = function(deferred, scope) {
		var reader = new FileReader();
		reader.onload = onLoad(reader, deferred, scope);
		reader.onerror = onError(reader, deferred, scope);
		reader.onprogress = onProgress(reader, scope);
		return reader;
	};

	var readAsDataURL = function(file, scope) {
		var deferred = $q.defer();

		var reader = getReader(deferred, scope);
		reader.readAsDataURL(file);

		return deferred.promise;
	};

	return {
		readAsDataUrl : readAsDataURL
	};
};

app.factory("fileReader", [ "$q", "$log", fileReader ]);
