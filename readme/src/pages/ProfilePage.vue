<template>
  <div v-if="findUserByIdStatus === PromiseStatuses.failed"
    class="flex flex-col items-center justify-center gap-3 py-4">
    <BiError color="#FF0000" :height="20" />
    <h2 class="text-black font-bold">C'è stato un errore nel caricamento dell'utente :|</h2>
  </div>
  <div class="w-full flex items-center justify-center">
    <SpinnerComponent v-if="findUserByIdStatus === PromiseStatuses.loading" />
  </div>
  <div v-if="user.value && findUserByIdStatus !== PromiseStatuses.loading"
    class="py-4 px-5 flex flex-col justify-center items-center gap-4">
    <img :src="`https://covers.openlibrary.org/b/isbn/${'8804404078'}-L.jpg`" class="rounded-full size-32" />
    <div class="flex flex-row gap-2 items-center">
      <span class="font-semibold text-3xl">{{ user?.value.username }}</span>
      <div class="cursor-pointer hover:opacity-70" @click="openModal = true">
        <BsPencil color="#104e64" v-if="isMe" />
      </div>
    </div>
    <div v-if="user.value.favorites.length" class="flex flex-wrap gap-2">
      <div v-for="book in user?.value.favorites" :key="book.id"
        class="flex flex-col grow gap-2 items-center justify-center">
        <img @click="getDetails(book)" :src="`https://covers.openlibrary.org/b/isbn/${book.isbn}-M.jpg`"
          class="p-1 object-cover cursor-pointer h-full rounded-md w-40 hover:bg-gray-200" />
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
    <ModalComponent @close="openModal = false" title="Modifica profilo" :isOpen="openModal">
      <img :src="`https://covers.openlibrary.org/b/isbn/${'8804404078'}-M.jpg`"
        class="rounded-full size-24 cursor-pointer hover:opacity-70 my-4" />
      <div class=" flex flex-col justify-start w-full">
        <label for="email">Username</label>
        <input v-if="findMeResponse" v-model="editedUsername" id="username"
          class="w-full border mt-1 border-gray-500/30 outline-none rounded p-2" placeholder="Inserisci username"
          required />
      </div>

      <button @click="() => userStore.updateUser(findMeResponse!.userId, { username: editedUsername })"
        :disabled="!editedUsername?.trim()" type="submit"
        class="w-full my-3 bg-[#14476e] cursor-pointer hover:bg-brand-primary active:scale-95 transition py-2.5 rounded text-white">
        Modifica
      </button>
    </ModalComponent>
  </div>
</template>

<script setup lang="ts">
import type { Book } from '@/data/book'
import { PromiseStatuses } from '@/data/utils'
import router from '@/router'
import { useBooksFiltersStore } from '@/stores/book'
import { useUserStore } from '@/stores/user'
import { storeToRefs } from 'pinia'
import { computed, ref } from 'vue'
import { BiError } from 'vue-icons-plus/bi'
import { BsPencil } from 'vue-icons-plus/bs'
import { useRoute } from 'vue-router'
import SpinnerComponent from '../components/SpinnerComponent.vue'
import ModalComponent from '@/components/ModalComponent.vue'

const route = useRoute()
const userStore = useUserStore()
const bookStore = useBooksFiltersStore()
const { findMeResponse, findUserByIdResponse, findUserByIdStatus } = storeToRefs(userStore)
const { } = storeToRefs(bookStore)

const openModal = ref(false)
const editedUsername = ref(findMeResponse.value?.username)
const isMe = computed(() => !route.params.id)
const user = computed(() => (isMe.value ? findMeResponse : findUserByIdResponse))

function getDetails(book: Book) {
  bookStore.findBookById(book.id)
  router.push('/books/' + book.id)
}

</script>

<style scoped></style>
