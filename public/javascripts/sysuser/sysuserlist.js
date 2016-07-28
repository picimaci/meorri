angular.module("SysUserList", ['bgf.paginateAnything', 'session-store', 'angular-loading-bar', 'angular-utils'])
    .config(function ($httpProvider) {
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    })
    .factory('SysUserListService', SysUserListService)
    .controller("SysUserListCtrl", SysUserListCtrl);

SysUserListService.$inject = ['$http', '$q'];
function SysUserListService($http, $q) {
    var service = {
        deleteSysUser: deleteSysUser,
        activateSysUser: activateSysUser,
        deactivateSysUser: deactivateSysUser,
    };

    return service;

    function doJobPost(uri) {
        var deferred = $q.defer();
        $http.post(uri.url)
            .success(function (data) {
                deferred.resolve(data);
            });
        return deferred.promise;
    }

    function deleteSysUser(id) {
        return doJobPost(jsRoutes.controllers.sysuser.list.SysUserListCtrl.delete(id));
    }

    function activateSysUser(id) {
        return doJobPost(jsRoutes.controllers.sysuser.list.SysUserListCtrl.activate(id));
    }

    function deactivateSysUser(id) {
        return doJobPost(jsRoutes.controllers.sysuser.list.SysUserListCtrl.deactivate(id));
    }
}

SysUserListCtrl.$inject = ['$scope', 'SysUserListService'];
function SysUserListCtrl($scope, SysUserListService) {
    var message = "";
    $scope.passive = 'true';
    $scope.perPage = 10;
    $scope.page = 0;
    $scope.clientLimit = 250;
    $scope.url = jsRoutes.controllers.sysuser.list.SysUserListCtrl.listAjax().url;

    $scope.tempParams = {
        lastTouch: "",
        name: "",
        email: "",
        isActive: "ALL"
    };

    $scope.urlParams = {
        lastTouch: "",
        name: "",
        email: "",
        isActive: "ALL"
    };

    $scope.init = function () {
        $scope.url = jsRoutes.controllers.sysuser.list.SysUserListCtrl.listAjax().url;
        $scope.passive = false;
        $scope.urlParams.lastTouch = new Date().getTime();
    };

    $scope.refresh = function () {
        $scope.urlParams.lastTouch = new Date().getTime();
    };

    $scope.filter = function () {
        $scope.urlParams.lastTouch = $scope.tempParams.lastTouch;
        $scope.urlParams.name = $scope.tempParams.name;
        $scope.urlParams.isActive = $scope.tempParams.isActive;
        $scope.urlParams.email = $scope.tempParams.email;
    };

    $scope.clear = function () {
        $scope.tempParams.name = "";
        $scope.tempParams.isActive = "ALL";
        $scope.tempParams.email = "";
        $scope.filter();
    };

    $scope.changeSort = function (sortBy) {
        if ($scope.urlParams.sortBy != sortBy) {
            $scope.urlParams.sortOrder = true;
        } else {
            $scope.urlParams.sortOrder = !$scope.urlParams.sortOrder;
        }
        $scope.urlParams.sortBy = sortBy;
    };

    $scope.edit = function (id) {
        location.href = jsRoutes.controllers.sysuser.edit.SysUserEditCtrl.index(id).url;
    };

    $scope.import = function() {
        location.href = jsRoutes.controllers.sysuser.importsysuser.SysUserImportCtrl.index().url;
    }

    $scope.activate = function(id) {
        pushConfirm(Messages('sysuserlist.question.activate'), doActivate, id);
    }

    doActivate = function(id) {
        SysUserListService.activateSysUser(id).then(function(data) {
            message = Messages('sysuserlist.activate.message.success');
            $scope.refresh();
        }, function(reason) {
            console.log(reason);
            pushError(Messages('global.error.getdata.title'), Messages('global.error.getdata.message'));
        });
    };

    $scope.deactivate = function(id) {
        pushConfirm(Messages('sysuserlist.question.deactivate'), doDeactivate, id);
    }

    doDeactivate = function(id) {
        SysUserListService.deactivateSysUser(id).then(function(data) {
            message = Messages('sysuserlist.deactivate.message.success');
            $scope.refresh();
        }, function(reason) {
            console.log(reason);
            pushError(Messages('global.error.getdata.title'), Messages('global.error.getdata.message'));
        });
    };

    $scope.delete = function (id) {
        pushConfirm(Messages("global.confirm.delete", Messages("sysuser")), doDelete, id);
    };

    function doDelete(id) {
        SysUserListService.deleteSysUser(id).then(function () {
            pushSuccess(Messages('global.message.delete.success', Messages("sysuser")));
            $scope.refresh();
        });
    };

    $scope.$on('pagination:loadPage', function (event, status, config) {
        if (message != "") {
            pushSuccess(message);
            message = "";
        }
    });

    $scope.$on('pagination:error', function (event, status, config) {
        pushError(Messages('global.error.getdata.title'), Messages('global.error.getdata.message'));
    });

}
