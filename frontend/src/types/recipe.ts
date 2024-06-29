enum MealTime {
  'breakfast',
  'lunch',
  'dinner',
}
export type RecipeReply = {
  recipe_id: number
  name: string
  description: string
  ingredients: [{ product_id: number; quantity: number }]
  price: number
  mealTime: MealTime
}
