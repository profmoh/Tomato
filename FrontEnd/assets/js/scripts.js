var mapping = angular.module('mapping', ['ui.router', 'ngDragDrop']);
var URLRoot = window.location.origin;
mapping.constant("URLRoot",URLRoot)
mapping.config(function ($stateProvider, $urlRouterProvider, $qProvider) {
   
    $qProvider.errorOnUnhandledRejections(false);

    $urlRouterProvider.otherwise('/services');
    $urlRouterProvider.when('', '/login');
    $stateProvider.state('login', {
        url: '/login',
        templateUrl: 'views/login.html',

    })
    $stateProvider.state('services', {
        url: '/services',
        templateUrl: 'views/services.html',

    }).state('services.configuration', {
        url: '/configuration',
        templateUrl: 'views/configuration.html',

    }).state('services.addProduct', {
        url: '/add-product',
        templateUrl: 'views/addProduct.html',

    }).state('services.mapping', {
        url: '/mapping',
        templateUrl: 'views/mapping.html',

    }).state('services.mapping.category', {
        url: '/category',
        templateUrl: 'views/category.html',
    }).state('services.mapping.color', {
        url: '/color',
        templateUrl: 'views/color.html',
    }).state('services.mapping.size', {
        url: '/size',
        templateUrl: 'views/size.html',
    }).state('services.mapping.product', {
        url: '/product',
        templateUrl: 'views/product.html',
    })
});

mapping.run(function ($rootScope, $state) {

    $rootScope.mapHome = function () {
        $state.go('mapping');
    }

});