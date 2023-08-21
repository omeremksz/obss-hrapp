import { Box, Button, ListItem, ListItemButton, ListItemText, Typography } from '@mui/material';
import React, { useCallback, useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { FixedSizeList } from 'react-window';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import EditIcon from '@mui/icons-material/Edit';
import { GetWithAuth } from '../../../services/HttpService';

const RenderRow = (props: ListChildComponentProps) => {
  const { index, style, applicationList, id, jobId } = props;

  const application = applicationList[index];
  const applicantId = application.applicantId;
  const applicationId = application.id;
  const [applicant, setApplicant] = useState(null);

  const getApplicant = useCallback(() => {
    GetWithAuth("/applicants/"+applicantId)
    .then(res => res.json())
    .then(
      (result) => {
        setApplicant(result);
      },
      (error) => {
        console.log(error)
      }
    )
  }, [applicantId]);

  useEffect(() => { 
    getApplicant();
  }, [getApplicant]);
    
  return (
    <ListItem style={style} key={index} component="div" disablePadding >
      <ListItemButton >
      {applicant ? (
          <>
            <ListItemText primary={`${applicant.firstName} ${applicant.lastName} | ${applicant.email}`} />
            <Button size="small" variant="contained" color="primary" endIcon={<EditIcon />}  sx={{ backgroundColor: '#1976D2', color: '#FFFFFF' }} >
              <Link style={{textDecoration: "none", color:"white"}} to={{ pathname: '/specialists/' + id + '/jobs/' + jobId + '/applications/' + applicationId + '/edit'}} >
                Edit
              </Link>
            </Button>
          </>
        ) : (
          <ListItemText primary="Loading..." />
        )}
      </ListItemButton>
    </ListItem>
  );
}

const DashJobApplications = () => {
  const {id, jobId} = useParams();
  const [applicationList, setApplicationList] = useState([]);

  const getJob = useCallback(() => {
    fetch("/jobs/"+jobId)
    .then(res => res.json())
    .then(
        (result) => {
            if (result.jobApplications && Array.isArray(result.jobApplications)) {
                setApplicationList(result.jobApplications);
            }
        },
        (error) => {
            console.log(error)
        }
    )
    },[jobId]
  )

  useEffect(() => { getJob() }, [getJob])

  return (
    <>
      {applicant && applicationList.length === 0 ? (
        <Typography 
          variant="h6"
          sx={{
            padding: 3,
            textAlign: 'center',
            color: '#999',
            fontStyle: 'italic',
          }}
        >
          No Applications Yet
        </Typography>
      ) : (
      <Box
          sx={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            bgcolor: 'background.paper',
            paddingTop: 2,
          }}
        >
        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', width: '100%', paddingLeft: 26, paddingRight: 26, }}>
            <Typography variant="h5" component="h1" sx={{ fontFamily: 'Arial', color: '#333', fontWeight: 'bold' }}>
                Application List
            </Typography>
            <Button size="small" variant="contained" color="primary" endIcon={<ArrowBackIcon />} >
              <Link style={{textDecoration: "none", color:"white"}} to={{ pathname: '/specialists/' + id + '/jobs'}} >
              Job List
              </Link>
            </Button >
        </Box>
        
        <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', bgcolor: 'background.paper', border: '5px solid #e0e0e0', marginTop: 3}}>
          <FixedSizeList
            height={770}
            width={1200}
            itemSize={85}
            itemCount={applicationList.length}
            overscanCount={5}
          >
          {(props) => <RenderRow {...props} applicationList={applicationList} id={id} jobId={jobId}/>}
          </FixedSizeList>
        </Box> 
        </Box>
        )}
      </>
  )
}

export default DashJobApplications