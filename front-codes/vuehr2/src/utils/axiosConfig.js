import axios from "axios";
import {Message} from "element-ui";
import router from "../router";

//- axios拦截器
axios.interceptors.response.use(success=>{
    console.log('success:',success);
    //- 请求成功了，但是后端有别的错误
    if (success.status && success.status == 200 && success.data.status == 500){
        //提示后端返回json对象的报错信息
        Message.error({message:success.data.msg});
        return;
    }
    //- 请求成功，后端也一切正常
    if (success.data.msg){//? 如果后端返回了json对象并且设置了msg属性
        Message.success({message:success.data.msg});
    }
    return success.data;//? 返回json对象到前端处理
},error=>{
    console.log('error:',error);
    if (error.response.status == 504 || error.response.status == 404){
        //- 前端请求服务器的路径写错了或者后端的资源文件找不到了
        Message.error({message:'服务器被吃了o(╯□╰)o'});
    }else if (error.response.status == 403){
        Message.error({message:'权限不足，请联系管理员'});
    }else if (error.response.status == 401){
        //- 中途后端重启了，前端再去执行其他操作
        Message.error('尚未登录，请重新登录');
        router.replace('/');
    }else{
        //- 其他错误
        if (error.response.data.msg){
            Message.error({message:error.response.data.msg});
        }else{
            Message.error('未知错误');
        }
    }
});

let base = '';
export const postKeyValueRequest2=(url,param)=>{
    return axios({
        method:'post',
        url:`${base}${url}`,
        data:param,
        transformRequest:[function (data) {
            let ret = '';
            for (let key in data){
                ret += encodeURIComponent(key) + '=' +encodeURIComponent(data[key]) + "&";
            }
            return ret;
        }],
        headers:{
            'Content-Type':'application/xxx-www-form-urlencoded'
        }
    })
}

export const postRequest2 = (url,param)=>{
    return axios({
        method:'post',
        url:`${base}${url}`,
        data:param
    });
}
export const putRequest2 = (url,param)=>{
    return axios({
        method:'put',
        url:`${base}${url}`,
        data:param
    });
}
export const getRequest2 = (url,param)=>{
    return axios({
        method:'get',
        url:`${base}${url}`,
        data:param
    });
}
export const deleteRequest2 = (url,param)=>{
    return axios({
        method:'delete',
        url:`${base}${url}`,
        data:param
    });
}