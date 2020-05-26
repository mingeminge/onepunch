import {login, userInfo} from '@/api/app'

const state = {
    sidebar: {
        opened: localStorage.getItem('sidebarStatus') ? !!+localStorage.getItem('sidebarStatus') : true,
        withoutAnimation: false
    },
    device: 'desktop',
    token: '',
    userInfo: {},
    avatar: ''
}

const mutations = {
    TOGGLE_SIDEBAR: state => {
        state.sidebar.opened = !state.sidebar.opened
        state.sidebar.withoutAnimation = false
        if (state.sidebar.opened) {
            localStorage.setItem('sidebarStatus', 1)
        } else {
            localStorage.setItem('sidebarStatus', 0)
        }
    },
    CLOSE_SIDEBAR: (state, withoutAnimation) => {
        localStorage.setItem('sidebarStatus', 0)
        state.sidebar.opened = false
        state.sidebar.withoutAnimation = withoutAnimation
    },
    TOGGLE_DEVICE: (state, device) => {
        state.device = device
    },
    SET_TOKEN: (state, token) => {
        state.token = token;
        /*localStorage.Authorization = token;*/
    },
    SET_AVATAR: (state, avatar) => {
        state.avatar = avatar;
    },
    SET_INFO: (state, info) => {
        state.userInfo = info;
    }
}
const actions = {
    toggleSideBar({ commit }) {
        commit('TOGGLE_SIDEBAR')
    },
    closeSideBar({ commit }, { withoutAnimation }) {
        commit('CLOSE_SIDEBAR', withoutAnimation)
    },
    toggleDevice({ commit }, device) {
        commit('TOGGLE_DEVICE', device)
    },
    getInfo: ({commit}, token) => {
        return new Promise((resolve, reject) => {
            userInfo({'token': token}).then(resp => {
                if (resp.success) {
                    commit('SET_TOKEN', token);
                    commit('SET_INFO', resp.data)
                } else {
                    commit('SET_INFO', {});
                    commit('SET_TOKEN', '');
                }
                resolve(resp);
            }).catch(e => {
                resolve(e)
            })
        });
    },
    setAvatar: ({commit}, avatar) => {
        commit('SET_AVATAR', avatar);
    },
    logout: ({commit}) => {
        return new Promise((resolve, reject) => {
            commit('SET_USERNAME', '');
            commit('SET_TOKEN', '');
            resolve();
        });
    },
    login: ({commit}, param) => {
        return new Promise((resolve, reject) => {
            param.password = Base64.encode(param.password);
            login(param).then(resp => {
                if (resp.success) {
                    localStorage.setItem('Authorization', resp.data.token);
                    commit('SET_TOKEN', resp.data.token);
                    commit('SET_AVATAR', resp.data.avatar);
                    commit('SET_INFO', resp.data)
                }
                resolve(resp);
            }).catch(e => {
                resolve(e);
            })
        })
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
