<template>
    <div class="flex items-center gap-2 text-gray-500 py-3">
        <button @click="prevPage" type="button" aria-label="previous" class="cursor-pointer mr-4 flex hover:bg-gray-100/70 p-2 rounded-md items-center gap-1">
            <svg class="mt-px" width="23" height="23" viewBox="0 0 23 23" fill="none"
                xmlns="http://www.w3.org/2000/svg">
                <path d="M5.75 12.5h11.5m-11.5 0 4.792-4.791M5.75 12.5l4.792 4.792" stroke="#6B7280" stroke-width="2"
                    stroke-linecap="round" stroke-linejoin="round" />
            </svg>
            <span class="hidden md:inline">Prec.</span>
        </button>

        <div class="flex gap-2 text-sm md:text-base">
            <button v-for="page in pages" :key="page" @click="goToPage(page)" :class="[
                'cursor-pointer flex items-center justify-center active:scale-95 w-9 md:w-12 h-9 md:h-12 aspect-square  hover:bg-gray-100/70 p-2  rounded-md transition-all',
                {
                    'bg-brand-primary text-white': page === currentPage
                }
            ]">
                {{ page }}
            </button>

        </div>

        <button @click="nextPage" type="button" aria-label="next" class="cursor-pointer ml-4 flex hover:bg-gray-100/70 p-2 rounded-md items-center gap-1">
            <span class="hidden md:inline ">Succ.</span>
            <svg class="mt-px" width="23" height="23" viewBox="0 0 23 23" fill="none"
                xmlns="http://www.w3.org/2000/svg">
                <path d="M17.25 11.5H5.75m11.5 0-4.792-4.79m4.792 4.79-4.792 4.792" stroke="#6B7280" stroke-width="2"
                    stroke-linecap="round" stroke-linejoin="round" />
            </svg>
        </button>
    </div>
</template>

<script setup lang="ts">
import { computed } from "vue";

type Props = {
    currentPage: number;
    totalItems: number;
    itemsPerPage: number;
};

const props = defineProps<Props>();

const emit = defineEmits<{
    (e: "update:currentPage", value: number): void;
}>();

const totalPages = computed(() =>
    Math.ceil(props.totalItems / props.itemsPerPage)
);

const pages = computed(() =>
    Array.from({ length: totalPages.value }, (_, i) => i + 1)
);

const goToPage = (page: number) => {
    if (page >= 1 && page <= totalPages.value) {
        emit("update:currentPage", page);
    }
}

const nextPage = () => {
    if (props.currentPage < totalPages.value) {
        goToPage(props.currentPage + 1);
    }
}

const prevPage = () => {
    if (props.currentPage > 1) {
        goToPage(props.currentPage - 1);
    }
}
</script>

<style scoped></style>
