import { Box } from '@mui/material';
import React from 'react'
import HeaderTop from './HeaderTop';
import DashSidebar from './DashSidebar';
import { ProSidebarProvider } from 'react-pro-sidebar';

const Layout = (Component) => ({ ...props }) => {

    return (
        <ProSidebarProvider >
            <>
                <div style={{ display: 'flex', minHeight: "100vh" }}>
                    <DashSidebar />
                    <Box sx={{ width: "100%", bgcolor: "#fafafa" }}>
                        <HeaderTop />
                        <Box sx={{ p: 3 }}>
                            <Component {...props} />
                        </Box>
                    </Box>
                </div>
            </>
        </ProSidebarProvider>
    )
}

export default Layout