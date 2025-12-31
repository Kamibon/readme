<script setup lang="ts">
import { useRoute } from 'vue-router';
import NavBar from './components/NavBar.vue'
import SideBar from './components/SideBar.vue';
import { useUserStore } from './stores/user';
import LoginPage from './pages/LoginPage.vue';
import { onMounted, watch, watchEffect, } from 'vue';
import { useBooksFiltersStore } from './stores/counter';
import './style.css'
import keycloak from './keycloak/keycloak';

const userStore = useUserStore()
const [userState, userMethods] = [userStore.userState, userStore.userMethods]

const route = useRoute()

const bookStore = useBooksFiltersStore()

const [bookState, bookMethods] = [bookStore.state, bookStore.methods]



watch(() => route.params.id, () => {
  if (route.params.id) {
    const paramId = route.params.id
    const isMe = Number.isNaN(paramId)
    const user = isMe ? userState.findMeResponse : userState.findUserByIdResponse
    if (!isMe)
      userMethods.findById(paramId as string)
  }
})



const logout = () => {
  localStorage.removeItem('auth')
  keycloak.value?.logout()
}
</script>

<template>
  <div class="background-container min-h-screen">
    <SideBar v-if="!route.fullPath.includes('login')" />
    <div v-if="!route.fullPath.includes('login')"
      :class="`${userState.menuStatus === 'closed' ? 'w-[95%] left-[5%]' : 'w-[80%] left-[20%]'}   relative ${route.fullPath.includes('login') && 'w-full left-0'}`">
      <NavBar :logout="() => logout()" />
      <RouterView :key="$route.fullPath" />
    </div>
    <LoginPage v-if="route.fullPath.includes('login')" />
  </div>
</template>

<style scoped></style>
