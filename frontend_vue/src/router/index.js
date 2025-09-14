import { createRouter, createWebHistory } from 'vue-router'
import AboutView from '../views/AboutView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: AboutView,
    },
    {
      path: '/minihome',
      name: 'minihome',
      component: () => import('../views/MiniHomeView.vue'),
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue'),
    },
    {
      path: '/guestbook',
      name: 'guestbook',
      component: () => import('../views/GuestbookView.vue'),
    },
    {
      path: '/signup',
      name: 'signup',
      component: () => import('../views/HomeView.vue'),
    },
    
  ],
})

export default router
