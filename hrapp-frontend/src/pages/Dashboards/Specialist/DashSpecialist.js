import React, { useCallback, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { GetWithAuth } from '../../../services/HttpService';
import { Box, Card, CardContent, Typography } from '@mui/material';

const DashSpecialist = () => {
    const { id } = useParams();
    const [specialist, setSpecialist] = useState(null);
    const [jobList, setJobList] = useState([]);

    const getSpecialist = useCallback(() => {
        GetWithAuth("/specialists/" + id)
        .then(res => res.json())
        .then(
            (result) => {
                setSpecialist(result);
                if (result.jobs && Array.isArray(result.jobs)) {
                  setJobList(result.jobs);
                }
            },
            (error) => {
                console.log(error)
            }
        )
        },[id]
      )
    
    useEffect(() => {
    getSpecialist() 
    }, [getSpecialist]);

  return (
    <Box sx={{flex:5, p:2}}>
            <Card sx={{ minWidth: 275, mb: 3, mt: 3 }}>
                <CardContent >
                    {specialist ? (
                        <>
                        <Typography variant="h6" >
                            Name: {specialist.firstName} {specialist.lastName}
                        </Typography>
                        <Typography variant="h6">
                            Mail: {specialist.email}
                        </Typography>
                        <Typography variant="h6">
                            Available Job Openings: {jobList.length}
                        </Typography>
                        </>
                    ) : (
                        <Typography variant="h6">
                            Loading specialist data...
                        </Typography>
                    )}
                </CardContent>
            </Card>
        </Box>
  )
}

export default DashSpecialist