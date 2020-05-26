const getters = {
    sidebar: state => state.app.sidebar,
    device: state => state.app.device,
    permission_routers: state => state.permission.routers,
    token: state => state.app.token,
    userInfo: state => state.app.userInfo,
    avatar: state => state.app.avatar,
};

export default getters;