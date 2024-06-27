import { Background } from '@/components/Background'
import { Container } from '@/components/Container'
import { Section } from '@/components/Section'
import { Title } from '@/components/Title'
import { Button } from '@/components/ui/button'
import { useNavigate } from 'react-router-dom'

export const RecipePage = () => {
  const navigate = useNavigate()
  return (
    <Background opacity={true}>
      <Container>
        <Title text="Recipe" />
      </Container>
    </Background>
  )
}
