import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { NotFound } from './pages/Error/NotFound';
import { CssBaseline, ThemeProvider } from '@mui/material';
import { theme } from './theme';
import JobDetails from './pages/Job/JobDetails';
import Auth from './pages/Auth/Auth';
import Home from './pages/Home/Home';
import Layout from './pages/Dashboards/Global/Layout';
import DashSpecialist from './pages/Dashboards/Specialist/DashSpecialist';
import DashJobs from './pages/Dashboards/Specialist/DashJobs';
import NewJob from './pages/Dashboards/Specialist/NewJob';
import DashJobApplications from './pages/Dashboards/Specialist/DashJobApplications';
import DashApplicationEdit from './pages/Dashboards/Specialist/DashApplicationEdit';
import DashBlacklist from './pages/Dashboards/Specialist/DashBlacklist';
import WelcomePage from './pages/Auth/WelcomePage';
import DashApplicant from './pages/Dashboards/Applicant/DashApplicant';

const DashSpecialistHOC = Layout(DashSpecialist);
const DashJobsHOC = Layout(DashJobs);
const NewJobHOC = Layout(NewJob);
const DashJobApplicationsHOC = Layout(DashJobApplications);
const DashApplicationEditHOC = Layout(DashApplicationEdit)
const DashBlacklistHoc = Layout(DashBlacklist);

const DashApplicantHOC = Layout(DashApplicant);

const App = () => {

  return (
    <>
      <ThemeProvider theme = {theme}>
        <CssBaseline/>
        <BrowserRouter>
        <Routes>
          <Route path='/' element={<Home/>}/>

          <Route path='/applicants/:id' element={<DashApplicantHOC/>}/>
          <Route path='/applicants/welcome-page/' element={<WelcomePage/>}/>

          <Route path='/specialists/:id' element={<DashSpecialistHOC />}/>
          <Route path='/specialists/:id/jobs' element={<DashJobsHOC />}/>
          <Route path='/specialists/:id/jobs/:jobId/applications' element={<DashJobApplicationsHOC />}/>
          <Route path='/specialists/:id/jobs/:jobId/applications/:applicationId/edit' element={<DashApplicationEditHOC />}/>
          <Route path='/specialists/:id/new-job' element={<NewJobHOC />}/>
          <Route path='/specialists/:id/blacklist' element={<DashBlacklistHoc />}/>
          
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

