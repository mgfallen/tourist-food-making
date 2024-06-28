import { Container } from '@/components/Container'
import { DayMeals } from './DayMeals'

export const MenuTab = () => {
  return (
    <Container>
      <DayMeals dayNumber="1" />
      <DayMeals dayNumber="2" />
      <DayMeals dayNumber="3" />
      <DayMeals dayNumber="4" />
      <DayMeals dayNumber="5" />
      <DayMeals dayNumber="6" />
      <DayMeals dayNumber="7" />
    </Container>
  )
}
