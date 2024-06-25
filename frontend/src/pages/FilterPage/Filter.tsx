import { Button } from '@/components/ui/button'
import { useNavigate } from 'react-router-dom'

export const FilterPage = () => {
  const navigate = useNavigate()
  return (
    <div className="flex items-center justify-center flex-col">
      <h1 className="text-center bg-red-200">FilterPage</h1>
      <Button onClick={() => navigate('/menu')}>Breakfast</Button>
    </div>
  )
}
