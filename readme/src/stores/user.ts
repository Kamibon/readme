import type { User } from '@/data/user'
import { apiPath, PromiseStatuses } from '@/data/utils'
import { defineStore } from 'pinia'
import { ref } from 'vue'
import api from '@/authAxios'
import keycloak from '@/keycloak/keycloak'
import type { Page } from '@/data/page'

export const useUserStore = defineStore('user', () => {
  const findAllUsersResponse = ref<Page<User>>()
  const findMeResponse = ref<User>()
  const findUserByIdResponse = ref<User>()
  const findAllUsersStatus = ref<PromiseStatuses>(PromiseStatuses.idle)
  const findUserByIdStatus = ref<PromiseStatuses>(PromiseStatuses.idle)
  const menuStatus = ref<'open' | 'closed'>('closed')
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
    findAllUsersStatus.value = PromiseStatuses.loading
    try {
      const { data } = await api.get(apiPath + 'api/users')
      findAllUsersStatus.value = PromiseStatuses.success
      findAllUsersResponse.value = data
    } catch (error) {
      console.log(error)
      findAllUsersStatus.value = PromiseStatuses.failed
    }
  }

  const findMe = async (user_id: string) => {
    try {
      const { data } = await api.get(apiPath + 'api/users/find-me/' + user_id)
      findMeResponse.value = data
    } catch (error) {
      console.log(error)
    }
  }

  const findById = async (id: number) => {
    findUserByIdStatus.value = PromiseStatuses.loading
    try {
      const { data } = await api.get(apiPath + 'api/users/' + id)
      findUserByIdResponse.value = data
      findUserByIdStatus.value = PromiseStatuses.success
    } catch (error) {
      console.log(error)
      findUserByIdStatus.value = PromiseStatuses.failed
    }
  }

  const updateUser = async (id: string, user: Partial<User>) => {
    try {
      await api.put(apiPath + 'api/users/' + id, user)
      findMe(id)
    } catch (error) {
      console.log(error)
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
    findMe,
    findMeResponse,
    findUserByIdResponse,
    findUserByIdStatus,
    menuStatus,
    setMenuStatus,
    setToken,
    token,
    updateUser,
  }
})
