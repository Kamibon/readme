<template>
  <div class="spinner-wrapper" :style="{ width: size + 'px', height: size + 'px' }">
    <div
      class="spinner"
      :style="{
        borderTopColor: color,
        animationDuration: speed + 's'
      }"
    ></div>
  </div>
</template>

<script lang="ts" setup>
defineProps({
  size: {
    type: Number,
    default: 40
  },
  color: {
    type: String,
    default: '#3498db'
  },
  speed: {
    type: Number,
    default: 1
  }
});
</script>

<style scoped>
.spinner-wrapper {
  display: inline-flex;
  align-items: center;
  justify-content: center;
}

.spinner {
  width: 100%;
  height: 100%;
  border: 4px solid rgba(0, 0, 0, 0.1);
  border-top: 4px solid;
  border-radius: 50%;
  animation: spin linear infinite;
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>
