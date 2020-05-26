<template>
    <div id="app">
        <div :style="{backgroundImage:'url('+bgUrl+')'}" style="background-size: 100% 100%">
            <router-view/>
        </div>
    </div>
</template>

<script>
    import {getSetting} from "@/api/app";

    export default {
        name: 'App',
        data() {
            return {
                bgUrl: ''
            }
        },
        mounted() {
            document.body.removeChild(document.getElementById('Loading'));
            this.getBg();
        },
        methods: {
            getBg() {
                let _this = this;
                getSetting({'type': 1}).then(resp => {
                    _this.bgUrl = resp.data.bgUrl;
                });
            }
        }
    }
</script>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        color: #2c3e50;
    }
</style>
