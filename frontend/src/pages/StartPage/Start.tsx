import { Background } from '@/components/Background'
import { Button } from '@/components/ui/button'
import { useNavigate } from 'react-router-dom'

export const StartPage = () => {
  const navigate = useNavigate()
  return (
    <Background opacity={false}>
      <div className="flex items-center flex-col py-[60px] px-[48px] min-h-screen justify-between">
        <div className="text-5xl font-bold text-white font-sedwick" >TourFood</div>
        <Button className="bg-mainBlue font-sedwick w-64"

          onClick={() => navigate('/filter')}
        >
          Start
        </Button>
      </div>
    </Background>
  )
}
