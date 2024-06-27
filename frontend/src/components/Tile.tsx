export const Tile = ({
  text,
  onClick,
  children,
}: {
  text: string
  onClick?: () => void
  children?: React.ReactNode
}) => {
  return (
    <div
      onClick={onClick}
      className="items-center flex flex-row justify-between w-full mb-[20px] rounded-[10px] text-white text-xs p-[16px] font-size-14 bg-gradient-to-r from-customPurple to-customPink border-ra"
    >
      {text}
      {children}
    </div>
  )
}
