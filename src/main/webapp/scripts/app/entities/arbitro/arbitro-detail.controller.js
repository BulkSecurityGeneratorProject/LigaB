'use strict';

angular.module('ligabasquetApp')
    .controller('ArbitroDetailController', function ($scope, $rootScope, $stateParams, entity, Arbitro, Liga) {
        $scope.arbitro = entity;
        $scope.load = function (id) {
            Arbitro.get({id: id}, function(result) {
                $scope.arbitro = result;
            });
        };
        $rootScope.$on('ligabasquetApp:arbitroUpdate', function(event, result) {
            $scope.arbitro = result;
        });
    });
