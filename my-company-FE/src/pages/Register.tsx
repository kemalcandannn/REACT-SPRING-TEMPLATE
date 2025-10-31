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
    CircularProgress,
} from '@mui/material';
import { FcGoogle } from 'react-icons/fc';
import BaseApiAxios from '../helpers/BaseApiAxios';
import { SERVICE_PATHS } from '../constants/Paths';
import { useApiErrorHandler } from '../helpers/ApiErrorHandler';
import { useAuthentication } from '../contexts/authentication/AuthenticationContext';

const Register: React.FC = () => {
    const { language } = useAuthentication();
    const { handleApiError } = useApiErrorHandler();

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [errors, setErrors] = useState<{ email?: string; password?: string; confirmPassword?: string }>({});
    const [loading, setLoading] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

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
        setSuccessMessage('');
        setErrorMessage('');

        if (!validate()) return;

        setLoading(true);
        try {
            await BaseApiAxios.post(SERVICE_PATHS.API_V1_AUTHENTICATION_REGISTER, {
                username: email,
                password: password,
                confirmPassword: confirmPassword,
                language: language
            });

            // Token geri dönerse fillToken ile session açabilirsiniz
            // const token = response.data.data.token;
            // fillToken(token);

            setSuccessMessage('Mailinize aktivasyon linki iletilmiştir. Linke tıkladıktan sonra uygulamaya giriş yapabilirsiniz.');
        } catch (err: any) {
            setErrorMessage(handleApiError(err));
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
            sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center', minHeight: '100vh' }}
        >
            <Paper elevation={6} sx={{ p: 4, width: '100%', maxWidth: 400, borderRadius: 3 }}>
                <Box display="flex" flexDirection="column" alignItems="center" mb={2}>
                    <Typography variant="h5" fontWeight="bold" gutterBottom>
                        Kaydol
                    </Typography>
                    <Typography variant="body2" color="text.secondary" align="center">
                        Hesap oluşturmak için bilgilerinizi girin
                    </Typography>
                </Box>

                {errorMessage && <Alert severity="error" sx={{ mb: 2 }}>{errorMessage}</Alert>}
                {successMessage && <Alert severity="success" sx={{ mb: 2 }}>{successMessage}</Alert>}

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

                <Button
                    variant="contained"
                    color="primary"
                    fullWidth
                    onClick={handleRegister}
                    sx={{ py: 1.5, mt: 2, fontWeight: 'bold', borderRadius: 2 }}
                    disabled={loading}
                >
                    {loading ? <CircularProgress size={24} /> : 'Kaydol'}
                </Button>

                <Divider sx={{ width: '100%', my: 2 }}>VEYA</Divider>

                <Button
                    variant="outlined"
                    fullWidth
                    startIcon={<FcGoogle />}
                    onClick={handleGoogleRegister}
                    sx={{ py: 1.5, fontWeight: 'bold', borderRadius: 2, textTransform: 'none' }}
                >
                    Google ile Devam Et
                </Button>

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
