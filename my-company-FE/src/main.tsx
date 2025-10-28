import { createRoot } from 'react-dom/client'
import App from './App.tsx'

/** TODO PRODUCTION'DA StrictMode ACILIR
import { StrictMode } from 'react'

<StrictMode>
    <App />
  </StrictMode> 
 */

createRoot(document.getElementById('root')!).render(
  <App />
)
