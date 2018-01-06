var wsUri = "";
var output;

function set_ws_uri(){
    var scheme   = "wss://";
    wsUri =  scheme + window.document.location.host + "/socket"
}


function startNewGame(){
    current_cell = "00";
    current_matrix = '';
    current_player = "Loading...";
    game_is_won = false;
    set_ws_uri();
    websocket = new WebSocket(wsUri);
    websocket.onopen = function(evt) { onOpenStartNew(evt) };
    websocket.onclose = function(evt) { onClose(evt) };
    websocket.onmessage = function(evt) { onMessage(evt) };
    websocket.onerror = function(evt) { onError(evt) };
}
function onOpenStartNew(evt)
{
    //writeToScreen("CONNECTED");
    doSend("start_new_game");

}

function init()
{
    output = document.getElementById("output");
    set_ws_uri();
    testWebSocket();

}

function testWebSocket()
{

    websocket = new WebSocket(wsUri);
    websocket.onopen = function(evt) { onOpen(evt) };
    websocket.onclose = function(evt) { onClose(evt) };
    websocket.onmessage = function(evt) { onMessage(evt) };
    websocket.onerror = function(evt) { onError(evt) };
}

function onOpen(evt)
{
    //writeToScreen("CONNECTED");
    doSend(current_cell);


}

function onClose(evt)
{
    //writeToScreen("DISCONNECTED");
}

function onMessage(evt)
{
    current_matrix = evt.data;
    //writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data+'</span>');
    websocket.close();
    showMatrix();
}

function onError(evt)
{
    //writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function doSend(message)
{
    //writeToScreen("SENT: " + message);
    websocket.send(message);
}

function writeToScreen(message)
{
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
}

//window.addEventListener("load", init, false);
//window.addEventListener("click", init, false);
//window.addEventListener("click", function(){ alert("Hello World!"); });