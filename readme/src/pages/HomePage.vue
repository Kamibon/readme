<template>
  <div v-if="bookState.findAllBooksResponse && userState.findMeResponse" class=" flex flex-col sm:flex-row h-full px-4 py-5 gap-4 ">
    <div v-for="book in bookState.findAllBooksResponse.filter(item => item.title.includes(bookState.filter))"
      :key="book.id" class=" flex flex-col grow gap-2 items-center justify-center">
      <img @click="getDetails(book)" :src="`https://covers.openlibrary.org/b/isbn/${book.isbn}-M.jpg`"
        class="p-1 object-cover cursor-pointer  h-full rounded-md w-40 hover:bg-gray-200 ">
      <span class=" text-md font-semibold text-cyan-900">{{ book.title }}</span>
      <div class="relative cursor-pointer group w-fit">
        <div
          @click="userMethods.addFavoriteBook(Number(userState.findMeResponse?.id), book.id)">
          <FiBook
            title="L'ho letto"
            color="orange"
            :class="{
              'opacity-0': userState.findMeResponse && userState.findMeResponse.favorites.find((elem) => elem.id === book.id),
              'opacity-100': !(userState.findMeResponse && userState.findMeResponse.favorites.find((elem) => elem.id === book.id)),
              'transition-opacity duration-300 group-hover:opacity-0': true
            }"
          />
          <FiBookOpen
            title="L'ho letto"
            color="orange"
            :class="{
              'opacity-100': userState.findMeResponse && userState.findMeResponse.favorites.find((elem) => elem.id === book.id),
              'opacity-0': !(userState.findMeResponse && userState.findMeResponse.favorites.find((elem) => elem.id === book.id)),
              'absolute top-0 left-0 transition-opacity duration-300 group-hover:opacity-100': true
            }"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Book } from "@/data/book"
import keycloak from "@/keycloak/keycloak"
import router from "@/router"
import { useBooksFiltersStore } from "@/stores/counter"
import { useUserStore } from "@/stores/user"
import { onMounted, watchEffect } from "vue"
import { FiBook, FiBookOpen } from 'vue-icons-plus/fi'

const bookStore = useBooksFiltersStore()
const userStore = useUserStore()

const [bookState, bookMethods] = [bookStore.state, bookStore.methods]
const [userState, userMethods] = [userStore.userState, userStore.userMethods]

function getDetails(book: Book) {
  bookMethods.setChosenBook(book)
  bookMethods.findBookById(book.id.toString())
  router.push("/books/" + book.id)
}

onMounted(() => {
  if (userState.findMeResponse)
    userMethods.findFollowers(userState.findMeResponse.follows)
})


watchEffect(() => {

  if (keycloak.value?.token) {
    bookMethods.findAllBooks()
    userMethods.findMe(keycloak.value!.subject!)
  }
  else {
    const id = setInterval(() => {
      if (keycloak.value?.token) {
        userMethods.findMe(keycloak.value!.subject!)
        bookMethods.findAllBooks()
        clearInterval(id)
        return
      }
    }, 1000);
  }

})

</script>

<style scoped></style>
