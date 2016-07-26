jQuery(document).ready(function () {
    jQuery('[data-toggle="modal"]').click(function (e) {
        e.preventDefault();
        var url = jQuery(this).attr('href');
        var target = jQuery(this).attr('data-target');
        jQuery(target).load(url);
    });
});

toastr.options = {
    "closeButton": true,
    "debug": false,
    "positionClass": "toast-bottom-full-width",
    "onclick": null,
    "showDuration": "1000",
    "hideDuration": "1000",
    "timeOut": "1500",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
};

var pushSuccess = function (title, message) {
    pushToast("success", title, message);
};
var pushInfo = function (title, message) {
    pushToast("info", title, message);
};
var pushWarning = function (title, message) {
    pushToast("warning", title, message);
};
var pushError = function (title, message) {
    pushToast("error", title, message);
};
var pushToast = function (type, title, message) {
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "positionClass": "toast-bottom-full-width",
        "onclick": null,
        "showDuration": "1000",
        "hideDuration": "1000",
        "timeOut": "30000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };
    toastr[type](message, title);
};

var pushToastTimeout = function (type, title, message, timeout) {
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "positionClass": "toast-bottom-full-width",
        "onclick": null,
        "showDuration": "1000",
        "hideDuration": "1000",
        "timeOut": timeout,
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };
    toastr[type](message, title);
};

//TODO Suxy 2016.07.02 - szerintem felesleges, nem mertem törölni, de ha működik enélkül is minden, akkor delete
//var pushConfirm = function (question, okAction, objectId) {
//    bootbox.dialog({
//        message: question,
//        title: "",
//        buttons: {
//            success: {
//                label: "Ok",
//                className: "btn-success",
//                callback: function () {
//                    var fn = window[okAction];
//                    if (typeof fn === "function") {
//                        fn(objectId);
//                    }
//
//                }
//            },
//            cancel: {
//                label: "Cancel",
//                className: "btn-default",
//                callback: function () {
//
//                }
//            }
//        }
//    });
//};

var pushConfirm = function (question, okAction, objectId, objectId2) {
    bootbox.dialog({
        message: question,
        title: "",
        buttons: {
            success: {
                label: "Ok",
                className: "btn-success",
                callback: function () {
                    okAction(objectId, objectId2);
                }
            },
            cancel: {
                label: "Cancel",
                className: "btn-default",
                callback: function () {

                }
            }
        }
    });
};
