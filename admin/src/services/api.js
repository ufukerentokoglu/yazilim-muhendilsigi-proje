import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api'
});

export const getOrders = () => api.get('/orders');
export const getStats = () => api.get('/orders/stats');
export const updateOrderStatus = (id, status) => api.patch(`/orders/${id}/status`, { status });
export const cancelOrder = (id) => api.patch(`/orders/${id}/cancel`);
export const archiveOrder = (id) => api.delete(`/orders/${id}`);
export const dayEnd = () => api.post('/orders/day-end', null, { responseType: 'blob' });
