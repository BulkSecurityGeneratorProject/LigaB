 'use strict';

angular.module('ligabasquetApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-ligabasquetApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-ligabasquetApp-params')});
                }
                return response;
            },
        };
    });