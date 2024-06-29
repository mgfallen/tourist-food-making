import { useQuery } from '@tanstack/react-query'
import { baseApiRequest } from '@/api/baseApiRequest'
import { OrderReply } from '@/types/order'
export const useGetOrder = ({ order_id }: { order_id: string }) => {
  const { data: orderData } = useQuery({
    enabled: !!order_id,
    queryKey: ['order', order_id],
    queryFn: () => {
      return baseApiRequest<OrderReply>({
        url: `/order/{order_id}`,
        method: 'GET',
      })
    },
  })

  return { orderData }
}
