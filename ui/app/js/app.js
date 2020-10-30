var app = angular.module('myApp', []);
app.config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.timeout = 20000;
}])
app.controller('myCtrl', function($scope, $http) {

$scope.myvalue = false;
$scope.myvalue2 = false;

    this.retrieve = function() {
    $http.get('http://127.0.0.1:8303/api/stock-service/rest/stock/' + $scope.name)
    .then(function (response) {
        $scope.myvalue = true;
        $scope.myvalue2 = false;
        console.log('inside'+ response);
        $scope.quotes = response.data;
    }, function (response) {
        console.log('came here');
    });
    }


    this.add = function() {
        var message = {
            userName: $scope.name,
            quotes: [$scope.quote]
        }
        $http.post('http://127.0.0.1:8303/api/db-service/rest/db/add', message)
            .then(function(response) {
                $scope.myvalue = false;
                $scope.myvalue2 = true;
                $scope.quotes = response.data;
            }, function(response) {
                console.log('error..');
            });
    }
});