import * as React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { IconButton, useTheme } from '@mui/material';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import { Link } from 'react-router-dom';
import AddIcon from '@mui/icons-material/Add';

const Job = (props) => {
    const { id, title, location, description, activationTime, deactivationTime, jobCategoryName, jobPositionName} = props;

    const { palette } = useTheme();

    return(
        <Card sx={{ minWidth: 275, mb: 3, mt: 3 }}>

            <CardContent >
                <Typography sx={{ fontSize: 15, color: palette.secondary.main, fontWeight: 500}} gutterBottom>
                    <IconButton><LocationOnIcon sx={{ color: palette.secondary.main, fontSize: 18 }} /></IconButton> {location}
                </Typography>
                <Typography variant="h5" component="div" >
                    {title}
                </Typography>
                <Typography sx={{ mb: 1.5 , fontSize: 15}} color="text.secondary">
                    {jobCategoryName} | {jobPositionName}
                </Typography>
                <Typography variant="body2">
                    Description: {description.split(" ").slice(0, 10).join(" ") + "..."}
                </Typography>
                <Typography  variant="body2">
                    Application Period: {activationTime.slice(0,10).split('-').reverse().join('/')} - {deactivationTime.slice(0,10).split('-').reverse().join('/')}
                </Typography>
            </CardContent>
            <CardActions>
                <Button disableElevation variant='contained' size="small" startIcon={<AddIcon />}><Link style={{ textDecoration: "none", color: "white", boxShadow: 0 }} to={{ pathname: '/jobs/'+id}}>More Details</Link></Button>
            </CardActions>
        </Card>
    )
}

export default Job;