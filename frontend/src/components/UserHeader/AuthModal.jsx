import React, { useContext, useEffect, useState } from 'react'
import { Modal } from 'antd'
import InputCustom from '../Input/InputCustom.jsx'
import { DatePicker } from '@mui/x-date-pickers'
import { Button } from '@mui/material'
import { authService } from '../../service/auth.service.js'
import { NotificationContext } from '../../App.jsx'

const AuthModal = ({ isOpen, onClose, mode, setMode }) => {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [fullName, setFullName] = useState('')
  const [birthDate, setBirthDate] = useState(null)
  const [otp, setOtp] = useState('')
  const [confirmLoading, setConfirmLoading] = useState(false)
  const [countdown, setCountdown] = useState(0)
  const [isCounting, setIsCounting] = useState(false)
  const valueContext = useContext(NotificationContext)

  useEffect(() => {
    let timer
    if (isCounting && countdown > 0) {
      timer = setInterval(() => {
        setCountdown((prev) => prev - 1)
      }, 1000)
    } else if (countdown === 0) {
      setIsCounting(false)
      clearInterval(timer)
    }
    return () => clearInterval(timer)
  }, [isCounting, countdown])

  const handleSubmit = () => {
    if (mode === 'login') {
      authService.login({ email, password }, 'EMAIL')
        .then((res) => {
          console.log(res.data.data.token)
          localStorage.setItem('token', JSON.stringify(res.data.data.token))
          onClose()
          valueContext.handleNotification('success', 'Login successfully')
          setEmail('')
          setPassword('')
        })
        .catch((err) => {
          valueContext.handleNotification('error', err.response.data.message)
        })
        .finally(() => {
          setConfirmLoading(false)
        })
    } else if (mode === 'register') {
      authService.register({
        email,
        password,
        fullName,
        birthDate: birthDate ? birthDate.format('YYYY-MM-DD') : null,
        otp
      })
        .then((res) => {
          console.log(res.data.data.token)
          localStorage.setItem('token', JSON.stringify(res.data.data.token))
          onClose()
          valueContext.handleNotification('success', 'Register successfully')
          setEmail('')
          setPassword('')
          setFullName('')
          setBirthDate(null)
          setOtp('')
        })
        .catch((err) => {
          valueContext.handleNotification('error', err.response.data.message)
        })
        .finally(() => {})
      // gọi API register
    } else if (mode === 'forgot_password') {
      authService.restPassword({ email, otp, password })
        .then((res) => {
          console.log(res.data.data.token)
          localStorage.setItem('token', JSON.stringify(res.data.data.token))
          onClose()
          valueContext.handleNotification('success', 'Reset password successfully')
          setEmail('')
          setPassword('')
          setOtp('')
        })
        .catch((err) => {
          valueContext.handleNotification('error', err.response.data.message)
        })
        .finally(() => {})

    }
  }

  const handleSendOtp = (type) => {
    authService.sendOTP({ email }, type)
      .then((res) => {
        valueContext.handleNotification('success', 'Send OTP successfully')
        setIsCounting(true)
        setCountdown(60)
      })
      .catch((err) => {
        valueContext.handleNotification('error', err.response.data.message)
      })

  }

  return (
    <Modal
      title={mode === 'login' ? 'Login' : mode === 'register' ? 'Register' : 'Forgot Password'}
      centered
      open={isOpen}
      onOk={handleSubmit}
      onCancel={onClose}
      okText={mode === 'login' ? 'Login' : mode === 'register' ? 'Register' : 'Change Password'}
      confirmLoading={confirmLoading}
    >
      <InputCustom label={'Email'} placeholder={'Enter your email'} value={email}
                   onChange={(e) => setEmail(e.target.value)}/>
      <InputCustom label={'Password'}
                   placeholder={`Enter your ${mode === 'login' ? 'password' : mode === 'register' ? 'password' : 'new password'}`}
                   isPassword={true} value={password} onChange={(e) => setPassword(e.target.value)}/>
      {mode === 'register' &&
        <>
          <InputCustom label={'Full Name'} placeholder={'Enter your full name'} value={fullName}
                       onChange={(e) => setFullName(e.target.value)}/>
          <DatePicker label="Choose your birthdate" value={birthDate} onChange={(newValue) => setBirthDate(newValue)}
                      slotProps={{
                        textField: {
                          fullWidth: true,
                          size: 'small',
                          margin: 'dense',
                        }
                      }}
          />
          <div className={'flex flex-row items-center gap-3'}>
            <div className={'w-7/12 sm:w-11/12'}>
              <InputCustom label={'OTP'} placeholder={'Enter OTP'} isOTP={true} value={otp}
                           onChange={(e) => setOtp(e.target.value)}/>
            </div>
            <div className={'w-1/3'}>
              <Button variant="contained" size="large" onClick={() => handleSendOtp('REGISTER_ACCOUNT')}
                      disabled={isCounting}
                      sx={{
                        marginTop: '5px',
                        height: '39px',
                        minWidth: '100px',
                        whiteSpace: 'nowrap'
                      }}
              >
                {isCounting ? `Resend ${countdown}s` : 'Send OTP'}
              </Button>
            </div>
          </div>
        </>}
      {mode === 'forgot_password'
        && <div className={'flex flex-row items-center gap-3'}>
          <div className={'w-7/12 sm:w-11/12'}>
            <InputCustom label={'OTP'} placeholder={'Enter OTP'} isOTP={true} value={otp}
                         onChange={(e) => setOtp(e.target.value)}/>
          </div>
          <div className={'w-1/3'}>
            <Button variant="contained" size="large" onClick={() => handleSendOtp('RESET_PASSWORD')}
                    disabled={isCounting}
                    sx={{
                      marginTop: '5px',
                      height: '39px',
                      minWidth: '100px',
                      whiteSpace: 'nowrap'
                    }}
            >
              {isCounting ? `Resend ${countdown}s` : 'Send OTP'}
            </Button>
          </div>
        </div>
      }
      <div className={'flex flex-row justify-between'}>
        {mode === 'login' ? (
          <span>
            Don't have an account?{' '}
            <a onClick={() => setMode('register')} className={'text-red-500 hover:text-black'}>Register</a>
          </span>
        ) : (
          <span>
            Have an account?{' '}
            <a onClick={() => setMode('login')} className={'text-red-500 hover:text-black'}>Login</a>
          </span>
        )}
        {mode !== 'forgot_password'
          ? <span>
            Forgot Password?{' '}
            <a onClick={() => setMode('forgot_password')}
               className={'text-red-500 hover:text-black'}>Reset Password</a>
          </span>
          : <span>
            Don't have an account?{' '}
            <a onClick={() => setMode('register')} className={'text-red-500 hover:text-black'}>Register</a>
          </span>
        }

      </div>
    </Modal>
  )
}

export default AuthModal