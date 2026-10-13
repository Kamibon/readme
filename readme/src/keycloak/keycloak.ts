import Keycloak from 'keycloak-js'
import { ref } from 'vue'

const keycloak = ref<Keycloak | null>(null)

keycloak.value = new Keycloak({
  url: import.meta.env.VITE_KEYCLOAK_URL,
  realm: import.meta.env.VITE_REALM,
  clientId: import.meta.env.VITE_CLIENTID,
})

keycloak.value.onTokenExpired = async () => {
  try {
    const refreshed = await keycloak.value!.updateToken(30)
    if (!refreshed) keycloak.value!.login()
  } catch (err) {
    console.error('Token refresh failed', err)
    keycloak.value!.login()
  }
}

keycloak.value
  .init({ onLoad: 'login-required', redirectUri: import.meta.env.VITE_REDIRECT_URI })
  .then((authenticated) => {
    if (authenticated) localStorage.setItem('auth', '')
  })
  .catch((error) => console.log(error))

export default keycloak
