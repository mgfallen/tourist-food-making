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
      num_days: parseInt(numDays),
      num_people: parseInt(numPeople),
      excluded_food: [1],
      budget: budget,
      available_cookware: 'кастрюля',
    })
  }
  return (
    <Background opacity={true}>
      <Container>
        <Title text="Filters" onClick={() => navigate('/')} />
        <div className="my-[20px] w-full">
          <DropDown
            title={'Исключаемые продукты'}
            items={['яйца', 'молоко', 'сахар', 'глютен']}
            multiSelect={true}
          />
          <DropDown
            title={'Доступная посуда'}
            items={['кастрюля', 'сковородка']}
            multiSelect={true}
          />
          <DropDown
            title={'Бюджет'}
            items={['маленький', 'средний', 'большой']}
            multiSelect={false}
            onSelect={(item) => setBudget(item)}
          />
          <Tile text="Количество людей">
            <NumberInput
              min={1}
              max={100}
              step={1}
              placeholder="1"
              value={numPeople}
              onChange={(e) => setNumPeople(e.target.value)}
            />
          </Tile>
          <Tile text="Количество дней">
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
          Поиск
        </Button>
      </Container>
    </Background>
  )
}
