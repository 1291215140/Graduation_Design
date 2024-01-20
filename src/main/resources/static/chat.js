//chat聊天
function seccess(data) {
    console.log(data);
}
function error(data) {
    console.log(data);
    console.log("error");
}
function sendmessage(){
    message = document.getElementById("message").value;
    data = {"name":"ceshi","message":message};
    if(data == ""){
        alert("Enter a message");
        return;
    }
    url = "/savemessage";
    type = "GET"
    ajax(url,data, type, seccess, error);
}
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