import axios from "axios";

const API_URL = 'https://billing-enef.onrender.com';
const AUTH_HEADER = {
  headers: {
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  }
};

export const fetchDashboardData = async () => {
  return await axios.get(`${API_URL}/dashboard`, AUTH_HEADER);
};
