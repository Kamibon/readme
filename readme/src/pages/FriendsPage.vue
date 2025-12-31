<template>
  <div class=" px-4 py-3 flex flex-wrap gap-4">
  <ProfileCard v-bind:key="value.id" v-for="value in userState.findFollowedResponse" :id = "value.id" :name= "value.username"  :followers="value.followed.length.toString()"
  :books="value.favorites.filter(fav => userState.findFollowedResponse[0]?.favorites?.includes(fav)).length"
 />

  </div>
</template>

<script setup lang="ts">
import ProfileCard from '@/components/ProfileCard.vue';
import { useUserStore } from '@/stores/user';

const userStore = useUserStore()
const [userState, userMethods] = [userStore.userState, userStore.userMethods]
</script>

<style scoped></style>
