mapping.controller('loginController', function ($scope, loginService,$state) {
    $scope.loginForm = {};
    $scope.submitForm = function () {
        $scope.loginForm.username = $scope.username;
        $scope.loginForm.password = $scope.password;
        loginService.login($scope.loginForm).then(function (result) {
        if(result.errorCode !=200){
            alert("Failed to login");
        }else {
            $state.go('services.configuration');
        }
        });
    }
});