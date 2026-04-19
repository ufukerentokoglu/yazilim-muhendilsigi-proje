import { useEffect, useState, useRef } from 'react';
import { getOrders, getStats, updateOrderStatus, cancelOrder, archiveOrder, dayEnd, getPopularDishes } from './services/api';
import PriceManager from './PriceManager';
import { ThemeProvider, useTheme } from './context/ThemeContext';
import { LangProvider, useLang } from './context/LangContext';
import { translateDish } from './utils/dishTranslator';
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

function AppInner() {
  const { theme, toggleTheme } = useTheme();
  const { lang, toggleLang, t } = useLang();
  const tDish = (text) => translateDish(text, lang);
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
      setLoginError(t('login.error'));
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
    if (!confirm(t('card.cancelConfirm'))) return;
    await cancelOrder(orderId);
    fetchOrders();
  };

  const handleArchive = async (orderId) => {
    await archiveOrder(orderId);
    fetchOrders();
  };

  const handleDayEnd = async () => {
    if (!confirm(t('dayend.confirm'))) return;
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
      alert(t('dayend.error'));
    }
  };

  const formatTime = (dateStr) => {
    if (!dateStr) return '-';
    return new Date(dateStr).toLocaleTimeString('tr-TR', { hour: '2-digit', minute: '2-digit' });
  };

  const handlePrint = (order) => {
    const created = order.createdAt ? new Date(order.createdAt) : new Date();
    const dateStr = created.toLocaleDateString('tr-TR');
    const timeStr = created.toLocaleTimeString('tr-TR', { hour: '2-digit', minute: '2-digit' });
    const typeLabel = order.orderTypeLabel || 'Servis';
    const typeIcon = order.orderTypeIcon || '🍽️';
    const readyAt = order.estimatedReadyAt ? new Date(order.estimatedReadyAt).toLocaleTimeString('tr-TR', { hour: '2-digit', minute: '2-digit' }) : '-';

    const itemsHtml = order.lines.map(l => `
      <tr>
        <td>${tDish(l.dishName)}<div class="meta">${l.region} · ${l.city}</div></td>
        <td class="c">x${l.quantity}</td>
        <td class="r">₺${l.unitPrice.toFixed(2)}</td>
        <td class="r">₺${l.lineTotal.toFixed(2)}</td>
      </tr>
    `).join('');

    const html = `<!doctype html><html><head><meta charset="utf-8"><title>Fiş #${order.orderId}</title>
<style>
  * { box-sizing: border-box; }
  body { font-family: 'Courier New', monospace; margin: 0; padding: 16px; color: #000; }
  .receipt { max-width: 320px; margin: 0 auto; }
  .center { text-align: center; }
  .title { font-size: 18px; font-weight: 700; margin-bottom: 4px; }
  .sub { font-size: 12px; margin-bottom: 8px; }
  hr { border: none; border-top: 1px dashed #000; margin: 10px 0; }
  .row { display: flex; justify-content: space-between; font-size: 12px; margin: 3px 0; }
  .type-box { text-align: center; font-size: 16px; font-weight: 700; padding: 8px; border: 2px solid #000; margin: 8px 0; letter-spacing: 2px; }
  table { width: 100%; border-collapse: collapse; font-size: 12px; }
  th, td { padding: 4px 2px; vertical-align: top; }
  th { border-bottom: 1px solid #000; text-align: left; }
  th.c, td.c { text-align: center; }
  th.r, td.r { text-align: right; }
  td .meta { font-size: 10px; color: #555; }
  .total-row { font-size: 15px; font-weight: 700; margin-top: 8px; }
  .note { font-size: 11px; font-style: italic; margin: 6px 0; padding: 6px; border: 1px dashed #000; }
  .foot { text-align: center; font-size: 11px; margin-top: 12px; }
  @media print { body { padding: 0; } .no-print { display: none; } }
</style></head><body>
<div class="receipt">
  <div class="center">
    <div class="title">TÜRKİYE YÖRESEL LEZZETLER</div>
    <div class="sub">Yöresel Yemek Siparişi</div>
  </div>
  <hr/>
  <div class="row"><span>Fiş No</span><strong>#${order.orderId}</strong></div>
  <div class="row"><span>Tarih</span><span>${dateStr} ${timeStr}</span></div>
  <div class="row"><span>Müşteri</span><strong>${order.customerName}</strong></div>
  ${order.tableNumber ? `<div class="row"><span>Masa</span><strong>${order.tableNumber}</strong></div>` : ''}
  <div class="type-box">${typeIcon} ${typeLabel.toUpperCase()}</div>
  ${order.orderNote ? `<div class="note">📝 ${order.orderNote}</div>` : ''}
  <hr/>
  <table>
    <thead><tr><th>Ürün</th><th class="c">Adet</th><th class="r">Birim</th><th class="r">Tutar</th></tr></thead>
    <tbody>${itemsHtml}</tbody>
  </table>
  <hr/>
  <div class="row total-row"><span>TOPLAM</span><span>₺${order.totalAmount.toFixed(2)}</span></div>
  <div class="row"><span>Hazırlık Süresi</span><span>${order.estimatedPrepTime} dk</span></div>
  <div class="row"><span>Hazır Olma Saati</span><span>${readyAt}</span></div>
  <hr/>
  <div class="foot">
    Afiyet olsun! 🍽️<br/>
    Sipariş takibi: /track?id=${order.orderId}
  </div>
</div>
<script>window.onload = () => { window.print(); };</script>
</body></html>`;

    const w = window.open('', '_blank', 'width=420,height=700');
    if (!w) { alert('Popup engellendi. Yazdırma için popup izni verin.'); return; }
    w.document.open();
    w.document.write(html);
    w.document.close();
  };

  // Login ekranı
  if (!isLoggedIn) {
    return (
      <div className="login-page">
        <form className="login-box" onSubmit={handleLogin}>
          <div style={{ display: 'flex', gap: 8, justifyContent: 'center', marginBottom: 12 }}>
            <button type="button" onClick={toggleTheme} style={{ width: 'auto', padding: '6px 12px', background: 'rgba(255,255,255,0.1)', fontSize: 14 }}>
              {theme === 'dark' ? '🌞' : '🌙'}
            </button>
            <button type="button" onClick={toggleLang} style={{ width: 'auto', padding: '6px 12px', background: 'rgba(255,255,255,0.1)', fontSize: 14 }}>
              🌐 {lang.toUpperCase()}
            </button>
          </div>
          <h2>{t('login.title')}</h2>
          <p>{t('login.subtitle')}</p>
          <input
            type="text"
            placeholder={t('login.username')}
            value={username}
            onChange={e => setUsername(e.target.value)}
          />
          <input
            type="password"
            placeholder={t('login.password')}
            value={password}
            onChange={e => setPassword(e.target.value)}
          />
          {loginError && <div className="login-error">{loginError}</div>}
          <button type="submit">{t('login.submit')}</button>
        </form>
      </div>
    );
  }

  if (loading) {
    return (
      <div className="loading">
        <div className="spinner"></div>
        <p>{t('loading')}</p>
      </div>
    );
  }

  return (
    <div className="admin">
      <header className="admin-header">
        <div className="admin-actions">
          <button onClick={toggleTheme} title="Theme">
            {theme === 'dark' ? '🌞' : '🌙'}
          </button>
          <button onClick={toggleLang} title="Language">
            🌐 {lang.toUpperCase()}
          </button>
        </div>
        <div className="admin-title">
          <h1>{t('header.title')}</h1>
          <span className="admin-badge">{t('header.badge')}</span>
        </div>
        <div className="admin-stats">
          <div className="stat-box">
            <span className="stat-number">{stats.total}</span>
            <span className="stat-label">{t('header.total')}</span>
          </div>
          <div className="stat-box stat-active">
            <span className="stat-number">{stats.active}</span>
            <span className="stat-label">{t('header.active')}</span>
          </div>
          <div className="stat-box stat-cancelled">
            <span className="stat-number">{stats.cancelled}</span>
            <span className="stat-label">{t('header.cancelled')}</span>
          </div>
          <div className="stat-box stat-revenue">
            <span className="stat-number">₺{stats.revenue.toFixed(0)}</span>
            <span className="stat-label">{t('header.revenue')}</span>
          </div>
          <button className="btn-popular" onClick={() => setShowPopular(!showPopular)}>
            {t('header.popular')}
          </button>
          <button className="btn-prices" onClick={() => setShowPrices(true)}>
            {t('header.prices')}
          </button>
          <button className="btn-day-end" onClick={handleDayEnd}>
            {t('header.dayend')}
          </button>
        </div>
      </header>

      {/* Popüler Yemekler */}
      {showPopular && popular.length > 0 && (
        <div className="popular-section">
          <h3>{t('popular.title')}</h3>
          <div className="popular-list">
            {popular.map((dish, i) => (
              <div key={i} className="popular-item">
                <span className="popular-rank">#{i + 1}</span>
                <span className="popular-icon">{categoryIcons[dish.category]}</span>
                <span className="popular-name">{tDish(dish.name)}</span>
                <span className="popular-region">{dish.region}</span>
                <span className="popular-count">{dish.count} {t('popular.orders')}</span>
              </div>
            ))}
          </div>
        </div>
      )}

      <main className="admin-main">
        {orders.length === 0 ? (
          <div className="empty-state">
            <span className="empty-icon">📭</span>
            <h2>{t('empty.title')}</h2>
            <p>{t('empty.desc')}</p>
          </div>
        ) : (
          <div className="orders-grid">
            {orders.map(order => {
              const currentIdx = statusFlow.indexOf(order.status);
              const isDelivered = order.status === 'Teslim Edildi';
              const isCancelled = order.status === 'İptal Edildi';
              const isActive = !isDelivered && !isCancelled;

              const typeClass = order.orderType === 'TAKEAWAY' ? 'is-takeaway' : 'is-dinein';

              return (
                <div key={order.orderId} className={`order-card ${typeClass} ${isDelivered ? 'delivered' : ''} ${isCancelled ? 'cancelled' : ''}`}>
                  {/* Tip şeridi (her zaman görünür) */}
                  <div
                    className="type-strip"
                    style={{ background: order.orderTypeColor || '#2980b9' }}
                  >
                    <span className="type-strip-icon">{order.orderTypeIcon || '🍽️'}</span>
                    <span className="type-strip-label">
                      {t(`ordertype.${order.orderType || 'DINE_IN'}`).toUpperCase()}
                    </span>
                    {order.tableNumber && order.orderType !== 'TAKEAWAY' && (
                      <span className="type-strip-meta">{t('card.table')} {order.tableNumber}</span>
                    )}
                    {order.orderType === 'TAKEAWAY' && (
                      <span className="type-strip-meta">{t('card.takeawayNote')}</span>
                    )}
                  </div>

                  {/* Header */}
                  <div className="card-top">
                    <div className="card-id">
                      <span className="order-num">#{order.orderId}</span>
                      <span className="order-time">{formatTime(order.createdAt)}</span>
                    </div>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '8px', flexWrap: 'wrap' }}>
                      <div className="status-badge" style={{ background: statusColors[order.status] || '#666' }}>
                        {statusIcons[order.status] || '•'} {t(`status.${order.status}`)}
                      </div>
                    </div>
                  </div>

                  {/* Müşteri */}
                  <div className="card-customer">
                    <span className="customer-icon">👤</span>
                    <span className="customer-name">{order.customerName}</span>
                  </div>

                  {/* Paketleme açıklaması */}
                  {order.packagingDescription && (
                    <div className={`packaging-info ${order.orderType === 'TAKEAWAY' ? 'takeaway' : 'dinein'}`}>
                      <span>{order.orderTypeIcon}</span>
                      <span>{order.packagingDescription}</span>
                    </div>
                  )}

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
                        <span className="item-name">{tDish(line.dishName)}</span>
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
                        <span className="info-label">{t('card.total')}</span>
                        <span className="info-value total">₺{order.totalAmount.toFixed(2)}</span>
                      </div>
                      <div className="info-item">
                        <span className="info-label">{t('card.prep')}</span>
                        <span className="info-value">{order.estimatedPrepTime} {t('unit.min')}</span>
                      </div>
                      <div className="info-item">
                        <span className="info-label">{t('card.ready')}</span>
                        <span className="info-value">{formatTime(order.estimatedReadyAt)}</span>
                      </div>
                    </div>

                    {!isCancelled && (
                      <div className="progress-bar">
                        {statusFlow.map((s, i) => (
                          <div key={s} className={`progress-step ${i <= currentIdx ? 'done' : ''}`}>
                            <div className="dot" style={i <= currentIdx ? { background: statusColors[order.status] } : {}}></div>
                            <span>{t(`status.${s}`)}</span>
                          </div>
                        ))}
                      </div>
                    )}

                    <div className="card-actions">
                      <button className="btn-print" onClick={() => handlePrint(order)} title={t('card.print')}>
                        {t('card.print')}
                      </button>
                      {isActive && (
                        <>
                          <button
                            className="btn-status"
                            style={{ background: statusColors[statusFlow[currentIdx + 1]] }}
                            onClick={() => handleStatusChange(order.orderId, order.status)}
                          >
                            → {t(`status.${statusFlow[currentIdx + 1]}`)}
                          </button>
                          <button className="btn-cancel" onClick={() => handleCancel(order.orderId)}>
                            {t('card.cancel')}
                          </button>
                        </>
                      )}
                      {(isDelivered || isCancelled) && (
                        <button className="btn-delete" onClick={() => handleArchive(order.orderId)}>
                          {t('card.remove')}
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

function App() {
  return (
    <ThemeProvider>
      <LangProvider>
        <AppInner />
      </LangProvider>
    </ThemeProvider>
  );
}

export default App;
