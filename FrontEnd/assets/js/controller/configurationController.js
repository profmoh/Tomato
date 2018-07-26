mapping.controller('configurationController', function ($scope, configurationService,$state) {
    $scope.configurationForm={};
    $scope.statusMessage = {};
    $scope.statusMessage.code = 0 ;
    $scope.statusMessage.message = "";
    $scope.loadCompanySetting = function () {
        configurationService.getCopmanyConfiguration().then(function (result) {
            if(result.errorCode !=200){
                $scope.statusMessage.message = "failed";
                $scope.statusMessage.code = '250';
            }else {
                $scope.configurationForm=result;
            }
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
        });
    }

    $scope.updateXmlPath=function(){
        configurationService.updateXmlPath($scope.configurationForm).then(function(result){
            if(result.errorCode !=200){
                $scope.statusMessage.message = "failed to update xml";
                $scope.statusMessage.code = '250';
            }else {
                $scope.configurationForm=result;
                $scope.statusMessage.message = "XmlPath updated Successfully";
                $scope.statusMessage.code = '200';
            }
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
                $scope.statusMessage.message = "Product added Successfully";
                $scope.statusMessage.code = '200';
            }
        });

    }

});  