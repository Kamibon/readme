import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { Book } from '@/data/book'
import { apiPath, PromiseStatuses } from '@/data/utils'
import api from '@/authAxios'

interface State {
  filter: string
  chosenBook?: Book
  findAllBooksResponse?: Book[]
  findBookByIdResponse?: Book
  findReadBooksResponse?: Book[]
  findAllBooksStatus: PromiseStatuses
  findBookByIdStatus: PromiseStatuses
  findReadBooksStatus: PromiseStatuses
}

export const useBooksFiltersStore = defineStore('filter', () => {
  const state = ref<State>({
    filter: '',
    chosenBook: undefined,
    findAllBooksStatus: PromiseStatuses.idle,
    findBookByIdStatus: PromiseStatuses.idle,
    findReadBooksStatus: PromiseStatuses.idle,
  })

  const methods = {
    changeFilter: (value: string) => {
      state.value.filter = value
    },
    setChosenBook: (book: Book) => {
      state.value.chosenBook = book
    },
    findAllBooks: async () => {
      state.value.findAllBooksResponse = await api
        .get(apiPath + 'books', {
          headers: {
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Headers': '*',
          },
        })
        .then((res) => res.data)
    },
    findBookById: async (id: string) => {
      state.value.findBookByIdResponse = await api
        .get(apiPath + 'books/' + id)
        .then((res) => res.data)
    },
    findReadBooks: async (ids: number[]) => {
      state.value.findReadBooksResponse = (
        await Promise.allSettled(ids.map((id) => api.get(apiPath + 'books/' + id)))
      )
        .filter((item) => item.status === 'fulfilled')
        .map((elem) => elem.value.data)
    },
    addReadBook: async (id: string, body: Book) => {
      api.put(apiPath + 'books/' + id, body)
      methods.findAllBooks()
    },
  }

  return { state, methods }
})
