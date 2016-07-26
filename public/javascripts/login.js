var app = angular.module("app", [])
    .config(function ($httpProvider) {
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    })
    .factory('LoginService', LoginService)
    .controller('LoginCtrl', LoginCtrl);


LoginService.$inject = ['$http', '$q'];
function LoginService($http, $q) {
    var service = {
        forgotPassword: forgotPassword
    };

    return service;

    function forgotPassword(data) {
        console.log("posting " + data);
        var deferred = $q.defer();
        $http.post(jsRoutes.controllers.LoginCtrl.forgotPassword().url, data)
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

    vm.forgotPassword = function () {
        LoginService.forgotPassword({email: vm.email}).then(function (data) {
            pushSuccess(Messages('login.forgotpassword.success'));
        });
    }
}
