'use strict';

angular.module('ligabasquetApp')
    .factory('Arbitro', function ($resource, DateUtils) {
        return $resource('api/arbitros/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
