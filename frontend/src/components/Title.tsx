export const Title = ({ text }: { text: string; onClick?: () => void }) => {
  return (
    <div className="rounded-[50px] text-white text-[20px] px-[75px] py-[10px] font-size-14 bg-customPink">
      {text}
    </div>
  )
}
