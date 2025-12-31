<template>
  <div class="p-8">
    <div class=" flex flex-col md:flex-row gap-4">
      <img :src="`https://covers.openlibrary.org/b/isbn/${bookState.findBookByIdResponse?.isbn}-L.jpg`">
      <div class=" flex flex-col gap-2">
        <span class=" text-3xl font-semibold">{{ bookState.findBookByIdResponse?.title }}</span>
        <span class=" text-xl">{{ bookState.findBookByIdResponse?.description }}</span>
        <!-- Da correggere -->
        <span class=" text-md">{{ bookState.findBookByIdResponse?.id }} hanno letto questo libro</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useBooksFiltersStore } from '@/stores/counter';

const bookStore = useBooksFiltersStore()
// eslint-disable-next-line @typescript-eslint/no-unused-vars
const [bookState, bookMethods] = [bookStore.state, bookStore.methods]
</script>

<style scoped></style>
