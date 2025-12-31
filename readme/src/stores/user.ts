import type { User } from '@/data/user'
import { apiPath, PromiseStatuses } from '@/data/utils'
import { defineStore } from 'pinia'
import { ref } from 'vue'
import api from '@/authAxios'
import keycloak from '@/keycloak/keycloak'

interface UserState {
  findAllUsersResponse?: User[]
  findUserByIdResponse?: User
  findMeResponse?: User
  findAllUsersStatus: PromiseStatuses
  findUserByIdStatus: PromiseStatuses
  findFollowedResponse: User[]
  findFollowedStatus: PromiseStatuses
  menuStatus: 'open' | 'closed'
  token?: string
}

export const useUserStore = defineStore('user', () => {
  const userState = ref<UserState>({
    findUserByIdStatus: PromiseStatuses.idle,
    findAllUsersStatus: PromiseStatuses.idle,
    menuStatus: 'closed',
    findFollowedResponse: [],
    findFollowedStatus: PromiseStatuses.idle,
  })

  const userMethods = {
    addFavoriteBook: async (user_id: number, book_id: number) => {
      try{

      await api.put(apiPath + 'users/' + user_id + '/books/' + book_id)
      }
      catch(e){
        console.log(e)
      }
      finally{
        userMethods.findMe(keycloak.value!.subject!)
      }
    },
    findAllUsers: async () => {
      userState.value.findAllUsersResponse = await api
        .get(apiPath + 'users')
        .then((res) => res.data)
    },
    findMe: async (user_id: string) => {
      userState.value.findMeResponse = await api
        .get(apiPath + 'users/find-me/' + user_id)
        .then((res) => res.data)
    },
    findById: async (id: string) => {
     userState.value.findMeResponse =  userState.value.findUserByIdResponse = await api
        .get(apiPath + 'users/' + id)
        .then((res) => res.data)
    },
    findFollowers: async (ids: string[]) => {
      userState.value.findFollowedResponse = (
        await Promise.allSettled(ids.map((id) => api.get(apiPath + 'users/' + id)))
      )
        .filter((item) => item.status === 'fulfilled')
        .map((elem) => elem.value.data)
    },
    setMenuStatus: (value: 'open' | 'closed') => {
      userState.value.menuStatus = value
    },
    setToken: (value: string) => {
      userState.value.token = value
    },
  }

  return { userState, userMethods }
})
