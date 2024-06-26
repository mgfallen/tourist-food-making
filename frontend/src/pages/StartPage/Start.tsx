import { Button } from '@/components/ui/button'
import { useNavigate } from 'react-router-dom'
import background from '@/assets/images/StartEng.png';
import "./Onboard.css"
export const StartPage = () => {
  const navigate = useNavigate()
  return (
    <div className="onboard" style={{backgroundImage: `url(${background})`} }>
      <span style={{margin: "61px"}}></span>

        <h1 className="text-center bg-red-200 onboardHeader">Tour Food</h1>

      <Button  className="buttonStart" onClick={() => navigate('/filter')}>Start</Button>
    </div>
  )
}
