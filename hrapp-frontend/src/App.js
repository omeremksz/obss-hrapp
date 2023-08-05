import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { NotFound } from './pages/Error/NotFound';
import { CssBaseline, ThemeProvider } from '@mui/material';
import { theme } from './theme';
import JobDetails from './pages/Job/JobDetails';
import Auth from './pages/Auth/Auth';
import Applicant from './pages/Profile/Applicant/Applicant';
import Specialist from './pages/Profile/Specialist/Specialist';
import Home from './pages/Home/Home';


const App = () => {

  return (
    <>
      <ThemeProvider theme = {theme}>
        <CssBaseline/>
        <BrowserRouter>
        <Routes>
          <Route path='/' element={<Home/>}/>
          <Route path='/applicants/:id' element={<Applicant/>}/>
          <Route path='/specialists/:id' element={<Specialist/>}/>
          <Route path='/jobs/:id' element={<JobDetails/>}/>
          <Route path="/auth" element={<Auth />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
        </BrowserRouter>
      </ThemeProvider>
    </>
  )
}

export default App