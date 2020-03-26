import {getToken, removeToken, setToken} from '@/utils/auth'
/*import {resetRouter} from '@/router'*/
import {login, logout, userInfo} from "@/api/user";

const state = {
  token: getToken(),
  name: localStorage.name,
  roles: [],
  avatar: localStorage.avatar,
  menus: localStorage.menus
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
    localStorage.setItem('name', name)
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
    localStorage.setItem('avatar', avatar)
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles;
    //localStorage.setItem('roles', roles)
  },
  SET_PERMISSIONS: (state, permissions) => {
    state.roles = permissions;
    //localStorage.setItem('permissions', permissions)
  },
  SET_LOAD_MENUS: (state, loadMenus) => {
    state.menus = loadMenus
  }
}

const actions = {
  // user login
  login({commit}, userInfo) {
    return new Promise((resolve, reject) => {
      userInfo.password = Base64.encode(userInfo.password);
      login(userInfo).then(response => {
        const {data} = response;
        commit('SET_TOKEN', data.token);
        commit('SET_NAME', data.username);
        commit('SET_AVATAR', data.avatar);
        setToken(data.token);
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({commit, state}) {
    return new Promise((resolve, reject) => {
      userInfo().then(response => {
        const {data} = response
        if (!data) {
          reject('Verification failed, please Login again.')
        }
        const {username, avatar, authorities} = data
        commit('SET_NAME', username)
        commit('SET_AVATAR', avatar)
        commit('SET_PERMISSIONS', authorities)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({commit, state}) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        removeToken()
        commit('SET_TOKEN', '')
        // resetRouter()
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({commit}) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      removeToken()
      resolve()
    })
  },
  updateLoadMenus({commit}) {
    return new Promise((resolve, reject) => {
      commit('SET_LOAD_MENUS', false)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

