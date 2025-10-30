import React, { useState } from 'react';
import {
    Container,
    Box,
    TextField,
    Button,
    Typography,
    Paper,
    Link,
    Alert,
    CircularProgress,
} from '@mui/material';
import { NAVIGATE_PATHS, SERVICE_PATHS } from '../constants/Paths';
import BaseApiAxios from '../helpers/BaseApiAxios';
import { useApiErrorHandler } from '../helpers/ApiErrorHandler';
import { useNavigate } from 'react-router-dom';

const ForgotPassword: React.FC = () => {
    const { handleApiError } = useApiErrorHandler();
    const navigate = useNavigate();

    const [email, setEmail] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const [loading, setLoading] = useState(false);

    const validateEmail = (email: string) => {
        return /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email);
    };

    const handleSend = async () => {
        setError('');
        setSuccess('');

        if (!email) {
            setError('E-posta boş olamaz.');
            return;
        }

        if (!validateEmail(email)) {
            setError('Geçerli bir e-posta adresi girin.');
            return;
        }

        setLoading(true);

        try {
            await BaseApiAxios.post(
                SERVICE_PATHS.API_V1_AUTHENTICATION_SEND_PASSWORD_RESET_LINK,
                { username: email }
            );

            setSuccess('Parola sıfırlama linki e-postanıza gönderildi!');
            setEmail('');
            setTimeout(() => navigate(NAVIGATE_PATHS.LOGIN), 1500);
        } catch (err: any) {
            setError(handleApiError(err));
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container
            maxWidth="sm"
            sx={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                minHeight: '100vh',
            }}
        >
            <Paper
                elevation={6}
                sx={{
                    p: 4,
                    width: '100%',
                    maxWidth: 400,
                    borderRadius: 3,
                }}
            >
                <Box display="flex" flexDirection="column" alignItems="center" mb={2}>
                    <Typography variant="h5" fontWeight="bold" gutterBottom>
                        Parolanızı mı unuttunuz?
                    </Typography>
                    <Typography variant="body2" color="text.secondary" align="center">
                        E-postanızı girin, size parola sıfırlama linki göndereceğiz.
                    </Typography>
                </Box>

                {error && (
                    <Alert severity="error" sx={{ mb: 2 }}>
                        {error}
                    </Alert>
                )}
                {success && (
                    <Alert severity="success" sx={{ mb: 2 }}>
                        {success}
                    </Alert>
                )}

                <TextField
                    label="E-Posta"
                    type="email"
                    fullWidth
                    margin="normal"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    disabled={loading}
                />

                <Button
                    variant="contained"
                    color="primary"
                    fullWidth
                    onClick={handleSend}
                    disabled={loading}
                    sx={{
                        py: 1.5,
                        mt: 2,
                        fontWeight: 'bold',
                        borderRadius: 2,
                    }}
                >
                    {loading ? (
                        <CircularProgress size={24} color="inherit" />
                    ) : (
                        'Gönder'
                    )}
                </Button>

                <Box mt={3} textAlign="center">
                    <Typography variant="body2">
                        Hatırladınız mı?{' '}
                        <Link href={NAVIGATE_PATHS.LOGIN} underline="hover">
                            Giriş Yap
                        </Link>
                    </Typography>
                </Box>
            </Paper>
        </Container>
    );
};

export default ForgotPassword;
