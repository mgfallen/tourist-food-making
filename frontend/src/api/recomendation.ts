import { useQueryClient, useMutation } from '@tanstack/react-query'
import { baseApiRequest } from './baseApiRequest'
import {
  RecomendationCreation,
  RecomendationCreationReply,
} from '@/types/recomendation'

export const useRecomendation = () => {
  const client = useQueryClient()
  return useMutation({
    mutationFn: (data: RecomendationCreation) =>
      baseApiRequest<RecomendationCreationReply>({
        url: '/choice',
        method: 'POST',
        data,
      }),
    onSuccess: (data: RecomendationCreationReply) => {
      client.invalidateQueries({ queryKey: ['recomendation'] })
      console.log(data.order_id)
    },
    onError: (err: Error) => {
      console.log(err)
    },
  })
}
