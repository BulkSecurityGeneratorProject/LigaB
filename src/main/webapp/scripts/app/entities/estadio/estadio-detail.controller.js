'use strict';

angular.module('ligabasquetApp')
    .controller('EstadioDetailController', function ($scope, $rootScope, $stateParams, entity, Estadio) {
        $scope.estadio = entity;
        $scope.load = function (id) {
            Estadio.get({id: id}, function(result) {
                $scope.estadio = result;
            });
        };
        $rootScope.$on('ligabasquetApp:estadioUpdate', function(event, result) {
            $scope.estadio = result;
        });
    });
