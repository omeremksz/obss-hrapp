import * as React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { Box, Container, Stack} from '@mui/material';
import { useState } from 'react';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useCallback } from 'react';
import Navbar from '../../component/Navbar';
import Footer from '../../component/Footer';
import Header from '../../component/Header';

let applicantId = 3;

const JobDetails = () => {
    const { id } = useParams();
    const [job, setJob] = useState(null);
    const [isApplied, setIsApplied] = useState(false);
    const [applicationCount, setApplicationCount] = useState(0);

    const getJob = useCallback(() => {
      fetch("/jobs/"+id)
      .then(res => res.json())
      .then(
          (result) => {
              console.log(result);
              setJob(result);
              if (result.jobApplications && Array.isArray(result.jobApplications)) {
                setApplicationCount(result.jobApplications.length);
              }
          },
          (error) => {
              console.log(error)
          }
      )
      },[id]
    )

    const saveApplication = () => {
      fetch("/applications",
      {
          method: "POST",
          headers: 
          {
              "Content-Type": "application/json",
          },
          body: JSON.stringify({
              applicantId: applicantId,
              jobId: id
          })
      })
      .then((res) => res.json())
      .catch((err) => console.log("Error!"))
  }

    const handleSubmit = () => {
      if(!isApplied){
        setIsApplied(true);
        saveApplication();
        setApplicationCount(applicationCount+1);
      }
    }

    useEffect(() => {
      getJob()
    }, [getJob])
    
    useEffect(() => {
      if (job && Array.isArray(job.jobApplications)) {
        const jobControl = job.jobApplications.find(
          (application) => application.applicantId === applicantId
        );
        if (jobControl != null) {
          setIsApplied(true);
        }
      }
    }, [job])

    return(
      <>
        <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
          <Navbar/>
          <Header/>
          <Box sx={{ flex: 1, bgcolor: "#fafafa", py: 4 }}>
            <Container sx={{ pt: '30px', minHeight: '100%' }}>
              <Stack direction={{ xs: 'column', sm: 'row' }} spacing={{ xs: 1, sm: 2, md: 4 }}>
                <Box sx={{ flex: 4, p: 2 }}>
                  <Card sx={{ minWidth: 275, mb: 3, mt: 3 }}>
                    {job ? (
                      <>
                        <CardContent >
                            <Typography variant="h5" component="h3" >
                                {job.title}
                            </Typography>
                            <Typography variant="body2" >
                              <Box component="span" sx={{ fontWeight: 700 }}>Code</Box>: {job.code}
                            </Typography>
                            <Typography variant="body2" >
                              <Box component="span" sx={{ fontWeight: 700 }}>Category</Box>: {job.jobCategoryName}
                            </Typography>
                            <Typography variant="body2" >
                              <Box component="span" sx={{ fontWeight: 700 }}>Position</Box>: {job.jobPositionName}
                            </Typography>
                            <Typography variant="body2" >
                              <Box component="span" sx={{ fontWeight: 700 }}>Location</Box>: {job.location}
                            </Typography>
                            <Typography  variant="body2">
                              <Box component="span" sx={{ fontWeight: 700 }}>Application Period</Box>: {job.activationTime.slice(0,10).split('-').reverse().join('/')} - {job.deactivationTime.slice(0,10).split('-').reverse().join('/')}
                            </Typography>
                            <Typography variant="body2">
                              <Box component="span" sx={{ fontWeight: 700 }}>Number of Applications</Box>: {job ? applicationCount : "Loading..."}
                            </Typography>
                            <Typography  variant="body2">
                              <Box component="span" sx={{ fontWeight: 700 }}>HR Specialist</Box>: {job.specialistFirstName} {job.specialistLastName}
                            </Typography>
                            <Typography variant="body2" sx={{ pt: 2 }}>
                                {job.description}
                            </Typography>
                        </CardContent>
                        <Box display="flex" justifyContent="flex-end" alignItems="flex-end">
                          <CardActions>
                            <Button variant="contained" size="small" onClick={handleSubmit} disabled={isApplied}>Apply</Button>
                            </CardActions>
                        </Box>
                      </>
                      ) : (<div>Loading...</div>)
                    }
                  </Card>
                </Box>
              </Stack>
            </Container>
          </Box>
          <Footer/>
        </Box>
      </>
    )
}

export default JobDetails;