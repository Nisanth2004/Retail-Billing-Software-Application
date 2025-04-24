import axios from 'axios';

const API_URL = 'https://billing-enef.onrender.com';

export const login = async (data) => {
  return await axios.post(`${API_URL}/login`, data);
};
