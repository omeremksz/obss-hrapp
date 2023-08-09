import { Box, Button, ListItem, ListItemButton, ListItemText, Typography } from '@mui/material';
import React, { useCallback, useEffect, useState } from 'react'
import { FixedSizeList, ListChildComponentProps } from 'react-window';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import DashEdit from './DashEdit';
import EditIcon from '@mui/icons-material/Edit';
import { GetWithAuth } from '../../../services/HttpService';

const RenderRow = (props: ListChildComponentProps) => {
    const { index, style, applicationList, handleEditButton } = props;

    const application = applicationList[index];
    const applicantId = application.applicantId;
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
              <Button size="small" variant="contained" color="primary" endIcon={<EditIcon />} onClick={() => handleEditButton(applicant, application)} sx={{ backgroundColor: '#1976D2', color: '#FFFFFF' }} >
                Edit
              </Button>
            </>
          ) : (
            <ListItemText primary="Loading..." />
          )}
        </ListItemButton>
      </ListItem>
    );
  }
  

const DashApplications = (props) => {
    const { jobId, specialist } = props;

    const [applicationList, setApplicationList] = useState([]);
    const [editMode, setEditMode] = useState(false);
    const [selectedApplicant, setSelectedApplicant] = useState(null);
    const [selectedApplication, setSelectedApplication] = useState(null);

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

      const handleBackButton = () => {
        window.location.reload();
      };
      
      const handleEditButton = (applicant, application) => {
        setEditMode(true);
        setSelectedApplicant(applicant);
        setSelectedApplication(application);
      }

  return (
    <Box>

      {editMode ? (
        <Box>
          <DashEdit applicant = {selectedApplicant} application = {selectedApplication} specialist = {specialist}/>
        </Box>
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
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', width: '100%', paddingLeft: 24, paddingRight: 24, }}>
          <Typography variant="h5" component="h1" sx={{ fontFamily: 'Arial', color: '#333', fontWeight: 'bold' }}>
              Application List
          </Typography>
          <Button size="small" variant="contained" color="primary" endIcon={<ArrowBackIcon />} onClick={handleBackButton} >
              Job List
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
        {(props) => <RenderRow {...props} applicationList={applicationList} handleEditButton={handleEditButton}/>}
        </FixedSizeList>
      </Box> 
      </Box>
      )}
      
    </Box>
  );
}

export default DashApplications