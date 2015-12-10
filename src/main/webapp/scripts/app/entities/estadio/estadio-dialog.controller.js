'use strict';

angular.module('ligabasquetApp').controller('EstadioDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Estadio',
        function($scope, $stateParams, $modalInstance, entity, Estadio) {

        $scope.estadio = entity;
        $scope.load = function(id) {
            Estadio.get({id : id}, function(result) {
                $scope.estadio = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('ligabasquetApp:estadioUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.estadio.id != null) {
                Estadio.update($scope.estadio, onSaveFinished);
            } else {
                Estadio.save($scope.estadio, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
