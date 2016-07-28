angular.module("SysUserProfileEdit", ['angular-loading-bar'])
    .config(function($httpProvider) {
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    })
    .factory('SysUserProfileEditService', SysUserProfileEditService)
    .controller('SysUserProfileEditCtrl', SysUserProfileEditCtrl);

SysUserProfileEditService.$inject = ['$http', '$q'];

function SysUserProfileEditService($http, $q) {
    var service = {
        getSysUserProfile: getSysUserProfile,
        saveSysUserProfile: saveSysUserProfile,
        getLanguages: getLanguages
    };
    return service;


    function getSysUserProfile() {
        var deferred = $q.defer();
        $http.get(jsRoutes.controllers.sysuser.profile.SysUserProfileEditCtrl.get().url)
            .success(function(data, status) {
                deferred.resolve(data);
            })
            .error(function(data, status){
                deferred.reject(status);
            });
        return deferred.promise;

    }

    function saveSysUserProfile(data) {
        var deferred = $q.defer();
        $http.post(jsRoutes.controllers.sysuser.profile.SysUserProfileEditCtrl.save(data).url, data)
            .success(function(data, status) {
                deferred.resolve(data);
            })
            .error(function(data, status){
                deferred.reject({});
            });
        return deferred.promise;
    }

    function getLanguages() {
        var deferred = $q.defer();
        $http.get(jsRoutes.controllers.sysuser.service.SysUserServiceCtrl.getLanguages().url)
            .success(function (data, status) {
                deferred.resolve(data);
            })
            .error(function (data, status) {
                deferred.reject(status);
            });
        return deferred.promise;
    }
}

SysUserProfileEditCtrl.$inject = ['$scope','SysUserProfileEditService'];

function SysUserProfileEditCtrl($scope, SysUserProfileEditService) {
    var vm = this;
    vm.sent = false;
    vm.sysUser = {};
    vm.languages = {};
    vm.save = save;
    vm.cancel = cancel;
    
    $scope.init = function() {
        SysUserProfileEditService.getSysUserProfile().then(function(data) {
            vm.sysUser = data;
        });
        SysUserProfileEditService.getLanguages().then(function (data) {
            vm.languages = data;
        });
    }

    function save() {
        vm.sent = true;
        if (vm.sysUserProfileEditForm.$valid) {
            vm.sysUser.isActive = true;
            SysUserProfileEditService.saveSysUserProfile(vm.sysUser).then(function (data) {
                cancel();
            }, function (reason) {
                pushError(Messages('global.error.getdata.title'), Messages('global.error.getdata.message'));
            });
        }
    }

    function cancel() {
        // location.href = jsRoutes.controllers.Application.index().url;
        location.href = history.back();
    }
}