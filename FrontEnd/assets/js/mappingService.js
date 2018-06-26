mapping.service('mappingService', ["$q", "$http", function ($q, $http) {
    this.getMappingLists = function (mappingType) {
        return $http.get("http://localhost:8080/mappingController/getMappingForm/"+mappingType).then(
            function (response) {
                return response.data;
            }, function (response) {
                d.reject(response.data || "Request failed");
            }
        );
    }

    this.mappingSave = function (mappingForm) {
        var d = $q.defer();
        $http.post("http://localhost:8080/mappingController/saveMappingResult", mappingForm)
            .then(function (response) {
                d.resolve(response.data);
            }, function (response) {
                d.reject(response.data || "Request failed");
            });
        return d.promise;
    }
    this.loginService = function (loginData) {
        var d = $q.defer();
        $http.post("http://localhost:8080/loginController/login", loginData)
            .then(function (response) {
                d.resolve(response.data);
            }, function (response) {
                d.reject(response.data || "Request failed");
            });
        return d.promise;
    }
}]);