import axios from "axios";
import {Message} from "element-ui";
import router from '../router'

//axios拦截后端的response响应
axios.interceptors.response.use(success=>{
    //后端内部的错误
    console.log('success:',success);
    //success.status返回的是http响应码,success.data返回的是后端的json数据,success.data.status是后端的响应码
    if (success.status && success.status===200 && success.data.status===500){
        //http响应200，但后端的status返回500，则说明是服务器内部的错误
        Message.error({message:success.data.msg});
        return ;//如果有错误则返回空
    }
    //没有错误
    if (success.data.msg){
        console.log('Message.success:',success.data.msg);
        Message.success({message:success.data.msg});
    }
    return success.data;//返回后端的json,注意：此时已经返回了resp.data

},error => {
    if (error.response.status === 504 || error.response.status === 404){
        /*服务器的请求路径写错了*/
        Message.error({message:'服务器被吃了o(╯□╰)o'});
    }else if (error.response.status === 403){
        Message.error({message:'权限不足,请联系管理员'});
    } else if (error.response.status === 401){
        //- 后端中途重启时，正在访问的前端让它去登录页
        Message.error({message:'尚未登录，请先登录'});
        router.replace('/');
    }else{
        //后端的传来的其他错误
        if (error.response.data.msg){
            Message.error({message:error.response.data.msg});
        }else{
            Message.error({message:'未知错误'});
        }
    }
});

let base='';

export const postKeyValueRequest=(url,params)=>{
    return axios({
        method:'post',
        url:`${base}${url}`,
        data:params,
        transformRequest:[function (data) {
            let ret = '';
            for (let key in data){
                /*
                * SpringSecurity只支持key/value形式，所以要对登录接口进行特殊处理
                * */
                ret += encodeURIComponent(key)+'='+encodeURIComponent(data[key])+'&';
            }
            console.log('ret:',ret);
            return ret;
        }],
        headers:{
            'Content-Type':'application/x-www-form-urlencoded'
        }
    });
}

export const postRequest=(url,params)=>{
    return axios({
       method:'post',
       url:`${base}${url}`,
       data:params
    });
}
export const putRequest=(url,params)=>{
    return axios({
       method:'put',
       url:`${base}${url}`,
       data:params
    });
}
export const getRequest=(url,params)=>{
    return axios({
       method:'get',
       url:`${base}${url}`,
       data:params
    });
}
export const deleteRequest=(url,params)=>{
    return axios({
       method:'delete',
       url:`${base}${url}`,
       data:params
    });
}

