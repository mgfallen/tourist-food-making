import { Background } from '@/components/Background'
import { Title } from '@/components/Title'
import { Button } from '@/components/ui/button'
import { useNavigate } from 'react-router-dom'
import pic from '@/assets/fire.json'
import Lottie from 'lottie-react'
export const StartPage = () => {
  const navigate = useNavigate()
  const windowWidth = window.innerWidth < 600
  return (
    <Background opacity={false}>
      <div className="flex items-center flex-col py-[60px] px-[48px] min-h-screen justify-between">
        <div className="text-5xl font-bold text-white">TourFood</div>
        {!windowWidth && (
          <div className="relative w-[300px] h-[300px] max-w-md overflow-hidden bg-transparent rounded-lg">
            <Lottie animationData={pic} loop={true} />
          </div>
        )}
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
