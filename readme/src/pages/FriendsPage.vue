<template>
  <div class="px-4 py-3 flex flex-wrap gap-4">
    <ProfileCard v-bind:key="value.id" v-for="value in follows" :id="value.id" :name="value.username" :followers="'0'"
      :books="commonBooksCount(value)" />
  </div>
</template>

<script setup lang="ts">
import ProfileCard from '@/components/ProfileCard.vue'
import type { User } from '@/data/user'
import { useFollowStore } from '@/stores/follow'
import { useUserStore } from '@/stores/user'
import { storeToRefs } from 'pinia'
import { computed } from 'vue'

const followStore = useFollowStore()
const userStore = useUserStore()
const { follows } = storeToRefs(followStore)
const { findMeResponse } = storeToRefs(userStore)


const myFavoriteIds = computed(() => {
  return new Set(findMeResponse.value?.favorites)
})

const commonBooksCount = (user: User) => {

  return user.favorites.filter((fav) =>
    myFavoriteIds.value.has(fav)
  ).length
}

</script>

<style scoped></style>
