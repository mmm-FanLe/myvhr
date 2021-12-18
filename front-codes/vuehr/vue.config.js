let proxyObj = {};
proxyObj['/'] = {//proxyObj['/']表示proxyObj的属性
    ws: false,//如果要代理 websockets，配置这个参数
    target: 'http://localhost:8081',//要代理的域名和端口号
    changeOrigin: true,//跨域
    /*
    *  - 这里用‘^/’代替target里面的地址，组件中调用接口时直接用''(空字符串)代替
    *       ?  比如我要调用'http://localhost:8081/user?page=1&pageSize=10
            ?  axios请求直接写‘/user?page=1&pageSize=10’即可
    * */
    pathRewrite: {
        '^/': ''
    }
}

module.exports= {
    devServer:{
        host:'localhost',
        port:8080,
        proxy:proxyObj
    }
}
