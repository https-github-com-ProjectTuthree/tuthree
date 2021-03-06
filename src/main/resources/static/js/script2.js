var stompClient = null;


$(document).ready(function() {
    console.log("Index page is ready");
    connect();

    $("#send").click(function() {
        sendMessage();
    });

});

function connect() {
    var socket = new SockJS('/tuthree-websocket');
    // 노션 문서 (1)
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages.2', function (message) {
            showMessage(decodeURI(JSON.parse(message.body).content));
        });
        // 노션 문서 (3)

    });
}

function showMessage(message) {
    $("#messages").append("<tr><td>" + message + "</td></tr>");
}

function sendMessage() {
    console.log("sending message");
    stompClient.send("/ws/message", {}, JSON.stringify({'room':{'id':1}, 'name' : encodeURI('김민지'), 'userId':'hello1', 'content': encodeURI($("#message").val())}));
    // 노션 문서 (2)
}

