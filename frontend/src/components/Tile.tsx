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
      className="items-center flex flex-row justify-between w-full mb-[20px] rounded-[10px] text-white p-[16px] text-base font-light font-nunito bg-myGrad border-ra"
    >
      {text}
      {children}
    </div>
  )
}
