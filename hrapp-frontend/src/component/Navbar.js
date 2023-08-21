import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import Container from '@mui/material/Container';
import Avatar from '@mui/material/Avatar';
import Tooltip from '@mui/material/Tooltip';
import MenuItem from '@mui/material/MenuItem';
import { useTheme } from '@emotion/react';
import { Link } from 'react-router-dom';
import WorkIcon from '@mui/icons-material/Work';
import { useState } from 'react';
import { useEffect } from 'react';

function Navbar() {
  const { palette } = useTheme();
  const [anchorElUser, setAnchorElUser] = useState(null)
  const [dashboardURL, setDashboardURL] = useState(null)

  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  const handleLogOut = () => {
    localStorage.removeItem("tokenKey");
    localStorage.removeItem("currentUser");
    localStorage.removeItem("userName");
    setTimeout(() => {
      window.location.href = `/auth`;
    }, 500);
  }

  useEffect(() => {
    if (localStorage.getItem("role") === "ROLE_SPECIALIST") {
      setDashboardURL("/specialists/");
    } else {
      setDashboardURL("/applicants/");
    }
  }, []);

  return (
    <AppBar position="static">
      <Container maxWidth="xl">
        <Toolbar disableGutters>
        <Box sx={{ display: 'flex', alignItems: 'center', flexGrow: 1 }}>
          <WorkIcon sx={{ display: { xs: 'none', md: 'flex' }, mr: 1 }} />
          <Typography
            variant="h7"
            noWrap
            component="a"
            href="/"
            sx={{
              mr: 2,
              display: { xs: 'none', md: 'flex' },
              fontFamily: 'monospace',
              fontWeight: 800,
              letterSpacing: '.3rem',
              color: 'inherit',
              textDecoration: 'none',
            }}
          >
            CAREER HUB
          </Typography>
          </Box>
          

            <Tooltip title="Open settings">
              <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                <Avatar alt="Remy Sharp" src="" sx={{ width: 36, height: 36 }}/>
              </IconButton>
            </Tooltip>
            <Menu
              sx={{ mt: '45px' }}
              id="menu-appbar"
              anchorEl={anchorElUser}
              anchorOrigin={{
                vertical: 'top',
                horizontal: 'right',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'right',
              }}
              open={Boolean(anchorElUser)}
              onClose={handleCloseUserMenu}
            >
              {localStorage.getItem("currentUser") == null ? (
              <Box>
              <MenuItem onClick={handleCloseUserMenu}>
                <Typography textAlign="center"><Link style={{ textDecoration: "none", color: palette.primary.main }} to="/auth">Log In</Link></Typography>
              </MenuItem>
              </Box>
              ) :(
              <Box>
              <MenuItem onClick={handleCloseUserMenu}>
                <Typography textAlign="center"><Link style={{ textDecoration: "none", color: palette.primary.main }} to={{ pathname: dashboardURL + localStorage.getItem("currentUser")}}>Dashboard</Link></Typography>
              </MenuItem>
              <MenuItem onClick={handleLogOut}>
                <Typography textAlign="center"><Link style={{ textDecoration: "none", color: palette.primary.main }} to={{ pathname: '/'}}>Log Out</Link></Typography>
              </MenuItem>
              </Box>
              )}
            </Menu>
        </Toolbar>
      </Container>
    </AppBar>
  );
}
export default Navbar;