mapping.controller('mappingController_multi', function ($scope, mappingService,$state) {
    var i;
    $scope.statusMessage = {};
    $scope.statusMessage.code = 0 ;
    $scope.statusMessage.message = "";
    $scope.mappingForm={};
    
    if ($state.current.name == 'services.mapping.category'){
        $scope.mappingType=1;
    } else if ($state.current.name == 'services.mapping.product'){
        $scope.mappingType=4;
    }
    $scope.loadMappingList = function () {
        mappingService.getMappingLists($scope.mappingType).then(function (result) {
            if(result.errorCode !=200){

            }else {
                $scope.mappingForm=result;
                $scope.destinationList=$scope.mappingForm.destinationList;
                $scope.sourceList=$scope.mappingForm.sourceList;
            }
        });
    }
    $scope.loadMappingList();

    $scope.deleteItem = function (item, parent) {
        var parentIndex = $scope.sourceList.indexOf(parent);
        var itemIndex = $scope.sourceList[parentIndex].childrenList.indexOf(item);
        $scope.sourceList[parentIndex].childrenList.splice(itemIndex, 1);
        $scope.destinationList.push(item);
        if(item.ismapped){
        var deletedMapping={};
        //deletedMapping.sourceId=parent.id;
        //deletedMapping.destinationId=item.id;
        deletedMapping.id=item.mappingId;
        $scope.mappingForm.deletedList.push(deletedMapping);
        }
    }
    $scope.showMe = function () {
        if($scope.sourceList !== undefined){
        for (i = 0; i < $scope.sourceList.length; i++) {
            if ($scope.sourceList[i].childrenList.length > 0) {
                return true;
            }
        }
      }
    }
    $scope.mapping = function () {
        $scope.mappingForm.mappingType=$scope.mappingType;
        mappingService.mappingSave($scope.mappingForm).then(function (result) {
            if(result.errorCode !=200){
                alert("Failed to Save");
            }else {
                $scope.statusMessage.message = "Mapping Saved Successfully";
                $scope.statusMessage.code = 200;           
            }
        });
    }

    $scope.statusClose = function(){
        $('.notificationMessage').addClass('ng-hide');
    }
});

mapping.controller('mappingController_single', function ($scope, mappingService,$state) {
    var i;
    $scope.statusMessage = {};
    $scope.statusMessage.code = 0 ;
    $scope.statusMessage.message = "";
    
    if ($state.current.name == 'services.mapping.color'){
        $scope.mappingType=2;
    }else if ($state.current.name == 'services.mapping.size'){
        $scope.mappingType=3;
    }
    $scope.loadMappingList = function () {
        mappingService.getMappingLists($scope.mappingType).then(function (result) {
            if(result.errorCode !=200){

            }else {
                $scope.mappingForm=result;
                $scope.destinationList=$scope.mappingForm.destinationList;
                $scope.sourceList=$scope.mappingForm.sourceList;
            }
        });
    }
    $scope.loadMappingList();
    $scope.deleteItem = function (item, parent) {
        var parentIndex = $scope.sourceList.indexOf(parent);
        $scope.sourceList[parentIndex].children = null;
        $scope.destinationList.push(item);
        if(item.ismapped){
        var deletedMapping={};
        //deletedMapping.sourceId=parent.id;
        //deletedMapping.destinationId=item.id;
        deletedMapping.id=item.mappingId;
        $scope.mappingForm.deletedList.push(deletedMapping);
        }
    }
    $scope.showMe = function () {
        if($scope.sourceList !== undefined){
        for (i = 0; i < $scope.sourceList.length; i++) {
            if ($scope.sourceList[i].children != null) {
                return true;
            }
        }
      }
    }
    $scope.mapping = function () {
        $scope.mappingForm.mappingType=$scope.mappingType;
        mappingService.mappingSave($scope.mappingForm).then(function (result) {
            if(result.errorCode !=200){
                alert("Failed to Save");
            }else {
                $scope.statusMessage.message = "Mapping Saved Successfully";
                $scope.statusMessage.code = 200;
               
            }
        });
    }
    $scope.statusClose = function(){
        $('.notificationMessage').addClass('ng-hide');
    }
});
