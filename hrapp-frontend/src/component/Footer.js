import { Box } from '@mui/material'
import React from 'react'
import { useTheme } from '@mui/material/styles';

const Footer = () => {
    const { palette } = useTheme();
    return (
        <>
            <Box sx={{
                height: '60px',
                bgcolor: palette.primary.main,
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center'
            }}>
                <Box component='span' sx={{ color: 'white' }}>© 2023 OBSS</Box>

            </Box>
        </>
    )
}

export default Footer;