<template>
  <div
    @click="
      () => {
        userStore.findById(id!)
        router.push('/profile/' + props.id)
      }
    "
    class="rounded-2xl bg-gradient-to-br from-blue-400 to-blue-200 flex flex-col items-center px-4 py-3 shadow-2xl cursor-pointer"
  >
    <img
      :src="`https://covers.openlibrary.org/b/isbn/${'8804404078'}-L.jpg`"
      class="rounded-4xl size-16"
    />
    <span class="font-bold text-base md:text-lg">{{ props.name }}</span>
    <span class="font-semibold text-xs">{{ props.followers }} follower</span>
    <span class="font-medium text-xs text-gray-500 pt-3">{{ props.books }} libri in comune</span>
    <button
      v-on:click="
        (e) => {
          props.clickHandler!()
          e.stopPropagation()
        }
      "
      class="cursor-pointer hover:bg-cyan-700 mt-2 rounded-xl px-2 py-1 bg-cyan-800 text-sm text-white"
    >
      Segui
    </button>
  </div>
</template>

<script setup lang="ts">
import router from '@/router'
import { useUserStore } from '@/stores/user'

const props = defineProps({
  id: Number,
  name: String,
  followers: String,
  books: Number,
  clickHandler: Function,
})

const userStore = useUserStore()
</script>

<style scoped></style>
