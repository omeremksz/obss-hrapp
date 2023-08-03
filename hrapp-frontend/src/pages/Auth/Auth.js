import { Avatar, Box, Button, TextField } from '@mui/material'
import React, { useState } from 'react'
import Navbar from '../../component/Navbar'
import { LockClockOutlined } from '@mui/icons-material'
import Footer from '../../component/Footer'
import { useNavigate } from 'react-router-dom';


const Auth = () => {

    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleUserName = (value) => {
        console.log("Username:", value);
        setUserName(value);
    }

    const handlePassword = (value) => {
        setPassword(value);
    }

    const handleLogin = () => {
        sendRequest();
        setUserName("");
        setPassword("");
        navigate("/auth");
    }

    const sendRequest = () => {
        fetch("/auth",
        {
            method: "POST",
            headers: 
            {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                userName : userName,
                password: password,
            })
        })
        .then((res) => res.json())
        .then((result) => {
                        localStorage.setItem("tokenKey", result.message);
                        localStorage.setItem("currentUser", result.id);
                        localStorage.setItem("userName", userName);
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
                        <Button fullWidth variant="contained" onClick={() => handleLogin()}>Log In</Button>
                    </Box>
                </Box>
            </Box>
            <Footer />
        </Box>
    </>
  )
}

export default Auth