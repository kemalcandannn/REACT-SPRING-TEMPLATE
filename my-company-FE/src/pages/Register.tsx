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
    Alert,
} from '@mui/material';
import { FcGoogle } from 'react-icons/fc';
import BaseApiAxios from '../helpers/BaseApiAxios';
import { SERVICE_PATHS } from '../constants/Paths';
import { useAuthentication } from '../contexts/authentication/AuthenticationContext';
import { useApiErrorHandler } from '../helpers/ApiErrorHandler';

const Register: React.FC = () => {
    const { fillToken } = useAuthentication();
    const { handleApiError } = useApiErrorHandler();

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [errors, setErrors] = useState<{ email?: string; password?: string; confirmPassword?: string }>({});
    const [loading, setLoading] = useState(false);
    const [apiError, setApiError] = useState('');

    const validate = () => {
        const newErrors: { email?: string; password?: string; confirmPassword?: string } = {};

        if (!email) {
            newErrors.email = 'E-Posta boş olamaz';
        } else if (!/^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email)) {
            newErrors.email = 'Geçerli bir e-posta girin';
        }

        if (!password) {
            newErrors.password = 'Parola boş olamaz';
        }

        if (!confirmPassword) {
            newErrors.confirmPassword = 'Parola doğrulama boş olamaz';
        } else if (confirmPassword !== password) {
            newErrors.confirmPassword = 'Parolalar eşleşmiyor';
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleRegister = async () => {
        setApiError('');
        if (!validate()) return;

        setLoading(true);

        try {
            const response = await BaseApiAxios.post(SERVICE_PATHS.API_V1_AUTHENTICATION_REGISTER, {
                username: email,
                password,
                confirmPassword,
            });

            const token = response.data.data.token;
            fillToken(token);
        } catch (err: any) {
            setApiError(handleApiError(err));
            setTimeout(() => setApiError(''), 3000); // 3 saniye sonra hata kaybolacak
        } finally {
            setLoading(false);
        }
    };

    const handleGoogleRegister = () => {
        console.log('Register with Google');
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
                        Kaydol
                    </Typography>
                    <Typography variant="body2" color="text.secondary" align="center">
                        Hesap oluşturmak için bilgilerinizi girin
                    </Typography>
                </Box>

                {/* API Hata Mesajı */}
                {apiError && (
                    <Alert severity="error" sx={{ mb: 2, width: '100%' }}>
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
                    type="password"
                    fullWidth
                    margin="normal"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    error={!!errors.password}
                    helperText={errors.password}
                />

                {/* Parola Doğrulama */}
                <TextField
                    label="Parola Doğrulama"
                    type="password"
                    fullWidth
                    margin="normal"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    error={!!errors.confirmPassword}
                    helperText={errors.confirmPassword}
                />

                {/* Kayıt Butonu */}
                <Button
                    variant="contained"
                    color="primary"
                    fullWidth
                    onClick={handleRegister}
                    disabled={loading}
                    sx={{ py: 1.5, mt: 2, fontWeight: 'bold', borderRadius: 2 }}
                >
                    {loading ? 'Kaydol...' : 'Kaydol'}
                </Button>

                {/* Divider */}
                <Divider sx={{ width: '100%', my: 2 }}>YA DA</Divider>

                {/* Google ile Kayıt */}
                <Button
                    variant="outlined"
                    fullWidth
                    startIcon={<FcGoogle />}
                    onClick={handleGoogleRegister}
                    sx={{
                        py: 1.5,
                        fontWeight: 'bold',
                        borderRadius: 2,
                        textTransform: 'none',
                    }}
                >
                    Google ile Devam Et
                </Button>

                {/* Girişe Dön Linki */}
                <Box mt={3} textAlign="center">
                    <Typography variant="body2">
                        Zaten hesabınız var mı?{' '}
                        <Link href="/login" underline="hover">
                            Giriş Yap
                        </Link>
                    </Typography>
                </Box>
            </Paper>
        </Container>
    );
};

export default Register;
