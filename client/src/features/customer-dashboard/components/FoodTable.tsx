import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import { Restaurant2 } from '../../../model/restaurant';
import { Button, ButtonGroup, TableContainer, Typography } from '@mui/material';
import { Food } from '../../../model/food';
import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { addFoodToCart } from '../../../stores/bill/slice';


function Row(props: { food: Food }) {
    const { food } = props;
    const [quantity, setQuantity] = useState(0);
    const dispatch = useDispatch();
    return <>
        <TableRow>
            <TableCell>{food.id}</TableCell>
            <TableCell align="right">{food.name}</TableCell>
            <TableCell align="right">{food.description}</TableCell>
            <TableCell align="right">{food.price}</TableCell>
            <TableCell align="right">{food.category}</TableCell>
            <TableCell>
                <ButtonGroup size="small" aria-label="small outlined button group">
                    <Button onClick={() => {
                        dispatch(addFoodToCart(
                            { foodId: food.id, quantity: quantity + 1 }
                        ));
                        setQuantity(quantity + 1);
                    }}>+</Button>
                    {<Button disabled>{quantity}</Button>}
                    {quantity > 0 && <Button onClick={() => {
                        dispatch(addFoodToCart(
                            { foodId: food.id, quantity: quantity - 1 }
                        )); setQuantity(quantity - 1);
                    }}>-</Button>}
                </ButtonGroup></TableCell>
        </TableRow>
    </>
}
export default function FoodTable(props: { restaurant: Restaurant2 }) {
    const { restaurant } = props;

    if (restaurant.foods == null || restaurant.foods.length < 1)
        return <Typography variant='h6'> This restaurant has no foods available at the moment</Typography>
        
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
                        <TableCell>Actions</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {restaurant.foods.map((row) => (
                        <Row food={row}></Row>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}