<template>
  <div class=" py-4 px-5 flex flex-col justify-center items-center gap-4">
    <img :src="`https://covers.openlibrary.org/b/isbn/${'8804404078'}-L.jpg`" class="  rounded-full size-32" />
    <span class=" font-semibold text-3xl">{{ user?.username }}</span>
    <div class=" flex flex-wrap gap-2">
      <div v-for="book in bookState.findReadBooksResponse" :key="book.id"
        class=" flex flex-col grow gap-2 items-center justify-center">
        <img @click="getDetails(book)" :src="`https://covers.openlibrary.org/b/isbn/${book.isbn}-M.jpg`"
          class="p-1 object-cover cursor-pointer  h-full rounded-md w-40 hover:bg-gray-200 ">
        <span class=" text-md font-semibold">{{ book.title }}</span>
        <div class="relative cursor-pointer group w-fit">
          <div
            @click="bookMethods.addReadBook( userState.findMeResponse!.id, book)">
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Book } from '@/data/book';
import router from '@/router';
import { useBooksFiltersStore } from '@/stores/counter';
import { useUserStore } from '@/stores/user';
import { watchEffect } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute()
const userStore = useUserStore()
const bookStore = useBooksFiltersStore()
const [userState, userMethods] = [userStore.userState, userStore.userMethods]
const [bookState, bookMethods] = [bookStore.state, bookStore.methods]


const isMe = typeof route.params.id === 'string'
const user = isMe? userState.findMeResponse : userState.findUserByIdResponse

function getDetails(book: Book) {
  bookMethods.setChosenBook(book)
  bookMethods.findBookById(book.id.toString())
  router.push("/books/" + book.id)
}

watchEffect(() => {
  if (user)
    bookMethods.findReadBooks( user.favorites.map(item => item.id ))
}
)
</script>

<style scoped></style>
