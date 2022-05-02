import {
    Box,
    Button,
    createTheme,
    FormControl,
    InputLabel,
    MenuItem,
    Modal,
    Select,
    TextField,
    ThemeProvider,
    Typography,
} from "@mui/material";
import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { Restaurant } from "../../../model/restaurant";
import { Admin } from "../../../model/user";
import { RootState } from "../../../stores/store";

type Props = {
    isOpen: boolean;
    onClose: () => void;
    onSubmit: any;
};
const foodCategories = ["Breakfast", "Lunch", "Dessert", "Beverages"];

const mdTheme = createTheme({
    palette: {
        mode: 'dark',
    },
});

const FoodModal = (props: Props) => {
    const [category, setCategory] = React.useState(0);
    return (
        <ThemeProvider theme={mdTheme}>
            <Modal
                style={{
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                }}
                open={props.isOpen}
                onClose={props.onClose}
            >
                <Box component="form" noValidate onSubmit={props.onSubmit}
                    sx={{
                        maxWidth: 500,
                        backgroundColor: "grey",
                        borderRadius: 2 / 1,
                        padding: 4,
                    }}
                >
                    <Typography sx={{ marginBottom: 2 }} variant="h6" component="h2">
                        Add a food
                    </Typography>

                    <TextField sx={{ marginBottom: 2 }}
                        margin="normal"
                        required
                        fullWidth
                        id="name"
                        label="Food name"
                        name="name"
                        autoFocus />
                    <TextField
                        sx={{ marginBottom: 2 }}
                        margin="normal"
                        required
                        fullWidth
                        id="description"
                        label="description"
                        name="description"
                        autoComplete="description"
                        autoFocus
                    />
                    <TextField
                        sx={{ marginBottom: 2 }}
                        margin="normal"
                        required
                        fullWidth
                        id="price"
                        label="price"
                        name="price"
                        type="number"
                        autoComplete="price"
                        autoFocus
                    />
                    <FormControl>
                        <InputLabel id="demo-simple-select-label">Category</InputLabel>
                        <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={category}
                            label="category"
                            name="category"
                            onChange={(e: any) => setCategory(e.target.value)}
                        >
                            <MenuItem value={-1}>-</MenuItem>
                            {foodCategories.map((e, index) => <MenuItem value={index}>{e}</MenuItem>)}
                        </Select>
                    </FormControl>
                    <br></br>
                    <Button
                        sx={{ marginTop: 2, textTransform: "none" }}
                        variant="contained"
                        type="submit"
                    >
                        Submit
                    </Button>
                </Box>
            </Modal >
        </ThemeProvider>
    );
};

export default FoodModal;