import React, { useCallback, useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { GetWithAuth } from '../../../services/HttpService';
import { Box, Button, ListItem, ListItemText, Typography } from '@mui/material';
import { FixedSizeList } from 'react-window';
import AddIcon from '@mui/icons-material/Add';
import moment from 'moment';
import { useTheme } from '@emotion/react';

const RenderRow = (props: ListChildComponentProps) => {
  const { index, style, applicationList, id } = props;
  const application = applicationList[index];
  const applicationId = application.id;
  const jobId = application.jobId;
  const [job, setJob] = useState(null);
  const formattedAppliedDate = moment(application.appliedDate).format('YYYY-MM-DD');
  const { palette } = useTheme();
  
  const getJob = useCallback(() => {
    fetch("/jobs/"+jobId)
    .then(res => res.json())
    .then(
        (result) => {
            setJob(result);
        },
        (error) => {
            console.log(error)
        }
    )
    },[jobId]
  )

  useEffect(() => { getJob() }, [getJob])
    
  return (
    <ListItem style={style} key={index} component="div" >
      {job ? (
          <>
            <ListItemText primary={`${job.title} | ${job.location} | ${job.code} | Applied Date:  ${formattedAppliedDate} | Application Status: ${application.applicationStatus}`} />
            <Button size="small" variant="contained" color="primary" startIcon={<AddIcon />}  sx={{ backgroundColor: palette.secondary.main, color: '#FFFFFF' }} >
              <Link style={{textDecoration: "none", color:"white"}} to={{ pathname: '/applicants/' + id + '/applied-jobs/' +applicationId+ '/application-details'}} >
                More Details
              </Link>
            </Button>
          </>
        ) : (
          <ListItemText primary="Loading..." />
        )}
    </ListItem>
  );
}

const DashAppliedJobs = () => {
  const { id } = useParams();
  const [applicationList, setApplicationList] = useState([]);

  const getApplicant = useCallback(() => {
    GetWithAuth("/applicants/"+id)
    .then(res => res.json())
    .then(
      (result) => {
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
    <>
      { applicationList.length === 0 ? (
        <>
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
        </>
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
                Applied Jobs
            </Typography>
        </Box>
        
        <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', bgcolor: 'background.paper', border: '5px solid #e0e0e0', marginTop: 3}}>
          <FixedSizeList
            height={770}
            width={1200}
            itemSize={85}
            itemCount={applicationList.length}
            overscanCount={5}
          >
          {(props) => <RenderRow {...props} applicationList={applicationList} id={id}/>}
          </FixedSizeList>
        </Box> 
        </Box>
        )}
      </>
  )
}

export default DashAppliedJobs