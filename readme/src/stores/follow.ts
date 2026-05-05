import { defineStore } from 'pinia'

import { apiPath } from '@/data/utils'
import api from '@/authAxios'
import { ref } from 'vue'
import type { User } from '@/data/user'

export const useFollowStore = defineStore('follow', () => {
  const follows = ref<User[]>([])
  const followers = ref<User[]>([])

  const getFollows = async (followerId: number) => {
    try {
      const response = await api.get(`${apiPath}api/follows/follows`, {
        params: { followerId },
      })
      follows.value = response.data
    } catch (error) {
      console.error('Error fetching follows:', error)
    }
  }

  const getFollowers = async (followedId: number) => {
    try {
      const response = await api.get(`${apiPath}api/follows/followers`, {
        params: { followedId },
      })
      followers.value = response.data
    } catch (error) {
      console.error('Error fetching followers:', error)
    }
  }

  const followUser = async (followerId: number, followedId: number) => {
    try {
      if(!followedId || !followedId) throw Error('Ids cannot be undefined')
      await api.post(`${apiPath}api/follows/follow`, null, {
        params: { followerId, followedId },
      })
      getFollows(followerId)
    } catch (error) {
      console.error('Error following user:', error)
    }
  }

  const unfollowUser = async (followerId: number, followedId: number) => {
    try {
      await api.post(`${apiPath}api/follows/unfollow`, null, {
        params: { followerId, followedId },
      })
      getFollows(followerId)
    } catch (error) {
      console.error('Error unfollowing user:', error)
    }
  }

  return {
    followers,
    follows,
    getFollows,
    getFollowers,
    followUser,
    unfollowUser,
  }
})
