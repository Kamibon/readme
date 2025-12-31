<template>
  <div @click="() => { router.push('/profile/' + props.id) }"
    class=" rounded-2xl bg-gradient-to-br from-blue-400 to-blue-200 flex flex-col items-center px-4 py-3 shadow-2xl cursor-pointer">
    <img :src="`https://covers.openlibrary.org/b/isbn/${'8804404078'}-L.jpg`" class=" rounded-4xl size-16" />
    <span class=" font-bold text-lg">{{ props.name }}</span>
    <span class=" font-semibold text-xs">{{ props.followers }} follower</span>
    <span class=" font-medium text-xs text-gray-500 pt-3">{{ props.books }} libri in comune</span>
  </div>
</template>

<script setup lang="ts">
import router from '@/router';
import { useUserStore } from '@/stores/user';

const props = defineProps({
  id: String,
  name: String,
  followers: String,
  books: Number
})

const userStore = useUserStore()
const { userState, userMethods } = userStore
</script>

<style scoped></style>
