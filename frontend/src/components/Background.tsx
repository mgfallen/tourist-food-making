import backgroundImage from '@/assets/background.jpg'
export const Background = ({
  opacity,
  children,
}: {
  opacity: boolean
  children: React.ReactNode
}) => {
  return (
    <div
      style={{
        backgroundImage: `url(${backgroundImage})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        width: '100vw',
        height: '100vh',
      }}
    >
      {opacity && (
        <div className="z-1  fixed  top-0  right-0  bottom-0  left-0  bg-customBlack">
          {children}
        </div>
      )}
      {!opacity && <>{children}</>}
    </div>
  )
}
