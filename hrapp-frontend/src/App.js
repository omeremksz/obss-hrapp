import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { NotFound } from './pages/NotFound';
import { Home } from './pages/Home';
import { CssBaseline, ThemeProvider } from '@mui/material';
import { theme } from './theme';

const App = () => {
  return (
    <>
      <ThemeProvider theme = {theme}>
        <CssBaseline/>
        <BrowserRouter>
        <Routes>
          <Route path='/' element={<Home/>}/>
          <Route path='*' element={<NotFound/>}/>
        </Routes>
        </BrowserRouter>
      </ThemeProvider>
    </>
  )
}

export default App