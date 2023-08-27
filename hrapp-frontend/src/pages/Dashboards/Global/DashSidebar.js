import React from 'react'
import { Sidebar, Menu, MenuItem, menuClasses, useProSidebar } from 'react-pro-sidebar';
import { Box, useTheme } from '@mui/material';
import WorkIcon from '@mui/icons-material/Work';
import Avatar from '@mui/material/Avatar';
import BlockIcon from '@mui/icons-material/Block';
import HomeIcon from '@mui/icons-material/Home';
import AccountBoxIcon from '@mui/icons-material/AccountBox';
import WorkHistoryIcon from '@mui/icons-material/WorkHistory';
import logoDashboard from '../../../images/OBSS_image3.png'
import { Link, useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import LoginIcon from '@mui/icons-material/Login';

const DashSidebar = () => {
    const { id } = useParams();
    const { palette } = useTheme();
    const { collapsed } = useProSidebar();
    const navigate = useNavigate();
    
    const logOut = () => {
        localStorage.removeItem("tokenKey");
        localStorage.removeItem("currentUser");
        localStorage.removeItem("role");
        setTimeout(() => {
            navigate('/auth');
        }, 300)
    }

    const homeButton = () => {
        setTimeout(() => {
            navigate('/');
        }, 300)
    }

    return (
        <>
            <Sidebar backgroundColor={palette.primary.main} style={{ borderRightStyle: "none" }}>
                <Box sx={{ display: "flex", justifyContent: "space-between", flexDirection: "column", height: "100%" }}>
                    <Box>
                        <Box sx={{ pt: 3, pb: 5, display: "flex", justifyContent: "center" }}>

                            {
                                collapsed ?
                                    <Avatar alt="logo dashboard" src={logoDashboard} /> :
                                    <Box sx={{ display: "flex", justifyContent: "center" }}>
                                        <img style={{ width: "100px", heigth: "100px", textAlign: "center", transition: "all ease-out .5s" }} src={logoDashboard} alt="logo dashboard" />
                                    </Box>
                            }

                        </Box>

                        <Menu

                            menuItemStyles={{


                                button: {
                                    [`&.${menuClasses.button}`]: {
                                        color: "#fafafa",
                                    },
                                    [`&.${menuClasses.disabled}`]: {
                                        color: "green",
                                    },
                                    '&:hover': {
                                        backgroundColor: palette.secondary.lighter,
                                        color: "#fafafa",
                                    },
                                },

                                icon: {
                                    [`&.${menuClasses.icon}`]: {
                                        color: "#fafafa",
                                    }
                                },
                            }}

                        >
                            {
                                localStorage.getItem("role") === "ROLE_SPECIALIST" ?
                                    <>
                                        <MenuItem component={<Link to={{ pathname: '/specialists/' + id }} />} icon={<AccountBoxIcon />}> Profile </MenuItem>
                                        <MenuItem component={<Link to={{ pathname: '/specialists/' + id + '/jobs'}} />} icon={<WorkIcon />}> Jobs </MenuItem>
                                        <MenuItem component={<Link to={{ pathname: '/specialists/' + id + '/blacklist'}} />} icon={<BlockIcon />}> Blacklist </MenuItem>
                                        <MenuItem onClick={homeButton} icon={<HomeIcon />}> Home </MenuItem>
                                    </>
                                    :
                                    <>
                                        <MenuItem component={<Link to={{ pathname: '/applicants/' + id }} />} icon={<AccountBoxIcon />}> Profile </MenuItem>
                                        <MenuItem component={<Link to={{ pathname: '/applicants/' + id + '/applied-jobs' }}/>} icon={<WorkHistoryIcon />}> Applied Jobs </MenuItem>
                                        <MenuItem onClick={homeButton} icon={<HomeIcon />}> Home </MenuItem>
                                    </>
                            }

                        </Menu>
                    </Box>
                    <Box sx={{ pb: 2 }}>
                        <Menu
                            menuItemStyles={{


                                button: {
                                    [`&.${menuClasses.button}`]: {
                                        color: "#fafafa",
                                    },

                                    '&:hover': {
                                        backgroundColor: palette.secondary.lighter,
                                        color: "#fafafa",
                                    },
                                },

                                icon: {
                                    [`&.${menuClasses.icon}`]: {
                                        color: "#fafafa",
                                    }
                                },
                            }}
                        >
                            <MenuItem onClick={logOut} icon={<LoginIcon />}> Log out </MenuItem>
                        </Menu>
                    </Box>
                </Box>
            </Sidebar>
        </>
    )
}

export default DashSidebar;