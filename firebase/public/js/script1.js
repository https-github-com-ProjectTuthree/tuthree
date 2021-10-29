var stompClient = null;

$(document).ready(function() {
    console.log("Index page is ready");
    connect();

    $("#send").click(function() {
        sendMessage();
    });

    // $("#send-private").click(function() {
    //     sendPrivateMessage();
    // });
});

function connect() {
    var socket = new SockJS('http://221.141.233.185:8088/tuthree-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        // stompClient.subscribe('/topic/messages', function (message) {
        stompClient.subscribe('/topic/messages.1', function (message) {
            // showMessage(decodeURI(JSON.parse(message.body).content));
            showMessage(decodeURI(JSON.parse(message.body).content));
        });

        // stompClient.subscribe('/user/topic/private-messages', function (message) {
        //     showMessage(JSON.parse(message.body).content);
        // });
    });
}

function showMessage(message) {
    $("#messages").append("<tr><td>" + message + "</td></tr>");
}

function sendMessage() {
    console.log("sending message");
    stompClient.send("/wss/message", {}, JSON.stringify({'room':{'id':1}, 'name' : encodeURI('김민지'), 'userId':'hello1', 'content': encodeURI($("#message").val())}));
}



// function sendPrivateMessage() {
//     console.log("sending private message");
//     stompClient.send("/ws/private-message", {}, JSON.stringify({'messageContent': $("#private-message").val()}));
// }
