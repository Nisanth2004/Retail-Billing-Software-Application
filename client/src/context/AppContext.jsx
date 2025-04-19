import { createContext, useEffect, useState } from "react";
import { fetchCategories } from "../Service/CategoryService";
import { fetchItems } from "../Service/ItemService";


export const AppContext = createContext(); 

export const AppContextProvider = ({ children }) => {
    const [categories, setCategories] = useState([]);
    const[auth,setAuth]=useState({token:null,role:null})

    const[itemsData,setItemsData]=useState([])

    useEffect(() => {
         async function loadData() {
            try {
                const response = await fetchCategories();
               const itemResponse= await fetchItems();
                setCategories(response.data);
                setItemsData(itemResponse.data)
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
        setAuthData,
        itemsData,
        setItemsData

    }

    return (
        <AppContext.Provider value={contextvalue}>
            {children}
        </AppContext.Provider>
    );
};


