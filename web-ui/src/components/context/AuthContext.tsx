import React, {
    createContext,
    ReactNode,
    useContext,
    useEffect,
    useState,
} from "react";

export interface AuthContextData {
    user: User | null;
    getUser: () => User | null;
    userIsAuthenticated: () => boolean;
    userLogin: (user: any) => void; // Replace `any` with the specific type for users
    userLogout: () => void;
}

interface UserJwtData {
    exp: number;
    iat: number;
    jti: string;
    iss: string;
    aud: string[];
    sub: string;
    rol: string[];
    name: string;
    preferred_username: string;
    email: string;
}

export interface User {
    data: UserJwtData;
    accessToken: string;
}

const AuthContext = createContext<AuthContextData>({
    user: null,
    getUser: () => null,
    userIsAuthenticated: () => false,
    userLogin: (user) => console.warn("no userLogin provider"),
    userLogout: () => console.warn("no userLogout provider"),
});

function AuthProvider({ children }: { children: ReactNode }) {
    const [user, setUser] = useState<User | null>(null);

    useEffect(() => {
        const storedUser = JSON.parse(localStorage.getItem("user") || "null");
        setUser(storedUser);
    }, []);

    const getUser = () => {
        return JSON.parse(localStorage.getItem("user") || "null");
    };

    const userIsAuthenticated = () => {
        let storedUserRaw = localStorage.getItem("user");
        if (!storedUserRaw) {
            return false;
        }
        const storedUser: User | null = JSON.parse(storedUserRaw);

        // if user has token expired, logout user
        if (storedUser && Date.now() > storedUser.data.exp * 1000) {
            userLogout();
            return false;
        }
        return true;
    };

    const userLogin = (user: User) => {
        localStorage.setItem("user", JSON.stringify(user));
        setUser(user);
    };

    const userLogout = () => {
        localStorage.removeItem("user");
        setUser(null);
    };

    const contextValue: AuthContextData = {
        user,
        getUser,
        userIsAuthenticated,
        userLogin,
        userLogout,
    };

    return (
        <AuthContext.Provider value={contextValue}>
            {children}
        </AuthContext.Provider>
    );
}

export default AuthContext;

export function useAuth() {
    return useContext(AuthContext);
}

export { AuthProvider };
