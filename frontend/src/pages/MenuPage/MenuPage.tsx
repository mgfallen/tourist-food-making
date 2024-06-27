import { Background } from '@/components/Background'
import { Container } from '@/components/Container'
import { Section } from '@/components/Section'
import { Title } from '@/components/Title'
import { Button } from '@/components/ui/button'
import { useNavigate } from 'react-router-dom'

export const MenuPage = () => {
  const navigate = useNavigate()
  return (
    <Background opacity={true}>
      <Container>
        <Title text="MenuPage" />
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
    </Background>
  )
}
