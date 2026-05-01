<template>
  <div class="flex flex-col md:hidden relative">
    <button @click="open = !open" class="w-full text-left px-4 pr-2 py-2 cursor-pointer">
      <FiBook v-if="!open" color="#1447e6" />
      <FiBookOpen v-if="open" color="#1447e6" />
    </button>

    <ul v-if="open" class="absolute top-full px-2 bg-white border border-gray-300 rounded shadow-md py-1 z-50">
      <li @click="router.push('/')" class="px-4 py-2 hover:bg-gray-500/10 cursor-pointer text-sm text-brand-primary">
        Home</li>
      <li @click="router.push('/friends')"
        class="px-4 py-2 hover:bg-gray-500/10 cursor-pointer text-sm text-brand-primary">Amici</li>
      <li @click="router.push('/profile')"
        class="px-4 py-2 hover:bg-gray-500/10 cursor-pointer text-sm text-brand-primary">Tu</li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import router from '@/router';
import { ref } from 'vue';
import { FiBook, FiBookOpen } from 'vue-icons-plus/fi';

const open = ref(false)

</script>
