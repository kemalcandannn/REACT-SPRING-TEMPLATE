import { StrictMode } from 'react'
import { GoogleOAuthProvider } from '@react-oauth/google';
import { createRoot } from 'react-dom/client'
import App from './App.tsx'
import { GOOGLE_CLIENT_ID } from './constants/Paths.tsx';

createRoot(document.getElementById('root')!).render(
  /*<StrictMode>*/
  <GoogleOAuthProvider clientId={GOOGLE_CLIENT_ID}>
    < App />
  </GoogleOAuthProvider>
  /*</StrictMode>*/
)
