import React from 'react';

const Job = (props) => {
    const { id, code, title, location, description, activationTime, deactivationTime, applicantsCount, 
        specialistId, specialistFirstName, specialistLastName, jobCategoryId, jobCategoryName, jobPositionId, jobPositionName} = props;
    return(
        <div>
            {id} {code} {title} {location} {description} {activationTime} {deactivationTime} {applicantsCount}
            {specialistId} {specialistFirstName} {specialistLastName} {jobCategoryId} {jobCategoryName} {jobPositionId} {jobPositionName}
        </div>
    )
}

export default Job;