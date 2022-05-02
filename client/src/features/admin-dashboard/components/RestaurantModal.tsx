import {
    Box,
    Button,
    Modal,
    TextField,
    Typography,
} from "@mui/material";

type Props = {
    isOpen: boolean;
    onClose: () => void;
    onSubmit: any;
};

const RestaurantModal = (props: Props) => {
    return (
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
                <Typography sx={{ marginBottom: 2 }} variant="h6">
                    Add a restaurant
                </Typography>

                <TextField sx={{ marginBottom: 2 }}
                    margin="normal"
                    required
                    fullWidth
                    id="name"
                    label="Restaurant name"
                    name="name"
                    autoFocus />
                <TextField
                    sx={{ marginBottom: 2 }}
                    margin="normal"
                    required
                    fullWidth
                    id="location"
                    label="Restaurant location"
                    name="location"
                    autoComplete="location"
                    autoFocus
                />
                <Button
                    sx={{ marginTop: 2, textTransform: "none" }}
                    variant="contained"
                    type="submit"
                >
                    Submit
                </Button>
            </Box>
        </Modal >
    );
};

export default RestaurantModal;