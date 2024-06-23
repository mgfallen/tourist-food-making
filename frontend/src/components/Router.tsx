import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import App from './App'
import { Start } from '../pages/StartPage/Start'
import { Filter } from '@/pages/FilterPage/Filter'

export const Router = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<App />} path="/"> 
        <Route element={<Start />} path="/" />
        <Route element={<Filter />} path="/filter" />
        </Route>
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </BrowserRouter>
  )
}
