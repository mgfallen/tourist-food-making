enum MealTime {
  'breakfast',
  'lunch',
  'dinner',
}
export type RecipeReply = {
  recipe_id: number
  name: string
  discription: string
  ingredients: [{ product_id: string; quantity: number }]
  price: number
  mealTime: MealTime
}
