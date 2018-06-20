var mapping = angular.module('mapping', ['ui.router', 'ngDragDrop']);


mapping.config(function ($stateProvider, $urlRouterProvider, $qProvider) {
   
    $qProvider.errorOnUnhandledRejections(false);

    $urlRouterProvider.otherwise('/mapping');
    $urlRouterProvider.when('', '/login');
    $stateProvider.state('login', {
        url: '/login',
        templateUrl: 'views/login.html',

    })
    $stateProvider.state('mapping', {
        url: '/mapping',
        templateUrl: 'views/mapping.html',

    }).state('mapping.category', {
        url: '/category',
        templateUrl: 'views/category.html',
    }).state('mapping.color', {
        url: '/color',
        templateUrl: 'views/color.html',
    }).state('mapping.size', {
        url: '/size',
        templateUrl: 'views/size.html',
    }).state('mapping.product', {
        url: '/product',
        templateUrl: 'views/product.html',
    })
});

mapping.run(function ($rootScope, $state) {

    $rootScope.mapHome = function () {
        $state.go('mapping');
    }

});