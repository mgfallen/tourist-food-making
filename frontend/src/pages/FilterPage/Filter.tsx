import { Background } from '@/components/Background'
import { Container } from '@/components/Container'
import { Tile } from '@/components/Tile'
import { Title } from '@/components/Title'
import { Button } from '@/components/ui/button'
import { useNavigate } from 'react-router-dom'

export const FilterPage = () => {
  const navigate = useNavigate()
  return (
    <Background opacity={true}>
      <Container>
        <Title text="FilterPage" />
        <div className=" w-full">
          <Tile text="filter 1" />
          <Tile text="filter 2" />
          <Tile text="filter 3" />
          <Tile text="filter 4" />
          <Tile text="filter 5" />
        </div>
        <Button
          size="custom"
          variant="custom"
          onClick={() => navigate('/menu')}
        >
          Search
        </Button>
      </Container>
    </Background>
  )
}
