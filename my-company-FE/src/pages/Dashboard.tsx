import React from 'react';
import { Typography, Grid, Paper } from '@mui/material';

const Dashboard: React.FC = () => {

    return (
        <>
            <Typography variant="h5" gutterBottom>
                Dashboard
            </Typography>
            <Grid container spacing={3}>
                <Paper sx={{ p: 2, textAlign: 'center', borderRadius: 2 }}>
                    <Typography variant="h6">Toplam Kullanıcı</Typography>
                    <Typography variant="h4">1200</Typography>
                </Paper>

                <Paper sx={{ p: 2, textAlign: 'center', borderRadius: 2 }}>
                    <Typography variant="h6">Aktif Oturumlar</Typography>
                    <Typography variant="h4">75</Typography>
                </Paper>

                <Paper sx={{ p: 2, textAlign: 'center', borderRadius: 2 }}>
                    <Typography variant="h6">Bekleyen İşlemler</Typography>
                    <Typography variant="h4">23</Typography>
                </Paper>
            </Grid>
        </>
    );
};

export default Dashboard;
