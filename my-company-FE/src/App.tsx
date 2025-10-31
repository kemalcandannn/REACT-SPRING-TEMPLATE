import { AuthenticationProvider } from './contexts/authentication/AuthenticationContext';
import AppRouter from './AppRouter';

function App() {
  return (
    <AuthenticationProvider>
      <AppRouter />
    </AuthenticationProvider>
  );
}

export default App;
