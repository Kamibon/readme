<template>
  <div v-if="!user.value" class="flex flex-col items-center justify-center gap-3 py-4">
    <BiError color="#FF0000" :height="20" />
    <h2 class="text-black font-bold">C'è stato un errore nel caricamento dell'utente :|</h2>
  </div>
  <div v-else class="py-4 px-5 flex flex-col justify-center items-center gap-4">
    <img
      :src="`https://covers.openlibrary.org/b/isbn/${'8804404078'}-L.jpg`"
      class="rounded-full size-32"
    />
    <div class="flex flex-row gap-2 items-center">
      <span class="font-semibold text-3xl">{{ user?.value.username }}</span>
      <BsPencil color="#104e64" v-if="isMe" />
    </div>
    <div v-if="user.value.favorites.length" class="flex flex-wrap gap-2">
      <div
        v-for="book in user?.value.favorites"
        :key="book.id"
        class="flex flex-col grow gap-2 items-center justify-center"
      >
        <img
          @click="getDetails(book)"
          :src="`https://covers.openlibrary.org/b/isbn/${book.isbn}-M.jpg`"
          class="p-1 object-cover cursor-pointer h-full rounded-md w-40 hover:bg-gray-200"
        />
        <span class="text-md font-semibold text-cyan-900">{{ book.title }}</span>
        <div class="relative cursor-pointer group w-fit">
          <div @click="bookStore.addReadBook(findMeResponse!.id, book)"></div>
        </div>
      </div>
    </div>
    <div v-else>
      <h3 class="text-black font-bold">
        {{ `Sembra che ${user.value.username} non abbia ancora letto alcun libro :|` }}
      </h3>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Book } from '@/data/book'
import router from '@/router'
import { useBooksFiltersStore } from '@/stores/book'
import { useUserStore } from '@/stores/user'
import { storeToRefs } from 'pinia'
import { computed, watchEffect } from 'vue'
import { BsPencil } from 'vue-icons-plus/bs'
import { BiError } from 'vue-icons-plus/bi'
import { useRoute } from 'vue-router'

const route = useRoute()
const userStore = useUserStore()
const bookStore = useBooksFiltersStore()
const { findMeResponse, findUserByIdResponse } = storeToRefs(userStore)
const {} = storeToRefs(bookStore)

const isMe = computed(() => !route.params.id)
const user = computed(() => (isMe.value ? findMeResponse : findUserByIdResponse))

function getDetails(book: Book) {
  bookStore.findBookById(book.id)
  router.push('/books/' + book.id)
}

watchEffect(() => {
  if (user.value.value)
    bookStore.findReadBooks(user.value.value.favorites.map((item: Book) => item.id))
})
</script>

<style scoped></style>
