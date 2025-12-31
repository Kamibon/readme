<template>
  <div class=" rounded-xl cursor-pointer w-full px-2 py-4">
    <span class="text-[#e4bf8a] text-md">{{ props.title }}</span>
  </div>
</template>

<script setup lang="ts">
const props = defineProps(["title", "link"])
</script>

<style scoped>

</style>
