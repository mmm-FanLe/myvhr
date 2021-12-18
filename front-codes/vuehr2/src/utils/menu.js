import {getRequest2} from "./axiosConfig";

export const initMenu2 = (router,store)=>{

    //已经登陆过
    if (store.state.routes.length > 0){
        return;
    }
    //第一次登录
    getRequest2('/system/config/menus').then(data=>{
        if (data){
            let routes = transformRoutes(data);
            router.addRoutes(routes);
            store.commit('initRoutes2',routes);
        }
    })
}

export const transformRoutes = (routes)=>{
    let arr = [];
    routes.forEach(route=>{
        //解构对象
        let {
            path,
            component,
            name,
            meta,
            iconCls,
            children
        } = route;

        if (children && children instanceof Array){
            children = transformRoutes(children);
        }

        //定义对象(将上面解构的值放进来)
        let routeObj = {
            path,
            name,
            iconCls,
            meta,
            children,
            //懒加载组件
            component(resolve){
                if (component.startsWith('Home')){
                    require(['../views/'+component+'.vue'],resolve);
                }else if (component.startsWith('Per')){
                    require(['../views/per/'+component+'.vue'],resolve);
                }else if (component.startsWith('Sal')){
                    require(['../views/sal/'+component+'.vue'],resolve);
                }else if (component.startsWith('Sta')){
                    require(['../views/sta/'+component+'.vue'],resolve);
                }else if (component.startsWith('Emp')){
                    require(['../views/emp/'+component+'.vue'],resolve);
                }else if (component.startsWith('Sys')){
                    require(['../views/sys/'+component+'.vue'],resolve);
                }
            }
        }

        arr.push(routeObj);
    })
    return arr;
}