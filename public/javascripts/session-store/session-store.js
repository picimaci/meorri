angular.module('session-store', ['angular-loading-bar'])
	.factory('SessionStoreService', SessionStoreService)
	.controller('SessionStoreCtrl', SessionStoreCtrl);

SessionStoreService.$inject = ['$http', '$q'];
function SessionStoreService($http, $q) {
	var service = {
		getSessionStore: getSessionStore
	};
	return service;

	////////////
    function getSessionStore(sessionStoreName) {
    	var deferred = $q.defer();
    	$http.get(jsRoutes.controllers.SessionStoreCtrl.getSession(sessionStoreName).url)
    		.success(function(data, status) {
    			deferred.resolve(data);
    		})
    		.error(function(data, status){
    			deferred.reject({});
    		});
    		return deferred.promise;
		}
	}

SessionStoreCtrl.$inject = ['SessionStoreService'];
function SessionStoreCtrl(SessionStoreService) { 
	var vm = this;
	vm.sessionStore = {};
	vm.getSessionStore = getSessionStore;
	function getSessionStore(sessionStoreName) {
		return SessionStoreService.getSessionStore(sessionStoreName).then(function(data) {
			vm.sessionStore = data;
			return vm.sessionStore;
		});
	}
}        