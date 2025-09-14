import { createRouter, createWebHistory } from 'vue-router'

import MiniHomeView from '../views/MiniHomeView.vue'
import GuestbookView from '../views/GuestbookView.vue'
import LoginView from '../views/LoginView.vue'
import SignupView from '../views/SignupView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignupView,
    },
    {
      path: '/minihome',
      name: 'minihome',
      component: MiniHomeView,
    },
    {
      path: '/guestbook',
      name: 'guestbook',
      component: GuestbookView,
    },
  ],
})

export default router
