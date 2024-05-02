import {
    BrowserRouter as Router,
    Routes,
    Route,
    Navigate,
} from "react-router-dom";
import { AuthProvider } from "./components/context/AuthContext";
import Home from "./components/home/Home";
import Login from "./components/home/Login";
import OAuth2Redirect from "./components/home/OAuth2Redirect";
import Signup from "./components/home/Signup";
import Navbar from "./components/misc/Navbar";

function App() {
    return (
        <AuthProvider>
            <Router>
                <Navbar />
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/signup" element={<Signup />} />

                    <Route
                        path="/oauth2/redirect"
                        element={<OAuth2Redirect />}
                    />
                </Routes>
            </Router>
        </AuthProvider>
    );
}

export default App;
