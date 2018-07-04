mapping.service('configurationService', ["$q", "$http", function ($q, $http) {

    this.getCopmanyConfiguration = function (mappingType) {
        return $http.get("http://localhost:8080/configurationController/CompanySetting").then(
            function (response) {
                return response.data;
            }, function (response) {
                d.reject(response.data || "Request failed");
            }
        );
    }


    this.saveConfiguration = function (configurationForm) {
        var d = $q.defer();
        $http.post("http://localhost:8080/configurationController/saveCompanySetting", configurationForm)
            .then(function (response) {
                d.resolve(response.data);
            }, function (response) {
                d.reject(response.data || "Request failed");
            });
        return d.promise;
    }
   

}]);