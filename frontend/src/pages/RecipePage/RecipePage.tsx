import { Background } from '@/components/Background'
import { Container } from '@/components/Container'
import { Section } from '@/components/Section'
import { Tile } from '@/components/Tile'
import { Title } from '@/components/Title'
import { Button } from '@/components/ui/button'
import { useLocation, useNavigate } from 'react-router-dom'
import { IngredientsTab } from '../MenuPage/components/IngredientsTab'
import { MenuTab } from '../MenuPage/components/MenuTab'
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'
import { RecipeTab } from './components/RecipeTab'
import { DiagramTab } from './components/DiagramTab'

export const RecipePage = () => {
  const navigate = useNavigate()
  const location = useLocation()
  const dayMeal = location.state
  return (
    <Background opacity={true}>
      <div className="flex flex-col w-full items-center">
        <Title text={dayMeal} onClick={() => navigate('/menu')} />
        <Tabs defaultValue="recipe" className="w-full flex flex-col">
          <TabsList>
            <TabsTrigger value="recipe">Recipe</TabsTrigger>
            <TabsTrigger value="pfc">PFC</TabsTrigger>
          </TabsList>
          <TabsContent value="recipe">
            <RecipeTab />
          </TabsContent>
          <TabsContent value="pfc">
            <DiagramTab />
          </TabsContent>
        </Tabs>
      </div>
    </Background>
  )
}
