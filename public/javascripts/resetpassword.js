var app = angular.module("app", [])
    .config(function ($httpProvider) {
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    })
    .factory('ResetPasswordService', ResetPasswordService)
    .controller('ResetPasswordCtrl', ResetPasswordCtrl);


ResetPasswordService.$inject = ['$http', '$q'];
function ResetPasswordService($http, $q) {
    var service = {
        resetPassword: resetPassword
    };

    return service;

    function resetPassword(data) {
        console.log("posting " + data);
        var deferred = $q.defer();
        $http.post(jsRoutes.controllers.LoginCtrl.saveNewPassword().url, data)
            .success(function (data, status) {
                deferred.resolve(data);
            })
            .error(function (data, status) {
                deferred.reject(status);
            });
        return deferred.promise;
    }
}
ResetPasswordCtrl.$inject = ['$scope', 'ResetPasswordService'];
function ResetPasswordCtrl($scope, ResetPasswordService) {
    var vm = this;
    vm.password1 = "";
    vm.password2 = "";

    vm.resetPassword = function () {
        if (vm.password1 === vm.password2) {
            var data = {
                token: vm.token,
                password: vm.password1
            };
            ResetPasswordService.resetPassword(data).then(function () {
                window.location.href = jsRoutes.controllers.Application.index().url;
            });
        } else {
            pushError(Messages('resetpassword.passwords.didntmatch'))
        }
    }
}
