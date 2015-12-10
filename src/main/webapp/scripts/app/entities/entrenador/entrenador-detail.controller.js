'use strict';

angular.module('ligabasquetApp')
    .controller('EntrenadorDetailController', function ($scope, $rootScope, $stateParams, entity, Entrenador) {
        $scope.entrenador = entity;
        $scope.load = function (id) {
            Entrenador.get({id: id}, function(result) {
                $scope.entrenador = result;
            });
        };
        $rootScope.$on('ligabasquetApp:entrenadorUpdate', function(event, result) {
            $scope.entrenador = result;
        });
    });
