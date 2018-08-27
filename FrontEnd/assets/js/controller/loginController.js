mapping.controller('loginController', function ($scope, loginService,$state,$timeout) {
    $scope.loginForm = {};
    $scope.statusMessage = {};
    $scope.statusMessage.code = 0 ;
    $scope.statusMessage.message = "";
    $scope.submitForm = function () {
        $scope.loginForm.username = $scope.username;
        $scope.loginForm.password = $scope.password;
        loginService.login($scope.loginForm).then(function (result) {
        if(result.errorCode !=200){
            $scope.statusMessage.message = "Faild To Login";
            $scope.statusMessage.code = '250'; 
        }else {
            $state.go('services.configuration');
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