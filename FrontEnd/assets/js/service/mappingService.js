mapping.service('mappingService', ["$q", "$http", function ($q, $http) {
    this.getMappingLists = function (mappingType) {
        return $http.get(URLRoot+"/mappingController/getMappingForm/"+mappingType).then(
            function (response) {
                return response.data;
            }, function (response) {
                d.reject(response.data || "Request failed");
            }
        );
    }

    this.mappingSave = function (mappingForm) {
        var d = $q.defer();
        $http.post(URLRoot+"/mappingController/saveMappingResult", mappingForm)
            .then(function (response) {
                d.resolve(response.data);
            }, function (response) {
                d.reject(response.data || "Request failed");
            });
        return d.promise;
    }

    this.reloadObjects = function (mappingType) {
        return $http.get(URLRoot+"/mappingController/reloadObjects/"+mappingType).then(
            function (response) {
                return response.data;
            }, function (response) {
                d.reject(response.data || "Request failed");
            }
        );
    }
   
}]);