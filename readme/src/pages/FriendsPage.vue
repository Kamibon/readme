<template>
  <div class="px-4 py-3 flex flex-wrap gap-4">
    <ProfileCard v-bind:key="value.id" v-for="value in follows" :id="value.id" :name="value.username" :followers="'0'"
      :books="value.favorites.filter((fav) => findMeResponse?.favorites?.includes(fav)).length
        " />
  </div>
</template>

<script setup lang="ts">
import ProfileCard from '@/components/ProfileCard.vue'
import { useFollowStore } from '@/stores/follow'
import { useUserStore } from '@/stores/user'
import { storeToRefs } from 'pinia'

const followStore = useFollowStore()
const userStore = useUserStore()
const { follows } = storeToRefs(followStore)
const { findMeResponse } = storeToRefs(userStore)

</script>

<style scoped></style>
