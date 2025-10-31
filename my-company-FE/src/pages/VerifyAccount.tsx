import React, { useEffect, useState } from 'react';
import { Container, Paper, Typography, CircularProgress, Button } from '@mui/material';
import { useSearchParams, Link as RouterLink, useNavigate } from 'react-router-dom';
import BaseApiAxios from '../helpers/BaseApiAxios';
import { NAVIGATE_PATHS, SERVICE_PATHS } from '../constants/Paths';
import { useApiErrorHandler } from '../helpers/ApiErrorHandler';

const VerifyAccount: React.FC = () => {
    const { handleApiError } = useApiErrorHandler();
    const navigate = useNavigate();

    const [status, setStatus] = useState<'loading' | 'success' | 'error'>('loading');
    const [message, setMessage] = useState(''); // Servisten gelen mesajı tutacağız
    const [searchParams] = useSearchParams();
    const token = searchParams.get('token'); // Onay linkinde token gönderiliyor

    useEffect(() => {
        const verifyAccount = async () => {
            setStatus("loading");
            try {
                const response = await BaseApiAxios.post(
                    SERVICE_PATHS.API_V1_AUTHENTICATION_VERIFY_ACCOUNT,
                    { token }
                );

                // Servisten mesaj varsa kullan, yoksa default mesaj
                setMessage(response.data?.message || '🎉 Hesabınız başarıyla doğrulandı! Artık giriş yapabilirsiniz.');
                setStatus("success");

                setTimeout(() => navigate(NAVIGATE_PATHS.LOGIN), 3000);
            } catch (err: any) {
                // Hata mesajını servis response'dan alabiliriz
                setMessage(handleApiError(err));
                setStatus("error");
            }
        };

        if (token) {
            verifyAccount();
        } else {
            setMessage('⚠️ Token bulunamadı. Lütfen linki kontrol edin.');
            setStatus('error');
        }
    }, [token]);

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
                    maxWidth: 500,
                    borderRadius: 3,
                    textAlign: 'center',
                }}
            >
                {status === 'loading' && <CircularProgress sx={{ mb: 3 }} />}
                <Typography variant="h6" mb={3}>
                    {status === 'loading' ? 'Hesabınız doğrulanıyor, lütfen bekleyin...'
                        : message ?? '⚠️ Hesap doğrulama başarısız oldu. Lütfen linki kontrol edin veya destek ile iletişime geçin.'}
                </Typography>

                {status === 'error' && (
                    <Button
                        variant="outlined"
                        color="secondary"
                        component={RouterLink}
                        to="/"
                        sx={{ mt: 2 }}
                    >
                        Ana Sayfaya Dön
                    </Button>
                )}
            </Paper>
        </Container>
    );
};

export default VerifyAccount;
