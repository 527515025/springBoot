var uiApp = angular.module("uiApp", ["ui.router"]);

uiApp.config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("person");

    $stateProvider
        .state('person', {
            url: '/person',
            templateUrl: '/tpl/person.html',
            controller: 'PersonController'
        })
        .state('some',{
        	url:'/some',
        	 templateUrl: '/tpl/some.html',
             controller: 'SomeController'
        });
});


uiApp.controller("PersonController", function ($scope, $http) {

        $scope.people = "";
        $scope.errorMessage = "";

    $scope.getMessageResponse = function(personName) {
    	$http.post('/dispatch', personName).success(function(data){
            $scope.people = data;
            $scope.errorMessage = "";
        }).error(function() {
            $scope.errorMessage = "错误";
        });
    }
  
});


uiApp.controller("SomeController", function ($scope, $http) {
	$scope.str = "";
    $scope.errorMessage = "";

$scope.getSome = function(){
	$http.get('/getsome').success(function(data){
        $scope.str = data;
        $scope.errorMessage = "";
    }).error(function() {
        $scope.errorMessage = "错误";
    });
}
});