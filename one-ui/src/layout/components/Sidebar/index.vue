<template>
  <div :class="{'has-logo':showLogo}">
    <logo v-if="showLogo" :collapse="isCollapse"/>
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <div class="zt-title" :style="{backgroundColor:variables.menuBg}">
        <div
          style="color: #99a9bf;line-height: 50px;font-size: large;font-weight: 600;font-family: Avenir,Helvetica Neue,Arial,Helvetica,sans-serif;">
          <svg-icon v-if="isCollapse" icon-class="logo" style="margin-right: 0px"></svg-icon>
          <div v-else>
            <svg-icon icon-class="logo"></svg-icon>英雄协会
          </div>

        </div>
      </div>
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :background-color="variables.menuBg"
        :text-color="variables.menuText"
        :unique-opened="false"
        :active-text-color="variables.menuActiveText"
        :collapse-transition="false"
        mode="vertical">
        <sidebar-item v-for="route in permission_routers" :key="route.path" :item="route" :base-path="route.path"/>
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  import Logo from './Logo'
  import SidebarItem from './SidebarItem'
  import variables from '@/styles/variables.scss'

  export default {
    components: {SidebarItem, Logo},
    computed: {
      ...mapGetters([
        'sidebar',
        'permission_routers'
      ]),
      routes() {
        return this.$router.options.routes
      },
      activeMenu() {
        const route = this.$route
        const {meta, path} = route
        // if set path, the sidebar will highlight the path you set
        if (meta.activeMenu) {
          return meta.activeMenu
        }
        return path
      },
      showLogo() {
        return this.$store.state.settings.sidebarLogo
      },
      variables() {
        return variables
      },
      isCollapse() {
        return !this.sidebar.opened
      }
    }
  }
</script>
<style>
  .zt-title {
    height: 50px;
    border-bottom: 1px solid #606266;
    border-right: 1px solid #606266;
    color: aliceblue;
    text-align: center;
    line-height: 50px;
    vertical-align: middle;
  }
</style>
