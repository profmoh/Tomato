mapping.controller('configurationController', function ($scope, configurationService,$state,$timeout) {
    $scope.configurationForm={};
    $scope.statusMessage = {};
    $scope.statusMessage.code = 0 ;
    $scope.statusMessage.message = "";
    $scope.loadCompanySetting = function () {
        configurationService.getCopmanyConfiguration().then(function (result) {
            if(result.errorCode !=200){
                $scope.statusMessage.message = "Faild To Load Company Setting";
                $scope.statusMessage.code = '250';
            }else {
                $scope.configurationForm=result;
            }
            $timeout(function(){
                $('.notificationMessage').addClass('ng-hide');
            },5000)
        });
    }
    $scope.loadCompanySetting();

    $scope.saveCompanySetting=function(){
        configurationService.saveConfiguration($scope.configurationForm).then(function(result){
            if(result.errorCode !=200){
                $scope.statusMessage.message = result.errorMessage;
                $scope.statusMessage.code = '250';
            }else {
                $scope.configurationForm=result;
                $scope.statusMessage.message = "Configuration Saved Successfully";
                $scope.statusMessage.code = '200';
            }
            $timeout(function(){
                $('.notificationMessage').addClass('ng-hide');
            },5000)
        });
    }

    $scope.updateXmlPath=function(){
        $scope.configurationForm.filePath=$scope.data;
        configurationService.updateXmlPath($scope.configurationForm).then(function(result){
            if(result.errorCode !=200){
                $scope.statusMessage.message = "Failed To Update XML";
                $scope.statusMessage.code = '250';
            }else {
                $scope.configurationForm=result;
                $scope.statusMessage.message = "XmlPath Updated Successfully";
                $scope.statusMessage.code = '200';
            }
            $timeout(function(){
                $('.notificationMessage').addClass('ng-hide');
            },5000)
        });

    
    
    }
    $scope.uploadXmlFile = function(){
        $('.inputXml').val($('.xmlPath').val().replace(/C:\\fakepath\\/i, ''));
        var file = document.getElementById('importFile').files[0],
        reader = new FileReader();
        reader.onloadend = function(e){
            $scope.data = e.target.result;
        };
        reader.readAsBinaryString(file);

      };

    $scope.fileInputchange=function(){
        $scope.fileInput = $('.xmlPath').val();
        $('.inputXml').val($('.xmlPath').val());
    }

    $scope.submit=function(){

        $scope.configurationForm.filePath=$scope.data;
        configurationService.addProduct($scope.configurationForm).then(function(result){
            if(result.errorCode !=200){
                $scope.statusMessage.message = result.errorMessage;
                $scope.statusMessage.code = '250';
            }else {
                $scope.configurationForm=result;
                $scope.statusMessage.message = "Product Added Successfully";
                $scope.statusMessage.code = '200';
            } 
            $timeout(function(){
                $('.notificationMessage').addClass('ng-hide');
            },5000)
        });

    }
    $scope.statusClose = function(){
        $('.notificationMessage').addClass('ng-hide');
    }
});  