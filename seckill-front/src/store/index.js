import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: '',
    userInfo: JSON.parse(localStorage.getItem("sec_userInfo")) || {},
    captId: JSON.parse(localStorage.getItem("capity_uuid")) || {},
  },
  // 相当于set
  mutations: {
    // 把后端传过来的token存在localstorage
    SET_TOKEN: (state, token) => {
      state.token = token
      localStorage.setItem("sec_token", token)
    },
    SET_USERINFO: (state, userInfo) => {
      state.userInfo = userInfo
      localStorage.setItem("sec_userInfo", JSON.stringify(userInfo))
    },
    SET_CAPTID: (state, captId) => {
      state.captId = captId
      localStorage.setItem("capity_uuid", JSON.stringify(captId))
    },
    // 清空本地和localstarage的信息
    REMOVE_INFO: (state) => {
      state.token = {}
      state.userInfo = {}
      window.localStorage.clear()
    },
    
    
  },
  getters: {
    getUser: state => {
      console.log(state);
      return JSON.parse(localStorage.getItem("sec_userInfo"))
    }
  },
})