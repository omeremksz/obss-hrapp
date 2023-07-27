import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { NotFound } from './pages/Error/NotFound';
import { Home } from './pages/Home/Home';
import { CssBaseline, ThemeProvider } from '@mui/material';
import { theme } from './theme';
import Applicant from './pages/Applicant/Applicant';
import Job from './pages/Job/Job';

const App = () => {
  return (
    <>
      <ThemeProvider theme = {theme}>
        <CssBaseline/>
        <BrowserRouter>
        <Routes>
          <Route path='/' element={<Home/>}/>
          <Route path='/applicants/:applicantId' element={<Applicant/>}/>
          <Route path='/jobs/:jobId' element={<Job/>}/> {/* Should navigate to job details page*/}
          <Route path='*' element={<NotFound/>}/>
        </Routes>
        </BrowserRouter>
      </ThemeProvider>
    </>
  )
}

export default App