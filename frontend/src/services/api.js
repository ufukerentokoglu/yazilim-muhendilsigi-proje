import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api'
});

export const getRegions = () => api.get('/regions');
export const getMenu = (regionKey, city) => api.get(`/menu/${regionKey}/${city}`);
export const getDishes = (regionKey) => api.get(`/dishes/${regionKey}`);
