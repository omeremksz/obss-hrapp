import React from 'react'
import { useParams } from 'react-router-dom'

const Applicant = () => {
    const {id} = useParams();
  return (
    <div>Applicant {id}</div>
  )
}

export default Applicant