'use strict';

angular.module('ligabasquetApp')
    .controller('EstadisticasController', function ($scope, Estadisticas, ParseLinks) {
        $scope.estadisticass = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Estadisticas.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.estadisticass = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Estadisticas.get({id: id}, function(result) {
                $scope.estadisticas = result;
                $('#deleteEstadisticasConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Estadisticas.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEstadisticasConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.estadisticas = {canastas: null, faltas: null, asistencias: null, rebotes: null, id: null};
        };
    });
