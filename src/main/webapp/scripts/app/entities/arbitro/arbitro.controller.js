'use strict';

angular.module('ligabasquetApp')
    .controller('ArbitroController', function ($scope, Arbitro, ParseLinks) {
        $scope.arbitros = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Arbitro.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.arbitros = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Arbitro.get({id: id}, function(result) {
                $scope.arbitro = result;
                $('#deleteArbitroConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Arbitro.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteArbitroConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.arbitro = {nombre: null, edad: null, id: null};
        };
    });
