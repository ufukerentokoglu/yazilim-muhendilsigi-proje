import { useEffect, useState, useRef } from 'react';
import { getOrders, getStats, updateOrderStatus, cancelOrder, archiveOrder, dayEnd, getPopularDishes } from './services/api';
import PriceManager from './PriceManager';
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
  const [popular, setPopular] = useState([]);
  const [showPopular, setShowPopular] = useState(false);
  const [showPrices, setShowPrices] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [loginError, setLoginError] = useState('');
  const prevOrderCount = useRef(0);

  const playNotification = () => {
    try {
      const ctx = new (window.AudioContext || window.webkitAudioContext)();
      const osc = ctx.createOscillator();
      const gain = ctx.createGain();
      osc.connect(gain);
      gain.connect(ctx.destination);
      osc.frequency.value = 800;
      gain.gain.value = 0.3;
      osc.start();
      osc.frequency.setValueAtTime(800, ctx.currentTime);
      osc.frequency.setValueAtTime(1000, ctx.currentTime + 0.1);
      osc.frequency.setValueAtTime(800, ctx.currentTime + 0.2);
      gain.gain.setValueAtTime(0.3, ctx.currentTime + 0.3);
      gain.gain.exponentialRampToValueAtTime(0.001, ctx.currentTime + 0.5);
      osc.stop(ctx.currentTime + 0.5);
    } catch {}
  };

  const fetchOrders = () => {
    Promise.all([getOrders(), getStats()]).then(([ordersRes, statsRes]) => {
      const newOrders = ordersRes.data;

      if (prevOrderCount.current > 0 && newOrders.length > prevOrderCount.current) {
        playNotification();
      }
      prevOrderCount.current = newOrders.length;

      setOrders(newOrders);
      setStats(statsRes.data);
      setLoading(false);
    }).catch(() => setLoading(false));
  };

  const fetchPopular = () => {
    getPopularDishes().then(res => setPopular(res.data)).catch(() => {});
  };

  useEffect(() => {
    if (!isLoggedIn) return;
    fetchOrders();
    fetchPopular();
    const interval = setInterval(() => { fetchOrders(); fetchPopular(); }, 4000);
    return () => clearInterval(interval);
  }, [isLoggedIn]);

  const handleLogin = (e) => {
    e.preventDefault();
    if (username === 'admin' && password === 'admin123') {
      setIsLoggedIn(true);
      setLoginError('');
    } else {
      setLoginError('Kullanıcı adı veya şifre hatalı');
    }
  };

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
      fetchPopular();
    } catch {
      alert('Gün sonu raporu oluşturulurken hata oluştu');
    }
  };

  const formatTime = (dateStr) => {
    if (!dateStr) return '-';
    return new Date(dateStr).toLocaleTimeString('tr-TR', { hour: '2-digit', minute: '2-digit' });
  };

  // Login ekranı
  if (!isLoggedIn) {
    return (
      <div className="login-page">
        <form className="login-box" onSubmit={handleLogin}>
          <h2>🍽️ Admin Paneli</h2>
          <p>Yönetim paneline erişmek için giriş yapın</p>
          <input
            type="text"
            placeholder="Kullanıcı adı"
            value={username}
            onChange={e => setUsername(e.target.value)}
          />
          <input
            type="password"
            placeholder="Şifre"
            value={password}
            onChange={e => setPassword(e.target.value)}
          />
          {loginError && <div className="login-error">{loginError}</div>}
          <button type="submit">Giriş Yap</button>
        </form>
      </div>
    );
  }

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
          <button className="btn-popular" onClick={() => setShowPopular(!showPopular)}>
            🏆 Popüler
          </button>
          <button className="btn-prices" onClick={() => setShowPrices(true)}>
            💰 Fiyatlar
          </button>
          <button className="btn-day-end" onClick={handleDayEnd}>
            📊 Gün Sonu
          </button>
        </div>
      </header>

      {/* Popüler Yemekler */}
      {showPopular && popular.length > 0 && (
        <div className="popular-section">
          <h3>🏆 En Çok Sipariş Edilen Yemekler</h3>
          <div className="popular-list">
            {popular.map((dish, i) => (
              <div key={i} className="popular-item">
                <span className="popular-rank">#{i + 1}</span>
                <span className="popular-icon">{categoryIcons[dish.category]}</span>
                <span className="popular-name">{dish.name}</span>
                <span className="popular-region">{dish.region}</span>
                <span className="popular-count">{dish.count} sipariş</span>
              </div>
            ))}
          </div>
        </div>
      )}

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
                    <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                      {order.tableNumber && (
                        <span className="table-badge">Masa {order.tableNumber}</span>
                      )}
                      <div className="status-badge" style={{ background: statusColors[order.status] || '#666' }}>
                        {statusIcons[order.status] || '•'} {order.status}
                      </div>
                    </div>
                  </div>

                  {/* Müşteri */}
                  <div className="card-customer">
                    <span className="customer-icon">👤</span>
                    <span className="customer-name">{order.customerName}</span>
                  </div>

                  {/* Sipariş notu */}
                  {order.orderNote && (
                    <div className="card-note">
                      📝 {order.orderNote}
                    </div>
                  )}

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
                      {(isDelivered || isCancelled) && (
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

      {showPrices && <PriceManager onClose={() => setShowPrices(false)} />}
    </div>
  );
}

export default App;
