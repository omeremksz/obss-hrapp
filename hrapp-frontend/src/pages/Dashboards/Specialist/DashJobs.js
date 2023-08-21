import React, { useCallback, useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { DeleteWithAuth, GetWithAuth } from '../../../services/HttpService';
import { Box, Button, ListItem, ListItemButton, ListItemText, Typography } from '@mui/material';
import ListIcon from '@mui/icons-material/List';
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';
import { FixedSizeList } from 'react-window';

const BUTTON_COLOR_PRIMARY = 'primary';
const TEXT_COLOR_PRIMARY = 'white';
const TEXT_COLOR_SECONDARY = 'red';
const JOB_ITEM_HEIGHT = 85;

const DashJobs = () => {

  const { id } = useParams();
  const [jobList, setJobList] = useState([]);

  const getSpecialist = useCallback(() => {
    GetWithAuth("/specialists/" + id)
    .then(res => res.json())
    .then(
        (result) => {
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

  const deleteJob = (jobId) => {
    DeleteWithAuth("/jobs/"+jobId)
    .then(() => {
      window.location.reload();
    })
  }

  const handleDeleteButton = (jobId) => {
    deleteJob(jobId);
  }

  const renderRow = (props: ListChildComponentProps) => {
    const { index, style } = props;
    const job = jobList[index];

    return (
      <ListItem style={style} key={index} component="div" disablePadding>
        <ListItemButton>
          <ListItemText primary={`${job.title} | ${job.location} | ${job.code}`} />
          <Button
            size="small"
            variant="contained"
            color={BUTTON_COLOR_PRIMARY}
            endIcon={<ListIcon />}
          >
            <Link style={{textDecoration: "none", color:"white"}} to={{ pathname: '/specialists/' + id + '/jobs/' + job.id + '/applications'}} >
              Applications
            </Link>
          </Button>
          <Button
            size="small"
            variant="outlined"
            endIcon={<DeleteIcon />}
            sx={{ marginLeft: 2, color: TEXT_COLOR_SECONDARY, borderColor: TEXT_COLOR_SECONDARY }}
            onClick={() => handleDeleteButton(job.id)}
          >
            Delete
          </Button>
        </ListItemButton>
      </ListItem>
    );
  };

    return (
      <>
      {jobList.length === 0 ? (
        <Typography 
          variant="h6"
          sx={{
            padding: 3,
            textAlign: 'center',
            color: '#999',
            fontStyle: 'italic',
          }}
        >
          No Jobs Created Yet
        </Typography>
      ) : (
      <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', bgcolor: 'background.paper', paddingTop: 2 }}>
          <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', width: '100%', paddingLeft: 26, paddingRight: 26 }}>
            <Typography variant="h5" component="h1" sx={{ fontFamily: 'Arial', color: '#333', fontWeight: 'bold' }}>
              Job List
            </Typography>
            <Button size="small" variant="contained" color={BUTTON_COLOR_PRIMARY} endIcon={<AddIcon />} sx={{ color: TEXT_COLOR_PRIMARY, backgroundColor: 'green' }}>
              <Link style={{textDecoration: "none", color:"white"}} to={{ pathname: '/specialists/' + id + '/new-job'}} >
                Create Job
              </Link>
            </Button>
          </Box>
          <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', bgcolor: 'background.paper', border: '5px solid #e0e0e0', marginTop: 3 }}>
            <FixedSizeList height={770} width={1200} itemSize={JOB_ITEM_HEIGHT} itemCount={jobList.length} overscanCount={5}>
              {(props) => renderRow({ ...props, jobList })}
            </FixedSizeList>
          </Box>
      </Box>
      )}
    </>
    )
}

export default DashJobs