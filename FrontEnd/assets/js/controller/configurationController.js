mapping.controller('configurationController', function ($scope, configurationService,$state) {
    $scope.configurationForm={};
    $scope.statusMessage = {};
    $scope.statusMessage.code = 0 ;
    $scope.statusMessage.message = "";

    $scope.loadCompanySetting = function () {
        configurationService.getCopmanyConfiguration().then(function (result) {
            if(result.errorCode !=200){

            }else {
                $scope.configurationForm=result;
            }
        });
    }
    $scope.loadCompanySetting();

    $scope.saveCompanySetting=function(){
        configurationService.saveConfiguration($scope.configurationForm).then(function(result){
            if(result.errorCode !=200){
                $scope.statusMessage.message = "failed";
                $scope.statusMessage.code = 250;
            }else {
                $scope.configurationForm=result;
                $scope.statusMessage.message = "Configuration Saved Successfully";
                $scope.statusMessage.code = 200;
            }
        });

    
    
    }

});  