import { useContext } from "react";
import { Container, Divider, Header, Segment } from "semantic-ui-react";
import AuthContext from "../context/AuthContext";

function Home() {
    return (
        <Container>
            <Segment padded="very" textAlign="center">
                <Header as="h1">Welcome Home!</Header>
                <Divider />
            </Segment>
        </Container>
    );
}

export default Home;
