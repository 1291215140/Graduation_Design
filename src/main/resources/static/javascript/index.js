// 获取DOM元素
const sendButton = document.getElementById('send-button');
const inputBox = document.getElementById('input-box');
const messageList = document.getElementById('message-list');
//获取socket
const socket = new WebSocket('ws://localhost:8765');
socket.onopen = function() {
    console.log("已经与服务器建立连接");
}
// 接收回复
socket.onmessage = function(event){
    const replyMsg = document.createElement('pre');
    replyMsg.textContent = 'Received: ' + event.data;
    replyMsg.style.background = 'lightgrey';
    replyMsg.style.padding = '5px';
    replyMsg.style.borderRadius = '4px';
    replyMsg.style.margin = '5px 0';
    messageList.appendChild(replyMsg);
    // 自动滚动到最新消息
    messageList.scrollTop = messageList.scrollHeight;
}
// 发送消息的事件处理函数
function sendMessage() {
    // 创建新的消息元素
    const msg = document.createElement('p');
    msg.textContent = inputBox.value;
    msg.style.background = 'lightblue';
    msg.style.padding = '5px';
    msg.style.borderRadius = '4px';
    msg.style.margin = '5px 0';

    // 追加到消息列表
    messageList.appendChild(msg);
    //发送消息
    var data = {
        "content": inputBox.value,
        "username": getCookieValue("username")
    };
    socket.send(JSON.stringify(data));
    // 清空输入框
    inputBox.value = '';
    // 自动滚动到最新消息
    messageList.scrollTop = messageList.scrollHeight;
}

// 绑定点击事件
sendButton.addEventListener('click', sendMessage);

// 绑定 Enter 键发送消息
inputBox.addEventListener('keypress', function (e) {
    console.log(e.key);
    if (e.key == "Enter" && e.shiftKey) {
        console.log("换行");
        inputBox.setAttribute("rows",Number(inputBox.getAttribute("rows"))+1);
    }
    else if (e.key == "Enter")
    {
        console.log("输出");
        sendMessage();
    }
    else {return;}
});
inputBox.addEventListener('input', function (e) {
    if(e.inputType==="deleteContentBackward")
    {
        var lines = inputBox.value.split('\n');
        for (var i=lines.length; i--;) {
            if (lines[i].length === 0) lines.splice(i, 1);
        }
        inputBox.rows = lines.length;
    }
});
function getCookieValue(key) {
    var cookies = document.cookie; // 获取所有Cookie
    if(cookies=="")return "none";
    var cookieArray = cookies.split("; ");
    for (var i = 0; i < cookieArray.length; i++) {
        var cookie = cookieArray[i];
        var eqPos = cookie.indexOf("=");
        var cookiekey = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
        if (cookiekey == key) {
            var cookieValue = cookie.substr(eqPos + 1);
            return cookieValue;
        }
    }
    return "none"; // 未找到指定名称或路径值，返回空字符串
}