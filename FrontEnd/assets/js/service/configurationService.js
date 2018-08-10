mapping.service('configurationService', ["$q", "$http", function ($q, $http) {

    this.getCopmanyConfiguration = function () {
        return $http.get(URLRoot+"/configurationController/CompanySetting").then(
            function (response) {
                return response.data;
            }, function (response) {
                d.reject(response.data || "Request failed");
            }
        );
    }


    this.saveConfiguration = function (configurationForm) {
        var d = $q.defer();
        $http.post(URLRoot+"/configurationController/saveCompanySetting", configurationForm)
            .then(function (response) {
                d.resolve(response.data);
            }, function (response) {
                d.reject(response.data || "Request failed");
            });
        return d.promise;
    }

    
    this.updateXmlPath = function (configurationForm) {
        var d = $q.defer();
        $http.post(URLRoot+"/configurationController/updateXmlPath", configurationForm)
            .then(function (response) {
                d.resolve(response.data);
            }, function (response) {
                d.reject(response.data || "Request failed");
            });
        return d.promise;
    }

    this.addProduct = function (configurationForm) {
        var d = $q.defer();
        $http.post(URLRoot+"/configurationController/addProduct", configurationForm)
            .then(function (response) {
                d.resolve(response.data);
            }, function (response) {
                d.reject(response.data || "Request failed");
            });
        return d.promise;
    }
   

}]);