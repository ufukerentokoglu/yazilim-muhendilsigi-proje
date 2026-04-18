import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api'
});

export const getRegions = () => api.get('/regions');
export const getMenu = (regionKey, city) => api.get(`/menu/${regionKey}/${city}`);
export const getDishes = (regionKey) => api.get(`/dishes/${regionKey}`);
export const createOrder = (data) => api.post('/orders', data);
export const getOrders = () => api.get('/orders');
export const getOrder = (id) => api.get(`/orders/${id}`);
export const updateOrderStatus = (id, status) => api.patch(`/orders/${id}/status`, { status });
