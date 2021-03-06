var wsUri = "";
var output;

function set_ws_uri(){
    var scheme   = "wss://";
    wsUri =  scheme + window.document.location.host + "/socket";
}


function saveGame(){
    intervalManager(false);
    document.getElementById("player").innerHTML = "LOADING...";
    set_ws_uri();
    websocket = new WebSocket(wsUri);
    websocket.onopen = function(evt) { onOpenSaveGame(evt) };
    //websocket.onclose = function(evt) { onClose(evt) };
    websocket.onmessage = function(evt) { onMessage(evt) };
    //websocket.onerror = function(evt) { onError(evt) };
    intervalManager(true, load_data, milliseconds);
}
function onOpenSaveGame(){
    doSend("save_game");
}
function loadGame(){
    intervalManager(false);
    document.getElementById("player").innerHTML = "LOADING...";
    set_ws_uri();
    websocket = new WebSocket(wsUri);
    websocket.onopen = function(evt) { onOpenLoadGame(evt) };
    //websocket.onclose = function(evt) { onClose(evt) };
    websocket.onmessage = function(evt) { onMessage(evt) };
    //websocket.onerror = function(evt) { onError(evt) };
    intervalManager(true, load_data, milliseconds);
}
function onOpenLoadGame(){
    doSend("load_save_game");
}

function startNewGame(){
    intervalManager(false);
    document.getElementById("player").innerHTML = "LOADING...";
    current_cell = "00";
    current_matrix = '';
    current_player = "LOADING...";
    game_is_won = false;
    set_ws_uri();
    websocket = new WebSocket(wsUri);
    websocket.onopen = function(evt) { onOpenStartNew(evt) };
    //websocket.onclose = function(evt) { onClose(evt) };
    websocket.onmessage = function(evt) { onMessage(evt) };
    //websocket.onerror = function(evt) { onError(evt) };
    intervalManager(true, load_data, milliseconds);
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
    if (websocket.readyState !== 3){
        websocket.onopen = function(evt) { onOpen(evt) };
        //websocket.onclose = function(evt) { onClose(evt) };
        websocket.onmessage = function(evt) { onMessage(evt) };
        //websocket.onerror = function(evt) { onError(evt) };
    }
}

function onOpen(evt)
{
    //writeToScreen("CONNECTED");
    if(current_cell_sent == false){
        doSend(current_cell);
        current_cell_sent = true
    } else{
        doSend("");
    }





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