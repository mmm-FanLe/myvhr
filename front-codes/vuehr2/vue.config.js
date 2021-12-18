let proxyObj = {};
proxyObj['/'] = {//proxyObj['/']表示proxyObj的属性
    ws: false,//如果要代理 websockets，配置这个参数
    target: 'http://localhost:8989',//要代理的域名和端口号
    changeOrigin: true,//跨域
    pathRewrite: {
        '^/': '/'
    }
}

module.exports= {
    devServer:{
        host:'localhost',
        port:8080,
        proxy:proxyObj
    }
}
