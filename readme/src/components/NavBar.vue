<template>
  <div
    class='bg-gradient-to-br from-[#e4bf8a] via-[#c1d3d0] to-[#9bb4b1] text-xl px-4 py-3 flex flex-row gap-4 justify-between items-center'>
    <input v-model="bookState.filter"
      class=" px-3 py-2 bg-white rounded-2xl flex-grow border border-amber-900 shadow-2xs text-xs "
      placeholder="Cerca una persona o un libro" />
    <div @click="props.logout()">
      <AiOutlineLogout color="red" class=" cursor-pointer" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { useBooksFiltersStore } from '@/stores/counter';
import { AiOutlineLogout } from 'vue-icons-plus/ai';
import { useRouter } from 'vue-router';
const bookStore = useBooksFiltersStore()
const bookState = bookStore.state

const props = defineProps(
  ['logout']
)

</script>

<style scoped></style>
