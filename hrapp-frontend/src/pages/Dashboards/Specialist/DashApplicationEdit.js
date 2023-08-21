import { Alert, Box, Button, Card, CardActions, CardContent, Collapse, FormControl, InputLabel, NativeSelect, OutlinedInput, Snackbar, Typography } from '@mui/material'
import React, { useEffect, useState } from 'react'
import { GetWithAuth, PostWithAuth, PutWithAuth } from '../../../services/HttpService';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

import { Link, useParams } from 'react-router-dom';

const DashApplicationEdit = () => {
  const { id, jobId, applicationId } = useParams();
  const [application, setApplication] = useState(null);
  const [applicantId, setApplicantId] = useState(null);
  const [status, setStatus] = useState(null);
  const [appliedDate, setAppliedDate] = useState(null);
  const [lastUpdateTime, setLastUpdateTime] = useState(null);
  const [applicant, setApplicant] = useState(null);
  const [specialist, setSpecialist] = useState(null);
  const [expanded, setExpanded] = useState(false);
  const [explanation, setExplanation] = useState("");
  const [isUpdated, setIsUpdated] = useState(false);
  const [isAdded, setIsAdded] = useState(false);

  const formattedAppliedDate = new Date(appliedDate).toLocaleString();
  const formattedLastUpdateTime = new Date(lastUpdateTime).toLocaleString();

  useEffect(() => {
    GetWithAuth(`/applications/${applicationId}`)
      .then(res => res.json())
      .then(
        (result) => {
          setApplication(result);
          if (result) {
            setApplicantId(result.applicantId);
            setStatus(result.applicationStatus);
            setAppliedDate(result.appliedDate);
            setLastUpdateTime(result.lastUpdateTime);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    
    GetWithAuth(`/specialists/${id}`)
      .then(res => res.json())
      .then(
        (result) => {
          setSpecialist(result);
        },
        (error) => {
          console.log(error);
        }
      );
  }, [applicationId, id]);

  useEffect(() => {
    if (applicantId !== null) {
      GetWithAuth(`/applicants/${applicantId}`)
        .then(res => res.json())
        .then(
          (result) => {
            setApplicant(result);
          },
          (error) => {
            console.log(error);
          }
        );
    }
  }, [applicantId]);

  const updateApplication = () => {
      PutWithAuth("/applications/"+application.id, {
          applicationStatus: status,
      })
      .then((res) => res.json())
      .catch((err) => console.log("Error!"))
  }

  const addBlacklist = () => {
      PostWithAuth("/blacklists", {
          explanation: explanation,
          applicantId: applicant.id,
          specialistId: specialist.id,
      })
      .then((res) => res.json())
      .catch((err) => console.log("Error!"))
  }

  const handleExpandClick = () => {
      setExpanded(!expanded);
    };

  const handleExplanation = (value) => {
      setExplanation(value);
  }

  const handleUpdateButton = () => {
      updateApplication();
      setIsUpdated(true);
  }

  const  handleAddButton = () => {
      addBlacklist();
      setIsAdded(true);
      setExplanation("");

      setTimeout(() => {
        window.location.href = `/specialists/${id}/jobs/${jobId}/applications`;
      }, 1500);
  }

  const handleSelect = (event) => {
    const selectedValue = event.target.value;
    setStatus(selectedValue);
    setIsUpdated(false);
};

const handleClose = (event: React.SyntheticEvent | Event, reason?: string) => {
    if (reason === 'clickaway') {
      return;
    }

    setIsUpdated(false);
    setIsAdded(false);
};

  return (
    <Box sx={{flex:5, p:2}}>
    <Snackbar open={isUpdated} autoHideDuration={1500} onClose={handleClose} anchorOrigin={{vertical: "bottom", horizontal: "center"}}>
        <Alert onClose={handleClose} severity="success" sx={{ width: '100%'}}>
            Application status changed successfully!
        </Alert>
    </Snackbar>
    <Snackbar open={isAdded} autoHideDuration={1500} onClose={handleClose} anchorOrigin={{vertical: "bottom", horizontal: "center"}}>
        <Alert onClose={handleClose} severity="success" sx={{ width: '100%'}}>
            Applicant added to blacklist successfully!
        </Alert>
    </Snackbar>
    <Box display="flex" justifyContent="flex-end" alignItems="flex-end" >
      <Button size="small" variant="contained" color="primary" endIcon={<ArrowBackIcon />} >
        <Link style={{textDecoration: "none", color:"white"}} to={{ pathname: '/specialists/' + id + '/jobs/' + jobId + '/applications'}} >
          Application List
        </Link>
      </Button >
    </Box>
        <Card sx={{ minWidth: 275, mb: 3, mt: 3 }}>
            <CardContent >
            {applicant && application ? (
              <>
                <Typography variant="h6" >
                    Applicant Name: {applicant.firstName} {applicant.lastName}
                </Typography>
                <Typography variant="h6">
                    Applicant Mail: {applicant.email}
                </Typography>
                <Typography variant="h6">
                    Applied Date & Time: {formattedAppliedDate}
                </Typography>
                <Typography variant="h6">
                    Last Update Date & Time: {formattedLastUpdateTime}
                </Typography>
                <Typography variant="h6">
                    Application Status: {application.applicationStatus}
                </Typography>
            
                <FormControl variant="standard" sx={{ minWidth: 120, mt:1 }}>
                    <InputLabel variant="standard" htmlFor="uncontrolled-native">
                        Change Status
                    </InputLabel>
                    <NativeSelect
                        defaultValue={application.applicationStatus}
                        onChange={handleSelect}
                        inputProps={{
                            name: 'status',
                            id: 'uncontrolled-native',
                        }}
                        >
                        <option value={"Processing"}>Processing</option>
                        <option value={"Accepted"}>Accepted</option>
                        <option value={"Rejected"}>Rejected</option>
                    </NativeSelect>
                </FormControl>
              </> 
              ) : (
                  <Typography variant="h6">
                            Loading specialist data...
                  </Typography>
              )}
            </CardContent>

            <CardActions sx={{ justifyContent: 'space-between', alignItems: 'center' }}>
                <Button
                variant='contained' 
                size="small" 
                endIcon={<ExpandMoreIcon/>}
                expand={expanded}
                onClick={handleExpandClick}
                aria-expanded={expanded}
                aria-label="show more"
                >Add User to Blacklist</Button>
                <Button variant='contained' size="small" onClick={handleUpdateButton}>Update</Button>
            </CardActions>
            <Collapse in={expanded} timeout="auto" unmountOnExit>
                <CardContent >
                <Typography sx={{ mb: 2,fontSize: 15,color: "text.secondary"}}>
                        {<OutlinedInput 
                            multiline
                            placeholder='Explanation'
                            inputProps={{maxLength:250}}
                            fullWidth
                            value={explanation}
                            onChange={ (i) => handleExplanation(i.target.value)}
                        />}
                    </Typography>
                    <Box display="flex" justifyContent="flex-end" alignItems="flex-end">
                    <Button variant='contained' size="small" onClick={handleAddButton} >
                      Add
                    </Button>
                    </Box>
                </CardContent>
            </Collapse>
        </Card>
    </Box>
  )
}

export default DashApplicationEdit