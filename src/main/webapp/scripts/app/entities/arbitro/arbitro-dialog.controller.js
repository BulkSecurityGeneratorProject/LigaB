'use strict';

angular.module('ligabasquetApp').controller('ArbitroDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Arbitro', 'Liga',
        function($scope, $stateParams, $modalInstance, entity, Arbitro, Liga) {

        $scope.arbitro = entity;
        $scope.ligas = Liga.query();
        $scope.load = function(id) {
            Arbitro.get({id : id}, function(result) {
                $scope.arbitro = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('ligabasquetApp:arbitroUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.arbitro.id != null) {
                Arbitro.update($scope.arbitro, onSaveFinished);
            } else {
                Arbitro.save($scope.arbitro, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
