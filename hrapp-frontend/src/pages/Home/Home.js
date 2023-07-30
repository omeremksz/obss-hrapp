import React, { useEffect, useState } from 'react';
import Navbar from '../../component/Navbar';
import Header from '../../component/Header';
import { Box, Container, Stack} from '@mui/material';
import Footer from '../../component/Footer';
import JobForm from '../Job/JobForm';
import Job from '../Job/Job';


export const Home = () => {
  const [error, setError] = useState(null);
  const [isLoaded, setIsLoaded] = useState(false);
  const [jobList, setJobList] = useState([]); 

  const refreshJobs = () => {
    fetch("/jobs")
      .then(res => res.json())
      .then(
          (result) => {
              setIsLoaded(true);
              setJobList(result);
          },
          (error) => {
              console.log(error);
              setIsLoaded(true);
              setError(error);
          }
      )
  }

  useEffect(() => {
    refreshJobs();
  },[jobList])

  if (error) {
      return <div> Error! </div>;
  } else if (!isLoaded){
      return <div> Loading... </div>
  }
  else {
      return (
      <>
        <Box sx={{bgcolor: "#fafafa", minHeight: "100vh"}}>
          <Navbar/>
          <Header/>
          <Container>
            <Stack
              direction={{ xs: 'column', sm: 'row' }}
              spacing={{ xs: 1, sm: 2, md: 4 }}
            >

              {/* Job List Column */}
              <Box sx={{flex:5, p:2}}>
                <JobForm code = {"SBR6643"} specialistId = {3} jobCategoryId = {6} jobPositionId = {4} refreshJobs = {refreshJobs}></JobForm>

                {jobList.map(job => (
                  <Job id ={job.id} code = {job.code} title = {job.title} location = {job.location} 
                  description = {job.description} activationTime = {job.activationTime} 
                  deactivationTime = {job.deactivationTime} applicantsCount = {job.applicantsCount}
                  jobCategoryName = {job.jobCategory.name} jobPositionName = {job.jobPosition.name}/>
                ))}
              </Box>
            </Stack>
          </Container>
        </Box>
        <Footer/>
      </>
      )
  }
}
