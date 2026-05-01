<template>
  <div class="flex flex-col gap-2">
    <span class="font-semibold mt-2 text-2xl text-blue-700 text-center">I libri di tendenza</span>
    <div v-if="findAllBooksResponse && findMeResponse" class="flex flex-col sm:flex-row h-full px-4 py-5 gap-4">
      <div v-for="book in filteredBooks" :key="book.id" class="flex flex-col gap-2 items-center justify-center">
        <img loading="lazy" @click="getDetails(book)" :src="`https://covers.openlibrary.org/b/isbn/${book.isbn}-M.jpg`"
          class="p-1 object-cover cursor-pointer h-full rounded-md w-40 hover:bg-gray-200" />
        <span class="text-md font-semibold text-cyan-900">{{ book.title }}</span>
        <div class="relative cursor-pointer group w-fit">
          <div @click="userStore.addFavoriteBook(Number(findMeResponse?.id), book.id)">
            <FiBook title="L'ho letto" color="orange" :class="{
              'opacity-0': isFavorite(book.id),
              'opacity-100': !isFavorite(book.id),
            }" />
            <FiBookOpen title="L'ho letto" color="orange" :class="{
              'opacity-0': !isFavorite(book.id),
              'opacity-100': isFavorite(book.id),
            }" />
          </div>
        </div>
      </div>
    </div>
    <span class="font-semibold mt-2 text-2xl text-blue-700 text-center">Persone che potresti conoscere</span>
    <div class="px-4 py-3 grid grid-cols-2 md:flex md:flex-row gap-4">
      <ProfileCard v-bind:key="value.id" v-for="value in suggestedUsers" :id="value.id" :name="value.username"
        :followers="value.followed?.length.toString()" :books="commonBooksCount(value)"
        :click-handler="() => userStore.followUser(userStore.findMeResponse!.id, value.id)" />
    </div>
  </div>
</template>

<script setup lang="ts">
import ProfileCard from '@/components/ProfileCard.vue'
import type { Book } from '@/data/book'
import type { User } from '@/data/user'
import keycloak from '@/keycloak/keycloak'
import router from '@/router'
import { useBooksFiltersStore } from '@/stores/book'
import { useFollowStore } from '@/stores/follow'
import { useUserStore } from '@/stores/user'
import { storeToRefs } from 'pinia'
import { computed, onMounted, watch } from 'vue'
import { FiBook, FiBookOpen } from 'vue-icons-plus/fi'

const bookStore = useBooksFiltersStore()
const userStore = useUserStore()
const followStore = useFollowStore()

const { findAllBooksResponse, filter } = storeToRefs(bookStore)
const { findMeResponse, findAllUsersResponse } = storeToRefs(userStore)

watch(() => keycloak.value, (val) => {
  if (val)
    val.onAuthSuccess = async () => {
      await userStore.findMe(keycloak.value!.subject!)
      await bookStore.findAllBooks()
      await userStore.findAllUsers()
    }
})


const filteredBooks = computed(() => {
  return findAllBooksResponse.value?.filter((item) =>
    item.title.includes(filter.value)
  ) ?? []
})

const suggestedUsers = computed(() => {
  if (!findMeResponse.value) return []

  return findAllUsersResponse.value.filter(
    (i) =>
      i.email !== findMeResponse.value!.email &&
      i.username !== 'admin'
  )
})

const myFavoriteIds = computed(() => {
  return new Set(findMeResponse.value?.favorites.map(f => f.id))
})

const isFavorite = (bookId: number) => {
  return findMeResponse.value?.favorites.some(f => f.id === bookId)
}

const commonBooksCount = (user: User) => {
  return user.favorites.filter((fav: Book) =>
    myFavoriteIds.value.has(fav.id)
  ).length
}

function getDetails(book: Book) {
  bookStore.setChosenBook(book)
  bookStore.findBookById(book.id)
  router.push('/books/' + book.id)
}

onMounted(() => {
  //if (findMeResponse) userMethods.findFollowers(findMeResponse.follows)
})

watch(findMeResponse, (val) => {
  if (val)
    followStore.getFollowers(val.id)
})
</script>
