enum Budget {
  'маленький',
  'средний',
  'большой',
}
enum Dishes {
  'кастрюля',
  'сковорода',
}
export type RecomendationCreation = {
  days: number
  people: number
  food_filters: number[]
  budget: Budget
  utensils: Dishes
}

export type RecomendationCreationReply = {
  order_id: number
  breakfast: {
    recipe_id: number
    name: string
  }
  lunch: {
    recipe_id: number
    name: string
  }
  dinner: {
    recipe_id: number
    name: string
  }
}
