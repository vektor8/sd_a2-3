import * as React from 'react';
import { styled, createTheme, ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import MuiDrawer from '@mui/material/Drawer';
import Box from '@mui/material/Box';
import MuiAppBar, { AppBarProps as MuiAppBarProps } from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import Container from '@mui/material/Container';
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import Link from '@mui/material/Link';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import { secondaryListItems } from '../components/listItems';
import Orders from '../components/Orders';
import { Admin } from '../../../model/user';
import { RootState } from '../../../stores/store';
import { useDispatch, useSelector } from 'react-redux';
import { Restaurant } from '../../../model/restaurant';
import { Alert, Autocomplete, FormControl, InputLabel, ListItemButton, MenuItem, Select, Snackbar, TextField } from '@mui/material';
import RestaurantModal from '../components/RestaurantModal';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import DashboardIcon from '@mui/icons-material/Dashboard';
import { customAxios } from '../../../api/api';
import RestaurantIcon from '@mui/icons-material/Restaurant';
import FastfoodIcon from '@mui/icons-material/Fastfood';
import FoodModal from '../components/FoodModal';
import FoodTable from '../components/FoodTable';
import { login, logout } from '../../../stores/user/slice';
import LogoutIcon from '@mui/icons-material/Logout';
import DownloadIcon from '@mui/icons-material/Download';
import { blob } from 'stream/consumers';

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

const Drawer = styled(MuiDrawer, { shouldForwardProp: (prop) => prop !== 'open' })(
  ({ theme, open }) => ({
    '& .MuiDrawer-paper': {
      position: 'relative',
      whiteSpace: 'nowrap',
      width: drawerWidth,
      transition: theme.transitions.create('width', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.enteringScreen,
      }),
      boxSizing: 'border-box',
      ...(!open && {
        overflowX: 'hidden',
        transition: theme.transitions.create('width', {
          easing: theme.transitions.easing.sharp,
          duration: theme.transitions.duration.leavingScreen,
        }),
        width: theme.spacing(7),
        [theme.breakpoints.up('sm')]: {
          width: theme.spacing(9),
        },
      }),
    },
  }),
);

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
  const [open, setOpen] = React.useState(true);
  const [restaurantModal, setRestaurantModal] = React.useState(false);
  const [foodModal, setFoodModal] = React.useState(false);
  const [status, setStatus] = React.useState(0);
  const [restaurantId, setRestaurantId] = React.useState(0);
  const [openSnackBarSuccess, setOpenSnackBarSuccess] = React.useState(false);
  const [openSnackBarFail, setOpenSnackBarFail] = React.useState(false);
  const dispatch = useDispatch();
  const toggleDrawer = () => {
    setOpen(!open);
  };

  const handleStatus = (e: any) => {
    setStatus(e.target.value);
  };

  React.useEffect(() => {
    customAxios.get('/api/user').then((response) => {
      console.log(response.data);
      dispatch(login(response.data));
    });
  }, []);

  const user: Admin = useSelector<RootState>(state => state.user.userData) as Admin;
  const restaurants: [Restaurant] = user.restaurants;

  const submitRestaurant = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    customAxios.post('/api/restaurant', {
      name: data.get("name"),
      location: data.get('location'),
      // adminId: user.id
    }).then((response) => {
      setOpenSnackBarSuccess(true);
      customAxios.get(`/api/user`)
        .then((resp) =>
          dispatch(login(resp.data))).catch((err) => console.log(err))
      setRestaurantModal(false);
    }).catch((err) => setOpenSnackBarFail(true));
  };

  const submitFood = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    customAxios.post('/api/food', {
      name: data.get("name"),
      description: data.get('description'),
      price: data.get('price'),
      restaurantId: +restaurantId,
      category: data.get("category")
    }).then((response) => {
      setOpenSnackBarSuccess(true);
      customAxios.get(`/api/user`)
        .then((resp) =>
          dispatch(login(resp.data))).catch((err) => console.log(err))
      setFoodModal(false);
    }).catch((err) => { setOpenSnackBarFail(true) });
  };

  return (
    <ThemeProvider theme={mdTheme}>
      <Snackbar open={openSnackBarSuccess} autoHideDuration={5000} onClose={() => setOpenSnackBarSuccess(false)}>
        <Alert onClose={() => setOpenSnackBarSuccess(false)} severity="success" sx={{ width: '100%' }}>
          Added sucessfully
        </Alert>
      </Snackbar>
      <Snackbar open={openSnackBarFail} autoHideDuration={5000} onClose={() => setOpenSnackBarFail(false)}>
        <Alert onClose={() => setOpenSnackBarFail(false)} severity="error" sx={{ width: '100%' }}>
          Invalid data
        </Alert>
      </Snackbar>
      <Box sx={{ display: 'flex' }}>
        <RestaurantModal
          isOpen={restaurantModal}
          onClose={() => setRestaurantModal(false)}
          onSubmit={submitRestaurant}
        />
        <FoodModal
          isOpen={foodModal}
          onClose={() => setFoodModal(false)}
          onSubmit={submitFood}
        />
        <CssBaseline />
        <AppBar position="absolute" open={open}>
          <Toolbar
            sx={{
              pr: '24px', // keep right padding when drawer closed
            }}
          >
            <IconButton
              edge="start"
              color="inherit"
              aria-label="open drawer"
              onClick={toggleDrawer}
              sx={{
                marginRight: '36px',
                ...(open && { display: 'none' }),
              }}
            >
              <MenuIcon />
            </IconButton>
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

        <Drawer variant="permanent" open={open}>
          <Toolbar
            sx={{
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'flex-end',
              px: [1],
            }}
          >
            <IconButton onClick={toggleDrawer}>
              <ChevronLeftIcon />
            </IconButton>
          </Toolbar>
          <Divider />
          <List component="nav">
            <ListItemButton>
              <ListItemIcon>
                <DashboardIcon />
              </ListItemIcon>
              <ListItemText primary="Dashboard" />
            </ListItemButton>
            <ListItemButton onClick={() => setRestaurantModal(true)}>
              <ListItemIcon>
                <RestaurantIcon />
              </ListItemIcon>
              <ListItemText primary="Add restaurant" />
            </ListItemButton>
            <ListItemButton onClick={() => setFoodModal(true)}>
              <ListItemIcon>
                <FastfoodIcon />
              </ListItemIcon>
              <ListItemText primary="Add food" />
            </ListItemButton>
            <Divider sx={{ my: 1 }} />
            {secondaryListItems}
          </List>
        </Drawer>
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
            <Paper
              sx={{
                p: 2,
                display: 'flex',
                flexDirection: 'column',
                height: 240,
              }}
            >
              <Grid container
                direction="row"
                justifyContent="space-between"
              >
                <Grid item >
                  <Typography variant="h4">Pick a restaurant</Typography>
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
                      }}
                    />
                  </FormControl>
                </Grid>
                {restaurantId > 0 &&
                  <Grid item
                    alignItems={"center"}>
                    <Typography variant="h6"> Download the menu</Typography>
                    <IconButton onClick={() => customAxios.get(`/api/restaurant/${restaurantId}`, {responseType: 'blob'}).then((response) => {
                      const url = window.URL.createObjectURL(new Blob([response.data]));
                      const link = document.createElement('a');
                      link.href = url;
                      link.setAttribute('download', `${restaurants.find(e => e.id == restaurantId)?.name}.pdf`); //or any other extension
                      document.body.appendChild(link);
                      link.click();
                    })}>
                    <DownloadIcon></DownloadIcon>
                  </IconButton>
                  </Grid>
                }
            </Grid>
          </Paper>

          {restaurantId > 0 &&
            <>
              <Grid item xs={12} md={4} lg={3}>
                <Paper
                  sx={{
                    p: 2,
                    display: 'flex',
                    flexDirection: 'column',
                  }}
                >
                  <Typography style={{ marginBottom: 10 }} variant='h4'>Orders</Typography>
                  <FormControl fullWidth style={{ marginTop: 2, marginBottom: 10 }}>
                    <InputLabel id="demo-simple-select-label">Status</InputLabel>
                    <Select
                      labelId="demo-simple-select-label"
                      id="demo-simple-select"
                      value={status}
                      label="Status"
                      onChange={handleStatus}
                    >
                      {orderStatus.map((e, index) => <MenuItem value={index}>{e}</MenuItem>)}
                    </Select>
                  </FormControl>
                  <Orders orders={restaurants.find(e => e.id == restaurantId)?.orders} status={status} />
                </Paper>
              </Grid>
              <Grid item xs={12}>
                <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column' }}>
                  <Typography variant="h4">Foods</Typography>
                  <FoodTable restaurantId={restaurantId} />
                </Paper>
              </Grid>
            </>
          }
          <Copyright sx={{ pt: 4 }} />
        </Container>
      </Box>
    </Box>
    </ThemeProvider >
  );
}

export default function AdminDashboardScreen() {
  return <DashboardContent />;
}

