import { useState, useEffect, useRef } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { getOrder } from '../services/api';
import { categoryIcons } from '../data/regionData';

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
const statusFlow = ['Bekliyor', 'Hazırlanıyor', 'Hazır', 'Teslim Edildi'];

function TrackOrderPage() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const [orderId, setOrderId] = useState(searchParams.get('id') || '');
  const [order, setOrder] = useState(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [searched, setSearched] = useState(false);
  const [statusChanged, setStatusChanged] = useState(false);
  const intervalRef = useRef(null);
  const prevStatusRef = useRef(null);

  const fetchOrder = async (id) => {
    try {
      const res = await getOrder(id);
      const newOrder = res.data;

      if (prevStatusRef.current && prevStatusRef.current !== newOrder.status) {
        setStatusChanged(true);
        setTimeout(() => setStatusChanged(false), 2000);
      }
      prevStatusRef.current = newOrder.status;

      setOrder(newOrder);
      setError('');
    } catch {
      // sipariş arşivlenmiş olabilir
    }
  };

  const handleSearch = async (e) => {
    e?.preventDefault();
    if (!orderId.trim()) return;

    setLoading(true);
    setError('');
    setOrder(null);
    setSearched(true);
    prevStatusRef.current = null;

    // Eski polling'i temizle
    if (intervalRef.current) clearInterval(intervalRef.current);

    try {
      const res = await getOrder(orderId.trim());
      setOrder(res.data);
      prevStatusRef.current = res.data.status;

      // Polling başlat — her 3 saniyede güncelle
      intervalRef.current = setInterval(() => fetchOrder(orderId.trim()), 3000);
    } catch {
      setError('Sipariş bulunamadı. Lütfen sipariş numaranızı kontrol edin.');
    }
    setLoading(false);
  };

  // URL'den id geliyorsa otomatik ara
  useEffect(() => {
    if (searchParams.get('id')) {
      handleSearch();
    }
    return () => {
      if (intervalRef.current) clearInterval(intervalRef.current);
    };
  }, []);

  const formatTime = (dateStr) => {
    if (!dateStr) return '-';
    return new Date(dateStr).toLocaleTimeString('tr-TR', { hour: '2-digit', minute: '2-digit' });
  };

  const isCancelled = order?.status === 'İptal Edildi';
  const isDelivered = order?.status === 'Teslim Edildi';
  const currentIdx = order ? statusFlow.indexOf(order.status) : -1;

  const getElapsedMinutes = () => {
    if (!order?.createdAt || !order?.deliveredAt) return null;
    const start = new Date(order.createdAt).getTime();
    const end = new Date(order.deliveredAt).getTime();
    return Math.round((end - start) / 60000);
  };

  return (
    <div className="track-page">
      <div className="track-banner">
        <button className="back-btn" onClick={() => navigate('/')}>← Ana Sayfa</button>
        <h2>📦 Sipariş Takip</h2>
        <p>Sipariş numaranızı girerek durumunuzu öğrenin</p>
      </div>

      <div className="track-content">
        <form className="track-form" onSubmit={handleSearch}>
          <div className="track-input-group">
            <span className="track-hash">#</span>
            <input
              type="number"
              placeholder="Sipariş numarası"
              value={orderId}
              onChange={e => setOrderId(e.target.value)}
              className="track-input"
            />
          </div>
          <button type="submit" className="track-btn" disabled={loading}>
            {loading ? 'Aranıyor...' : 'Sorgula'}
          </button>
        </form>

        {error && (
          <div className="track-error">
            <span>❌</span> {error}
          </div>
        )}

        {searched && !loading && !error && !order && (
          <div className="track-error">
            <span>❌</span> Sipariş bulunamadı
          </div>
        )}

        {order && (
          <div className={`track-result ${statusChanged ? 'track-pulse' : ''}`}>
            {/* Canlı gösterge */}
            <div className="track-live">
              <span className="live-dot"></span> Canlı takip aktif
            </div>

            {/* Üst bilgi */}
            <div className="track-header">
              <div>
                <span className="track-order-num">Sipariş #{order.orderId}</span>
                <span className="track-customer">{order.customerName}</span>
              </div>
              <div className="track-status-badge" style={{ background: statusColors[order.status] }}>
                {statusIcons[order.status]} {order.status}
              </div>
            </div>

            {/* Durum çubuğu */}
            {!isCancelled && (
              <div className="track-progress">
                {statusFlow.map((s, i) => (
                  <div key={s} className={`track-step ${i <= currentIdx ? 'active' : ''}`}>
                    <div className="track-dot" style={i <= currentIdx ? { background: statusColors[order.status] } : {}}></div>
                    <span>{s}</span>
                  </div>
                ))}
              </div>
            )}

            {isCancelled && (
              <div className="track-cancelled-msg">
                Bu sipariş iptal edilmiştir.
              </div>
            )}

            {/* Ürünler */}
            <div className="track-items">
              <h4>Sipariş Detayı</h4>
              {order.lines.map((line, i) => (
                <div key={i} className="track-item">
                  <span className="track-item-icon">{categoryIcons[line.category]}</span>
                  <div className="track-item-info">
                    <span className="track-item-name">{line.dishName}</span>
                    <span className="track-item-meta">{line.region} · {line.city}</span>
                  </div>
                  <span className="track-item-qty">x{line.quantity}</span>
                  <span className="track-item-price">₺{line.lineTotal.toFixed(0)}</span>
                </div>
              ))}
            </div>

            {/* Alt bilgi */}
            <div className="track-footer">
              <div className="track-info-grid">
                <div className="track-info-item">
                  <span className="track-info-label">Toplam Tutar</span>
                  <span className="track-info-value track-total">₺{order.totalAmount.toFixed(2)}</span>
                </div>
                {isDelivered ? (
                  <>
                    <div className="track-info-item">
                      <span className="track-info-label">Geçen Süre</span>
                      <span className="track-info-value">{getElapsedMinutes()} dk</span>
                    </div>
                    <div className="track-info-item">
                      <span className="track-info-label">Sipariş</span>
                      <span className="track-info-value">{formatTime(order.createdAt)}</span>
                    </div>
                    <div className="track-info-item">
                      <span className="track-info-label">Teslim</span>
                      <span className="track-info-value">{formatTime(order.deliveredAt)}</span>
                    </div>
                  </>
                ) : (
                  <>
                    <div className="track-info-item">
                      <span className="track-info-label">Tahmini Süre</span>
                      <span className="track-info-value">{order.estimatedPrepTime} dk</span>
                    </div>
                    <div className="track-info-item">
                      <span className="track-info-label">Sipariş Saati</span>
                      <span className="track-info-value">{formatTime(order.createdAt)}</span>
                    </div>
                    <div className="track-info-item">
                      <span className="track-info-label">Hazır Olma</span>
                      <span className="track-info-value">{formatTime(order.estimatedReadyAt)}</span>
                    </div>
                  </>
                )}
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default TrackOrderPage;
