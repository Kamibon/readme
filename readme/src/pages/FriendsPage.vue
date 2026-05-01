<template>
  <div class="px-4 py-3 flex flex-wrap gap-4">
    <ProfileCard v-bind:key="value.id" v-for="value in followers" :id="value.id" :name="value.username"
      :followers="'5'" :books="value.favorites.filter((fav) => findFollowedResponse[0]?.favorites?.includes(fav)).length
        " />
  </div>
</template>

<script setup lang="ts">
import ProfileCard from '@/components/ProfileCard.vue'
import { useFollowStore } from '@/stores/follow'
import { useUserStore } from '@/stores/user'
import { storeToRefs } from 'pinia'

const userStore = useUserStore()
const followStore = useFollowStore()
const { findFollowedResponse } = storeToRefs(userStore)
const { followers } = storeToRefs(followStore)
</script>

<style scoped></style>
