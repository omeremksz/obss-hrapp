import React, { useCallback, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import { GetWithAuth } from '../../../services/HttpService';
import { Box, Card, CardContent, Typography } from '@mui/material';

const DashApplicant = () => {
  const { id } = useParams();
  const [applicant, setApplicant] = useState(null);
  const [applicationList, setApplicationList] = useState([]);

  const getApplicant = useCallback(() => {
    GetWithAuth("/applicants/"+id)
    .then(res => res.json())
    .then(
      (result) => {
        setApplicant(result);
        if (result.applications && Array.isArray(result.applications)) {
          setApplicationList(result.applications);
        }
      },
      (error) => {
        console.log(error)
      }
    )
  }, [id]);

  useEffect(() => { 
    getApplicant();
  }, [getApplicant]);

  return (
    <Box sx={{flex:5, p:2}}>
            <Card sx={{ minWidth: 275, mb: 3, mt: 3 }}>
                <CardContent >
                    {applicant ? (
                        <>
                        <Typography variant="h6" >
                            Name: {applicant.firstName} {applicant.lastName}
                        </Typography>
                        <Typography variant="h6">
                            Mail: {applicant.email}
                        </Typography>
                        <Typography variant="h6">
                            Job Applications: {applicationList.length}
                        </Typography>
                        </>
                    ) : (
                        <Typography variant="h6">
                            Loading applicant data...
                        </Typography>
                    )}
                </CardContent>
            </Card>
        </Box>
  )
}

export default DashApplicant