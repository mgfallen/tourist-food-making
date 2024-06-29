import { Container } from '@/components/Container'
import { Button } from '@/components/ui/button'
import { Separator } from '@/components/ui/separator'
import { useNavigate } from 'react-router-dom'

export const IngredientsTab = () => {
  //   const navigate = useNavigate()
  return (
    <Container>
      <Button
        size="large"
        variant="customGradient"
        // onClick={() => navigate('/recipe')}
      >
        <div className="w-full flex flex-row  justify-between">
          <div>Продукт 1</div> <div>price</div>
        </div>
      </Button>
      <Button
        size="large"
        variant="customGradient"
        // onClick={() => navigate('/recipe')}
      >
        <div className="w-full flex flex-row justify-between">
          <div>Продукт 2</div> <div>price</div>
        </div>
      </Button>
      <Button
        size="large"
        variant="customGradient"
        // onClick={() => navigate('/recipe')}
      >
        <div className="w-full flex flex-row justify-between">
          <div>Продукт 3</div> <div>цена</div>
        </div>
      </Button>
      <Separator />
      <Button size="large" variant="customGradient">
        <div className="w-full flex flex-row justify-between">
          <div>Итого</div> <div>цена</div>
        </div>
      </Button>
    </Container>
  )
}
