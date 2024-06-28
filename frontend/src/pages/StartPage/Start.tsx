import { Background } from '@/components/Background'
import { Title } from '@/components/Title'
import { Button } from '@/components/ui/button'
import { useNavigate } from 'react-router-dom'

export const StartPage = () => {
  const navigate = useNavigate()
  return (
    <Background opacity={false}>
      <div className="flex items-center flex-col py-[60px] px-[48px] min-h-screen justify-between">
        <div className="text-5xl font-bold text-white">TourFood</div>
        <Button
          variant="custom"
          size="custom"
          onClick={() => navigate('/filter')}
        >
          Start
        </Button>
      </div>
    </Background>
  )
}
