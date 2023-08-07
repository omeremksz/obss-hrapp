import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { Box } from '@mui/material';

const SpecialistProfile = (props) => {
    const { specialist } = props;

    return(
        <Box sx={{flex:5, p:2}}>
            <Card sx={{ minWidth: 275, mb: 3, mt: 3 }}>
                <CardContent >
                    <Typography variant="h6" >
                        Name: {specialist.firstName} {specialist.lastName}
                    </Typography>
                    <Typography variant="h6">
                        Mail: {specialist.email}
                    </Typography>
                    <Typography variant="h6">
                        Available Job Openings: {specialist.jobs.length}
                    </Typography>
                </CardContent>
            </Card>
        </Box>
    )
}

export default SpecialistProfile