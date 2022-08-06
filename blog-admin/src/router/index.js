import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: '仪表盘',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '仪表盘', icon: 'dashboard' }
    }]
  },

  {
    path: '/articles',
    component: Layout,
    redirect: '/articles/list',
    name: 'Articles',
    meta: { title: '文章', icon: 'form' },
    children: [
      {
        path: 'list',
        name: 'List',
        component: () => import('@/views/article/articleList'),
        meta: { title: '所有文章', icon: 'table' }
      },
      {
        path: 'write',
        name: 'Write',
        component: () => import('@/views/article/article'),
        meta: { title: '写文章', icon: 'write' }
      },
      {
        path: 'write/:articleId',
        name: 'Write',
        component: () => import('@/views/article/article'),
        meta: { title: '修改文章' }
      },
      {
        path: 'categories',
        name: 'Categories',
        component: () => import('@/views/categories/index'),
        meta: { title: '分类目录', icon: 'category' }
      },
      {
        path: 'tags',
        name: 'Tags',
        component: () => import('@/views/tags/index'),
        meta: { title: '标签', icon: 'tag' }
      }
    ]
  },

  {
    path: '/album',
    component: Layout,
    children: [
      {
        path: '',
        name: '相册',
        component: () => import('@/views/album/Album'),
        meta: { title: '相册', icon: 'album' }
      }
    ]
  },
  {
    path: '/album/',
    component: Layout,
    hidden: true,
    children: [
      {
        path: ':albumId',
        name: '照片管理',
        component: () => import('@/views/album/Photo'),
        meta: { title: '照片管理' }
      }
    ]
  },
  {
    path: '/security',
    component: Layout,
    redirect: '/security/role',
    name: '权限',
    meta: {
      title: '权限',
      icon: 'security'
    },
    children: [
      {
        path: 'role',
        component: () => import('@/views/security/role/role'), // Parent router-view
        name: '角色管理',
        meta: { title: '角色管理', icon: 'role' }
      },
      {
        path: 'permission',
        component: () => import('@/views/security/permission/permission'),
        name: '接口管理',
        meta: { title: '接口管理', icon: 'permission' }
      },
      {
        path: 'user',
        component: () => import('@/views/security/user/user'),
        name: '用户管理',
        meta: { title: '用户管理', icon: 'user' }
      }
    ]
  },

  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
        meta: { title: 'External Link', icon: 'link' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
