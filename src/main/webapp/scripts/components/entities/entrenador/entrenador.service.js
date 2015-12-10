'use strict';

angular.module('ligabasquetApp')
    .factory('Entrenador', function ($resource, DateUtils) {
        return $resource('api/entrenadors/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.edad = DateUtils.convertLocaleDateFromServer(data.edad);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.edad = DateUtils.convertLocaleDateToServer(data.edad);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.edad = DateUtils.convertLocaleDateToServer(data.edad);
                    return angular.toJson(data);
                }
            }
        });
    });
