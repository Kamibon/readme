import type { User } from '@/data/user'
import { apiPath, PromiseStatuses } from '@/data/utils'
import { defineStore } from 'pinia'
import { ref } from 'vue'
import api from '@/authAxios'
import keycloak from '@/keycloak/keycloak'

interface UserState {
  findAllUsersResponse: User[]
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
  const findAllUsersResponse = ref<User[]>([])
  const findMeResponse = ref<User>()
  const findUserByIdResponse = ref<User>()
  const findAllUsersStatus = ref<PromiseStatuses>(PromiseStatuses.idle)
  const findUserByIdStatus = ref<PromiseStatuses>(PromiseStatuses.idle)
  const menuStatus = ref<'open' | 'closed'>('closed')
  const findFollowedResponse = ref<User[]>([])
  const findFollowedStatus = ref<PromiseStatuses>(PromiseStatuses.idle)
  const token = ref<string | undefined>(undefined)

  const addFavoriteBook = async (user_id: number, book_id: number) => {
    try {
      await api.put(apiPath + 'api/users/' + user_id + '/books/' + book_id)
    } catch (e) {
      console.log(e)
    } finally {
      findMe(keycloak.value!.subject!)
    }
  }

  const findAllUsers = async () => {
    findAllUsersResponse.value = await api
      .get(apiPath + 'api/users')
      .then((res) => res.data)
      .catch((e) => console.log(e))
  }

  const findMe = async (user_id: string) => {
    findMeResponse.value = await api
      .get(apiPath + 'api/users/find-me/' + user_id)
      .then((res) => res.data)
  }

  const findById = async (id: number) => {
    findUserByIdResponse.value = await api.get(apiPath + 'api/users/' + id).then((res) => res.data)
  }

  const findFollowers = async (ids: number[]) => {
    findFollowedResponse.value = (
      await Promise.allSettled(ids.map((id) => api.get(apiPath + 'api/users/' + id)))
    )
      .filter((item) => item.status === 'fulfilled')
      .map((elem) => elem.value.data)
  }

  const followUser = async (followerId: number, followedId: number) => {
    try {
      await api.post(apiPath + 'api/users/' + followerId + '/follow/' + followedId)
    } catch (error) {
      console.log(error)
    } finally {
      findAllUsers()
    }
  }

  const setMenuStatus = (value: 'open' | 'closed') => {
    menuStatus.value = value
  }

  const setToken = (value: string) => {
    token.value = value
  }

  return {
    addFavoriteBook,
    findAllUsers,
    findAllUsersResponse,
    findAllUsersStatus,
    findById,
    findFollowedResponse,
    findFollowedStatus,
    findFollowers,
    findMe,
    findMeResponse,
    findUserByIdResponse,
    findUserByIdStatus,
    followUser,
    menuStatus,
    setMenuStatus,
    setToken,
    token,
  }
})
