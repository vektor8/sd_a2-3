import * as React from 'react';
import { styled, createTheme, ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import MuiAppBar, { AppBarProps as MuiAppBarProps } from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import Link from '@mui/material/Link';
import Orders from '../components/Orders';
import { RootState } from '../../../stores/store';
import { useDispatch, useSelector } from 'react-redux';
import { Restaurant2 } from '../../../model/restaurant';
import { Alert, Autocomplete, Button, FormControl, IconButton, InputLabel, MenuItem, Select, Snackbar, TextField } from '@mui/material';
import { customAxios } from '../../../api/api';
import FoodTable from '../components/FoodTable';
import { Customer } from '../../../model/user';
import { clearCart, selectRestaurant } from '../../../stores/bill/slice';
import { login, logout } from '../../../stores/user/slice';
import { BillState } from '../../../stores/bill/state';
import LogoutIcon from '@mui/icons-material/Logout';


function Copyright(props: any) {
  return (
    <Typography variant="body2" color="text.secondary" align="center" {...props}>
      {'Copyright © '}
      <Link color="inherit" href="https://mui.com/">
        FoodPanda
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

const drawerWidth: number = 240;

interface AppBarProps extends MuiAppBarProps {
  open?: boolean;
}

const AppBar = styled(MuiAppBar, {
  shouldForwardProp: (prop) => prop !== 'open',
})<AppBarProps>(({ theme, open }) => ({
  zIndex: theme.zIndex.drawer + 1,
  transition: theme.transitions.create(['width', 'margin'], {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  ...(open && {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  }),
}));


const mdTheme = createTheme({
  palette: {
    mode: 'dark',
  },
});

export const orderStatus = [
  "-",
  "PENDING",
  "ACCEPTED",
  "IN_DELIVERY",
  "DELIVERED",
  "DECLINED"
];

function DashboardContent() {
  const bill = useSelector<RootState>(state => state.bill) as BillState;
  const [restaurantId, setRestaurantId] = React.useState(0);
  const [restaurants, setRestaurants]: [Restaurant2[], any] = React.useState([]);
  const [openSnackBarSuccess, setOpenSnackBarSuccess] = React.useState(false);
  const [openSnackBarFail, setOpenSnackBarFail] = React.useState(false);
  const dispatch = useDispatch();

  const handleChange = (e: any) => {
    setRestaurantId(e.target.value)
    dispatch(selectRestaurant(e.target.value));
    dispatch(clearCart);
  };


  const user: Customer = useSelector((state: RootState) => state.user.userData) as Customer;

  React.useEffect(() => {
    customAxios.get('/api/restaurant').then((response) => {
      setRestaurants(response.data);
    });
    customAxios.get("/api/user").then((response) => {
      dispatch(login(response.data));
    });
  }, []);

  const placeOrder = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    customAxios.post("/api/order", {
      customerId: bill.customerId,
      restaurantId: bill.restaurantId,
      orderedFoods: bill.orderedFoods.filter(e => e.quantity > 0),
      address: data.get("address"),
      orderDetails: data.get("details"),
    }).then((response) => {
      setOpenSnackBarSuccess(true);
      customAxios.get(`/api/user`)
        .then((resp) =>
          dispatch(login(resp.data))).catch((err) => console.log(err))
    }).catch((err) => setOpenSnackBarFail(true))
  }
  return (

    <ThemeProvider theme={mdTheme}>
      <Snackbar open={openSnackBarSuccess} autoHideDuration={5000} onClose={() => setOpenSnackBarSuccess(false)}>
        <Alert onClose={() => setOpenSnackBarSuccess(false)} severity="success" sx={{ width: '100%' }}>
          Order was placed succesfully
        </Alert>
      </Snackbar>
      <Snackbar open={openSnackBarFail} autoHideDuration={5000} onClose={() => setOpenSnackBarFail(false)}>
        <Alert onClose={() => setOpenSnackBarFail(false)} severity="error" sx={{ width: '100%' }}>
          Order could not be placed
        </Alert>
      </Snackbar>
      <Box sx={{ display: 'flex' }}>
        <CssBaseline />
        <AppBar>
          <Toolbar
          >
            <Typography
              component="h1"
              variant="h6"
              color="inherit"
              noWrap
              sx={{ flexGrow: 1 }}
            >
              Hello {user.email}
            </Typography>
            <IconButton onClick={() => dispatch(logout())}>
              <LogoutIcon />
            </IconButton>
          </Toolbar>
        </AppBar>
        <Box
          component="main"
          sx={{
            backgroundColor: (theme) =>
              theme.palette.mode === 'light'
                ? theme.palette.grey[100]
                : theme.palette.grey[900],
            flexGrow: 1,
            height: '100vh',
            overflow: 'auto',
          }}
        >
          <Toolbar />
          <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
            <Grid item xs={12} md={4} lg={3}>
              <Paper
                sx={{
                  p: 2,
                  display: 'flex',
                  flexDirection: 'column',
                }}>
                <Typography variant='h4'>My Orders</Typography>
                {user.orders.length > 0 && <Orders orders={user.orders} />}
                {(user.orders == null || user.orders.length < 1) && <Typography variant='h6'>You haven't placed any orders yet</Typography>}
              </Paper>
            </Grid>
            <Grid item xs={12} md={4} lg={3}>
              <Paper
                sx={{
                  p: 2,
                  display: 'flex',
                  flexDirection: 'column',
                  height: 240,
                }}
              >
                <Typography variant="h4">New Order</Typography>
                <Typography variant="h6">Pick a restaurant</Typography>
                <br></br>
                <FormControl fullWidth>
                  <Autocomplete
                    disablePortal
                    options={restaurants.map(restaurant => restaurant.name)}
                    sx={{ width: 300 }}
                    renderInput={params => (
                      <TextField {...params} label="Restaurant" />
                    )}
                    value={restaurants.find(e => e.id === +restaurantId)?.name}
                    onChange={(_, newValue) => {
                      let id = restaurants.find(
                        restaurant => restaurant.name === newValue,
                      )?.id;
                      if (id)
                        setRestaurantId(id as number);
                      dispatch(selectRestaurant(id as number));
                      dispatch(clearCart);
                    }}
                  />
                </FormControl>
              </Paper>
            </Grid>
            <Grid item xs={12} >
              <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                {restaurantId > 0 &&
                  <>
                    <Typography variant="h6">Pick your foods</Typography>
                    <FoodTable restaurant={restaurants.find((e: Restaurant2) => e.id == restaurantId) as unknown as Restaurant2} />
                  </>}
                {bill.orderedFoods.length > 0 &&
                  <Box component="form" noValidate onSubmit={placeOrder}
                    sx={{
                      maxWidth: 500,
                      backgroundColor: "grey",
                      borderRadius: 2 / 1,
                      padding: 4,
                    }}
                  >
                    <TextField
                      sx={{ marginBottom: 2 }}
                      margin="normal"
                      required
                      fullWidth
                      id="details"
                      label="details"
                      name="details"
                      autoComplete="details"
                      autoFocus
                    />
                    <TextField
                      sx={{ marginBottom: 2 }}
                      margin="normal"
                      required
                      fullWidth
                      id="address"
                      label="address"
                      name="address"
                      autoComplete="address"
                      autoFocus
                    />

                    <Button
                      sx={{ marginTop: 2, textTransform: "none", width: "50%" }}
                      variant="contained"
                      type="submit"
                    >
                      Place order
                    </Button>
                  </Box>
                }
              </Paper>
            </Grid>
            <Copyright sx={{ pt: 4 }} />
          </Container>
        </Box>
      </Box>
    </ThemeProvider >
  );
}

export default function CustomerDashboardScreen() {
  return <DashboardContent />;
}
