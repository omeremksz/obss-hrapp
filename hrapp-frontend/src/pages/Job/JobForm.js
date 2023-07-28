import * as React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { Alert, Box, OutlinedInput, Snackbar} from '@mui/material';
import { useState } from 'react';


const JobForm = ({ code, specialistId, jobCategoryId, jobPositionId, refreshJobs}) => {

        const[title, setTitle] = useState("");
        const[description, setDescription] = useState("");
        const[location, setLocation] = useState("");
        const[activationTime, setActivationTime] = useState("");
        const[deactivationTime, setDectivationTime] = useState("");

        const[isPosted, setIsPosted] = useState(false);

        const handleTitle = (value) => {
            setTitle(value);
            setIsPosted(false);
        }

        const handleDescription = (value) => {
            setDescription(value);
            setIsPosted(false);
        }

        const handleLocation = (value) => {
            setLocation(value);
            setIsPosted(false);
        }

        const handleActivationTime = (value) => {
            setActivationTime(value);
            setIsPosted(false);
        }

        const handleDectivationTime = (value) => {
            setDectivationTime(value);
            setIsPosted(false);
        }

        const saveJob = () => {
            fetch("/jobs",
            {
                method: "POST",
                headers: 
                {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    code: code,
                    title: title,
                    description: description,
                    location: location,
                    activationTime: activationTime,
                    deactivationTime: deactivationTime,
                    specialistId: specialistId,
                    jobCategoryId: jobCategoryId,
                    jobPositionId: jobPositionId
                })
            })
            .then((res) => res.json())
            .catch((err) => console.log("Error!"))
        }

        const handleSubmit = () => {
            saveJob();
            setIsPosted(true);
            setTitle("");
            setDescription("");
            setLocation("");
            setActivationTime("");
            setDectivationTime("");
            refreshJobs();
        }

        const handleClose = (event: React.SyntheticEvent | Event, reason?: string) => {
            if (reason === 'clickaway') {
              return;
            }
        
            setIsPosted(false);
          };

        return (
            <Box>
                <Snackbar open={isPosted} autoHideDuration={1500} onClose={handleClose} anchorOrigin={{vertical: "bottom", horizontal: "center"}}>
                    <Alert onClose={handleClose} severity="success" sx={{ width: '100%'}}>
                        Job is posted successfully!
                    </Alert>
                </Snackbar>
                <Card sx={{ minWidth: 275, mb: 3, mt: 3 }}>
                    <CardContent >
                        <Typography sx={{ mb: 1.5,fontSize: 15,color: "text.secondary"}}>
                            {<OutlinedInput 
                                multiline
                                placeholder='Job Title'
                                inputProps={{maxLength:50}}
                                fullWidth
                                value={title}
                                onChange={ (i) => handleTitle(i.target.value)}
                            />}
                        </Typography>
                        <Typography sx={{ mb: 1.5,fontSize: 15,color: "text.secondary"}}>
                            {<OutlinedInput 
                                multiline
                                placeholder='Job Description'
                                inputProps={{maxLength:250}}
                                fullWidth
                                value={description}
                                onChange={ (i) => handleDescription(i.target.value)}
                            />}
                        </Typography>
                        <Typography sx={{ mb: 1.5,fontSize: 15,color: "text.secondary"}}>
                            {<OutlinedInput 
                                multiline
                                placeholder='Location'
                                inputProps={{maxLength:25}}
                                fullWidth
                                value={location}
                                onChange={ (i) => handleLocation(i.target.value)}
                            />}
                        </Typography>
                        <Typography sx={{ mb: 1.5,fontSize: 15,color: "text.secondary"}}>
                            {<OutlinedInput 
                                multiline
                                placeholder='Activation Time'
                                inputProps={{maxLength:25}}
                                fullWidth
                                value={activationTime}
                                onChange={ (i) => handleActivationTime(i.target.value)}
                            />}
                        </Typography>
                        <Typography sx={{ mb: 1.5,fontSize: 15,color: "text.secondary"}}>
                            {<OutlinedInput 
                                multiline
                                placeholder='Deactivation Time'
                                inputProps={{maxLength:25}}
                                fullWidth
                                value={deactivationTime}
                                onChange={ (i) => handleDectivationTime(i.target.value)}
                            />}
                        </Typography>
                    </CardContent>
                    <Box display="flex" justifyContent="flex-end" alignItems="flex-end">
                        <CardActions>
                            <Button variant="contained" size="small" onClick={handleSubmit}>Post</Button>
                        </CardActions>
                    </Box>
                </Card>
            </Box>
        );
}

export default JobForm;