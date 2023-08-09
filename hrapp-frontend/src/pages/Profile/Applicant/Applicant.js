import { Box } from '@mui/material';
import React from 'react'
import { useParams } from 'react-router-dom'

const Applicant = () => {
    const {id} = useParams();
  return (
    <Box>Applicant {id}</Box>
  )
}

export default Applicant