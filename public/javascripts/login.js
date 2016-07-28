var app = angular.module("Login", [])
    .config(function ($httpProvider) {
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    })
    .factory('LoginService', LoginService)
    .controller('LoginCtrl', LoginCtrl);


LoginService.$inject = ['$http', '$q'];
function LoginService($http, $q) {
    var service = {
        forgotPassword: forgotPassword,
        authenticate: authenticate
    };

    return service;

    function forgotPassword(data) {
        var deferred = $q.defer();
        $http.post(jsRoutes.controllers.login.LoginCtrl.forgotPassword().url, data)
            .success(function (data, status) {
                deferred.resolve(data);
            })
            .error(function (data, status) {
                deferred.reject(status);
            });
        return deferred.promise;
    }

    function authenticate(data) {
        var deferred = $q.defer();
        $http.post(jsRoutes.controllers.login.LoginCtrl.authenticate(data).url, data)
            .success(function (data, status) {
                deferred.resolve(data);
            })
            .error(function (data, status) {
                deferred.reject(status);
            });
        return deferred.promise;
    }
}
LoginCtrl.$inject = ['$scope', 'LoginService'];
function LoginCtrl($scope, LoginService) {
    var vm = this;
    vm.sent = false;
    vm.sysUser = {};
    
    $scope.init = function() {
        jQuery.getJSON('http://ipinfo.io', function(data){
            vm.sysUser.city = data.city;
            vm.sysUser.country = data.country;
            vm.sysUser.region = data.region;
        })
    }
    
    vm.login = function(data) {
        vm.sent = true;
        if (vm.loginForm.$valid) {
            LoginService.authenticate(vm.sysUser).then(function (data) {
                location.href = data;
            });
        }
    }

    vm.forgotPassword = function () {
        LoginService.forgotPassword({email: vm.sysUser.email}).then(function (data) {
            pushSuccess(Messages('login.forgotpassword.success'));
        });
    }
}