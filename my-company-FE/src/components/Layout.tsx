import React, { type ReactNode } from 'react';
import { AppBar, Toolbar, Typography, Box, Button } from '@mui/material';
import LogoutIcon from '@mui/icons-material/Logout';
import { ChangeCircle } from '@mui/icons-material';
import { useAuthentication } from '../contexts/authentication/AuthenticationContext';
import { NAVIGATE_PATHS } from '../constants/Paths';
import { PROJECT_NAME } from '../constants/Constants';
import { useNavigate } from 'react-router-dom';

interface LayoutProps {
    children: ReactNode;
}

const Layout: React.FC<LayoutProps> = ({ children }) => {
    const { sessionUser } = useAuthentication();
    const navigate = useNavigate();

    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
            {/* Header */}
            <AppBar position="static" color="primary" elevation={2}>
                <Toolbar sx={{ display: 'flex', justifyContent: 'space-between' }}>
                    <Typography variant="h6" sx={{ fontWeight: 'bold' }}>
                        {PROJECT_NAME}
                    </Typography>

                    <Box display="flex" alignItems="center" gap={1}>
                        <Typography variant="body1">{sessionUser?.username}</Typography>

                        <Button
                            onClick={() => {
                                navigate(NAVIGATE_PATHS.CHANGE_PASSWORD, { state: { from: location.pathname } });
                            }}
                            variant="outlined"
                            color="inherit"
                            startIcon={<ChangeCircle />}
                            sx={{ textTransform: 'none', borderRadius: 2, '&:hover': { backgroundColor: 'rgba(255,255,255,0.1)' } }}
                        >
                            Change Password
                        </Button>

                        <Button
                            href={NAVIGATE_PATHS.LOGOUT}
                            color="inherit"
                            startIcon={<LogoutIcon />}
                            sx={{ textTransform: 'none', borderRadius: 2, '&:hover': { backgroundColor: 'rgba(255,255,255,0.1)' } }}
                        >
                            Logout
                        </Button>
                    </Box>
                </Toolbar>
            </AppBar>

            {/* Content */}
            <Box sx={{ flex: 1, mt: 4, mb: 4 }}>{children}</Box>

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
                    © {new Date().getFullYear()} {PROJECT_NAME}. All rights reserved.
                </Typography>
            </Box>
        </Box>
    );
};

export default Layout;
