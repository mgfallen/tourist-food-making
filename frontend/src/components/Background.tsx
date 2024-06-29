import backgroundImage from '@/assets/background.jpg'
import { cn } from '@/utils'

export const Background = ({
  opacity,
  children,
}: {
  opacity: boolean
  children: React.ReactNode
}) => {
  const windowWidth = window.innerWidth < 600

  return (
    <>
      {windowWidth && (
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
            className={cn('z-1 fixed top-0 right-0 bottom-0 left-0', {
              'bg-customBlack': opacity,
            })}
          ></div>
        </div>
      )}
      {!windowWidth && (
        <div
          style={{
            backgroundImage: 'linear-gradient(to top, #C93D5F, #241A57)',
            width: '100vw',
            height: '100vh',
            position: 'fixed',
          }}
        >
          <div
            className={cn('z-1 fixed top-0 right-0 bottom-0 left-0', {
              'bg-customBlack': opacity,
            })}
          ></div>
        </div>
      )}
      <div className="relative min-h-screen">{children}</div>
    </>
  )
}
