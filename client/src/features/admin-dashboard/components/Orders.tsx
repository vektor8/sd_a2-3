import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import { Box, Button, Collapse, IconButton, TableContainer, TableFooter, Typography } from '@mui/material';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import { Order } from '../../../model/order';
import { orderStatus } from '../screens/dashboard_screen';
import { customAxios } from '../../../api/api';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../stores/store';
import { Admin } from '../../../model/user';
import { login } from '../../../stores/user/slice';

function Row(props: { row: Order }) {
  const { row } = props;
  const dispatch = useDispatch();
  const user: Admin = useSelector<RootState>(state => state.user.userData) as Admin;
  const [rowData, setRowData] = React.useState(row);
  const [open, setOpen] = React.useState(false);
  const advance = () => {
    customAxios.put(`/api/order/${rowData.id}`, {
      orderStatus: orderStatus[orderStatus.indexOf(rowData.orderStatus) + 1].toUpperCase()
    }).then((response) => {
      customAxios.get(`/api/user`)
        .then((resp) =>
          dispatch(login(resp.data))).catch((err) => console.log(err))
    }).catch((err) => console.log(err));
  }

  const decline = () => {
    customAxios.put(`/api/order/${rowData.id}`, {
      orderStatus: "DECLINED"
    }).then((response) => {
      customAxios.get(`/api/user`)
        .then((resp) =>
          dispatch(login(resp.data))).catch((err) => console.log(err))
    }).catch((err) => console.log(err));
  }
  return (
    <React.Fragment>
      <TableRow sx={{ '& > *': { borderBottom: 'unset' } }}>
        <TableCell>
          <IconButton
            aria-label="expand row"
            size="small"
            onClick={() => setOpen(!open)}
          >
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell component="th" scope="row">
          {rowData.id}
        </TableCell>
        <TableCell align="right">{rowData.orderStatus}</TableCell>
        <TableCell align="right">
          {rowData.orderStatus != "DECLINED" && rowData.orderStatus != "DELIVERED" &&
            <Button onClick={() => { advance(); }} variant="contained" color="success">
              Advance
            </Button>}
          {rowData.orderStatus == "PENDING" && <Button onClick={() => { decline(); }} variant="contained" color="error">
            Decline
          </Button>}
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box sx={{ margin: 1 }}>
              <Typography variant="h6" gutterBottom component="div">
                Ordered foods
              </Typography>
              <Table size="small" aria-label="purchases">
                <TableHead>
                  <TableRow>
                    <TableCell>Name</TableCell>
                    <TableCell>Price</TableCell>
                    <TableCell align="right">Category</TableCell>
                    <TableCell align="right">Quantity</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {rowData.orderedFoods.map((orderedFood) => (
                    <TableRow key={orderedFood.id.toString()}>
                      <TableCell component="th" scope="row">
                        {orderedFood.food.name}
                      </TableCell>
                      <TableCell>{orderedFood.food.price}</TableCell>
                      <TableCell align="right">{orderedFood.food.category}</TableCell>
                      <TableCell align="right">
                        {orderedFood.quantity}
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
                <TableFooter>
                  <TableRow>
                    <TableCell align="left" colSpan={3}>Total</TableCell>
                    <TableCell align='right'>
                      {row.orderedFoods.reduce((acc, curr) => acc + (curr.quantity * curr.food.price), 0)} Lei
                    </TableCell>
                  </TableRow>
                </TableFooter>
              </Table>
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}

export default function Orders(props: { orders: [Order] | undefined, status: number }) {
  const { orders, status } = props;
  if (!orders)
    return <></>
  const statusString = orderStatus[status].toUpperCase();
  let displayOrders = statusString == "-" ? orders : orders.filter(e => e.orderStatus == statusString);
  if (displayOrders == null || displayOrders.length < 1)
    return <Typography variant='h6'>You have no {statusString} orders for the selected restaurant</Typography>

  return (
    <TableContainer>
      <Table aria-label="collapsible table">
        <TableHead>
          <TableRow>
            <TableCell />
            <TableCell>Id</TableCell>
            <TableCell align="right">Order status</TableCell>
            <TableCell align="right">Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {displayOrders?.map((row) => (
            <Row key={row.id.toString()} row={row} />
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}