import { createRouter, createWebHistory } from 'vue-router'
import MiniHomeView from '../views/MiniHomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'minihome',
      component: MiniHomeView,
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
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/AboutView.vue'),
    },
  ],
})

export default router
