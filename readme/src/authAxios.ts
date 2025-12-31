
import axios from 'axios'
import keycloak from './keycloak/keycloak'

const api = axios.create({
  baseURL: '/',
})

api.interceptors.request.use(async (config) => {
  if (!config.url?.startsWith('/api')) {
    return config
  }

  await new Promise<void>((resolve) => {
    if (keycloak.value!.token) {
      return resolve()
    }

    const id = setInterval(() => {
      if (keycloak.value!.token) {
        clearInterval(id)
        resolve()
      }
    }, 500)
  })

  config.headers.Authorization = `Bearer ${keycloak.value!.token}`
  return config
})

export default api
