import { API_URL } from '@/config/constants'
import axios from 'axios'

interface BaseApiRequestOptions {
  url: string
  method?: 'GET' | 'POST' | 'PUT' | 'DELETE' | 'PATCH'
  data?: object
}

export async function baseApiRequest<T>({
  url,
  method = 'GET',
  data,
}: BaseApiRequestOptions): Promise<T> {
  const headers: Record<string, string> = {}

  const apiUrl = `${API_URL}${url}`
  const response = await axios({
    method,
    url: apiUrl,
    data,
    headers,
  })

  return response.data
}
