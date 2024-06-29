import { Background } from '@/components/Background'
import { Container } from '@/components/Container'
import { Section } from '@/components/Section'
import { Tile } from '@/components/Tile'
import { Title } from '@/components/Title'
import { Button } from '@/components/ui/button'
import { useNavigate } from 'react-router-dom'

export const DiagramTab = () => {
  const navigate = useNavigate()
  return (
    <Container>
      <Section title="Ratio of proteins, fats and carbohydrates">
        "PFC Diagram"
      </Section>
    </Container>
  )
}
