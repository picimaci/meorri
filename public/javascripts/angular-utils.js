angular.module('angular-utils', [])
    .filter('bool', BoolFilter)
    .filter('imageNumberFilterToAngularImageUploader', ImageNumberFilterToAngularImageUploader)
    .filter('imageFilterToAngularImageUploader', ImageFilterToAngularImageUploader)
    .filter('nameFilterToAngularImageUploader', NameFilterToAngularImageUploader)
    .directive('datepicker', DatePicker)
    .directive('datetimepicker', DateTimePicker)
    .directive('filterButton', FilterButton)
    .directive('ngEnter', NgEnter)
    .directive('selectOnClick', SelectOnClick)
    .directive('angularImageUpload', AngularImageUploaderTemplate)
    .directive('extendYourReading', ExtendYourReading)
    .directive('vocabulary', Vocabulary)
    .directive('relatedVideo', RelatedVideo)
    .directive('quiz', Quiz)
    .directive('writingTip', WritingTip)
    .directive('exampleSentence', ExampleSentence)
    .directive('ngThumb', NgThumb)
    .config(HttpProvider)
;

function AngularImageUploaderTemplate() {
    return {
        restrict: 'E',
        templateUrl: function (element, attr) {
            return attr.templateUrl || '/assets/tpl/angular-image-upload.html';
        }
    }
}

function ExtendYourReading() {
    return {
        restrict: 'E',
        templateUrl: function (element, attr) {
            return attr.templateUrl || '/assets/tpl/article/extend-your-reading.html';
        }
    }
}

function Vocabulary() {
    return {
        restrict: 'E',
        templateUrl: function (element, attr) {
            return attr.templateUrl || '/assets/tpl/article/vocabulary.html';
        }
    }
}

function RelatedVideo() {
    return {
        restrict: 'E',
        templateUrl: function (element, attr) {
            return attr.templateUrl || '/assets/tpl/article/related-video.html';
        }
    }
}

function Quiz() {
    return {
        restrict: 'E',
        templateUrl: function (element, attr) {
            return attr.templateUrl || '/assets/tpl/article/quiz.html';
        }
    }
}

function WritingTip() {
    return {
        restrict: 'E',
        templateUrl: function (element, attr) {
            return attr.templateUrl || '/assets/tpl/reporttypestep/writing-tip.html';
        }
    }
}

function ExampleSentence() {
    return {
        restrict: 'E',
        templateUrl: function (element, attr) {
            return attr.templateUrl || '/assets/tpl/reporttypestep/example-sentence.html';
        }
    }
}

function NgThumb($window) {
    var helper = {
        support: !!($window.FileReader && $window.CanvasRenderingContext2D),
        isFile: function (item) {
            return angular.isObject(item) && item instanceof $window.File;
        },
        isImage: function (file) {
            var type = '|' + file.name.slice(file.name.lastIndexOf('.') + 1) + '|';
            return '|jpg|'.indexOf(type) !== -1;
        }
    };

    return {
        restrict: 'A',
        template: '<canvas/>',
        link: function (scope, element, attributes) {
            if (!helper.support) return;

            var params = scope.$eval(attributes.ngThumb);

            if (!helper.isFile(params.file)) return;
            if (!helper.isImage(params.file)) return;

            var canvas = element.find('canvas');
            var reader = new FileReader();

            reader.onload = onLoadFile;
            reader.readAsDataURL(params.file);

            function onLoadFile(event) {
                var img = new Image();
                img.onload = onLoadImage;
                img.src = event.target.result;
            }

            function onLoadImage() {
                var width = params.width || this.width / this.height * params.height;
                var height = params.height || this.height / this.width * params.width;
                canvas.attr({width: width, height: height});
                canvas[0].getContext('2d').drawImage(this, 0, 0, width, height);
            }
        }
    };
}

function ImageFilterToAngularImageUploader() {
    return function (item) {
        var type = '|' + item.name.slice(item.name.lastIndexOf('.') + 1) + '|';
        if ('|jpg|'.indexOf(type) !== -1) {
            return true;
        } else {
            pushError(Messages("angularfileuploader.validation.wrong.extension", item.name));
            return false;
        }
    }
}

function NameFilterToAngularImageUploader(images) {
    return function (item) {
        for (var i = 0; i < images.length; i++) {
            if (item.name == images[i]._file.name) {
                pushError(Messages("angularfileuploader.validation.duplicate.file.name", item.name));
                return false;
            }
        }
        return true;
    }
}

function ImageNumberFilterToAngularImageUploader(images) {
    return function (item) {
        if (images.length > 0) {
            pushError(Messages("angularfileuploader.validation.morethanone.images"));
            return false;
        }
        return true;
    }
}


function HttpProvider($httpProvider) {
    $httpProvider.interceptors.push(function ($q) {
        return {
            'response': function (response) {
                return response;
            },
            'responseError': function (response) {
                if (response.status == 403) {
                    location.href = jsRoutes.controllers.ErrorHandler.forbidden().url
                }
                if (response.data.title != undefined && response.data.message != undefined) {
                    pushError(response.data.title, response.data.message)
                } else {
                    pushError(Messages("global.error.title"), Messages("global.error.something.went.wrong"))
                }
                return $q.reject(response);
            }
        };
    });
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    $httpProvider.defaults.headers.common["Cache-Control"] = "no-cache";
    $httpProvider.defaults.headers.common.Pragma = "no-cache";
}

function DatePicker() {
    return function (scope, element, attrs) {
        element.datepicker({
            dateFormat: 'd M, yy',
            firstDay: 1,
            showButtonPanel: true,
            beforeShow: function (element, datepicker) {
                if (attrs.minDate) {
                    angular.element(element).datepicker("option", "minDate", attrs.minDate);
                }
                if (attrs.maxDate) {
                    angular.element(element).datepicker("option", "maxDate", attrs.maxDate);
                }
            }
        });
    }
}

function DateTimePicker() {
    return function (scope, element, attrs) {
        element.datetimepicker({
            dateFormat: 'd M, yy',
            timeFormat: 'HH:mm',
            firstDay: 1,
            showButtonPanel: true
        });
    }
}

function FilterButton() {
    return {
        restrict: 'E',
        templateUrl: function (element, attr) {
            return attr.templateUrl || 'tpl/filter-button.html';
        }
    }
}

function NgEnter() {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if (event.which === 13) {
                scope.$apply(function () {
                    scope.$eval(attrs.ngEnter);
                });

                event.preventDefault();
            }
        });
    }
}
function SelectOnClick() {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            element.on('click', function () {
                this.select();
            });
        }
    };
}

function BoolFilter() {
    return function (bool) {
        if (bool == true) return Messages('global.true');
        if (bool == false) return Messages('global.false');
    }
}
