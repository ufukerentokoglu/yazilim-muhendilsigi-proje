import { useEffect, useState } from 'react';
import { getOrders, getStats, updateOrderStatus, cancelOrder, archiveOrder, dayEnd } from './services/api';
import './App.css';

const statusFlow = ['Bekliyor', 'Hazırlanıyor', 'Hazır', 'Teslim Edildi'];
const statusColors = {
  'Bekliyor': '#f39c12',
  'Hazırlanıyor': '#3498db',
  'Hazır': '#27ae60',
  'Teslim Edildi': '#95a5a6',
  'İptal Edildi': '#e74c3c'
};
const statusIcons = {
  'Bekliyor': '⏳',
  'Hazırlanıyor': '👨‍🍳',
  'Hazır': '✅',
  'Teslim Edildi': '🍽️',
  'İptal Edildi': '❌'
};
const categoryIcons = {
  'Ana Yemek': '🍖',
  'Başlangıç': '🥣',
  'Tatlı': '🍰',
  'İçecek': '🥤'
};

function App() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [stats, setStats] = useState({ total: 0, active: 0, cancelled: 0, revenue: 0 });

  const fetchOrders = () => {
    Promise.all([getOrders(), getStats()]).then(([ordersRes, statsRes]) => {
      setOrders(ordersRes.data);
      setStats(statsRes.data);
      setLoading(false);
    }).catch(() => setLoading(false));
  };

  useEffect(() => {
    fetchOrders();
    const interval = setInterval(fetchOrders, 4000);
    return () => clearInterval(interval);
  }, []);

  const handleStatusChange = async (orderId, currentStatus) => {
    const idx = statusFlow.indexOf(currentStatus);
    if (idx < statusFlow.length - 1) {
      await updateOrderStatus(orderId, statusFlow[idx + 1]);
      fetchOrders();
    }
  };

  const handleCancel = async (orderId) => {
    if (!confirm('Bu siparişi iptal etmek istediğine emin misin?')) return;
    await cancelOrder(orderId);
    fetchOrders();
  };

  const handleArchive = async (orderId) => {
    await archiveOrder(orderId);
    fetchOrders();
  };

  const handleDayEnd = async () => {
    if (!confirm('Gün sonu raporu oluşturulacak ve tüm siparişler sıfırlanacak. Emin misin?')) return;
    try {
      const res = await dayEnd();
      const blob = new Blob([res.data], { type: 'text/plain;charset=utf-8' });
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      const today = new Date();
      const fileName = `${String(today.getDate()).padStart(2,'0')}-${String(today.getMonth()+1).padStart(2,'0')}-${today.getFullYear()}.txt`;
      a.href = url;
      a.download = fileName;
      a.click();
      window.URL.revokeObjectURL(url);
      fetchOrders();
    } catch {
      alert('Gün sonu raporu oluşturulurken hata oluştu');
    }
  };

  const formatTime = (dateStr) => {
    if (!dateStr) return '-';
    return new Date(dateStr).toLocaleTimeString('tr-TR', { hour: '2-digit', minute: '2-digit' });
  };

  if (loading) {
    return (
      <div className="loading">
        <div className="spinner"></div>
        <p>Yükleniyor...</p>
      </div>
    );
  }

  return (
    <div className="admin">
      <header className="admin-header">
        <div className="admin-title">
          <h1>🍽️ Admin Paneli</h1>
          <span className="admin-badge">Sipariş Yönetimi</span>
        </div>
        <div className="admin-stats">
          <div className="stat-box">
            <span className="stat-number">{stats.total}</span>
            <span className="stat-label">Toplam Sipariş</span>
          </div>
          <div className="stat-box stat-active">
            <span className="stat-number">{stats.active}</span>
            <span className="stat-label">Aktif</span>
          </div>
          <div className="stat-box stat-cancelled">
            <span className="stat-number">{stats.cancelled}</span>
            <span className="stat-label">İptal</span>
          </div>
          <div className="stat-box stat-revenue">
            <span className="stat-number">₺{stats.revenue.toFixed(0)}</span>
            <span className="stat-label">Ciro</span>
          </div>
          <button className="btn-day-end" onClick={handleDayEnd}>
            📊 Gün Sonu
          </button>
        </div>
      </header>

      <main className="admin-main">
        {orders.length === 0 ? (
          <div className="empty-state">
            <span className="empty-icon">📭</span>
            <h2>Henüz sipariş yok</h2>
            <p>Müşteri arayüzünden (localhost:5173) sipariş geldiğinde burada görünecek</p>
          </div>
        ) : (
          <div className="orders-grid">
            {orders.map(order => {
              const currentIdx = statusFlow.indexOf(order.status);
              const isDelivered = order.status === 'Teslim Edildi';
              const isCancelled = order.status === 'İptal Edildi';
              const isActive = !isDelivered && !isCancelled;

              return (
                <div key={order.orderId} className={`order-card ${isDelivered ? 'delivered' : ''} ${isCancelled ? 'cancelled' : ''}`}>
                  {/* Header */}
                  <div className="card-top">
                    <div className="card-id">
                      <span className="order-num">#{order.orderId}</span>
                      <span className="order-time">{formatTime(order.createdAt)}</span>
                    </div>
                    <div className="status-badge" style={{ background: statusColors[order.status] || '#666' }}>
                      {statusIcons[order.status] || '•'} {order.status}
                    </div>
                  </div>

                  {/* Müşteri */}
                  <div className="card-customer">
                    <span className="customer-icon">👤</span>
                    <span className="customer-name">{order.customerName}</span>
                  </div>

                  {/* Ürünler */}
                  <div className="card-items">
                    {order.lines.map((line, i) => (
                      <div key={i} className="item-row">
                        <span className="item-cat">{categoryIcons[line.category]}</span>
                        <span className="item-name">{line.dishName}</span>
                        <span className="item-region">{line.region}</span>
                        <span className="item-qty">x{line.quantity}</span>
                        <span className="item-price">₺{line.lineTotal.toFixed(0)}</span>
                      </div>
                    ))}
                  </div>

                  {/* Alt bilgi */}
                  <div className="card-bottom">
                    <div className="card-info-row">
                      <div className="info-item">
                        <span className="info-label">Toplam</span>
                        <span className="info-value total">₺{order.totalAmount.toFixed(2)}</span>
                      </div>
                      <div className="info-item">
                        <span className="info-label">Süre</span>
                        <span className="info-value">{order.estimatedPrepTime} dk</span>
                      </div>
                      <div className="info-item">
                        <span className="info-label">Hazır</span>
                        <span className="info-value">{formatTime(order.estimatedReadyAt)}</span>
                      </div>
                    </div>

                    {/* Progress - sadece aktif ve teslim edilenler için */}
                    {!isCancelled && (
                      <div className="progress-bar">
                        {statusFlow.map((s, i) => (
                          <div key={s} className={`progress-step ${i <= currentIdx ? 'done' : ''}`}>
                            <div className="dot" style={i <= currentIdx ? { background: statusColors[order.status] } : {}}></div>
                            <span>{s}</span>
                          </div>
                        ))}
                      </div>
                    )}

                    {/* Butonlar */}
                    <div className="card-actions">
                      {isActive && (
                        <>
                          <button
                            className="btn-status"
                            style={{ background: statusColors[statusFlow[currentIdx + 1]] }}
                            onClick={() => handleStatusChange(order.orderId, order.status)}
                          >
                            → {statusFlow[currentIdx + 1]}
                          </button>
                          <button className="btn-cancel" onClick={() => handleCancel(order.orderId)}>
                            ✕ İptal
                          </button>
                        </>
                      )}
                      {isDelivered && (
                        <button className="btn-delete" onClick={() => handleArchive(order.orderId)}>
                          🗑️ Kaldır
                        </button>
                      )}
                      {isCancelled && (
                        <button className="btn-delete" onClick={() => handleArchive(order.orderId)}>
                          🗑️ Kaldır
                        </button>
                      )}
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
        )}
      </main>
    </div>
  );
}

export default App;
