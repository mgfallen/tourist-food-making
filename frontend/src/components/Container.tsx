export const Container = ({ children }: { children: React.ReactNode }) => {
  return (
    <div className="overflow-auto flex items-center flex-col my-[20px] px-[30px] h-full">
      {children}
    </div>
  )
}
