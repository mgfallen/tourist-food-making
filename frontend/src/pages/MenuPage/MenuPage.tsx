import { Background } from '@/components/Background'
import { Title } from '@/components/Title'
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'
import { MenuTab } from './components/MenuTab'
import { IngredientsTab } from './components/IngredientsTab'
import { useLocation, useNavigate } from 'react-router-dom'
import { Button } from '@/components/ui/button'
import { CircleX, Container, Cross } from 'lucide-react'

export const MenuPage = () => {
  const navigate = useNavigate()
  const data = useLocation()
  const recomendations = data.state
  return (
    <Background opacity={true}>
      <div className="flex flex-col w-full items-center">
        <Title text="Menu" onClick={() => navigate('/filter')} />
        <Tabs defaultValue="menu" className="w-full flex flex-col">
          <TabsList>
            <TabsTrigger value="menu">Меню</TabsTrigger>
            <TabsTrigger value="ingredients">Продукты</TabsTrigger>
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
