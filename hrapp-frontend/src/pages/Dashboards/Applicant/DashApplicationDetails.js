import { Alert, Box, Button, Card, CardActions, CardContent, Snackbar, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom';
import { DeleteWithAuth, GetWithAuth } from '../../../services/HttpService';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import CancelPresentationIcon from '@mui/icons-material/CancelPresentation';
import moment from 'moment';

const DashApplicationDetails = () => {
  const { id, applicationId } = useParams();
  const [application, setApplication] = useState(null);
  const [jobId, setJobId] = useState(null);
  const [status, setStatus] = useState(null);
  const [job, setJob] = useState(null);
  const [formattedAppliedDate, setFormattedAppliedDate] = useState(null);
  const [formattedLastUpdateTime, setFormattedLastUpdateTime] = useState(null);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    GetWithAuth(`/applications/${applicationId}`)
      .then(res => res.json())
      .then(
        (result) => {
          setApplication(result);
          if (result) {
            setJobId(result.jobId);
            setStatus(result.applicationStatus);
            if(result.appliedDate) {
              setFormattedAppliedDate(moment(result.appliedDate).format('YYYY-MM-DD HH:mm:ss'));
            }
            if(result.lastUpdateTime) {
              setFormattedLastUpdateTime(moment(result.lastUpdateTime).format('YYYY-MM-DD HH:mm:ss'));
            }
          }
        },
        (error) => {
          console.log(error);
        }
      );
  }, [applicationId]);

  useEffect(() => {
    if (jobId !== null) {
      GetWithAuth(`/jobs/${jobId}`)
        .then(res => res.json())
        .then(
          (result) => {
            setJob(result);
          },
          (error) => {
            console.log(error);
          }
        );
    }
  }, [jobId]);

  const handleDeleteButton = (applicationId) => {
    deleteJob(applicationId);
  }

  const deleteJob = (applicationId) => {
    DeleteWithAuth("/applications/"+applicationId)
    .then(() => {
      setSnackbarMessage('Application withdrawn successfully.');
      setSnackbarOpen(true);
      setTimeout(() => {
        navigate('/applicants/' + id + '/applied-jobs/')
      }, 2000);
    })
  }

  const handleSnackbarClose = () => {
    setSnackbarOpen(false);
  };


  return (
    <Box sx={{flex:5, p:2}}>
    <Box display="flex" justifyContent="flex-end" alignItems="flex-end" >
      <Button size="small" variant="contained" color="primary" endIcon={<ArrowBackIcon />} >
        <Link style={{textDecoration: "none", color:"white"}} to={{ pathname: '/applicants/' + id + '/applied-jobs/'}} >
          Application List
        </Link>
      </Button >
    </Box>
          {application && job ? (
            <>
              <Typography component="h1" sx={{ fontSize: '18px', fontFamily: 'Arial', color: '#333', fontWeight: 'bold' }}>
                Job Details
              </Typography>
              <Card sx={{ minWidth: 275, mb: 3, mt: 3 }}>
              <CardContent >
                <Typography variant="h6">
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
                <Typography  variant="body2">
                  <Box component="span" sx={{ fontWeight: 700 }}>HR Specialist</Box>: {job.specialistFirstName} {job.specialistLastName}
                </Typography>
                <Typography variant="body2" sx={{ pt: 2 }}>
                    {job.description}
                </Typography>
                </CardContent >
                </Card>
                
                <Typography component="h1" sx={{ fontSize: '18px', fontFamily: 'Arial', color: '#333', fontWeight: 'bold' }}>
                  Application Details
                </Typography>
                <Card sx={{ minWidth: 275, mb: 3, mt: 3 }}>
                <CardContent >
                <Typography variant="body2">
                    <Box component="span" sx={{ fontWeight: 700 }}>Applied Date & Time</Box>: {formattedAppliedDate}
                </Typography>
                <Typography variant="body2">
                  <Box component="span" sx={{ fontWeight: 700 }}>Last Update Date & Time</Box>: {formattedLastUpdateTime}
                </Typography>
                <Typography variant="body2">
                  <Box component="span" sx={{ fontWeight: 700 }}>Application Status</Box>: {status}
                </Typography>
                </CardContent >
                <Box display="flex" justifyContent="flex-end" alignItems="flex-end">
                  <CardActions>
                    <Button 
                      size="small"
                      variant="contained"
                      endIcon={<CancelPresentationIcon />}
                      sx={{ marginLeft: 2, color: 'white', borderColor: 'white' }}
                      onClick={() => handleDeleteButton(applicationId)}
                    >
                    Withdraw
                  </Button>
                    </CardActions>
                </Box>
                </Card>
                <Snackbar
                  open={snackbarOpen}
                  autoHideDuration={2000}
                  onClose={handleSnackbarClose}
                  anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
                >
                  <Alert
                    elevation={6}
                    onClose={handleSnackbarClose}
                    severity="success"
                  >
                    {snackbarMessage}
                  </Alert>
                </Snackbar>
                </>
              ) : (<Box>Loading...</Box>)
            }
        </Box>
  )
}

export default DashApplicationDetails