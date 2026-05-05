<template>
  <div v-if="isOpen" class="fixed inset-0 z-50 flex items-center justify-center">

    <div class="absolute inset-0 bg-black/50" @click="close"></div>

    <form
      class="relative bg-white text-gray-500 w-full flex flex-col justify-center items-center max-w-md mx-4 md:p-6 p-4 text-left text-sm rounded-lg shadow-lg z-10"
      @submit.prevent="submit">

      <div class="flex justify-between items-center mb-2 w-full">
        <h2 class="text-2xl font-semibold text-center text-gray-800">
          {{ title }}
        </h2>
        <button type="button" class=" cursor-pointer  text-gray-400 hover:text-gray-600" @click="close">
          ✕
        </button>
      </div>
      <slot />
    </form>
  </div>
</template>

<script setup lang="ts">
defineProps({
  title: {
    required: true,
    type: String
  },
  isOpen: {
    type: Boolean,
    required: true
  }
})

const emit = defineEmits(["close", "submit"])

const close = () => emit("close")
const submit = () => emit("submit")
</script>
