<template>
  <div
    class="bg-gradient-to-br from-[#e4bf8a] via-brand-primary to-[#9bb4b1] text-xl px-4 py-3 flex flex-row gap-4 justify-between items-center">
    <DropdownMenu />
    <input v-model="filter"
      class=" hidden xs:block px-3 py-2 bg-white rounded-2xl flex-grow border border-amber-900 shadow-2xs text-xs text-amber-950"
      placeholder="Cerca un libro" />
    <button @click="logout()">
      <AiOutlineLogout color="red" class="cursor-pointer" />
    </button>
  </div>
</template>

<script setup lang="ts">
import { useBooksFiltersStore } from '@/stores/book'
import { storeToRefs } from 'pinia'
import { AiOutlineLogout } from 'vue-icons-plus/ai'
import DropdownMenu from './DropdownMenu.vue'

const bookStore = useBooksFiltersStore()
const { filter } = storeToRefs(bookStore)

defineProps(['logout'])
</script>

<style scoped></style>
