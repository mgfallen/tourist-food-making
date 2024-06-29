import { Background } from '@/components/Background'
import { Container } from '@/components/Container'
import { Tile } from '@/components/Tile'
import { Title } from '@/components/Title'

import { Button } from '@/components/ui/button'

import { useNavigate } from 'react-router-dom'
import { DropDown } from './components/DropDown'
import { NumberInput } from './components/NumberInput'
import { useState } from 'react'
import { useRecomendation } from '@/api/recomendation'
export const FilterPage = () => {
  const navigate = useNavigate()
  const { mutate: recomendationRequest } = useRecomendation()
  const [budget, setBudget] = useState<string>('')
  const [numPeople, setNumPeople] = useState<string>('1')
  const [numDays, setNumDays] = useState<string>('1')
  const handleSendAnswer = () => {
    return recomendationRequest({
      days: parseInt(numDays),
      people: parseInt(numPeople),
      food_filters: [1],
      budget: budget,
      utensils: '',
    })
  }
  return (
    <Background opacity={true}>
      <Container>
        <Title text="Filters" onClick={() => navigate('/')} />
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
            onSelect={(item) => setBudget(item)}
          />
          <Tile text="Number of people">
            <NumberInput
              min={1}
              max={100}
              step={1}
              placeholder="1"
              value={numPeople}
              onChange={(e) => setNumPeople(e.target.value)}
            />
          </Tile>
          <Tile text="Number of days">
            <NumberInput
              min={1}
              max={100}
              step={1}
              placeholder="1"
              value={numDays}
              onChange={(e) => setNumDays(e.target.value)}
            />
          </Tile>
        </div>
        <Button
          size="custom"
          variant="custom"
          onClick={() => handleSendAnswer()}
        >
          Search
        </Button>
      </Container>
    </Background>
  )
}
