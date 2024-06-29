import { useQueryClient, useMutation } from '@tanstack/react-query'
import { baseApiRequest } from './baseApiRequest'
import {
  RecomendationCreation,
  RecomendationCreationReply,
} from '@/types/recomendation'
import { useNavigate } from 'react-router-dom'

export const useRecomendation = () => {
  const client = useQueryClient()
  const navigate = useNavigate()
  return useMutation({
    mutationFn: (data: RecomendationCreation) =>
      baseApiRequest<RecomendationCreationReply>({
        url: '/api/v1/recommendation',
        method: 'POST',
        data,
      }),
    onSuccess: (data: RecomendationCreationReply) => {
      client.invalidateQueries({ queryKey: ['recomendation'] })
      console.log(data.orderID)
      navigate('/menu', { state: data })
    },
    onError: (err: Error) => {
      console.log(err)
    },
  })
}
