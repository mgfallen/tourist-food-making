import { API_URL } from '@/config/constants'
import { Outlet } from 'react-router-dom'

const App = () => {
  console.log(API_URL)

  return <Outlet />
}
export default App
