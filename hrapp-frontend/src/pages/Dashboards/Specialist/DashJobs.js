import React, { useCallback, useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { DeleteWithAuth, GetWithAuth, PutWithAuth } from '../../../services/HttpService';
import { Alert, Box, Button, ListItem, ListItemText, Snackbar, Typography } from '@mui/material';
import ListIcon from '@mui/icons-material/List';
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';
import CloseIcon from '@mui/icons-material/Close';
import CheckIcon from '@mui/icons-material/Check';
import { FixedSizeList } from 'react-window';
import ActivateJobPopup from './ActivateJobPopup';

const BUTTON_COLOR_PRIMARY = 'primary';
const TEXT_COLOR_PRIMARY = 'white';
const TEXT_COLOR_SECONDARY = 'red';
const GREEN_BUTTON_COLOR = '#4CAF50';
const DARK_GREEN_BUTTON_COLOR = '#004D00';
const JOB_ITEM_HEIGHT = 85;

const DashJobs = () => {
  const { id } = useParams();
  const [jobList, setJobList] = useState([]);
  const [openPopup, setOpenPopup] = useState(null);
  const [errorSnackbarOpen, setErrorSnackbarOpen] = useState(false);
  const [errorSnackbarMessage, setErrorSnackbarMessage] = useState('');
  const [successSnackbarOpen, setSuccessSnackbarOpen] = useState(false);
  const [successSnackbarMessage, setSuccessSnackbarMessage] = useState('');

  const getSpecialist = useCallback(() => {
    GetWithAuth("/specialists/" + id)
    .then(res => res.json())
    .then(
        (result) => {
            if (result.jobs && Array.isArray(result.jobs)) {
              setJobList(result.jobs);
            }
        },
        (error) => {
            console.log(error)
        }
    )
    },[id]
  )

  useEffect(() => {
  getSpecialist() 
  }, [getSpecialist]);

  const deleteJob = (jobId) => {
    DeleteWithAuth("/jobs/"+jobId)
    .then(() => {
      window.location.reload();
    })
  }

  const handleDeleteButton = (jobId) => {
    deleteJob(jobId);
  }

  const handleStatusChangeWithTimes = (jobId, activationTime, deactivationTime) => {
    if (!activationTime || !deactivationTime) {
      setErrorSnackbarMessage("Please fill both activation and deactivation times.");
      setErrorSnackbarOpen(true);
      return;
    }
    const currentTime = new Date();
    if(activationTime.isBefore(currentTime)|| deactivationTime.isBefore(currentTime)) {
      setErrorSnackbarMessage("Activation or Deactivation time cannot be in the past.");
      setErrorSnackbarOpen(true);
      return;
    }
    const formattedActivationTime = activationTime.format("YYYY-MM-DDTHH:mm:ss");
    const formattedDeactivationTime = deactivationTime.format("YYYY-MM-DDTHH:mm:ss");

    PutWithAuth("/jobs/" + jobId, {
      activationTime: formattedActivationTime,
      deactivationTime: formattedDeactivationTime,
      activationStatus: "Active"
    })
      .then(() => {
        getSpecialist();
        setOpenPopup(null);
        handleSuccessSnackbarOpen('Job activated successfully!');
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleStatusChange = (jobId, newStatus) => {
    PutWithAuth("/jobs/" + jobId, {
      activationStatus: newStatus
    })
    .then(() => {
      getSpecialist();
    })
    .catch((error) => {
      console.error(error);
    });
  }

  const handleErrorSnackbarClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setErrorSnackbarOpen(false);
  };

  const handleSuccessSnackbarOpen = (message) => {
    setSuccessSnackbarMessage(message);
    setSuccessSnackbarOpen(true);
  };
  
  const handleSuccessSnackbarClose = () => {
    setSuccessSnackbarOpen(false);
  };

  const renderRow = (props: ListChildComponentProps) => {
    const { index, style } = props;
    const job = jobList[index];

    return (
      <ListItem style={style} key={index} component="div" >
          <ListItemText primary={`${job.title} | ${job.location} | ${job.code}`} />
          <Button
            size="small"
            variant="contained"
            color={BUTTON_COLOR_PRIMARY}
            endIcon={<ListIcon />}
          >
            <Link style={{textDecoration: "none", color:"white"}} to={{ pathname: '/specialists/' + id + '/jobs/' + job.id + '/applications'}} >
              Applications
            </Link>
          </Button>
          {job.activationStatus === 'Active' ? (
            <Button
              size="small"
              variant="outlined"
              endIcon={<CloseIcon />}
              sx={{ width: '120px', marginLeft: 2, color: DARK_GREEN_BUTTON_COLOR, borderColor: DARK_GREEN_BUTTON_COLOR, overflow: 'hidden', }}
              onClick={() => handleStatusChange(job.id, 'Deactivated')}
            >
              Deactivate
            </Button>
          ) : (
            <>
            <ActivateJobPopup
              open={openPopup === job.id}
              onClose={() => setOpenPopup(null)}
              onActivate={(activationTime, deactivationTime) =>
                handleStatusChangeWithTimes(job.id, activationTime, deactivationTime)
              }
            />
            <Button
              size="small"
              variant="outlined"
              color={BUTTON_COLOR_PRIMARY}
              endIcon={<CheckIcon />}
              sx={{ width: '120px', marginLeft: 2, color: GREEN_BUTTON_COLOR, borderColor: GREEN_BUTTON_COLOR, overflow: 'hidden', }}
              onClick={() => setOpenPopup(job.id)}
            >
              Activate
            </Button>
            </>
          )}

          <Button
            size="small"
            variant="outlined"
            endIcon={<DeleteIcon />}
            sx={{ marginLeft: 2, color: TEXT_COLOR_SECONDARY, borderColor: TEXT_COLOR_SECONDARY }}
            onClick={() => handleDeleteButton(job.id)}
          >
            Delete
          </Button>
      </ListItem>
    );
  };

    return (
      <>
      {jobList.length === 0 ? (
        <Typography 
          variant="h6"
          sx={{
            padding: 3,
            textAlign: 'center',
            color: '#999',
            fontStyle: 'italic',
          }}
        >
          No Jobs Created Yet
        </Typography>
      ) : (
      <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', bgcolor: 'background.paper', paddingTop: 2 }}>
          <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', width: '100%', paddingLeft: 26, paddingRight: 26 }}>
            <Typography variant="h5" component="h1" sx={{ fontFamily: 'Arial', color: '#333', fontWeight: 'bold' }}>
              Job List
            </Typography>
            <Button size="small" variant="contained" color={BUTTON_COLOR_PRIMARY} endIcon={<AddIcon />} sx={{ color: TEXT_COLOR_PRIMARY, backgroundColor: 'green' }}>
              <Link style={{textDecoration: "none", color:"white"}} to={{ pathname: '/specialists/' + id + '/new-job'}} >
                Create Job
              </Link>
            </Button>
          </Box>
          <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', bgcolor: 'background.paper', border: '5px solid #e0e0e0', marginTop: 3 }}>
            <FixedSizeList height={770} width={1200} itemSize={JOB_ITEM_HEIGHT} itemCount={jobList.length} overscanCount={5}>
              {(props) => renderRow({ ...props, jobList })}
            </FixedSizeList>
          </Box>
          <Snackbar open={successSnackbarOpen} autoHideDuration={4000} onClose={handleSuccessSnackbarClose} anchorOrigin={{vertical: "bottom", horizontal: "center"}}>
            <Alert elevation={6} onClose={handleSuccessSnackbarClose} severity="success" sx={{ width: '100%'}}>
              {successSnackbarMessage}
            </Alert>
          </Snackbar>
          <Snackbar open={errorSnackbarOpen} autoHideDuration={4000} onClose={handleErrorSnackbarClose} anchorOrigin={{vertical: "bottom", horizontal: "center"}}>
            <Alert elevation={6} onClose={handleErrorSnackbarClose} severity="error" sx={{ width: '100%'}}>
              {errorSnackbarMessage}
            </Alert>
          </Snackbar>
      </Box>
      )}
    </>
    )
}

export default DashJobs