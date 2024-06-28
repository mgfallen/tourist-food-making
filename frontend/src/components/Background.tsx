import backgroundImage from '@/assets/background.jpg'
import { cn } from '@/lib/utils'
export const Background = ({
  opacity,
  children,
}: {
  opacity: boolean
  children: React.ReactNode
}) => {
  return (
    <>
      <div
        style={{
          backgroundImage: `url(${backgroundImage})`,
          backgroundSize: 'cover',
          backgroundPosition: 'center',
          width: '100vw',
          height: '100vh',
          position: 'fixed',
        }}
      >
        <div
          className={cn('z-1 fixed  top-0  right-0  bottom-0  left-0  ', {
            'bg-customBlack': opacity,
          })}
        ></div>
      </div>
      <div className="relative min-h-screen">{children}</div>
    </>
  )
}
