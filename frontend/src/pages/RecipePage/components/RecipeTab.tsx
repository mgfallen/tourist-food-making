import { Background } from '@/components/Background'
import { Container } from '@/components/Container'
import { Section } from '@/components/Section'
import { Tile } from '@/components/Tile'
import { Title } from '@/components/Title'
import { Button } from '@/components/ui/button'
import { useNavigate } from 'react-router-dom'

export const RecipeTab = () => {
  const navigate = useNavigate()
  return (
    <Container>
      <Section title="Продукты">
        <Tile text="Продукт" />
        <Tile text="Продукт" />
        <Tile text="Продукт" />
      </Section>
      <Section title="Dishes">
        <Tile text="Посуда" />
      </Section>
      <Section title="Рецепт">
        <Tile text="А здесь будет целый абзац текста про то, как варить овсянку" />
      </Section>
    </Container>
  )
}
