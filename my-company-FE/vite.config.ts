import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    open: true,
    port: 8080,
    proxy: {
      "/my-company": {
        target: "http://localhost:8090",
        changeOrigin: true,
        secure: false,
      },
    },
  }
})
