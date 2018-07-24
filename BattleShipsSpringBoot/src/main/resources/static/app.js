var url = '/dupa';
var sock = new SockJS(url);
stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic', function () {
        showGreeting(JSON.parse(greeting.body).content);
    });
});

sock.onopen = function(){
	console.log('Otwieranie połączenia');
}

sock.onmessage = function(e){
	console.log('Odebrano wiadomosc:'+e.data);
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() { sendName(); });
});