'use strict';

angular.module('ligabasquetApp').controller('EquipoDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Equipo', 'Entrenador', 'Estadio', 'Jugador', 'Socio', 'Temporada',
        function($scope, $stateParams, $modalInstance, $q, entity, Equipo, Entrenador, Estadio, Jugador, Socio, Temporada) {

        $scope.equipo = entity;
        $scope.entrenadors = Entrenador.query({filter: 'equipo-is-null'});
        $q.all([$scope.equipo.$promise, $scope.entrenadors.$promise]).then(function() {
            if (!$scope.equipo.entrenador.id) {
                return $q.reject();
            }
            return Entrenador.get({id : $scope.equipo.entrenador.id}).$promise;
        }).then(function(entrenador) {
            $scope.entrenadors.push(entrenador);
        });
        $scope.estadios = Estadio.query({filter: 'equipo-is-null'});
        $q.all([$scope.equipo.$promise, $scope.estadios.$promise]).then(function() {
            if (!$scope.equipo.estadio.id) {
                return $q.reject();
            }
            return Estadio.get({id : $scope.equipo.estadio.id}).$promise;
        }).then(function(estadio) {
            $scope.estadios.push(estadio);
        });
        $scope.jugadors = Jugador.query({filter: 'equipo-is-null'});
        $q.all([$scope.equipo.$promise, $scope.jugadors.$promise]).then(function() {
            if (!$scope.equipo.jugador.id) {
                return $q.reject();
            }
            return Jugador.get({id : $scope.equipo.jugador.id}).$promise;
        }).then(function(jugador) {
            $scope.jugadors.push(jugador);
        });
        $scope.socios = Socio.query();
        $scope.temporadas = Temporada.query();
        $scope.load = function(id) {
            Equipo.get({id : id}, function(result) {
                $scope.equipo = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('ligabasquetApp:equipoUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.equipo.id != null) {
                Equipo.update($scope.equipo, onSaveFinished);
            } else {
                Equipo.save($scope.equipo, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
