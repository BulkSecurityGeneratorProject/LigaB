'use strict';

angular.module('ligabasquetApp').controller('EstadisticasDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Estadisticas', 'Jugador', 'Partido',
        function($scope, $stateParams, $modalInstance, entity, Estadisticas, Jugador, Partido) {

        $scope.estadisticas = entity;
        $scope.jugadors = Jugador.query();
        $scope.partidos = Partido.query();
        $scope.load = function(id) {
            Estadisticas.get({id : id}, function(result) {
                $scope.estadisticas = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('ligabasquetApp:estadisticasUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.estadisticas.id != null) {
                Estadisticas.update($scope.estadisticas, onSaveFinished);
            } else {
                Estadisticas.save($scope.estadisticas, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
