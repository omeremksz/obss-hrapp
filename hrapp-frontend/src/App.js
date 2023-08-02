import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { NotFound } from './pages/Error/NotFound';
import { Home } from './pages/Home/Home';
import { CssBaseline, ThemeProvider } from '@mui/material';
import { theme } from './theme';
import Applicant from './pages/Applicant/Applicant';
import JobDetails from './pages/Job/JobDetails';
import Authentication from './pages/Authentication/Authentication';

const App = () => {
  return (
    <>
      <ThemeProvider theme = {theme}>
        <CssBaseline/>
        <BrowserRouter>
        <Routes>
          <Route path='/' element={<Home/>}/>
          <Route path='/applicants/:id' element={<Applicant/>}/>
          <Route path='/jobs/:id' element={<JobDetails/>}/>
          <Route path='/auth' element={<Authentication/>}/>
          <Route path='*' element={<NotFound/>}/>
        </Routes>
        </BrowserRouter>
      </ThemeProvider>
    </>
  )
}

export default App