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
  num_days: number
  num_people: number
  excluded_food: number[]
  budget: string
  available_cookware: string
}

export type RecomendationCreationReply = {
  orderID: number
  recommendations: [
    {
      day: 1
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
}
