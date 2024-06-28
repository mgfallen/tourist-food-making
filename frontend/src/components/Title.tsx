import { ChevronLeft } from 'lucide-react'
import { Button } from './ui/button'

export const Title = ({
  text,
  onClick,
}: {
  text: string
  onClick?: () => void
}) => {
  return (
    <div className="flex flex-row w-full h-fit items-center justify-between">
      <Button size="icon" onClick={onClick}>
        <ChevronLeft />
      </Button>
      <div className="rounded-[50px] text-white text-xl px-[75px] py-[10px] my-[20px] ">
        {text}
      </div>
      <Button size="icon">
        <ChevronLeft color="transparent" />
      </Button>
    </div>
  )
}
