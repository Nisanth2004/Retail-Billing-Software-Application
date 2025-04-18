import { createContext, useEffect, useState } from "react";
import { fetchCategories } from "../Service/CategoryService";


export const AppContext = createContext(); 

export const AppContextProvider = ({ children }) => {
    const [categories, setCategories] = useState([]);
    const[auth,setAuth]=useState({token:null,role:null})

    useEffect(() => {
         async function loadData() {
            try {
                const response = await fetchCategories();
                console.log(response.data)
                setCategories(response.data);
            } catch (err) {
                console.error("Error fetching categories:", err);
            }
        }

        loadData();
    }, []);

    const setAuthData=(token,role)=>{
        setAuth({token,role})
    }

    const contextvalue={
        categories,
        setCategories,
        auth,
        setAuthData

    }

    return (
        <AppContext.Provider value={contextvalue}>
            {children}
        </AppContext.Provider>
    );
};


