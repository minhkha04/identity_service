import React from 'react'
import { useRoutes } from 'react-router-dom'
import UserTemplate from '../template/UserTemplate/UserTemplate.jsx'
import PageNotFound from '../components/PageNotFound/PageNotFound.jsx'
import { path } from '../common/path.js'

const UseRoutesCustom = () => {
  return useRoutes([
    {
      path: path.homePage,
      element: <UserTemplate/>,
    },
    {
      path: path.pageNotFound,
      element: <PageNotFound/>,
    }
  ])

}

export default UseRoutesCustom