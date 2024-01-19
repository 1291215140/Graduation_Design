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