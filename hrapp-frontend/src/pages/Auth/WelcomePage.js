import { Avatar, Box, Button, TextField, Typography } from '@mui/material';
import React, { useCallback, useEffect, useState } from 'react'
import { Link, useLocation, useNavigate } from 'react-router-dom';
import Navbar from '../../component/Navbar';
import LinkedInIcon from '@mui/icons-material/LinkedIn';
import Footer from '../../component/Footer';
import { GetWithAuth, PutWithAuth } from '../../services/HttpService';

const WelcomePage = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const applicantId = queryParams.get('applicantId');
  const token = queryParams.get('token');
  const navigate = useNavigate();

  localStorage.setItem("tokenKey", token);
  localStorage.setItem("currentUser", applicantId);
  localStorage.setItem("role", "ROLE_APPLICANT");

  const [linkedInProfileURL, setLinkedInProfileURL] = useState("");
  const [applicant, setApplicant] = useState(null);

  const getApplicant = useCallback(() => {
    GetWithAuth("/applicants/"+applicantId)
    .then(res => res.json())
    .then(
      (result) => {
        setApplicant(result);
      },
      (error) => {
        console.log(error)
      }
    )
  }, [applicantId]);

  useEffect(() => { 
    getApplicant();
  }, [getApplicant]);

  const updateApplicant = () => {
    PutWithAuth("/applicants/"+applicantId, {
      linkedInProfileURL: linkedInProfileURL
    })
    .then((res) => res.json())
    .catch((err) => console.log("Error!"))
  }

  const handleLinkedInURL = (value) => {
    setLinkedInProfileURL(value);  
  }

  const handleVerify = () => {
    updateApplicant();
    setLinkedInProfileURL("");
  }

  useEffect(() => {
    if (applicant && (applicant.linkedInProfileURL !== null && applicant.linkedInProfileURL.trim() !== "")) {
      navigate('/applicants/' + applicantId);
    }
  }, [applicant, navigate, applicantId]);

  return (
    <>
        <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
            <Navbar />
            <Box sx={{ flex: '1 0 auto', display: "flex", alignItems: "center", justifyContent: "center" }}>
                <Box component="form" className='form_style border-style' sx={{ maxWidth: 500, width: '100%', padding: '16px' }}>
                    <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center", width: "100%" }}>
                        <Avatar sx={{ m: 1, bgcolor: "primary.main", mb: 3 }}>
                            <LinkedInIcon />
                        </Avatar>
                        <Typography variant="body1" sx={{ mb: 3, textAlign: 'center' }}>
                          To verify your account, please paste your LinkedIn profile URL in the box below and click the verify button.
                        </Typography>
                        <TextField sx={{ mb: 3 }}
                                fullWidth
                                id="linkedInProfileUrl"
                                label="linkedInProfileUrl"
                                name='linkedInProfileUrl'
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                placeholder="LinkedIn Profile URL"
                                onChange={(i) => handleLinkedInURL(i.target.value)}
                            />
                        <Button fullWidth variant="contained" onClick={() => handleVerify()}> 
                          <Link style={{textDecoration: "none", color:"white"}} to={{ pathname: '/applicants/' + applicantId }} >
                            Verify
                          </Link>
                        </Button>
                    </Box>
                </Box>
            </Box>
            <Footer />
        </Box>
    </>
  )
}

export default WelcomePage