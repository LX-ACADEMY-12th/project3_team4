import { createRouter, createWebHistory } from 'vue-router'

import MiniHomeView from '../views/MiniHomeView.vue'
import GuestbookView from '../views/GuestbookView.vue'
import LoginView from '../views/LoginView.vue'
import SignupView from '../views/SignupView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // 로그인 페이지 경로
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
    // 미니홈피를 방문하는 경로 (동적 파라미터)
    {
      path: '/minihome/:loginId',
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
