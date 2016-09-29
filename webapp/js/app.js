var app = angular.module('app', []);
// var domain = "http://10.144.66.166:8080"
var domain = "http://172.16.40.147:8080"
var stompClient = null;

app.controller('MainController', function MainController($scope, $http){

    $scope.jobs = [];
    runNotification();
    connect();
    var lastStatuses = {};
    $scope.currentJobs = [];

    $scope.addMachine = function(){
        $http({
            method: 'POST',
            url: domain+"/api/configuration/server/add/",
            data: $.param({url: $scope.url}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function successCallback(response) {
            $scope.url = "";
            $scope.jobs = response.data.jobs; 
        }, function errorCallback(response) {
            alert("Incorrect url format");
            $scope.url = "";
      });
    }

    $scope.addThisJob = function(jobUrl){
        $http({
            method: 'POST',
            url: domain+"/api/configuration/job/register/",
            data: $.param({url: jobUrl}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function successCallback(response) {
            console.log(response)   
        }, function errorCallback(response) {
            if (response.data.hasOwnProperty("error")){
                alert(response.data.error);
            }else{
                alert("Unknown error occurd");
            }
        });
    }

    $scope.removeThisJob = function(jobUrl){
        if (confirm("Are You sure to want to remove this job? "+jobUrl)){
            $http({
            method: 'POST',
            url: domain+"/api/configuration/job/remove/",
            data: $.param({url: jobUrl}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
          }).then(function successCallback(response) {
             alert("Job has benn removed")
          }, function errorCallback(response) {
            alert("Unknown error occurd");
          });
        } 
    }

    $scope.clearJobsList = function(){
        $scope.jobs = [];
    }


    function runNotification(){
           // At first, let's check if we have permission for notification
           // If not, let's ask for it
           if (window.Notification && Notification.permission !== "granted") {
             Notification.requestPermission(function (status) {
               if (Notification.permission !== status) {
                 Notification.permission = status;
               }
             });
           }

        var button = document.getElementsByTagName('button')[0];

        button.addEventListener('click', function () {
          // If the user agreed to get notified
          // Let's try to send ten notifications
          if (window.Notification && Notification.permission === "granted") {
            
            // Using an interval cause some browsers (including Firefox) are blocking notifications if there are too much in a certain time.
              // Thanks to the tag, we should only see the "Hi! 9" notification 
              // var n = new Notification("Hi! ", {tag: 'soManyNotification'});
          }

          // If the user hasn't told if he wants to be notified or not
          // Note: because of Chrome, we are not sure the permission property
          // is set, therefore it's unsafe to check for the "default" value.
          else if (window.Notification && Notification.permission !== "denied") {
            Notification.requestPermission(function (status) {
              // If the user said okay
              if (status === "granted") {
                
                // Using an interval cause some browsers (including Firefox) are blocking notifications if there are too much in a certain time.
                  // Thanks to the tag, we should only see the "Hi! 9" notification 
                  // var n = new Notification("Hi! ", {tag: 'soManyNotification'});  
              }

              // Otherwise, we can fallback to a regular modal alert
              else {
                alert("Hi!");
              }
            });
          }

          // If the user refuses to get notified
          else {
            // We can fallback to a regular modal alert
            alert("Hi!");
          }
        });
      }

    function setConnected(connected) {
        // $("#connect").prop("disabled", connected);
        // $("#disconnect").prop("disabled", !connected);
        // if (connected) {
        //     $("#conversation").show();
        // }
        // else {
        //     $("#conversation").hide();
        // }
        // $("#greetings").html("");
    }

    function connect() {
        // var socket = new SockJS('http://10.144.66.166:8080/gs-guide-websocket');
        var socket = new SockJS(domain+'/gs-guide-websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/greetings', function (greeting) {
                showGreeting(JSON.parse(greeting.body));
            });
        });
    }

    function disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }

    function sendName() {
        stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
    }

    function showGreeting(message) {
        // $("#greetings").append("<tr><td>" + message + "</td></tr>");
        console.log("Number of jobs: "+message.length)

        // find changes
        for (var i = 0; i<message.length; i++){
            if (!lastStatuses.hasOwnProperty(message[i].projectName) || message[i].building != lastStatuses[message[i].projectName]){
                lastStatuses[message[i].projectName] = message[i].building;
                if (message[i].building === true){
                    var n = new Notification("New build started of "+message[i].projectName, {tag: 'soManyNotification'});
                    alert("New build started of "+message[i].projectName);
                }
            }
            console.log(message[i].projectName+" ("+message[i].building+")");
        } 

        //update list in memory
        lastStatuses = [];
        $scope.currentJobs = [];
        for (var i = 0; i<message.length; i++){
            lastStatuses[message[i].projectName] = message[i].building;
            $scope.currentJobs.push(message[i].projectName);
            $scope.$apply();
        } 
        // $scope.myJobs = lastStatuses;
    }
     
});
