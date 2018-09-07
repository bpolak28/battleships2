var stompClient = null;

var socket = new SockJS('/chat');
stompClient = Stomp.over(socket);
stompClient.connect({}, function(frame) {
	console.log('Connected: ' + frame);
	stompClient.subscribe('/topic', function(messageOutput) {
		showMessageOutput(JSON.parse(messageOutput.body));
	});
});

document.getElementById('from').style.visibility = 'hidden';

function sendMessage() {
	var from = document.getElementById('from').value;
	var text = document.getElementById('text').value;
	stompClient.send("/app/chat", {}, JSON.stringify({
		'fromPlayer' : from,
		'message' : text
	}));
	
	
}

function showMessageOutput(messageOutput) {
	var response = document.getElementById('response');
	console.log(messageOutput.fromPlayer + ": "
			+ messageOutput.message);
	var p = document.createElement('p');
	p.appendChild(document.createTextNode(messageOutput.fromPlayer + ": "
			+ messageOutput.message));
	response.appendChild(p);
	document.getElementById('response').scrollTop=1e6;
	document.getElementById('text').value = "";
}

//
// var stompClient = null;
//
// function setConnected(connected) {
// document.getElementById('connect').disabled = connected;
// document.getElementById('disconnect').disabled = !connected;
// document.getElementById('conversationDiv').style.visibility = connected ?
// 'visible'
// : 'hidden';
// document.getElementById('response').innerHTML = '';
// }
//
// function connect() {
//	var socket = new SockJS('/spring-mvc-java/chat');
//	stompClient = Stomp.over(socket);
//	stompClient.connect({}, function(frame) {
//		setConnected(true);
//		console.log('Connected: ' + frame);
//		stompClient.subscribe('/topic/messages', function(messageOutput) {
//			showMessageOutput(JSON.parse(messageOutput.body));
//		});
//	});
//}
//
//function disconnect() {
//	if (stompClient != null) {
//		stompClient.disconnect();
//	}
//	setConnected(false);
//	console.log("Disconnected");
//}
//
//function sendMessage() {
//	var from = document.getElementById('from').value;
//	var text = document.getElementById('text').value;
//	stompClient.send("/app/chat", {}, JSON.stringify({
//		'fromPlayer' : from,
//		'message' : text
//	}));
//}
//
//function showMessageOutput(messageOutput) {
//	var response = document.getElementById('response');
//	var p = document.createElement('p');
//	p.style.wordWrap = 'break-word';
//	p.appendChild(document.createTextNode(messageOutput.from + ": "
//			+ messageOutput.text + " (" + messageOutput.time + ")"));
//	response.appendChild(p);
//}

// var url = '/dupa';
// var sock = new SockJS(url);
// stompClient.connect({}, function (frame) {
// console.log('Connected: ' + frame);
// stompClient.subscribe('/topic', function () {
// showGreeting(JSON.parse(greeting.body).content);
// });
// });
//
// sock.onopen = function(){
// console.log('Otwieranie połączenia');
// }
//
// sock.onmessage = function(e){
// console.log('Odebrano wiadomosc:'+e.data);
// }
//
// function sendName() {
// stompClient.send("/app/hello", {}, JSON.stringify({'name':
// $("#name").val()}));
// }
//
// $(function () {
// $("form").on('submit', function (e) {
// e.preventDefault();
//    });
//    $( "#send" ).click(function() { sendName(); });
//});