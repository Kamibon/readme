import api from '@/authAxios'
import type { Book } from '@/data/book'
import type { Page } from '@/data/page'
import { apiPath, PromiseStatuses } from '@/data/utils'
import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useBooksFiltersStore = defineStore('filter', () => {
  const filter = ref('')
  const chosenBook = ref<Book | undefined>()

  const findAllBooksResponse = ref<Page<Book>>()
  const findBookByIdResponse = ref<Book>()
  const findReadBooksResponse = ref<Book[]>()

  const findAllBooksStatus = ref(PromiseStatuses.idle)
  const findBookByIdStatus = ref(PromiseStatuses.idle)
  const findReadBooksStatus = ref(PromiseStatuses.idle)

  const changeFilter = (value: string) => {
    filter.value = value
  }

  const setChosenBook = (book: Book) => {
    chosenBook.value = book
  }

  const findAllBooks = async () => {
    findAllBooksStatus.value = PromiseStatuses.loading
    try {
      const { data } = await api.get(apiPath + 'api/books')
      findAllBooksStatus.value = PromiseStatuses.success
      findAllBooksResponse.value = data
    } catch (error) {
      console.log(error)
      findAllBooksStatus.value = PromiseStatuses.failed
    }
  }

  const findBookById = async (id: number) => {
    findBookByIdStatus.value = PromiseStatuses.loading
    try {
      const { data } = await api.get(apiPath + 'api/books/' + id)
      findBookByIdResponse.value = data
      findBookByIdStatus.value = PromiseStatuses.success
    } catch (error) {
      console.log(error)
      findBookByIdStatus.value = PromiseStatuses.failed
    }
  }

  const findReadBooks = async (ids: number[]) => {
    findReadBooksResponse.value = (
      await Promise.allSettled(ids.map((id) => api.get(apiPath + 'api/books/' + id)))
    )
      .filter((item) => item.status === 'fulfilled')
      .map((elem) => (elem as PromiseFulfilledResult<never>).value.data)
  }

  const addReadBook = async (id: number, body: Book) => {
    await api.put(apiPath + 'api/books/' + id, body)
    findAllBooks()
  }

  return {
    filter,
    chosenBook,
    findAllBooksResponse,
    findBookByIdResponse,
    findReadBooksResponse,
    findAllBooksStatus,
    findBookByIdStatus,
    findReadBooksStatus,
    changeFilter,
    setChosenBook,
    findAllBooks,
    findBookById,
    findReadBooks,
    addReadBook,
  }
})
