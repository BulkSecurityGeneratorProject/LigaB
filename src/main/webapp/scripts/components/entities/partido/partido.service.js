'use strict';

angular.module('ligabasquetApp')
    .factory('Partido', function ($resource, DateUtils) {
        return $resource('api/partidos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.horaInicio = DateUtils.convertDateTimeFromServer(data.horaInicio);
                    data.horaFinal = DateUtils.convertDateTimeFromServer(data.horaFinal);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
