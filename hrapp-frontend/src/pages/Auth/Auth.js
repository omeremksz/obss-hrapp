import { Avatar, Box, Button, TextField } from '@mui/material'
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
                        localStorage.setItem("userName", userName);
                        navigate("/specialists/" + result.id);
                    })
        .catch((err) => console.log(err))
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
                        />
                        <Button fullWidth variant="contained" onClick={() => handleSpecialistLogin()} sx={{ mb: 3 }}>Log In</Button>
                        <Button fullWidth variant="contained" > 
                            <a style={{textDecoration: "none", color:"white"}} href={linkedInLogin} class="redirect" >Log In with LinkedIn</a>
                        </Button>
                    </Box>
                </Box>
            </Box>
            <Footer />
        </Box>
    </>
  )
}

export default Auth;