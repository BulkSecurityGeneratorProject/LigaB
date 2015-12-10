'use strict';

angular.module('ligabasquetApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
