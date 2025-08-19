import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import { path } from '../../common/path.js'
import { assets } from '../../assets/assets.jsx'
import AuthModal from './AuthModal.jsx'
import { Button } from '@mui/material'

const UserHeader = () => {
  const [modalOpen, setModalOpen] = useState(false)
  const [model, setModel] = useState('')
  return (
    <header>
      <AuthModal isOpen={modalOpen} onClose={() => setModalOpen(false)} title={'Login'} mode={model}
                 setMode={setModel}/>
      <div className={'container border-2 border-red-600 flex flex-row justify-between items-center'}>
        <div className={'logo'}>
          <Link to={path.homePage} onClick={() => scrollTo(0, 0)}>
            <img src={assets.logo} alt={'logo'} className={'h-16 w-16'}/>
          </Link>
        </div>
        <nav>
          <div className={'flex flex-row gap-4'}>
            <Button onClick={() => {
              setModalOpen(true)
              setModel('login')
            }} variant="outlined" size="small"
            >Login</Button>

            <Button variant="contained" size="small"
                    onClick={() => {
                      setModalOpen(true)
                      setModel('register')
                    }}
            >Register</Button>
          </div>

        </nav>
      </div>
    </header>
  )
}

export default UserHeader