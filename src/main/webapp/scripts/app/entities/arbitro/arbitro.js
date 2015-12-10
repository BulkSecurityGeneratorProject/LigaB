'use strict';

angular.module('ligabasquetApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('arbitro', {
                parent: 'entity',
                url: '/arbitros',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ligabasquetApp.arbitro.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/arbitro/arbitros.html',
                        controller: 'ArbitroController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('arbitro');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('arbitro.detail', {
                parent: 'entity',
                url: '/arbitro/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ligabasquetApp.arbitro.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/arbitro/arbitro-detail.html',
                        controller: 'ArbitroDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('arbitro');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Arbitro', function($stateParams, Arbitro) {
                        return Arbitro.get({id : $stateParams.id});
                    }]
                }
            })
            .state('arbitro.new', {
                parent: 'arbitro',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/arbitro/arbitro-dialog.html',
                        controller: 'ArbitroDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {nombre: null, edad: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('arbitro', null, { reload: true });
                    }, function() {
                        $state.go('arbitro');
                    })
                }]
            })
            .state('arbitro.edit', {
                parent: 'arbitro',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/arbitro/arbitro-dialog.html',
                        controller: 'ArbitroDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Arbitro', function(Arbitro) {
                                return Arbitro.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('arbitro', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
