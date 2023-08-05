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

const renderRow = (props: ListChildComponentProps) => {
  const { index, style } = props;

  const handleButtonClick = (buttonText: string) => {
  }
  
  return (
    <ListItem style={style} key={index} component="div" disablePadding >
      <ListItemButton>
        <ListItemText primary={`Item ${index + 1}`} />
        <Button size="small" variant="contained" color="primary" onClick={() => handleButtonClick('Button 1')} >
         Applications
        </Button>
        <Button size="small" variant="contained" color="secondary" onClick={() => handleButtonClick('Button 2')} sx={{ marginLeft: 2}}>
          Edit
        </Button>
        <Button size="small" variant="outlined" onClick={() => handleButtonClick('Button 3')} sx={{ marginLeft: 2, color: "red", borderColor: 'red'}}>
          Delete
        </Button>
      </ListItemButton>
    </ListItem>
  );
}

const DashJobs = () => {
  const [showCreateJob, setShowCreateJob] = useState(false);

  const handleCreateJobClick = () => {
    if(!showCreateJob)
      setShowCreateJob(true)
    else
      setShowCreateJob(false)
  };

  return (
    <Box>
      { showCreateJob ? (
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
              Jobs List
          </Typography>
          <Button size="small" variant="contained" color="primary" Icon={<AddIcon />} onClick={handleCreateJobClick} sx={{ color: "white", backgroundColor: "green"}}>
              Create Job
            </Button> 
          
      </Box>
      
      <Box
        sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', bgcolor: 'background.paper', border: '5px solid #e0e0e0', marginTop: 3}}
      >
        <FixedSizeList
          height={770}
          width={1200}
          itemSize={85}
          itemCount={15}
          overscanCount={5}
        >
          {renderRow}
        </FixedSizeList>
      </Box> 
      </Box>
      )}
    </Box>
  );
}

export default DashJobs;