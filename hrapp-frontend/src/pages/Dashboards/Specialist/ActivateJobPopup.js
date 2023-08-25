import React, { useState } from 'react';
import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Button } from '@mui/material';
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';

const ActivateJobPopup = ({ open, onClose, onActivate }) => {
  const [activationTime, setActivationTime] = useState(null);
  const [deactivationTime, setDeactivationTime] = useState(null);

  const handleActivate = () => {
    onActivate(activationTime, deactivationTime);
    onClose();
  };

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>Activate Job</DialogTitle>
      <DialogContent>
        <DialogContentText>
          Please set the activation and deactivation times for the job.
        </DialogContentText>
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <DateTimePicker label="Activation Time" value={activationTime} onChange={setActivationTime} />
          <DateTimePicker label="Deactivation Time" value={deactivationTime} onChange={setDeactivationTime} />
        </LocalizationProvider>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose} color="secondary">
          Cancel
        </Button>
        <Button onClick={handleActivate} color="primary">
          Activate
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default ActivateJobPopup;
