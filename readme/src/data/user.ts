import type { Book } from './book'

export interface User {
  id: number
  user_id: string
  email: string
  username: string
  password: string
  favorites: Book[]
  follows: string[]
  followed: string[]
}

export interface CredentialsDTO {
  username: string
  password: string
}
