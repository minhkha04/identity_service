import React from 'react'
import UserHeader from '../../components/UserHeader/UserHeader.jsx'
import UserFooter from '../../components/UserFooter/UserFooter.jsx'
import { Outlet } from 'react-router-dom'

const UserTemplate = () => {
  return (
    <>
      <UserHeader />
      <main>
        <h1>Hello</h1>
        <Outlet />
      </main>
      <UserFooter />
    </>
  )
}

export default UserTemplate