import { Alert, Box, Button, Card, CardActions, CardContent, OutlinedInput, Snackbar, Typography } from '@mui/material';
import React, { useState } from 'react'
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import NativeSelect from '@mui/material/NativeSelect';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Collapse from '@mui/material/Collapse'; 
import ArrowBackIcon from '@mui/icons-material/ArrowBack';

const DashEdit = (props) => {
    const {applicant, application} = props;

    const [expanded, setExpanded] = useState(false);
    const [explanation, setExplanation] = useState("");
    const [isUpdated, setIsUpdated] = useState(false);
    const [status, setStatus] = useState(application.applicationStatus);

    const formattedAppliedDate = new Date(application.appliedDate).toLocaleString();
    const formattedLastUpdateTime = new Date(application.lastUpdateTime).toLocaleString();

    const updateApplication = () => {
        fetch("/applications/"+application.id,
        {
            method: "PUT",
            headers: 
            {
                "Content-Type": "application/json",
                "Authorization": localStorage.getItem("tokenKey")
            },
            body: JSON.stringify({
                applicationStatus: status,
                lastUpdateTime: new Date().toISOString()
            })
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
        
    }

    const handleBackButton = () => {
        window.location.reload();
    };

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
    };
    
  return (
    <Box sx={{flex:5, p:2}}>
        <Snackbar open={isUpdated} autoHideDuration={1800} onClose={handleClose} anchorOrigin={{vertical: "bottom", horizontal: "center"}}>
            <Alert onClose={handleClose} severity="success" sx={{ width: '100%'}}>
                Application status changed successfully!
            </Alert>
        </Snackbar>
        <Box display="flex" justifyContent="flex-end" alignItems="flex-end" >
          <Button size="small" variant="contained" color="primary" endIcon={<ArrowBackIcon />} onClick={handleBackButton} >
              Job List
          </Button >
        </Box>
            <Card sx={{ minWidth: 275, mb: 3, mt: 3 }}>
                <CardContent >
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
                        <Button variant='contained' size="small" onClick={handleAddButton} >Add</Button>
                        </Box>
                    </CardContent>
                </Collapse>
            </Card>
        </Box>
  )
}

export default DashEdit