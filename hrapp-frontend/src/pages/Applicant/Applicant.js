import React from 'react';
import { useParams } from 'react-router-dom';

const Applicant = () => {
    const {applicantId} = useParams();
  return (
    <div>Applicant {applicantId}</div>
  )
}

export default Applicant