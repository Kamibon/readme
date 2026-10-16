
export interface CredentialsDTO {
  username: string
  password: string
}

export interface User extends CredentialsDTO{
  id: number
  userId: string
  email: string
  favorites: number[]
}




