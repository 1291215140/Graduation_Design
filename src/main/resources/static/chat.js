const socket = new WebSocket('ws://localhost:8765');
// 当与服务器成功建立连接时触发
socket.onopen = function() {
    console.log("已经与服务器建立连接");
}
// 获取文本输入框和消息显示区域的元素
var messageInput = document.getElementById('message');
// 收到消息时触发的函数
socket.onmessage = function(event) {
    var messageDisplay = document.getElementById('return');
    var sendButton = document.getElementById("sendButton");
    //清除loader
    if (document.getElementById("loader")) {
        document.getElementById("loader").remove();
    }
    // 将按钮设置为可用状态
    sendButton.disabled = false;
    // 获取消息内容
    var messageContent = event.data;

    // 创建显示消息的 div 元素
    var messageDiv = document.createElement('div');

    // 创建 span 元素来显示消息内容
    var messageSpan = document.createElement('span');

    // 将 span 添加到 div 中
    messageDiv.appendChild(messageSpan);

    // 将 div 添加到消息显示区域
    messageDisplay.appendChild(messageDiv);

    // 消息索引，用于逐个显示字符
    var charIndex = 0;

    // 设置定时器，每隔一定时间显示下一个字符
    var intervalId = setInterval(function() {
        // 获取当前字符
        var currentChar = messageContent[charIndex];

        // 将字符添加到 span 中
        messageSpan.textContent += currentChar;

        // 增加索引
        charIndex++;

        // 判断是否显示完所有字符
        if (charIndex === messageContent.length) {
            // 清除定时器
            clearInterval(intervalId);

            // 滚动到消息底部以确保最新消息可见
            messageDisplay.scrollTop = messageDisplay.scrollHeight;
        }
    }, 50); // 间隔时间，单位毫秒，可以根据需要调整
};
// 关闭连接时触发
socket.onclose = function() {
    console.log("与服务器断开连接");
};

// 发生错误时触发
socket.onerror = function(error) {
    console.error(`发生错误：${error}`);
};
function sendmessage() {
    // 获取按钮元素
    var sendButton = document.getElementById("sendButton");
    // 创建包含消息和 Cookie 的数据对象
    var data = {
        "content": document.getElementById("message").value,
        "username": getCookieValue("username")
    };
    console.log(data)
    // 检查消息是否为空
    if (data.message === "") {
        alert("Enter a message");
        return;
    }
    // 禁用按钮，显示加载图标
    sendButton.disabled = true;
    sendButton.innerHTML = "发送消息<span id='loader'></span>";
    // 使用 WebSocket 发送消息
    socket.send(JSON.stringify(data));
    console.log(data)
}
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
//退出登录
function logout()
{
    //清除cookie
    var cookies = document.cookie.split("; ");
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i];
        var eqPos = cookie.indexOf("=");
        var cookieName = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
        document.cookie = cookieName + "=; expires=" + new Date(0).toUTCString();}
    //返回到登录页面
    location.reload();
}

