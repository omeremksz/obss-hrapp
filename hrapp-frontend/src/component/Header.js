import { Box, styled } from '@mui/material';
import React from 'react';
import headerImage from "../images/OBSS_image2.jpg";

const Header = () => {
  const StyleHeader = styled(Box)(({theme}) => (
    {
      display:'flex',
      justifyContent:'center', 
      minHeight:400,
      backgroundImage: `url(${headerImage})`,
      backgroundSize: 'cover',
      backgroundPosition: 'center',
      backgroundColor: theme.palette.secondary.main
    }
  ));
  return (
    <div>
      <StyleHeader>

      </StyleHeader>
    </div>
  )
}

export default Header