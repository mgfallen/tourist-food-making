import { useQuery } from '@tanstack/react-query'
import { baseApiRequest } from '@/api/baseApiRequest'
import { ProductsReply } from '@/types/products'
export const useGetRecipe = ({ recipe_id }: { recipe_id: string }) => {
  const { data: productData } = useQuery({
    enabled: !!recipe_id,
    queryKey: ['recipe', recipe_id],
    queryFn: () => {
      return baseApiRequest<ProductsReply>({
        url: `/recipe/{recipe_id}`,
        method: 'GET',
      })
    },
  })

  return { productData }
}
