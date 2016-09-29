var app = angular.module('app', []);
var domain = "http://10.144.66.166:8080"
var stompClient = null;

app.controller('MainController', function MainController($scope, $http){

    runNotification();
    connect();

    $scope.addMachine = function(){
        $http({
            method: 'POST',
            url: domain+"/api/configuration/server/add",
            data: $.param({url: $scope.url}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function successCallback(response) {

        });
    }
    $( "#disconnect" ).click(function() { disconnect(); });

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
              var n = new Notification("Hi! ", {tag: 'soManyNotification'});
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
                  var n = new Notification("Hi! ", {tag: 'soManyNotification'});  
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
        $("#connect").prop("disabled", connected);
        $("#disconnect").prop("disabled", !connected);
        if (connected) {
            $("#conversation").show();
        }
        else {
            $("#conversation").hide();
        }
        $("#greetings").html("");
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
        $("#greetings").append("<tr><td>" + message + "</td></tr>");
        var n = new Notification("powiadomienie", {tag: 'soManyNotification'});
    }  
});
