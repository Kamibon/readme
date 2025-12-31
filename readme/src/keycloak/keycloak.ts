import Keycloak from 'keycloak-js'
import { ref } from 'vue'

const keycloak = ref<Keycloak|null>(null)

keycloak.value = new Keycloak({
  url: 'http://localhost:9090',
  realm: 'readme',
  clientId: 'readme',
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

keycloak.value.init({ onLoad: 'login-required', redirectUri: 'http://localhost:5173' }).then((authenticated) => {
  if(authenticated)
  localStorage.setItem('auth', '')
}).catch((error)=> console.log(error))

export default keycloak
