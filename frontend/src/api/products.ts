import { useQuery } from '@tanstack/react-query'
import { baseApiRequest } from '@/api/baseApiRequest'
import { ProductsReply } from '@/types/products'
export const useGetForm = ({ order_id }: { order_id: string }) => {
  const { data: productData } = useQuery({
    enabled: !!order_id,
    queryKey: ['product', order_id],
    queryFn: () => {
      return baseApiRequest<ProductsReply>({
        url: `/products/{order_id}`,
        method: 'GET',
      })
    },
  })

  return { productData }
}
