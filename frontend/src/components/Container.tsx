export const Container = ({ children }: { children: React.ReactNode }) => {
  return (
    <div className="overflow-auto flex items-center flex-col px-[30px] min-h-full max-w-[400px] m-auto">
      {children}
    </div>
  )
}
