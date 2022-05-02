import { useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';
import { RootState } from '../../../stores/store';


function AuthenticationSwitch(props: any) {
  const isAuthenticated = useSelector<RootState>(state => state.user.isLoggedIn);
  const isAdmin = useSelector<RootState>(state => state.user.userData?.admin);
  const { to, redirect } = props;
  console.log(isAuthenticated);
  return (
    isAuthenticated
      ? isAdmin ? (to ?? <Navigate to="/admin-dashboard" />) : (to ?? <Navigate to="/customer-dashboard" />)
      : (redirect ?? <Navigate to="/login" />)
  )
}

export default AuthenticationSwitch
