import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getOrders, updateOrderStatus } from '../services/api';
import { categoryIcons } from '../data/regionData';

const statusFlow = ['Bekliyor', 'Hazırlanıyor', 'Hazır', 'Teslim Edildi'];
const statusColors = {
  'Bekliyor': '#f39c12',
  'Hazırlanıyor': '#3498db',
  'Hazır': '#27ae60',
  'Teslim Edildi': '#95a5a6'
};
const statusIcons = {
  'Bekliyor': '⏳',
  'Hazırlanıyor': '👨‍🍳',
  'Hazır': '✅',
  'Teslim Edildi': '🍽️'
};

function OrdersPage() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  const fetchOrders = () => {
    getOrders().then(res => {
      setOrders(res.data);
      setLoading(false);
    }).catch(() => setLoading(false));
  };

  useEffect(() => {
    fetchOrders();
    const interval = setInterval(fetchOrders, 5000);
    return () => clearInterval(interval);
  }, []);

  const handleStatusChange = async (orderId, currentStatus) => {
    const currentIndex = statusFlow.indexOf(currentStatus);
    if (currentIndex < statusFlow.length - 1) {
      const nextStatus = statusFlow[currentIndex + 1];
      await updateOrderStatus(orderId, nextStatus);
      fetchOrders();
    }
  };

  const formatTime = (dateStr) => {
    if (!dateStr) return '-';
    const d = new Date(dateStr);
    return d.toLocaleTimeString('tr-TR', { hour: '2-digit', minute: '2-digit' });
  };

  if (loading) {
    return (
      <div className="loading">
        <div className="spinner"></div>
        <p>Siparişler yükleniyor...</p>
      </div>
    );
  }

  return (
    <div className="orders-page">
      <div className="orders-banner">
        <button className="back-btn" onClick={() => navigate('/')}>← Ana Sayfa</button>
        <h2>📋 Sipariş Takip Paneli</h2>
        <p>Tüm siparişleri buradan takip edebilirsiniz</p>
      </div>

      {orders.length === 0 ? (
        <div className="orders-empty">
          <span className="empty-icon-large">📭</span>
          <h3>Henüz sipariş yok</h3>
          <p>Menüden yemek seçip sipariş verin</p>
          <button className="btn-go-menu" onClick={() => navigate('/')}>Menüye Git</button>
        </div>
      ) : (
        <div className="orders-list">
          {orders.map(order => (
            <div key={order.orderId} className="order-card">
              <div className="order-card-header">
                <div className="order-card-id">
                  <span className="order-number">#{order.orderId}</span>
                  <span className="order-customer">{order.customerName}</span>
                </div>
                <div
                  className="order-status-badge"
                  style={{ background: statusColors[order.status] }}
                >
                  {statusIcons[order.status]} {order.status}
                </div>
              </div>

              <div className="order-card-body">
                <div className="order-items-list">
                  {order.lines.map((line, i) => (
                    <div key={i} className="order-item-row">
                      <span className="order-item-icon">{categoryIcons[line.category]}</span>
                      <span className="order-item-name">{line.dishName}</span>
                      <span className="order-item-region">{line.region} · {line.city}</span>
                      <span className="order-item-qty">{line.quantity}x</span>
                      <span className="order-item-price">₺{line.lineTotal.toFixed(0)}</span>
                    </div>
                  ))}
                </div>

                <div className="order-card-meta">
                  <div className="meta-item">
                    <span className="meta-label">Toplam</span>
                    <span className="meta-value meta-total">₺{order.totalAmount.toFixed(2)}</span>
                  </div>
                  <div className="meta-item">
                    <span className="meta-label">Tahmini Süre</span>
                    <span className="meta-value">{order.estimatedPrepTime} dk</span>
                  </div>
                  <div className="meta-item">
                    <span className="meta-label">Sipariş Saati</span>
                    <span className="meta-value">{formatTime(order.createdAt)}</span>
                  </div>
                  <div className="meta-item">
                    <span className="meta-label">Hazır Olma</span>
                    <span className="meta-value">{formatTime(order.estimatedReadyAt)}</span>
                  </div>
                </div>
              </div>

              <div className="order-card-footer">
                <div className="status-progress">
                  {statusFlow.map((s, i) => {
                    const currentIdx = statusFlow.indexOf(order.status);
                    return (
                      <div key={s} className={`status-step ${i <= currentIdx ? 'active' : ''}`}>
                        <div className="step-dot" style={i <= currentIdx ? { background: statusColors[order.status] } : {}}></div>
                        <span className="step-label">{s}</span>
                      </div>
                    );
                  })}
                </div>
                {order.status !== 'Teslim Edildi' && (
                  <button
                    className="btn-next-status"
                    style={{ background: statusColors[statusFlow[statusFlow.indexOf(order.status) + 1]] }}
                    onClick={() => handleStatusChange(order.orderId, order.status)}
                  >
                    → {statusFlow[statusFlow.indexOf(order.status) + 1]}
                  </button>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default OrdersPage;
