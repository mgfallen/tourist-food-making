import { Background } from '@/components/Background'
import { Container } from '@/components/Container'
import { Tile } from '@/components/Tile'
import { Title } from '@/components/Title'

import { Button } from '@/components/ui/button'

import { useNavigate } from 'react-router-dom'
import { DropDown } from './components/DropDown'
import { NumberInput } from './components/NumberInput'
export const FilterPage = () => {
  const navigate = useNavigate()
  return (
    <Background opacity={true}>
      <Container>
        <Title text="FilterPage" />
        <div className="my-[20px] w-full">
          <DropDown
            title={'Excluded foods'}
            items={['eggs', 'milk', 'sugar', 'gluten']}
            multiSelect={true}
          />
          <DropDown
            title={'Available dishes'}
            items={['pot', 'microwave', 'pan']}
            multiSelect={true}
          />
          <DropDown
            title={'Budget'}
            items={['small', 'medium', 'large']}
            multiSelect={false}
          />
          <DropDown
            title={'Number of people'}
            items={['1', '2', '3', '1', '2', '3']}
            multiSelect={false}
          />

          <Tile text="Number of people">
            <NumberInput min={1} max={100} step={1} placeholder="1" />
          </Tile>
          <Tile text="Number of days">
            <NumberInput min={1} max={100} step={1} placeholder="1" />
          </Tile>
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
