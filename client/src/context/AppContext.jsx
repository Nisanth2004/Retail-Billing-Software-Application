import { createContext, useEffect, useState } from "react";
import { fetchCategories } from "../Service/CategoryService";


export const AppContext = createContext(); 

export const AppContextProvider = ({ children }) => {
    const [categories, setCategories] = useState([]);

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

    return (
        <AppContext.Provider value={{ categories, setCategories }}>
            {children}
        </AppContext.Provider>
    );
};


