import { Button, Typography} from '@mui/material';
import Box from '@mui/material/Box';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import { FixedSizeList, ListChildComponentProps } from 'react-window';
import AddIcon from '@mui/icons-material/Add';
import JobForm from '../../Job/JobForm';
import { useState } from 'react';
import CancelIcon from '@mui/icons-material/Cancel';
import DashApplications from './DashApplications';
import DeleteIcon from '@mui/icons-material/Delete';
import ListIcon from '@mui/icons-material/List';
import { DeleteWithAuth } from '../../../services/HttpService';

const renderRow = (props: ListChildComponentProps) => {
  const { index, style, jobList, handleApplicationButton } = props;

  const deleteJob = (jobId) => {
    DeleteWithAuth("/jobs/"+jobId)
    .then(() => {
      window.location.reload();
    })
  }

  const handleDeleteButton = (jobId) => {
    deleteJob(jobId);
  }

  const job = jobList[index];
  
  return (
    <ListItem style={style} key={index} component="div" disablePadding >
      <ListItemButton>
        <ListItemText primary={`${job.title} | ${job.location} | ${job.code}`} />
        <Button size="small" variant="contained" color="primary" endIcon={<ListIcon />} onClick={() => handleApplicationButton(job.id)} >
         Applications
        </Button>
        <Button size="small" variant="outlined" endIcon={<DeleteIcon />} onClick={() => handleDeleteButton(job.id)} sx={{ marginLeft: 2, color: "red", borderColor: 'red'}}>
          Delete
        </Button>
      </ListItemButton>
    </ListItem>
  );
}

const DashJobs = (props) => {
  const { jobList, specialist } = props;

  const [showCreateJob, setShowCreateJob] = useState(false);
  const [showApplications, setShowApplications] = useState(false);
  const [selectedJob, setSelectedJob] = useState(null);

  const handleCreateJobClick = () => {
    if(!showCreateJob)
      setShowCreateJob(true)
    else
      setShowCreateJob(false)
  };

  const handleApplicationButton = (jobId) => {
    setShowApplications(true);
    setSelectedJob(jobId);
  };

  return (
    <Box>

      {showApplications ? (
      <Box>
        <DashApplications jobId={selectedJob} specialist={specialist} /> 
      </Box>
      ) : showCreateJob ? (
        <Box >
          <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', width: '100%', paddingLeft: 24, paddingRight: 24, paddingTop:2, }}>
          <Typography variant="h5" component="h1" sx={{ fontFamily: 'Arial', color: '#333', fontWeight: 'bold' }}>
              New Job
          </Typography>
            <Button size="small" variant="contained" color="primary" endIcon={<CancelIcon />} onClick={handleCreateJobClick} sx={{ color: "white", backgroundColor: "red", }}>
            Cancel
          </Button >
         </Box> 
         <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center',  }}>
          <JobForm  /> 
         </Box>
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
              Job List
          </Typography>
          <Button size="small" variant="contained" color="primary" endIcon={<AddIcon />} onClick={handleCreateJobClick} sx={{ color: "white", backgroundColor: "green"}}>
              Create Job
            </Button> 
      </Box>
      
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', bgcolor: 'background.paper', border: '5px solid #e0e0e0', marginTop: 3}} >
        <FixedSizeList
          height={770}
          width={1200}
          itemSize={85}
          itemCount={jobList.length}
          overscanCount={5}
        >
          {(props) => renderRow({ ...props, jobList, handleApplicationButton })}
        </FixedSizeList>
      </Box> 
      </Box>
      )}

    </Box>
  );
}

export default DashJobs;