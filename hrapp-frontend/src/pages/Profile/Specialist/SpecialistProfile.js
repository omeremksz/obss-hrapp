import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { Box } from '@mui/material';

const SpecialistProfile = (props) => {
    const { specialist } = props;

    return(
        <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', width: '100%' }}>
            <Card style={{ width: '770px', height: '125px', justifyContent: 'center', alignItems: 'center', display: 'flex' }}>
                <CardContent >
                    <Typography variant="h6" >
                        Name: {specialist.firstName} {specialist.lastName}
                    </Typography>
                    <Typography variant="h6">
                        Mail: {specialist.email}
                    </Typography>
                </CardContent>
            </Card>
        </Box>
    )
}

export default SpecialistProfile