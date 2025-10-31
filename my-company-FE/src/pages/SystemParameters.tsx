import React, { useEffect, useState } from "react";
import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    TextField,
    Select,
    MenuItem,
    Button,
    Typography,
    Snackbar,
    Alert
} from "@mui/material";
import type { Parameter } from "../contexts/authentication/model/Parameter";
import { useAuthentication } from "../contexts/authentication/AuthenticationContext";
import { STATUSES } from "../constants/Enumerations";
import BaseApiAxios from "../helpers/BaseApiAxios";
import { SERVICE_PATHS } from "../constants/Paths";

const SystemParameters: React.FC = () => {
    const { parameters } = useAuthentication();
    const [params, setParams] = useState<Parameter[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const handleChange = (code: string, newValue: string) => {
        setParams((prev) =>
            prev.map((p) => (p.code === code ? { ...p, value: newValue } : p))
        );
    };

    useEffect(() => {
        setParams(parameters);
    }, [parameters]);

    const handleSave = async () => {
        setLoading(true);
        try {
            await BaseApiAxios.put(SERVICE_PATHS.API_V1_PARAMETER_UPDATE_ALL_PARAMETER, {
                parameterList: params
            });
            setSuccess("Parameters updated successfully!");
            // 3 saniye sonra toast kapanacak
            setTimeout(() => setSuccess(""), 3000);
        } catch (err: any) {
            const msg = err?.response?.data?.message || "An error occurred!";
            setError(msg);

            setTimeout(() => setError(""), 3000);
        } finally {
            setLoading(false);
        }
    };

    return (
        <Paper sx={{ padding: 4 }}>
            <Typography variant="h5" gutterBottom>
                System Parameters
            </Typography>

            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Code</TableCell>
                            <TableCell>Type</TableCell>
                            <TableCell>Value</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {params.map((p) => (
                            <TableRow key={p.code}>
                                <TableCell>{p.code}</TableCell>
                                <TableCell>{p.type}</TableCell>
                                <TableCell>
                                    {p.type === "NUMERIC" ? (
                                        <TextField
                                            type="number"
                                            value={p.value}
                                            onChange={(e) => handleChange(p.code, e.target.value)}
                                            variant="outlined"
                                            size="small"
                                        />
                                    ) : (
                                        <Select
                                            value={p.value}
                                            onChange={(e) => handleChange(p.code, e.target.value)}
                                            size="small"
                                        >
                                            {Object.values(STATUSES).map((s) => (
                                                <MenuItem key={s} value={s}>
                                                    {s}
                                                </MenuItem>
                                            ))}
                                        </Select>
                                    )}
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            <Button
                variant="contained"
                color="primary"
                sx={{ mt: 2 }}
                onClick={handleSave}
                disabled={loading}
            >
                {loading ? "Saving..." : "Save Changes"}
            </Button>

            {/* Hata Toast */}
            <Snackbar open={!!error} autoHideDuration={3000} onClose={() => setError("")} anchorOrigin={{ vertical: "top", horizontal: "right" }}>
                <Alert severity="error" sx={{ width: "100%" }}>
                    {error}
                </Alert>
            </Snackbar>

            {/* Başarılı Toast */}
            <Snackbar open={!!success} autoHideDuration={3000} onClose={() => setSuccess("")} anchorOrigin={{ vertical: "top", horizontal: "right" }}>
                <Alert severity="success" sx={{ width: "100%" }}>
                    {success}
                </Alert>
            </Snackbar>
        </Paper>
    );
};

export default SystemParameters;
