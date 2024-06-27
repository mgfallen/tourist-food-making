import { Button } from '@/components/ui/button'
import { useNavigate } from 'react-router-dom'

export const FilterPage = () => {
  const navigate = useNavigate()
  return (
    <div className="flex items-center flex-col py-[60px] px-[48px] min-h-screen justify-between">
      <div className="text-5xl font-bold text-white bg-pink-300 rounded-3xl p-2  text-4xl">Filters</div>
      <Button className="bg-mainBlue" onClick={() => navigate('/menu')}>Search</Button>
    </div>
  )
}
