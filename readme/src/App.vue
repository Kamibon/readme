<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { useRoute } from 'vue-router'
import NavBar from './components/NavBar.vue'
import SideBar from './components/SideBar.vue'
import keycloak from './keycloak/keycloak'
import LoginPage from './pages/LoginPage.vue'
import { useUserStore } from './stores/user'
import './style.css'
import { watch } from 'vue'
import { useBooksFiltersStore } from './stores/book'
import api from './authAxios'

const userStore = useUserStore()
const { menuStatus, findMeResponse } = storeToRefs(userStore)

const route = useRoute()

const bookStore = useBooksFiltersStore()

watch(
  () => keycloak.value,
  (kc) => {
    if (!kc) return
    kc.onAuthSuccess = () => {
      api.interceptors.request.use(async (config) => {
        if (!config.url?.startsWith('/api')) {
          return config
        }

        config.headers.Authorization = `Bearer ${kc.token}`
        return config
      })
      if (kc.subject)
        userStore.findMe(kc.subject)
      bookStore.findAllBooks()
      userStore.findAllUsers()
    }

  },
  { immediate: true }
)

watch(findMeResponse, (val) => {
  if (val) {
    bookStore.findBooksByIds(val.favorites)
  }
})

</script>

<template>
  <div class="background-container min-h-screen">
    <SideBar v-if="!route.fullPath.includes('login')" />
    <div v-if="!route.fullPath.includes('login')"
      :class="`${menuStatus === 'closed' ? 'w-full md:w-[95%] left-0 md:left-[5%]' : 'w-full md:w-[80%] left-[20%]'} relative ${route.fullPath.includes('login') && 'w-full left-0'}`">
      <RouterView :key="$route.fullPath" />
    </div>
    <LoginPage v-if="route.fullPath.includes('login')" />
  </div>
</template>

<style scoped></style>
