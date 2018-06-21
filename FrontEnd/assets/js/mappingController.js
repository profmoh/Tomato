mapping.controller('loginController', function ($scope, mappingService,$state) {
    $scope.loginForm = {};
    $scope.submitForm = function () {
        $scope.loginForm.username = $scope.username;
        $scope.loginForm.password = $scope.password;
       mappingService.loginService($scope.loginForm).then(function (result) {
        if(result.errorCode !=200){
            alert("Failed to login");
        }else {
            $state.go('mapping');
        }
        });
    }
});


mapping.controller('mappingController_multi', function ($scope, mappingService,$state) {
    var i;
    $scope.statusMessage = {};
    $scope.statusMessage.code = 0 ;
    $scope.statusMessage.message = "";
    
    if ($state.current.name == 'mapping.category'){
        $scope.mappingType = 1;
    } else if ($state.current.name == 'mapping.product'){
        $scope.mappingType =4;
    }
    $scope.loadMappingList = function () {
        mappingService.getMappingLists($scope.mappingType).then(function (result) {
            if(result.errorCode !=200){

            }else {
                $scope.mappingForm=result;
                $scope.destinationList=$scope.mappingForm.destinationList;
            }
        });
    }
    $scope.loadMappingList();
    $scope.sourceList = [{'name': 'Category 1', 'id': "1", children: []},
        {'name': 'Category 2', 'id': "2", children: []},
        {'name': 'Category 3', 'id': "3", children: []},
        {'name': 'Category 4', 'id': "4", children: []},
        {'name': 'Category 5', 'id': "5", children: []},
        {'name': 'Category 6', 'id': "6", children: []},
        {'name': 'Category 7', 'id': "7", children: []}];

    $scope.deleteItem = function (item, parent) {
        var parentIndex = $scope.sourceList.indexOf(parent);
        var itemIndex = $scope.sourceList[parentIndex].children.indexOf(item);
        $scope.sourceList[parentIndex].children.splice(itemIndex, 1);
        $scope.destinationList.push(item)
    }
    $scope.showMe = function () {
        for (i = 0; i < $scope.sourceList.length; i++) {
            if ($scope.sourceList[i].children.length > 0) {
                return true;
            }
        }
    }
    $scope.mapping = function () {
        $scope.statusMessage.message = "Success";
        $scope.statusMessage.code = 200;
        $scope.controllerNamePost = function () {
            mappingService.serviceNamePost($scope.sourceList).then(function (result) {

            });
        }
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
    
    if ($state.current.name == 'mapping.color'){
        $scope.mappingType =2;
    }else if ($state.current.name == 'mapping.size'){
        $scope.mappingType =3;
    }
    $scope.loadMappingList = function () {
        mappingService.getMappingLists($scope.mappingType).then(function (result) {
            if(result.errorCode !=200){

            }else {
                $scope.mappingForm=result;
                $scope.destinationList=$scope.mappingForm.destinationList;
            }
        });
    }
    $scope.loadMappingList();
    $scope.sourceList = [{'name': 'Category 1', 'id': "1", children: null},
        {'name': 'Category 2', 'id': "2", children: null},
        {'name': 'Category 3', 'id': "3", children: null},
        {'name': 'Category 4', 'id': "4", children: null},
        {'name': 'Category 5', 'id': "5", children: null},
        {'name': 'Category 6', 'id': "6", children: null},
        {'name': 'Category 7', 'id': "7", children: null}];

    $scope.deleteItem = function (item, parent) {
        var parentIndex = $scope.sourceList.indexOf(parent);
        $scope.sourceList[parentIndex].children = null;
        $scope.destinationList.push(item)
    }
    $scope.showMe = function () {
        for (i = 0; i < $scope.sourceList.length; i++) {
            if ($scope.sourceList[i].children != null) {
                return true;
            }
        }
    }
    $scope.mapping = function () {
        $scope.statusMessage.message = "Success";
        $scope.statusMessage.code = 200;
        $scope.controllerNamePost = function () {
            mappingService.serviceNamePost($scope.sourceList).then(function (result) {

            });
        }
    }
    $scope.statusClose = function(){
        $('.notificationMessage').addClass('ng-hide');
    }
});
