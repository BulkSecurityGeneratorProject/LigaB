'use strict';

angular.module('ligabasquetApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


