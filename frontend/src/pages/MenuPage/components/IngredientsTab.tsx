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
          <div>Item 1</div> <div>price</div>
        </div>
      </Button>
      <Button
        size="large"
        variant="customGradient"
        // onClick={() => navigate('/recipe')}
      >
        <div className="w-full flex flex-row justify-between">
          <div>Item 2</div> <div>price</div>
        </div>
      </Button>
      <Button
        size="large"
        variant="customGradient"
        // onClick={() => navigate('/recipe')}
      >
        <div className="w-full flex flex-row justify-between">
          <div>Item 3</div> <div>price</div>
        </div>
      </Button>
      <Separator />
      <Button size="large" variant="customGradient">
        <div className="w-full flex flex-row justify-between">
          <div>Total</div> <div>price</div>
        </div>
      </Button>
    </Container>
  )
}
