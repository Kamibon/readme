// stores/auth.ts
import axios from 'axios'
import { defineStore } from 'pinia'
import { ref } from 'vue'

const authUrl = 'auth'

export const useAuthStore = defineStore('auth', () => {
  const accessToken = ref<string>('')

  async function login(form: { username: string; password: string }) {
    const response = await axios.post(
      authUrl,
      new URLSearchParams({
        client_id: 'readme',
        username: form.username,
        password: form.password,
        grant_type: 'password',
        client_secret: import.meta.env.VITE_CLIENT_SECRET,
      }),
      {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
      },
    )

    setAccessToken(response.data.access_token)
  }

  function setAccessToken(token: string) {
    accessToken.value = token
  }

  return {
    accessToken,
    login,
    setAccessToken,
  }
})
