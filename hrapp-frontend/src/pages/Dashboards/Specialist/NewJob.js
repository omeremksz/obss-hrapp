import { Box, Button, Typography } from '@mui/material'
import React from 'react'
import JobForm from '../../Job/JobForm'
import CancelIcon from '@mui/icons-material/Cancel';
import { Link, useParams } from 'react-router-dom';

const BUTTON_COLOR_SECONDARY = 'secondary';
const TEXT_COLOR_PRIMARY = 'white';
const TEXT_COLOR_SECONDARY = 'red';

const NewJob = () => {
    const {id} = useParams();

  return (
    <Box>
        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', width: '100%', paddingLeft: 24, paddingRight: 24, paddingTop: 2 }}>
        <Typography variant="h5" component="h1" sx={{ fontFamily: 'Arial', color: '#333', fontWeight: 'bold' }}>
            New Job
        </Typography>
        <Button size="small" variant="contained" color={BUTTON_COLOR_SECONDARY} endIcon={<CancelIcon />} sx={{ color: TEXT_COLOR_PRIMARY, backgroundColor: TEXT_COLOR_SECONDARY }}>
            <Link style={{textDecoration: "none", color:"white"}} to={{ pathname: '/specialists/' + id + '/jobs'}} >
              Cancel
            </Link>
        </Button>
        </Box>
        <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <JobForm id={id}/>
        </Box>
    </Box>
  )
}

export default NewJob