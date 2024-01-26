//验证密码是否简单
function firm(password)
{
    //验证密码是否简单
    if (password.length < 6) {
        alert("密码长度不能小于6位");
        return false;
    }
    if (password.length > 20) {
        alert("密码长度不能大于20位");
        return false;
    }
    if (password.search(/[\u4e00-\u9fa5]/) != -1) {
        alert("密码不能包含中文");
        return false;
    }
    if (password.search(/[0-9]/) == -1) {
        alert("密码必须包含数字");
        return false;
    }
    if (password.search(/[a-z]/) == -1) {
        alert("密码必须包含小写字母");
        return false;
    }
    return true;
}