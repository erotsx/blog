import Home from '@/Home'

// import {Message} from 'element-ui';

import store from '@/store'

import {getToken} from '@/request/token'

// Vue.use(VueRouter)

const router = new VueRouter({
  routes: [
    {
      path: '/write/:id?',
      component: r => require.ensure([], () => r(require('@/views/blog/BlogWrite')), 'blogwrite'),
      meta: {
        requireLogin: true
      },
    },
    {
      path: '',
      name: 'Home',
      component: Home,
      children: [
        {
          path: '/',
          component: r => require.ensure([], () => r(require('@/views/Index')), 'index')
        },
        {
          path: '/log',
          component: r => require.ensure([], () => r(require('@/views/Log')), 'log')
        },
        {
          path: '/archives/:year?/:month?',
          component: r => require.ensure([], () => r(require('@/views/blog/BlogArchive')), 'archives')
        },
        {
          path: '/archive',
          component: r => require.ensure([], () => r(require('@/views/blog/Archive')), 'archive')
        },
        {
          path: '/messageBoard',
          component: r => require.ensure([], () => r(require('@/views/MessageBoard')), 'messageboard')
        },
        {
          path: '/tag',
          component: r => require.ensure([], () => r(require('@/views/blog/Tag')), 'tag')
        },
        {
          path: '/category',
          component: r => require.ensure([], () => r(require('@/views/blog/Category')), 'category')
        },
        {
          path: '/view/:id',
          component: r => require.ensure([], () => r(require('@/views/blog/BlogView')), 'blogview')
        },
        {
          path: '/article/:id',
          component: r => require.ensure([], () => r(require('@/views/blog/Article')), 'article')
        },
        {
          path: '/categories/:categoryId',
          component: r => require.ensure([], () => r(require('@/views/blog/ArticleList')), 'articleList')
        },
        {
          path: '/tags/:tagId',
          component: r => require.ensure([], () => r(require('@/views/blog/ArticleList')), 'articleList')
        },
        {
          path: '/:type/all',
          component: r => require.ensure([], () => r(require('@/views/blog/BlogAllCategoryTag')), 'blogallcategorytag')
        },
        {
          path: '/:type/:id',
          component: r => require.ensure([], () => r(require('@/views/blog/BlogCategoryTag')), 'blogcategorytag')
        }
      ]
    },
    {
      path: '/login',
      component: r => require.ensure([], () => r(require('@/views/Login')), 'login')
    },
    {
      path: '/register',
      component: r => require.ensure([], () => r(require('@/views/Register')), 'register')
    }

  ],
  scrollBehavior(to, from, savedPosition) {
    return {x: 0, y: 0}
  }
})

router.beforeEach((to, from, next) => {

  if (getToken()) {

    if (to.path === '/login') {
      next({path: '/'})
    } else {
      if (store.state.account.length === 0) {
        store.dispatch('getUserInfo').then(data => { //获取用户信息
          next()
        }).catch(() => {
          this.$message({
            type: 'warning',
            showClose: true,
            message: '登录已过期'
          })
          next({path: '/'})
        })
      } else {

        next()
      }
    }
  } else {
    if (to.matched.some(r => r.meta.requireLogin)) {
      this.$message({
        type: 'warning',
        showClose: true,
        message: '请先登录哦'
      })

    } else {
      next();
    }
  }
})


export default router
