import { AxiosError } from "axios";
import { ChangeEvent, useState } from "react";
import { ErrorResponse, Navigate, NavLink } from "react-router-dom";
import {
    Button,
    Form,
    Grid,
    InputOnChangeData,
    Message,
    Segment,
} from "semantic-ui-react";
import { useAuth } from "../context/AuthContext";
import { handleLogError, parseJwt } from "../misc/Helpers";
import { socialApi } from "../misc/SocialApi";

interface HandleInputChangeEvent extends ChangeEvent<HTMLInputElement> {
    target: HTMLInputElement & InputOnChangeData;
}

function Signup() {
    const Auth = useAuth();
    const isLoggedIn = Auth.userIsAuthenticated();

    const [password, setPassword] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [isError, setIsError] = useState<boolean>(false);
    const [errorMessage, setErrorMessage] = useState<string>("");

    const handleInputChange = (
        e: HandleInputChangeEvent,
        { name, value }: InputOnChangeData,
    ) => {
        switch (name) {
            case "email":
                setEmail(value);
                break;
            case "password":
                setPassword(value);
                break;
            default:
                break;
        }
    };

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        if (!(email && password)) {
            setIsError(true);
            setErrorMessage("Please, inform all fields!");
            return;
        }

        const user = { email, password };

        try {
            const response = await socialApi.signup(user);
            const { accessToken } = response.data;
            const data = parseJwt(accessToken);
            const authenticatedUser = { data, accessToken };

            Auth.userLogin(authenticatedUser);

            setPassword("");
            setIsError(false);
            setErrorMessage("");
        } catch (error) {
            handleLogError(error);
            const errorResponse = error as {
                response: {
                    data: {
                        detail: string;
                        status: number;
                    };
                };
            };
            let errorMessage = "Invalid fields";
            if (errorResponse.response.data.detail) {
                errorMessage = errorResponse.response.data.detail;
            }
            setIsError(true);
            setErrorMessage(errorMessage);
        }
    };

    if (isLoggedIn) {
        return <Navigate to="/" />;
    }

    return (
        <Grid textAlign="center">
            <Grid.Column style={{ maxWidth: 450 }}>
                <Form size="large" onSubmit={handleSubmit}>
                    <Segment>
                        <Form.Input
                            fluid
                            name="email"
                            icon="at"
                            iconPosition="left"
                            placeholder="Email"
                            onChange={(e, data) =>
                                handleInputChange(
                                    e as HandleInputChangeEvent,
                                    data,
                                )
                            }
                        />
                        <Form.Input
                            fluid
                            name="password"
                            icon="lock"
                            iconPosition="left"
                            placeholder="Password"
                            type="password"
                            onChange={(e, data) =>
                                handleInputChange(
                                    e as HandleInputChangeEvent,
                                    data,
                                )
                            }
                        />
                        <Button color="black" fluid size="large">
                            Signup
                        </Button>
                    </Segment>
                </Form>
                <Message>
                    {`Already have an account? `}
                    <NavLink to="/login" color="purple">
                        Login
                    </NavLink>
                </Message>
                {isError && <Message negative>{errorMessage}</Message>}
            </Grid.Column>
        </Grid>
    );
}

export default Signup;
