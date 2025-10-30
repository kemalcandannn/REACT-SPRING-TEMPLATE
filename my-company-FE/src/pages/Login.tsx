import React, { useState } from 'react';
import {
    Container,
    Box,
    TextField,
    Button,
    Typography,
    Divider,
    Link,
    Paper,
    IconButton,
    InputAdornment,
    Alert,
} from '@mui/material';
import { FcGoogle } from 'react-icons/fc';
import { Visibility, VisibilityOff } from '@mui/icons-material';
import BaseApiAxios from '../helpers/BaseApiAxios';
import { NAVIGATE_PATHS, SERVICE_PATHS } from '../constants/Paths';
import { useAuthentication } from '../contexts/authentication/AuthenticationContext';
import { useApiErrorHandler } from '../helpers/ApiErrorHandler';

const Login: React.FC = () => {
    const { fillToken } = useAuthentication();
    const { handleApiError } = useApiErrorHandler();

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [errors, setErrors] = useState<{ email?: string; password?: string }>({});
    const [loading, setLoading] = useState(false);
    const [apiError, setApiError] = useState('');

    const validate = () => {
        const newErrors: { email?: string; password?: string } = {};

        if (!email) {
            newErrors.email = 'E-Posta boş olamaz';
        } else if (!/^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email)) {
            newErrors.email = 'Geçerli bir e-posta girin';
        }

        if (!password) {
            newErrors.password = 'Parola boş olamaz';
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleLogin = async () => {
        if (!validate()) return;

        setLoading(true);
        setApiError('');

        try {
            const response = await BaseApiAxios.post(SERVICE_PATHS.API_V1_AUTHENTICATION_LOGIN, {
                username: email,
                password: password,
            });

            const token = response.data.data.token;
            fillToken(token);
        } catch (err: any) {
            setApiError(handleApiError(err));

            // 3 saniye sonra hata mesajını temizle
            setTimeout(() => setApiError(''), 3000);
        } finally {
            setLoading(false);
        }
    };

    const handleGoogleLogin = () => {
        console.log('Login with Google');
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
                        Giriş Yap
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        Hesabınıza giriş yapın veya Google ile devam edin
                    </Typography>
                </Box>

                {/* API Hatası */}
                {apiError && (
                    <Alert severity="error" sx={{ mb: 2 }}>
                        {apiError}
                    </Alert>
                )}

                {/* E-Posta */}
                <TextField
                    label="E-Posta"
                    type="email"
                    fullWidth
                    margin="normal"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    error={!!errors.email}
                    helperText={errors.email}
                />

                {/* Parola */}
                <TextField
                    label="Parola"
                    type={showPassword ? 'text' : 'password'}
                    fullWidth
                    margin="normal"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    error={!!errors.password}
                    helperText={errors.password}
                    InputProps={{
                        endAdornment: (
                            <InputAdornment position="end">
                                <IconButton
                                    onClick={() => setShowPassword(!showPassword)}
                                    edge="end"
                                >
                                    {showPassword ? <VisibilityOff /> : <Visibility />}
                                </IconButton>
                            </InputAdornment>
                        ),
                    }}
                />

                {/* Parolanızı mı unuttunuz */}
                <Box width="100%" textAlign="right" mt={0.5} mb={2}>
                    <Link href={NAVIGATE_PATHS.FORGOT_PASSWORD} underline="hover" variant="body2">
                        Parolanızı mı unuttunuz?
                    </Link>
                </Box>

                {/* Giriş Yap Butonu */}
                <Button
                    variant="contained"
                    color="primary"
                    fullWidth
                    onClick={handleLogin}
                    disabled={loading}
                    sx={{ py: 1.5, mb: 2, fontWeight: 'bold', borderRadius: 2 }}
                >
                    {loading ? 'Giriş Yapılıyor...' : 'Giriş Yap'}
                </Button>

                {/* Divider */}
                <Divider sx={{ width: '100%', my: 2 }}>YA DA</Divider>

                {/* Google ile Giriş */}
                <Button
                    variant="outlined"
                    fullWidth
                    startIcon={<FcGoogle />}
                    onClick={handleGoogleLogin}
                    sx={{
                        py: 1.5,
                        fontWeight: 'bold',
                        borderRadius: 2,
                        textTransform: 'none',
                    }}
                >
                    Google ile Devam Et
                </Button>

                {/* Kaydol Linki */}
                <Box mt={3} textAlign="center">
                    <Typography variant="body2">
                        Hesabın yok mu?{' '}
                        <Link href={NAVIGATE_PATHS.REGISTER} underline="hover">
                            Kaydol
                        </Link>
                    </Typography>
                </Box>
            </Paper>
        </Container>
    );
};

export default Login;
