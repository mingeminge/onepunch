import router from "../router";
import store from "../store";

const permission = router.beforeEach((to, from, next) => {
    let token = store.getters.token;
    if (!token) {
        if (to.path === '/login') {
            next();
        } else {
            store.dispatch('app/getInfo', localStorage.Authorization).then(resp => {
                if (resp.success) {
                    next();
                } else {
                    next('/login')
                }
            })
        }
    } else {
        next();
    }
});
export default permission;