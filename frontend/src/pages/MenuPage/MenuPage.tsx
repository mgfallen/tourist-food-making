import { Background } from '@/components/Background'
import { Title } from '@/components/Title'
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'
import { MenuTab } from './components/MenuTab'
import { IngredientsTab } from './components/IngredientsTab'
import { useNavigate } from 'react-router-dom'
import { Button } from '@/components/ui/button'
import { CircleX, Cross } from 'lucide-react'

export const MenuPage = () => {
  const navigate = useNavigate()
  return (
    <Background opacity={true}>
      <div className="flex flex-col w-full items-center">
        <Title text="Menu" onClick={() => navigate('/filter')} />
        <Tabs defaultValue="menu" className="w-full flex flex-col">
          <TabsList>
            <TabsTrigger value="menu">Menu</TabsTrigger>
            <TabsTrigger value="ingredients">Ingredients</TabsTrigger>
          </TabsList>
          <TabsContent value="menu">
            <MenuTab />
          </TabsContent>
          <TabsContent value="ingredients">
            <IngredientsTab />
          </TabsContent>
        </Tabs>
      </div>
    </Background>
  )
}
