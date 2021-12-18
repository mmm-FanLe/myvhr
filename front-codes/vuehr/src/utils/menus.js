/*
* 1.将后端传过来的字符串转成数组对象
* 2.将数组对象添加到vue的动态路由中
* */
import {getRequest} from "./api";

//- 导出一个函数
export const initMenu = (router, store) => {
    //这是一次正常的跳转，则不需要更新routes，他已经保存在Vuex中了
    if (store.state.routes.length > 0) {
        return;
    }
    //如果用户按了刷新键的跳转,需要得到routes并存入store中
    getRequest('/system/config/menu').then(data => {
        if (data) {
            //console.log('/system/config/menu',data);
            //将字符串转化成Route对象并交给fmtRoutes数组
            let fmtRoutes = formatRoutes(data);
            //console.log('fmtRoutes:',fmtRoutes);
            //将fmtRoutes数组添加到路由中
            router.addRoutes(fmtRoutes);
            //动态路由在this.$router.options找不到是故意为之的
            //但是下面这样就可以找到
            //router.options.routes=router.options.routes.concat(fmtRoutes);
            //将fmtRoutes数组保存到vuex中(供其他组件调用)
            store.commit("initRoutes", fmtRoutes);
        }
    })
}

export const formatRoutes = (routes) => {
    let fmRoutes = [];
    routes.forEach(route => {
        //解构对象：得到对象中的所有属性
        let {
            path,//相当于let path = route.path;
            component,
            name,
            meta,
            iconCls,
            children
        } = route;
        //递归出口：如果没有children(值为null)
        if (children && children instanceof Array) {
            children = formatRoutes(children);
        }

        //定义对象
        let fmRouter = {
            path,
            name,
            iconCls,
            meta,
            children,
            component(resolve) {
                //加载组件
                //如果不加上Home，则不能实现在Home组件中跳转，又因为路由中是/home/xxxx而不是/xxx，所以也不能单独跳转到另一个页面
                if (component.startsWith("Home")) {
                    require(['../views/' + component + '.vue'], resolve);
                } else if (component.startsWith("Per")) {
                    require(['../views/per/' + component + '.vue'], resolve);
                } else if (component.startsWith("Sal")) {
                    require(['../views/sal/' + component + '.vue'], resolve);
                } else if (component.startsWith("Sta")) {
                    require(['../views/sta/' + component + '.vue'], resolve);
                } else if (component.startsWith("Emp")) {
                    require(['../views/emp/' + component + '.vue'], resolve);
                } else if (component.startsWith("Sys")) {
                    require(['../views/sys/' + component + '.vue'], resolve);
                }
            }
        }
        //将对象化的路由加入数组
        fmRoutes.push(fmRouter);
    });
    return fmRoutes;
}