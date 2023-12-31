import * as React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { Alert, Box, OutlinedInput, Snackbar} from '@mui/material';
import { useState } from 'react';
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import { PostWithAuth } from '../../services/HttpService';
import { useNavigate } from 'react-router-dom';

const generateRandomCode = () => {
    const characters = 'abcdefghijklmnopqrstuvwxyz01234567890123456789';
    const randomCode = Array.from({ length: 6 }, () => characters[Math.floor(Math.random() * characters.length)]).join('');
    return randomCode;
};

const JobForm = (props) => {
        const { id } = props;
        const code = "#"+generateRandomCode();
        const specialistId = localStorage.getItem("currentUser");
        const[title, setTitle] = useState("");
        const[jobCategory, setJobCategory] = useState("");
        const[jobPosition, setJobPosition] = useState("");
        const[description, setDescription] = useState("");
        const[location, setLocation] = useState("");
        const[activationTime, setActivationTime] = useState(null);
        const[deactivationTime, setDectivationTime] = useState(null);
        const [isError, setIsError] = useState(false);
        const [errorSnackbarMessage, setErrorSnackbarMessage] = useState("");
        const[isPosted, setIsPosted] = useState(false);
        const navigate = useNavigate();

        const handleTitle = (value) => {
            setTitle(value);
            setIsPosted(false);
        }

        const handleJobCategory = (value) => {
            setJobCategory(value);
            setIsPosted(false);
        }

        const handleJobPosition = (value) => {
            setJobPosition(value);
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

        const handleSubmit = () => {
            if (!title || !jobCategory || !jobPosition || !description || !location || !activationTime || !deactivationTime) {
                setErrorSnackbarMessage("All fields must be filled.");
                setIsError(true);
                return ;
            }
              
            saveJob();
            setIsPosted(true);
            setTitle("");
            setJobCategory("");
            setJobPosition("");
            setDescription("");
            setLocation("");
            setActivationTime(null);
            setDectivationTime(null);
            setTimeout(() => {
                navigate('/specialists/' + id + '/jobs');
            }, 3000);
        }

        const handleClose = (event: React.SyntheticEvent | Event, reason?: string) => {
            if (reason === 'clickaway') {
              return;
            }
        
            setIsPosted(false);
          };

        const saveJob = () => {
            const formattedActivationTime = activationTime.format("YYYY-MM-DDTHH:mm:ss");
            const formattedDeactivationTime = deactivationTime.format("YYYY-MM-DDTHH:mm:ss");

            PostWithAuth("/jobs", {
                code: code,
                title: title,
                description: description,
                location: location,
                activationTime: formattedActivationTime,
                deactivationTime: formattedDeactivationTime,
                jobCategory: jobCategory,
                jobPosition: jobPosition,
                specialistId: specialistId,
            })
            .then((res) => res.json())
            .catch((err) => console.log(err))
        }
        
        return (
            <Box sx={{ width: '76%', }}>
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
                                placeholder='Job Category'
                                inputProps={{maxLength:50}}
                                fullWidth
                                value={jobCategory}
                                onChange={ (i) => handleJobCategory(i.target.value)}
                            />}
                        </Typography>
                        <Typography sx={{ mb: 1.5,fontSize: 15,color: "text.secondary"}}>
                            {<OutlinedInput 
                                multiline
                                placeholder='Job Position'
                                inputProps={{maxLength:50}}
                                fullWidth
                                value={jobPosition}
                                onChange={ (i) => handleJobPosition(i.target.value)}
                            />}
                        </Typography>
                        <Typography sx={{ mb: 1.5,fontSize: 15,color: "text.secondary"}}>
                            {<OutlinedInput 
                                multiline
                                placeholder='Job Description'
                                inputProps={{maxLength:500}}
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
                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                            <DemoContainer components={['DateTimePicker']} >
                                <DateTimePicker label="Activation Time" value={activationTime} onChange={setActivationTime} />
                            </DemoContainer>
                        </LocalizationProvider>
                        </Typography>
                        <Typography >
                        <LocalizationProvider dateAdapter={AdapterDayjs} >
                            <DemoContainer components={['DateTimePicker']} >
                                <DateTimePicker label="Deactivation Time" value={deactivationTime} onChange={setDectivationTime} />
                            </DemoContainer>
                        </LocalizationProvider>
                        </Typography>
                    </CardContent>
                    <Box display="flex" justifyContent="flex-end" alignItems="flex-end">
                        <CardActions>
                            <Button variant="contained" size="small" onClick={handleSubmit}>
                                Post
                            </Button>
                        </CardActions>
                    </Box>
                </Card>
                <Snackbar open={isPosted} autoHideDuration={3000} onClose={handleClose} anchorOrigin={{vertical: "bottom", horizontal: "center"}}>
                    <Alert elevation={6} onClose={handleClose} severity="success" sx={{ width: '100%'}}>
                        Job is posted successfully!
                    </Alert>
                </Snackbar>
                <Snackbar open={isError} autoHideDuration={4000} onClose={() => setIsError(false)} anchorOrigin={{ vertical: "bottom", horizontal: "center" }}>
                    <Alert elevation={6} onClose={() => setIsError(false)} severity="error" sx={{ width: '100%' }}>
                        {errorSnackbarMessage}
                    </Alert>
                </Snackbar>
            </Box>
        );
}

export default JobForm;