import axios from 'axios'
// import router from '@/router'
import { Message } from 'element-ui';
axios.defaults.baseURL = 'http://localhost:9090'
// axios.defaults.baseURL = 'http://47.119.115.60:8089'
// axios.defaults.baseURL = 'http://47.106.104.139:9090/'
// 点击标签时，取消之前正在执行的请求，使得切换标签时，页面得到的是最后请求的结果，而不是响应最慢的结果。
// let CancelToken = axios.CancelToken
// 请求拦截器
axios.interceptors.request.use(config => {
    
    console.log("前置拦截")
    // // 可以统一设置请求头
    const token=window.localStorage.getItem('sec_token')||'{}'
    const uuid=window.localStorage.getItem('capity_uuid')||'{}'
    if(token !== '{}' || uuid !== '{}' ){
        config.headers.authorization = token
        config.headers.capityUUID = uuid
        console.log(uuid);
    }
    return config
})
axios.interceptors.response.use(response => {
    // const t=response.headers["uuid"];
    // if(t!==null){
    //     window.localStorage.setItem("capity_uuid",t)
    // }
    
    
   
    const res = response.data;
    console.log("后置拦截")
    console.log(res);
    // 当结果的code是否为200的情况
    if (res.code === 200 || res.code === 400) {
        return response
    } else {
        // 直接拒绝往下面返回结果信息
        Message({
            message: response.data.msg,
            type: 'error',
            // 显示时间两秒
            duration: 2 * 1000
        })
        return Promise.reject(response.data.msg)
    }
},
    error => {             
        return Promise.reject(error)
    })
export default axios