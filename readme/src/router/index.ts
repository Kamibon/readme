
import BookDetails from '@/pages/BookDetails.vue'
import FriendsPage from '@/pages/FriendsPage.vue'
import HomePage from '@/pages/HomePage.vue'
import LoginPage from '@/pages/LoginPage.vue'
import ProfilePage from '@/pages/ProfilePage.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Home',
      redirect: '',
      component: HomePage,
      children: [],
    },
    {
      path: '/login',
      name: 'Login',
      redirect: '',
      component: LoginPage,
      children: [],
    },
    {
      path: '/friends',
      name: 'Friends',
      redirect: '',
      component: FriendsPage,
      children: [],
    },
    {
      path: '/profile',
      name: 'Profilo',
      redirect: '',
      component: ProfilePage,
      children: [],
    },
    {
      path: '/profile/:id?',
      name: 'Profilo-dettaglio',
      redirect: '',
      component: ProfilePage,
      children: [],
    },
    {
      path: '/books/:id',
      name: 'BookDetails',
      redirect: '',
      component: BookDetails,
      children: [],
    },
  ],
})

export default router
