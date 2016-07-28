angular.module("SysUserImport", ['angularFileUpload'])
    .config(function ($httpProvider) {
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    })
    .factory('SysUserImportService', SysUserImportService)
    .controller("SysUserImportCtrl", SysUserImportCtrl);

SysUserImportService.$inject = ['$http', '$q'];
function SysUserImportService($http, $q) {
    var service = {
    };

    return service;
}

SysUserImportCtrl.$inject = ['$scope', 'FileUploader', 'SysUserImportService', '$window'];
function SysUserImportCtrl($scope, FileUploader, SysUserImportService, $window) {
    $scope.url = jsRoutes.controllers.sysuser.importsysuser.SysUserImportCtrl.index().url;

    $scope.urlParams = {
        lastTouch: ""
    };

    $scope.init = function () {
        $scope.urlParams.lastTouch = new Date().getTime();
    };

    var uploader = $scope.uploader = new FileUploader({url: jsRoutes.controllers.sysuser.importsysuser.SysUserImportCtrl.importSysUsersFromCSV().url});

    // FILTERS

    uploader.filters.push({
        name: 'customFilter',
        fn: function (item /*{File|FileLikeObject}*/, options) {
            return this.queue.length < 10;
        }
    });

    uploader.onAfterAddingFile = function (item) {
        var extn = item.file.name.split(".").pop();
        if(extn != 'csv') {
            item.remove();
            pushError(Messages("global.import.csvextensionerror"));
        }
    };

    uploader.onSuccessItem = function (item, response, status, headers) {
        pushSuccess(Messages("global.import.success", Messages("sysuser")));
    }

    uploader.onErrorItem = function (item, response, status, headers){
        pushError(Messages(response));
    }

    $scope.done = function () {
        $window.location.href = jsRoutes.controllers.sysuser.list.SysUserListCtrl.index().url;
    }

    $scope.refresh = function () {
        $scope.urlParams.lastTouch = new Date().getTime();
    };
}
