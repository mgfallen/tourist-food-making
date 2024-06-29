import { Input, InputProps } from '@/components/ui/input'
import { cn } from '@/utils'
import React from 'react'

type NumberInputProps = Omit<InputProps, 'type'> & {
  type?: 'number'
  min?: number
  max?: number
  step?: number
  onSelect?: (num: number) => void
}

export const NumberInput = React.forwardRef<HTMLInputElement, NumberInputProps>(
  ({ className, type = 'number', min, max, step, ...props }, ref) => {
    return (
      <Input
        ref={ref}
        type={type}
        min={min}
        max={max}
        step={step}
        className={cn('rounded-e-lg rounded-s-none', className)}
        {...props}
      />
    )
  },
)

NumberInput.displayName = 'NumberInput'
