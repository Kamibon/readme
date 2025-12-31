<template>
  <div :class="`bg-[#9bb4b1] flex flex-col shrink-0 gap-2 items-center fixed left-0 top-0 z-20 h-screen transition-all w-[5%] px-2 py-3`">
    <span class="text-2xl text-amber-800 font-bold">{{  'R' }}</span>
    <li @click="router.push(link.path)"
      :class="` flex flex-row gap-3 items-center justify-center rounded-xl cursor-pointer w-full px-2 py-3 text-[#ab7c3a] text-md font-semibold ${route.path === link.path ? 'bg-white' : ''}`"
      v-for="(link, key) in links" :key="key">
      <component :is="link.icon" class="w-5 h-5" />
      <RouterLink v-if="isHover" :to="link.path">{{ link.name }}</RouterLink>
    </li>
  </div>
</template>

<script setup lang="ts">
import { FiHome } from 'vue-icons-plus/fi';
import { useRoute } from 'vue-router';
import { BsFillPeopleFill, BsFillPersonFill } from 'vue-icons-plus/bs'
import { ref } from 'vue';
import router from '@/router';
import { useUserStore } from '@/stores/user';

const route = useRoute()

const isHover = ref(false)

const links = {
  home: {
    path: "/",
    name: "Home",
    icon: FiHome
  },
  friends: {
    path: "/friends",
    name: "Amici",
    icon: BsFillPeopleFill
  },
  profile: {
    path: "/profile",
    name: "Profilo",
    icon: BsFillPersonFill
  }
}

</script>

<style scoped></style>
