import { useState } from "react";
import { Navigate, NavLink } from "react-router-dom";
import { Button, Divider, Form, Grid, Icon, Menu, Message, Segment } from "semantic-ui-react";
import { useAuth, User } from "../context/AuthContext";
import { getSocialLoginUrl, handleLogError, parseJwt } from "../misc/Helpers";
import { socialApi } from "../misc/SocialApi";

function Login() {
    const Auth = useAuth();
    const isLoggedIn = Auth.userIsAuthenticated();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [isError, setIsError] = useState(false);

    const handleInputChange = (e: any) => {
        const { name, value } = e.target;
        if (name === "email") {
            setEmail(value);
        }
        if (name === "password") {
            setPassword(value);
        }
    };

    const handleSubmit = async (e: any) => {
        e.preventDefault();

        if (!(email && password)) {
            setIsError(true);
            return;
        }
        try {
            const response = await socialApi.authenticate(email, password);
            const { accessToken } = response.data;
            const data = parseJwt(accessToken);
            const authenticatedUser: User = { data, accessToken };
            Auth.userLogin(authenticatedUser);
        } catch (error) {
            handleLogError(error);
            setIsError(true);
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
                            autoFocus
                            name="email"
                            icon="email"
                            iconPosition="left"
                            placeholder="Email address"
                            onChange={handleInputChange}
                        />
                        <Form.Input
                            fluid
                            name="password"
                            icon="lock"
                            iconPosition="left"
                            placeholder="Password"
                            type="password"
                            onChange={handleInputChange}
                        />
                        <Button color="black" fluid size="large">
                            Login
                        </Button>
                    </Segment>
                </Form>
                {isError && (
                    <Message negative>The Email or password provided are incorrect!</Message>
                )}

                <Divider horizontal>or connect with</Divider>

                <Menu compact icon="labeled">
                    <Menu.Item name="google" href={getSocialLoginUrl("google")}>
                        <Icon name="google" />
                        Google
                    </Menu.Item>
                    <Menu.Item name="keycloak" href={getSocialLoginUrl("keycloak")}>
                        <Icon name="key" />
                        Keycloak
                    </Menu.Item>
                </Menu>
            </Grid.Column>
        </Grid>
    );
}

export default Login;
