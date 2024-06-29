export type OrderReply = {
  orderId: number
  timestamp: string
  users: []
  recipes: [
    {
      day: number
      recipes: {
        breakfast: {
          recipeId: number
          name: string
        }
        lunch: {
          recipeId: number
          name: string
        }
        dinner: {
          recipeId: number
          name: string
        }
      }
    },
  ]
  products: [
    {
      productId: number
      amount: number
    },
  ]
}
