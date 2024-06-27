export const Tile = ({
  text,
  onClick,
}: {
  text: string
  onClick?: () => void
}) => {
  return (
    <div
      onClick={onClick}
      className="w-full mb-[20px] rounded-[10px] text-white text-xs p-[16px] font-size-14 bg-gradient-to-r from-customPurple to-customPink border-ra"
    >
      {text}
    </div>
  )
}
