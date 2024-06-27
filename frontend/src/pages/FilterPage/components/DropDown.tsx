import { Button } from '@/components/ui/button'
import {
  DropdownMenu,
  DropdownMenuCheckboxItem,
  DropdownMenuContent,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu'
import { ChevronDown } from 'lucide-react'
import { useState } from 'react'

export const DropDown = ({
  title,
  items,
  multiSelect,
}: {
  title: string
  items: string[]
  multiSelect: boolean
}) => {
  const [selectedItems, setSelectedItems] = useState<boolean[]>(
    items.map(() => false),
  )
  const handleFoodClick = (index: number) => {
    setSelectedItems((prevState) =>
      multiSelect
        ? prevState.map((selected, i) => (i === index ? !selected : selected))
        : prevState.map((selected, i) => (i === index ? !selected : false)),
    )
  }
  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button variant="customGradient" size="large">
          <div className="w-full flex flex-row justify-between items-center">
            {title}
            <ChevronDown />
          </div>
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent className="w-56">
        <div>
          {items.map((item, index) => (
            <DropdownMenuCheckboxItem
              key={index}
              checked={selectedItems[index]}
              onCheckedChange={() => handleFoodClick(index)}
            >
              {item}
            </DropdownMenuCheckboxItem>
          ))}
        </div>
      </DropdownMenuContent>
    </DropdownMenu>
  )
}
