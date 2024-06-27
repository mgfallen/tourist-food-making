export const Title = ({ text }: { text: string; onClick?: () => void }) => {
  return (
    <div className="rounded-[50px] text-white text-xl px-[75px] py-[10px] my-[20px] ">
      {text}
    </div>
  )
}
