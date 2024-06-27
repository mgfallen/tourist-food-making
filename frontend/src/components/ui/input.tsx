import * as React from 'react'
import { cn } from '@/lib/utils'

export interface InputProps
  extends React.InputHTMLAttributes<HTMLInputElement> {}

const Input = React.forwardRef<HTMLInputElement, InputProps>(
  ({ className, type, ...props }, ref) => {
    return (
      <input
        type={type}
        className={cn(
          'flex h-10 w-fit rounded-l-[0px] rounded-r-[0px] border-b border-b-white border-t-transparent border-t-transparent border-l-transparent border-l-transparent border-r-transparent border-r-transparent bg-transparent px-3 py-2 text-sm text-white placeholder:text-white text-center focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-transparent focus-visible:ring-offset-2 focus-visible:rounded-l-[8px] focus-visible:rounded-r-[10px] disabled:cursor-not-allowed disabled:opacity-50',
          className,
        )}
        ref={ref}
        {...props}
      />
    )
  },
)

Input.displayName = 'Input'

export { Input }
