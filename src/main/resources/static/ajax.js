//jquery下的ajax请求
//ajax不支持跨域,即不支持跨域请求
function ajax(url, data, type, success, error) {
    $.ajax({
        // 请求地址
        url: url,
        // 请求数据
        data: data,
        // 请求方式
        type: type,
        // 请求数据类型
        dataType: 'json',
        // 成功回调
        success: success,
        // 失败回调
        error: error
    })
}


