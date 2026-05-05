<template>
  <div class="p-8">
    <div class="w-full flex items-center justify-center">
      <SpinnerComponent v-if="findBookByIdStatus === PromiseStatuses.loading" />
    </div>
    <div v-if="book.value && findBookByIdStatus !== PromiseStatuses.loading"
      class="flex flex-col items-center md:items-start md:flex-row gap-4">
      <img class=" size-[60%] md:size-[40%]" :src="`https://covers.openlibrary.org/b/isbn/${book?.value.isbn}-L.jpg`" />
      <div class="flex flex-col gap-2">
        <span class="md:text-3xl text-2xl font-semibold text-cyan-950">{{ book?.value.title }}</span>
        <span class="md:text-xl text-lg text-cyan-900">{{ (book?.value.description as string).split('-')[0] }}</span>
        <!-- Da correggere -->
        <span class="text-md text-cyan-900">{{ (book?.value.description as string).split('-')[1] }}</span>
      </div>
    </div>
    <div v-if="findBookByIdStatus === PromiseStatuses.failed" class="flex flex-col items-center justify-center gap-3 py-4">
      <BiError color="#FF0000" :height="20" />
      <h2 class="text-black font-bold">Non è stato possibile trovare i dettagli del libro :|</h2>
    </div>
  </div>
</template>

<script setup lang="ts">
import SpinnerComponent from '@/components/SpinnerComponent.vue'
import { PromiseStatuses } from '@/data/utils'
import { useBooksFiltersStore } from '@/stores/book'
import { storeToRefs } from 'pinia'
import { computed } from 'vue'
import { BiError } from 'vue-icons-plus/bi'

const bookStore = useBooksFiltersStore()

const { findBookByIdResponse, findBookByIdStatus } = storeToRefs(bookStore)
const book = computed(() => findBookByIdResponse)

</script>

<style scoped></style>
