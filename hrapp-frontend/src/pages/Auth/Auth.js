import { Alert, Avatar, Box, Button, Snackbar, TextField } from '@mui/material'
import React, { useState } from 'react'
import Navbar from '../../component/Navbar'
import { LockClockOutlined } from '@mui/icons-material'
import Footer from '../../component/Footer'
import { useNavigate } from 'react-router-dom';
import { PostWithoutAuth } from '../../services/HttpService'

const Auth = () => {
    const navigate = useNavigate();
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");

    const [authError, setAuthError] = useState(null);
    const [successMessage, setSuccessMessage] = useState(null);
    const [snackbarOpen, setSnackbarOpen] = useState(false);

    const linkedInLogin = "https://www.linkedin.com/oauth/v2/authorization?"+
    "response_type=code&client_id=77oimdom7ofswl" +
    "&redirect_uri=http://localhost:8080/linkedin/callback" +
    "&state=ASk220aSFAxx&scope=openid%20profile%20email"

    const handleUserName = (value) => {
        setUserName(value);
    }

    const handlePassword = (value) => {
        setPassword(value);
    }

    const handleSpecialistLogin = () => {
        if (!userName || !password) {
            setAuthError("Username and/or password cannot be empty!");
            setSnackbarOpen(true);
            return;
        }

        sendSpecialistRequest();
        if(localStorage.getItem("currentUser")!== null) {
            setUserName("");
            setPassword("");
        }
    }

    const sendSpecialistRequest = () => {
        PostWithoutAuth("/auth/specialist", {
            userName : userName,
            password: password,
        })
        .then((res) => res.json())
        .then((result) => {
                        localStorage.setItem("tokenKey", result.message);
                        localStorage.setItem("currentUser", result.id);
                        localStorage.setItem("role", "ROLE_SPECIALIST");
                        setSuccessMessage("Login successful!");
                        setSnackbarOpen(true);
                        setTimeout(() => {
                            navigate("/specialists/" + result.id);
                        }, 2000);
                    })
        .catch((err) => {
            setAuthError("Invalid username or password");
            setSnackbarOpen(true);
            setUserName("");
            setPassword("");
        })
    }

  return (
    <>
        <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
            <Navbar />
            <Box sx={{ flex: '1 0 auto', display: "flex", alignItems: "center", justifyContent: "center" }}>
                <Box component="form" className='form_style border-style' >
                    <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center", width: "100%" }}>
                        <Avatar sx={{ m: 1, bgcolor: "primary.main", mb: 3 }}>
                            <LockClockOutlined />
                        </Avatar>
                        <TextField sx={{ mb: 3 }}
                                fullWidth
                                id="username"
                                label="Username"
                                name='username'
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                placeholder="Username"
                                onChange={(i) => handleUserName(i.target.value)}
                                value={userName}
                            />

                        <TextField sx={{ mb: 3 }}
                            fullWidth
                            id="password"
                            name="password"
                            label="Password"
                            type="password"
                            InputLabelProps={{
                                shrink: true,
                            }}
                            placeholder="Password"
                            onChange={(i) => handlePassword(i.target.value)}
                            value={password}
                        />
                        <Button fullWidth variant="contained" onClick={() => handleSpecialistLogin()} sx={{ mb: 3 }}>Log In</Button>
                        <Button fullWidth variant="contained" > 
                            <a style={{textDecoration: "none", color:"white"}} href={linkedInLogin} class="redirect" >Log In with LinkedIn</a>
                        </Button>
                    </Box>
                </Box>
            </Box>
            <Snackbar open={snackbarOpen} autoHideDuration={successMessage ? 2000 : 4000} onClose={() => setSnackbarOpen(false)} anchorOrigin={{vertical: "bottom", horizontal: "center"}}>
              <Alert elevation={6} severity={successMessage ? "success" : "error"} onClose={() => setSnackbarOpen(false)} sx={{ width: '100%' }}>
                {successMessage || authError}
              </Alert>
            </Snackbar>
            <Footer />
        </Box>
    </>
  )
}

export default Auth;