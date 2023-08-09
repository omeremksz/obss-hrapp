import React, { useCallback, useEffect, useState } from 'react'
import { GetWithAuth } from '../../../services/HttpService';
import { Box, Button, ListItem, ListItemButton, ListItemText, Typography } from '@mui/material';
import { FixedSizeList } from 'react-window';
import DeleteIcon from '@mui/icons-material/Delete';
import moment from 'moment';

const RenderRow = (props: ListChildComponentProps) => {
    const { index, style, blacklist } = props;
   
    const blacklistEntity = blacklist[index];
    const applicantId = blacklistEntity.applicantId;
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
    
      const handleDeleteButton = () => {
    }

    const formattedBlacklistedAt = moment(blacklistEntity.blacklistedAt).format('YYYY-MM-DD HH:mm:ss');;
    
    return (
      <ListItem style={style} key={index} component="div" disablePadding >
        <ListItemButton>
        {applicant ? (
            <>
            <ListItemText primary={`Applicant Name: ${applicant.firstName} ${applicant.lastName} | Blacklisted at: ${formattedBlacklistedAt} | Explanation: ${blacklistEntity.explanation}`} />
            <Button size="small" variant="outlined" endIcon={<DeleteIcon />} onClick={() => handleDeleteButton()} sx={{ marginLeft: 2, color: "red", borderColor: 'red'}}>
                Delete
            </Button>
          </>
        ) : (
            <ListItemText primary="Loading..." />
        )}
        </ListItemButton>
      </ListItem>
    );
  }

const DashBlacklist = (props) => {
    const { specialist } = props;

    const [blacklist, setBlacklist] = useState([]);

    const getBlacklist = useCallback(() => {
        GetWithAuth(`/blacklists?specialistId=${specialist.id}`)
        .then(res => res.json())
        .then(
            (result) => {
                setBlacklist(result)
            },
            (error) => {
                console.log("Error!")
            }
        );
      }, [specialist])
  
      useEffect(() => { getBlacklist() }, [getBlacklist])

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
            Blacklist
        </Typography>

    </Box>
    
    <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', bgcolor: 'background.paper', border: '5px solid #e0e0e0', marginTop: 3}} >
        <FixedSizeList
        height={770}
        width={1200}
        itemSize={85}
        itemCount={blacklist.length}
        overscanCount={5}
        >
        {(props) => <RenderRow {...props} blacklist={blacklist} />}
        </FixedSizeList>
    </Box> 
    </Box>
  )
}

export default DashBlacklist