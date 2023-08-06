import { Box, Button, ListItem, ListItemButton, ListItemText, Typography } from '@mui/material';
import React, { useCallback, useEffect, useState } from 'react'
import CancelIcon from '@mui/icons-material/Cancel';
import { FixedSizeList, ListChildComponentProps } from 'react-window';

const RenderRow = (props: ListChildComponentProps) => {
    const { index, style, applicationList } = props;
    const application = applicationList[index];
    const applicantId = application.applicantId;
    const [applicant, setApplicant] = useState(null);
  
    const getApplicant = useCallback(() => {
      fetch("/applicants/"+applicantId ,{  
        headers: {
          "Authorization": localStorage.getItem("tokenKey"),
        },
      })
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
        <ListItemButton>
          {applicant ? (
            <ListItemText primary={`${applicant.firstName} | ${applicant.lastName} | ${applicant.email}`} />
          ) : (
            <ListItemText primary="Loading..." />
          )}
        </ListItemButton>
      </ListItem>
    );
  }
  

const DashApplications = (props) => {
    const { jobId } = props;
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

      const handleCreateJobClick = () => {
      };

      useEffect(() => { getJob() }, [getJob])

  return (
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
              Jobs List
          </Typography>
          <Button size="small" variant="contained" color="primary" endIcon={<CancelIcon />} onClick={handleCreateJobClick} sx={{ color: "white", backgroundColor: "red", }}>
            Cancel
          </Button >
          
      </Box>
      
      <Box
        sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', bgcolor: 'background.paper', border: '5px solid #e0e0e0', marginTop: 3}}
      >
        <FixedSizeList
          height={770}
          width={1200}
          itemSize={85}
          itemCount={applicationList.length}
          overscanCount={5}
        >
        {(props) => <RenderRow {...props} applicationList={applicationList} />}
        </FixedSizeList>
      </Box> 
      </Box>
  );
}

export default DashApplications