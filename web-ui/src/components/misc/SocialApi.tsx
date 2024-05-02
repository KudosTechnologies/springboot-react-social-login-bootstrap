import axios from "axios";
import { config } from "../../Constants";
import { User } from "../context/AuthContext";
import { parseJwt } from "./Helpers";

export const socialApi = {
    authenticate,
    signup,
};

function authenticate(username: string, password: string) {
    return instance.post(
        "/auth/authenticate",
        { username, password },
        {
            headers: { "Content-type": "application/json" },
        },
    );
}

function signup(user: { email: string; password: string }) {
    return instance.post("/auth/signup", user, {
        headers: { "Content-type": "application/json" },
    });
}

const instance = axios.create({
    baseURL: config.url.API_BASE_URL,
});

instance.interceptors.request.use(
    function (config) {
        // If token is expired, redirect user to login
        const authorization = config.headers.Authorization;
        if (authorization && typeof authorization === "string") {
            const token = authorization.split(" ")[1];
            const data = parseJwt(token);
            if (Date.now() > data.exp * 1000) {
                window.location.href = "/login";
            }
        }
        return config;
    },
    function (error) {
        return Promise.reject(error);
    },
);

// function bearerAuth(user: User) {
//     return `Bearer ${user.accessToken}`;
// }
