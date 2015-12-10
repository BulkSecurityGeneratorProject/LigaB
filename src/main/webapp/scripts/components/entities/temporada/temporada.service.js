'use strict';

angular.module('ligabasquetApp')
    .factory('Temporada', function ($resource, DateUtils) {
        return $resource('api/temporadas/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.year = DateUtils.convertLocaleDateFromServer(data.year);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.year = DateUtils.convertLocaleDateToServer(data.year);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.year = DateUtils.convertLocaleDateToServer(data.year);
                    return angular.toJson(data);
                }
            }
        });
    });
