const socket = new WebSocket('ws://localhost:8765');
// 当与服务器成功建立连接时触发
socket.onopen = function() {
    console.log("已经与服务器建立连接");
};
 
// 收到消息时触发
socket.onmessage = function(event) {
    const message = event.data;
    console.log(`收到消息：${message}`);
};
 
// 关闭连接时触发
socket.onclose = function() {
    console.log("与服务器断开连接");
};
 
// 发生错误时触发
socket.onerror = function(error) {
    console.error(`发生错误：${error}`);
};