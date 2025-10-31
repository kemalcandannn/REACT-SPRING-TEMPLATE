import React, { useState } from 'react';
import { AppBar, Toolbar, Typography, Container, Box, Paper, Button, Grid, Select, type SelectChangeEvent, MenuItem } from '@mui/material';
import LogoutIcon from '@mui/icons-material/Logout';
import { useAuthentication } from '../contexts/authentication/AuthenticationContext';
import { STATUSES } from '../constants/Enumerations';
import ChangePassword from './ChangePassword';
import { NAVIGATE_PATHS } from '../constants/Paths';
import { ChangeCircle } from '@mui/icons-material';
import { PROJECT_NAME } from '../constants/Constants';

const Dashboard: React.FC = () => {
    const { sessionUser } = useAuthentication();
    const [clickedChangePassword, setClickedChangePassword] = useState<boolean>(false);

    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
            {/* Header */}
            <AppBar position="static" color="primary" elevation={2}>
                <Toolbar sx={{ display: 'flex', justifyContent: 'space-between' }}>
                    {/* Sol taraf: Logo / Başlık */}
                    <Typography variant="h6" component="div" sx={{ fontWeight: 'bold' }}>
                        {PROJECT_NAME}
                    </Typography>

                    {/* Sağ taraf: Kullanıcı adı + Dil Seçici + Çıkış */}
                    <Box display="flex" alignItems="center" gap={1}>
                        <Typography variant="body1" sx={{ mr: 1 }}>
                            {sessionUser?.username}
                        </Typography>

                        <Button
                            variant="outlined"
                            color="inherit"
                            startIcon={<ChangeCircle />}
                            sx={{
                                textTransform: 'none',
                                borderRadius: 2,
                                '&:hover': { backgroundColor: 'rgba(255,255,255,0.1)' },
                            }}
                            onClick={() => setClickedChangePassword(true)}
                        >
                            Change Password
                        </Button>

                        <Button
                            href={NAVIGATE_PATHS.LOGOUT}
                            color="inherit"
                            startIcon={<LogoutIcon />}
                            sx={{
                                textTransform: 'none',
                                borderRadius: 2,
                                '&:hover': { backgroundColor: 'rgba(255,255,255,0.1)' },
                            }}
                        >
                            Logout
                        </Button>
                    </Box>
                </Toolbar>
            </AppBar>

            {/* Content */}
            <Container sx={{ flex: 1, mt: 4, mb: 4 }}>
                {sessionUser?.status == STATUSES.ACTIVE &&
                    (clickedChangePassword ?
                        <ChangePassword setClickedChangePassword={setClickedChangePassword} />
                        :
                        (sessionUser?.passwordValidUntil != null &&
                            new Date(sessionUser?.passwordValidUntil) < new Date()) ?
                            <ChangePassword />
                            :
                            <>
                                <Typography variant="h5" gutterBottom>
                                    Dashboard
                                </Typography>

                                <Grid container spacing={3}>
                                    {/* Örnek Kartlar */}
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
                    )
                }
            </Container>

            {/* Footer */}
            <Box
                component="footer"
                sx={{
                    py: 2,
                    textAlign: 'center',
                    bgcolor: 'background.paper',
                    borderTop: '1px solid #e0e0e0',
                }}
            >
                <Typography variant="body2" color="text.secondary">
                    © {new Date().getFullYear().toString()} {PROJECT_NAME}. All rights reserved.
                </Typography>
            </Box>
        </Box>
    );
};

export default Dashboard;
