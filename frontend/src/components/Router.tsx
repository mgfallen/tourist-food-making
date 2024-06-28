import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import App from './App'
import { StartPage } from '../pages/StartPage/Start'
import { FilterPage } from '@/pages/FilterPage/Filter'
import { MenuPage } from '@/pages/MenuPage/MenuPage'
import { RecipePage } from '@/pages/ResipePage/ResipePage'

export const Router = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<App />} path="/">
          <Route element={<StartPage />} path="/" />
          <Route element={<FilterPage />} path="/filter" />
          <Route element={<MenuPage />} path="/menu" />
          <Route element={<RecipePage />} path="/recipe" />
        </Route>
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </BrowserRouter>
  )
}
