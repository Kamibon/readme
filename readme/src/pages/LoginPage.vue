<template>
  <div class="min-h-screen flex items-center justify-center bg-cover bg-center relative w-screen"
    style="background-image: url('https://images.unsplash.com/photo-1512820790803-83ca734da794?auto=format&fit=crop&w=1600&q=80');">
    <div class="absolute inset-0 bg-opacity-500"></div>

    <div class="relative z-10 bg-white bg-opacity-90 rounded-xl shadow-lg p-8 w-full max-w-md">
      <h1 class="text-2xl font-bold text-center mb-6 text-gray-800">
        📚 Login
      </h1>

      <form @submit.prevent="onSubmit" class="space-y-4">
        <div>
          <label for="username" class="block text-sm font-medium text-gray-700">Username</label>
          <input v-model="form.username" id="username" required
            class="mt-1 w-full px-3 py-2 border border-gray-300 rounded-md focus:ring focus:ring-indigo-300 focus:border-indigo-500" />
        </div>

        <div>
          <label for="password" class="block text-sm font-medium text-gray-700">Password</label>
          <input v-model="form.password" id="password" type="password" required
            class="mt-1 w-full px-3 py-2 border border-gray-300 rounded-md focus:ring focus:ring-indigo-300 focus:border-indigo-500" />
        </div>

        <button type="submit"
          class="w-full bg-indigo-600 text-white py-2 rounded-md hover:bg-indigo-700 transition-colors">
          Accedi
        </button>
      </form>

      <p class="text-center text-sm text-gray-600 mt-4">
        Non hai un account?
        <router-link to="/" class="text-indigo-600 hover:underline font-medium">Registrati</router-link>
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
import keycloak  from '@/keycloak/keycloak'
import { useAuthStore } from '@/stores/auth'
import { useUserStore } from '@/stores/user'
import { storeToRefs } from 'pinia'
import { ref, watchEffect } from 'vue'
import { useRouter } from 'vue-router'

const form = ref({
  username: '',
  password: ''
})

const router = useRouter()

const userStore = useUserStore()
const authStore = useAuthStore()

const [userState, userMethods] = [userStore.userState, userStore.userMethods]
const { accessToken } = storeToRefs(authStore)

const { login } = authStore

const onSubmit = () => {
  login(form.value)
}

watchEffect(async() => {
  if (userState.findMeResponse) {
    router.replace('/')
    userMethods.findFollowers(userState.findMeResponse.follows)
  }
  if (accessToken.value.trim()) {
    await keycloak.init({ onLoad: 'check-sso' })
    keycloak.login()
    //router.replace('/')
  }
},)

</script>

<style scoped>
.bg-opacity-90 {
  backdrop-filter: blur(6px);
}
</style>
