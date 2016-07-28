angular.module("SysUserEdit", ['bgf.paginateAnything'])
    .config(function ($httpProvider) {
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    })
    .factory('SysUserEditService', SysUserEditService)
    .controller("SysUserEditCtrl", SysUserEditCtrl);

SysUserEditService.$inject = ['$http', '$q'];
function SysUserEditService($http, $q) {
    var service = {
        saveSysUser: saveSysUser,
        getLanguages: getLanguages,
        getSysUser: getSysUser
    };

    return service;

    function getSysUser(id) {
        var deferred = $q.defer();
        $http.get(jsRoutes.controllers.sysuser.edit.SysUserEditCtrl.get(id).url)
            .success(function (data, status) {
                deferred.resolve(data);
            })
            .error(function (data, status) {
                deferred.reject(status);
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

    function saveSysUser(data) {
        var deferred = $q.defer();
        $http.post(jsRoutes.controllers.sysuser.edit.SysUserEditCtrl.save(data).url, data)
            .success(function (data, status) {
                deferred.resolve(data);
            })
            .error(function (data, status) {
                deferred.reject(status);
            });
        return deferred.promise;
    }
}

SysUserEditCtrl.$inject = ['$scope', 'SysUserEditService'];
function SysUserEditCtrl($scope, SysUserEditService) {
    var vm = this;
    vm.sent = false;
    vm.id = -1;
    vm.sysUser = {};
    vm.languages = {};
    vm.save = save;
    vm.cancel = cancel;

    function getSysUserCore() {
        if (vm.id > 0) {
            SysUserEditService.getSysUser(vm.id).then(function (data) {
                vm.sysUser = data;
            });
        }
        else {
            vm.sysUser = {
                id: vm.id
            }
        }
        SysUserEditService.getLanguages().then(function (data) {
            vm.languages = data;
        });
    }

    function save() {
        vm.sent = true;
        if (vm.sysUserEditForm.$valid) {
            SysUserEditService.saveSysUser(vm.sysUser).then(function (data) {
                vm.cancel();
            });
        }
    }

    function cancel() {
        location.href = jsRoutes.controllers.sysuser.list.SysUserListCtrl.index().url;
    }

    $scope.init = function () {
        getSysUserCore();
    }
}
