mapping.service('loginService', ["$q", "$http", function ($q, $http) {
    this.login = function (loginData) {
        var d = $q.defer();
        $http.post(URLRoot +"/loginController/login", loginData)
            .then(function (response) {
                d.resolve(response.data);
            }, function (response) {
                d.reject(response.data || "Request failed");
            });
        return d.promise;
    }
}]);