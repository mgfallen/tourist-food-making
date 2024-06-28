import { Container } from '@/components/Container'
import { Section } from '@/components/Section'
import { Button } from '@/components/ui/button'
import { useNavigate } from 'react-router-dom'

export const MenuTab = () => {
  const navigate = useNavigate()
  return (
    <Container>
      <Section title="Day 1">
        <Button
          size="large"
          variant="customGradient"
          onClick={() => navigate('/recipe')}
        >
          Breakfast
        </Button>
        <Button
          size="large"
          variant="customGradient"
          onClick={() => navigate('/recipe')}
        >
          Lunch
        </Button>
        <Button
          size="large"
          variant="customGradient"
          onClick={() => navigate('/recipe')}
        >
          Dinner
        </Button>
      </Section>
      <Section title="Day 2">
        <Button
          size="large"
          variant="customGradient"
          onClick={() => navigate('/recipe')}
        >
          Breakfast
        </Button>
        <Button
          size="large"
          variant="customGradient"
          onClick={() => navigate('/recipe')}
        >
          Lunch
        </Button>
        <Button
          size="large"
          variant="customGradient"
          onClick={() => navigate('/recipe')}
        >
          Dinner
        </Button>
      </Section>
      <Section title="Day 3">
        <Button
          size="large"
          variant="customGradient"
          onClick={() => navigate('/recipe')}
        >
          Breakfast
        </Button>
        <Button
          size="large"
          variant="customGradient"
          onClick={() => navigate('/recipe')}
        >
          Lunch
        </Button>
        <Button
          size="large"
          variant="customGradient"
          onClick={() => navigate('/recipe')}
        >
          Dinner
        </Button>
      </Section>
    </Container>
  )
}
