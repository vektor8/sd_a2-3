import { Route, Routes } from 'react-router-dom'
import AuthenticationSwitch from '../../authenticate/components/authenticate'
import AdminDashboardScreen from '../../admin-dashboard/screens/dashboard_screen'
import LoginScreen from '../../login/screens/login_screen'
import RegisterScreen from '../../register/screens/register_screen'
import CustomerDashboardScreen from '../../customer-dashboard/screens/dashboard_screen'

interface Props {}

function MainRouter(props: Props) {
  return (
    <Routes>
      <Route path="/" element={<AuthenticationSwitch/>}/>
      <Route path="/login" element={<AuthenticationSwitch redirect={<LoginScreen/>}/>}/>
      <Route path="/register" element={<AuthenticationSwitch redirect={<RegisterScreen/>}/>}/>
      <Route path="/admin-dashboard" element={<AuthenticationSwitch to={<AdminDashboardScreen/>}/>}/>
      <Route path="/customer-dashboard" element={<AuthenticationSwitch to={<CustomerDashboardScreen/>}/>}/>
    </Routes>
    
  )
}



export default MainRouter
