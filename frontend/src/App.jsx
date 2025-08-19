import useRoutesCustom from './hooks/useRoutesCustom.jsx'
import { ConfigProvider, message } from 'antd'
import { createTheme, ThemeProvider } from '@mui/material'
import { LocalizationProvider } from '@mui/x-date-pickers'
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import { createContext } from 'react'
import { AuthProvider } from './components/context/AuthContext.jsx'

export const NotificationContext = createContext()

function App () {
  const [messageApi, contextHolder] = message.useMessage()
  const handleNotification = (type, content) => {
    messageApi.open({
      type,
      content
    })
  }

  const theme = createTheme({
    palette: {
      primary: {
        main: '#000000',
        contrastText: '#fff'
      },
      secondary: {
        main: '#f50057',
      },
    },
  })

  return (
    <AuthProvider>
      <NotificationContext.Provider value={{ handleNotification }}>
        <ThemeProvider theme={theme}>
          <ConfigProvider theme={{
            token: {
              colorPrimary: '#000000',
            },
          }}>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              {contextHolder}
              {useRoutesCustom()}
            </LocalizationProvider>
          </ConfigProvider>
        </ThemeProvider>
      </NotificationContext.Provider>
    </AuthProvider>
  )

}

export default App
