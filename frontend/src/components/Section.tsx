export const Section = ({
  title,
  children,
}: {
  title: string
  children: React.ReactNode
}) => {
  return (
    <div className="my-[20px] w-full h-fit rounded-[10px] border border-white flex flex-col items-center">
      <div className="text-white text-base font-light font-bold font-nunito py-[14px]">
        {title}
      </div>
      {children}
    </div>
  )
}
