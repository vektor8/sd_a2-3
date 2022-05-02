import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import { Admin } from '../../../model/user';
import { useSelector } from 'react-redux';
import { RootState } from '../../../stores/store';
import { Restaurant } from '../../../model/restaurant';
import { TableContainer, Typography } from '@mui/material';

export default function FoodTable(props: { restaurantId: number }) {
    const { restaurantId } = props;
    const user: Admin = useSelector<RootState>(state => state.user.userData) as Admin;
    const restaurants: [Restaurant] = user.restaurants;
    const restaurant = restaurants.find(e => e.id == restaurantId);
    if (restaurant?.foods == null || restaurant.foods.length < 1)
        return <Typography variant='h6'>You have no foods for the selected restaurant</Typography>
    return (
        <TableContainer>
            <Table aria-label="collapsible table">
                <TableHead>
                    <TableRow>
                        <TableCell>Id</TableCell>
                        <TableCell align="right">Name</TableCell>
                        <TableCell align="right">Description</TableCell>
                        <TableCell align="right">Price</TableCell>
                        <TableCell align="right">Category</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {restaurant?.foods.map((row) => (
                        <TableRow>
                            <TableCell>{row.id}</TableCell>
                            <TableCell align="right">{row.name}</TableCell>
                            <TableCell align="right">{row.description}</TableCell>
                            <TableCell align="right">{row.price}</TableCell>
                            <TableCell align="right">{row.category}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}