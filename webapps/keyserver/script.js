function autoJump(){
	window.location = "./index.html#/menu";
}

(function(angular) {
var myApp = angular.module('door_manager',['ngRoute','ngAnimate']).
	config(['$routeProvider', function($routeProvider){
        $routeProvider.when('/login', {
            templateUrl: 'login',
            controller: function($scope, $timeout, $routeParams){
                $scope.flipforward = 1;
	    }
        })
        .when('/menu', {
            templateUrl: 'menu',
            controller: function($scope, $routeParams){
                $scope.checksession();
	    }
        })
        .when('/door_manager', {
            templateUrl: 'door_manager',
            controller: function($scope, $routeParams){
                $scope.checksession();
	    }
        })
        .when('/history', {
            templateUrl: 'history',
            controller: function($scope, $routeParams){
                $scope.checksession();
	    }
        })
        .when('/cardreaders', {
            templateUrl: 'testdb',
            controller: function($scope, $routeParams){
                $scope.checksession();
	    }
        })
        .when('/registration', {
            templateUrl: 'user_registration',
            controller: function($scope, $routeParams){
                $scope.checksession();
	    }
        })
        .when('/user_edit', {
            templateUrl: 'user_edit',
            controller: function($scope, $routeParams){
                $scope.checksession();
	    }
        })
        .when('/test', {
            templateUrl: 'testjson',
            controller: function($scope, $routeParams){
            	$scope.flipforward = 1;
            }
        })
        .otherwise({redirectTo: '/login'});
    }])
.controller('MyViewController', ['$scope','$http', '$timeout', '$route', function($scope, $http, $timeout, $route) {
	$scope.route = $route;

	// セッションがあるか確認する
	$scope.checksession = function(){
	  var keys = ["order"];
	  var values = ["checksession"];
	  var myurl = MakeURLWithParams('http://192.168.100.111:8080/keyserver/central', keys, values);
	  var result = "";

	  $http({
                method: 'POST',
                url: myurl
          })
          .success(function(data, status, headers, config){
            result = data['result'];
            var ok = "OK";
            if (result != ok){
                window.location = "index.html#/login";
            }
          })
          .error(function(data, status, headers, config){
            result = '通信失敗！';
          });
	}
	// ログアウトボタン
        $scope.onclick_logout = function() {
          var keys = ["order"];
      	  var values = ["logout"];
      	  var myurl = MakeURLWithParams('http://192.168.100.111:8080/keyserver/central', keys, values);
      	  var result = myurl;

	  $http({
        	method: 'POST',
        	url: myurl
      	  })
      	  .success(function(data, status, headers, config){
            result = data['result'];
            var ok = "OK";
            if (result == ok){
                window.location = "index.html#/login";
            }
      	  })
      	  .error(function(data, status, headers, config){
            result = '通信失敗！';
      	  });
        };        

	// ドア一覧ボタン
	$scope.onclick_todoorlist = function(){
	  window.location = "index.html#/door_manager";
	}

 	// 履歴ボタン
	$scope.onclick_tohistory = function(){
	  window.location = "index.html#/history";
	}

	// カードリーダボタン
	$scope.onclick_tocardreaders = function(){
	  window.location = "index.html#/cardreaders";
	}

	// ユーザ登録ボタン
	$scope.onclick_toregistration = function(){
	  window.location = "index.html#/registration";
	}

	// ユーザ編集ボタン
	$scope.onclick_touser_edit = function(){
	  window.location = "index.html#/user_edit";
	}

	$scope.boxarr = [];
	
	$scope.onclick_useredit_update = function(){
	  // keyとvalueにservletに渡す値を詰めていく
	  var keys = [];
	  var values = [];

	  // ユーザID
	  keys.push("userid");
	  values.push($scope.selectedItem);

	  // ユーザ名
	  keys.push("username");
	  values.push($scope.username);

	  // CardID1
	  keys.push("c1");
	  values.push($scope.c1);
	
	  // CardID2
	  keys.push("c2");
	  values.push($scope.c2);
	  
	  // CardID3
	  keys.push("c3");
	  values.push($scope.c3);

	  // CardID4
	  keys.push("c4");
	  values.push($scope.c4);

	  // AdminFlg
	  keys.push("adminflg");
	  values.push($scope.adminflg);

	  // WebControllerFlg
	  keys.push("webflg");
	  values.push($scope.webflg);

	  // チェックが入っている、入っていないドアのリストを作成
          var i;
          var checked_list = "";
	  var unchecked_list = "";
	  var checked;
	  var comma = false;
	  var comma_b = false;
	  for(i = 0; i < $scope.boxarr.length; i++)
	  {
            eval("checked = $scope.door" + $scope.boxarr[i]['value'] + ";");
	    if(checked == "NO" || checked === undefined)
	    {
		// チェックされていない
		if(comma_b){
		  unchecked_list += ",";
		}
		comma_b = true;
	    	unchecked_list += $scope.boxarr[i]['value'];
	    }
	    else
	    {
		// チェックされている
	    	if(comma){
	      	  checked_list += ",";
	    	}
		comma = true;
	    	checked_list += $scope.boxarr[i]['value'];
   	    }
	  }
 
	}

	$scope.onclick_useredit_reset = function(){
	}


	$scope.boxarr_clear = function(){
	    $scope.boxarr.length = 0;
	}

    	$scope.boxarr_add = function(_name, _value){
            $scope.boxarr.push({name: _name, value: _value});
    	}

    $scope.onclick = function() {
        var i;
        for(i = 0; i < $scope.arr.length; i++)
        {
            window.alert("window.alert(check" + i + ");");
            eval("window.alert($scope.check" + (i+1) + ");");
        }
    };

	// ユーザ編集更新ボタン 
	$scope.onlick_useredit_update = function(){
	}

	// ユーザ編集リセットボタン 
	$scope.onclick_useredit_reset = function(){
	}
	
        function MakeURLWithParams(url, keys, values){
          var newurl = url + "?";
  	  var i;
  	  num = keys.length;
  	  for(i = 0; i < num; i++){
	    if(i > 0){
	      newurl += "&";
	    }
	    newurl += keys[i] + "=" + values[i];
	  }
	  return newurl;
	}
}])
.directive('polling', function() {
    return {
	  scope:false,
      link:function(scope){
     	scope.poll(1);
      }
    };
  })
.directive('rstamnt', function() {
    return {
	  scope:false,
      link:function(scope){
     	scope.Reset();
      }
    };
  })
.directive('loadpolling', function() {
    return {
	  scope:false,
      link:function(scope){
     	scope.load_polling();
      }
    };
  });
})(window.angular);


