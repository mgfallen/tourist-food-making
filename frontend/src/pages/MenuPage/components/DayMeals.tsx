import { Section } from '@/components/Section'
import { Button } from '@/components/ui/button'
import { useNavigate } from 'react-router-dom'

export const DayMeals = ({ dayNumber }: { dayNumber: string }) => {
  const navigate = useNavigate()
  return (
    <Section title={`Day ${dayNumber}`}>
      <Button
        size="large"
        variant="customGradient"
        onClick={() =>
          navigate('/recipe', { state: `День ${dayNumber}. Завтрак` })
        }
      >
        Breakfast
      </Button>
      <Button
        size="large"
        variant="customGradient"
        onClick={() =>
          navigate('/recipe', { state: `День ${dayNumber}. Обед` })
        }
      >
        Lunch
      </Button>
      <Button
        size="large"
        variant="customGradient"
        onClick={() =>
          navigate('/recipe', { state: `День ${dayNumber}. Ужин` })
        }
      >
        Dinner
      </Button>
    </Section>
  )
}
