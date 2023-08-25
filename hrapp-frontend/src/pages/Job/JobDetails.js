import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { Alert, Box, Container, Snackbar, Stack} from '@mui/material';
import { useState } from 'react';
import { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useCallback } from 'react';
import Navbar from '../../component/Navbar';
import Footer from '../../component/Footer';
import Header from '../../component/Header';
import { GetWithAuth, PostWithAuth } from '../../services/HttpService';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';

const JobDetails = () => {
    const { id } = useParams();
    const [job, setJob] = useState(null);
    const [isApplied, setIsApplied] = useState(false);
    const [inBlacklist, setInBlacklist] = useState(false);
    const isAuthorized = localStorage.getItem("currentUser") == null ? false : true;
    const role = localStorage.getItem("role");
    const [applicationCount, setApplicationCount] = useState(0);
    const [blacklist, setBlacklist] = useState([]);
    const [loginRequiredDialogOpen, setLoginRequiredDialogOpen] = useState(false);
    const [successSnackbarOpen, setSuccessSnackbarOpen] = useState(false);
    const navigate = useNavigate();

    const getJob = useCallback(() => {
      fetch("/jobs/"+id)
      .then(res => res.json())
      .then(
          (result) => {
              setJob(result);
              if (result.jobApplications && Array.isArray(result.jobApplications)) {
                setApplicationCount(result.jobApplications.length);
              }
          },
          (error) => {
              console.log("Error!")
          }
      )
      },[id]
    )

    useEffect(() => { getJob() }, [getJob])

    const getBlacklist = useCallback(() => {
      if (job && job.specialistId) {
      GetWithAuth(`/blacklists?specialistId=${job.specialistId}`)
      .then(res => res.json())
      .then(
          (result) => {
              setBlacklist(result)
          },
          (error) => {
              console.log("Error!")
          }
      );
        }
    }, [job])

    useEffect(() => { getBlacklist() }, [getBlacklist])

    const checkBlacklist = useCallback(() => {
      if(blacklist) {
      const blacklistControl = blacklist.find(blacklistEntity => ""+blacklistEntity.applicantId === localStorage.getItem("currentUser"));
      if (blacklistControl != null) {
          setInBlacklist(true);
      }}
    }, [blacklist]);
  
    useEffect(() => { checkBlacklist()}, [checkBlacklist]);

    const saveApplication = () => {
      PostWithAuth("/applications", {
        applicantId: localStorage.getItem("currentUser"),
        jobId: id,
    })
      .then((res) => res.json())
      .then(() => {
        setSuccessSnackbarOpen(true);
      })
      .catch((err) => console.log(err))
  }

  const checkApplications = useCallback(() => {
    if (job && Array.isArray(job.jobApplications)) {
      const jobControl = job.jobApplications.find(
        (application) => ""+application.applicantId === localStorage.getItem("currentUser")
      );
      if (jobControl != null) {
        setIsApplied(true);
      }
    }
  }, [job]);

  useEffect(() => { checkApplications()}, [ checkApplications])

  const handleSubmit = () => {
    if (!isAuthorized) {
      setLoginRequiredDialogOpen(true);
    } else if (!isApplied){
      setIsApplied(true);
      saveApplication();
      setApplicationCount(applicationCount+1);
      setTimeout(() => {
        navigate("/applicants/" +localStorage.getItem("currentUser")+ "/applied-jobs");
      }, 3000);
    }
  }
    
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
                              <Box component="span" sx={{ fontWeight: 700 }}>Category</Box>: {job.jobCategory}
                            </Typography>
                            <Typography variant="body2" >
                              <Box component="span" sx={{ fontWeight: 700 }}>Position</Box>: {job.jobPosition}
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
                          {role !== "ROLE_SPECIALIST" && (
                            <Button 
                              variant="contained" 
                              size="small" 
                              onClick={handleSubmit} 
                              disabled={ isApplied || inBlacklist }>
                                {inBlacklist ? "Blacklisted" : (isApplied ? "Applied" : "Apply")}
                                </Button>
                          )}
                            </CardActions>
                        </Box>
                        <Dialog
                          open={loginRequiredDialogOpen}
                          onClose={() => setLoginRequiredDialogOpen(false)}
                        >
                          <DialogContent>
                            <DialogContentText>
                              You need to be logged in to apply for this job.
                              Would you like to log in?
                            </DialogContentText>
                          </DialogContent>
                          <DialogActions>
                            <Button
                              onClick={() => setLoginRequiredDialogOpen(false)}
                              color="primary"
                            >
                              Cancel
                            </Button>
                            <Button
                              onClick={() => {
                                setLoginRequiredDialogOpen(false);
                                navigate(`/auth`);
                              }}
                              color="primary"
                            >
                              Log In
                            </Button>
                          </DialogActions>
                        </Dialog>
                      </>
                      ) : (<Box>Loading...</Box>)
                    }
                  </Card>
                </Box>
              </Stack>
            </Container>
          </Box>
          <Footer/>
        </Box>
        <Snackbar open={successSnackbarOpen} autoHideDuration={2000} onClose={() => setSuccessSnackbarOpen(false)} anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}>
        <Alert elevation={6} onClose={() => setSuccessSnackbarOpen(false)} everity="success" sx={{ width: '100%' }}>
          Congrats, your application was sent successfully!
        </Alert>
      </Snackbar>
      </>
    )
}

export default JobDetails;