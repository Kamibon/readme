
export interface User {
  id: number
  userId: string
  email: string
  username: string
  password: string
  favorites: number[]
}

export interface CredentialsDTO {
  username: string
  password: string
}


