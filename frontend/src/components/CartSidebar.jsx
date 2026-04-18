import { useState } from 'react';
import { useCart } from '../context/CartContext';
import { categoryIcons } from '../data/regionData';
import { createOrder } from '../services/api';

function CartSidebar() {
  const { items, removeItem, updateQuantity, clearCart, totalAmount, totalItems, isCartOpen, setIsCartOpen } = useCart();
  const [customerName, setCustomerName] = useState('');
  const [tableNumber, setTableNumber] = useState('');
  const [orderNote, setOrderNote] = useState('');
  const [orderResult, setOrderResult] = useState(null);
  const [ordering, setOrdering] = useState(false);

  const handleOrder = async () => {
    if (!customerName.trim()) {
      alert('Lütfen isminizi girin');
      return;
    }
    if (items.length === 0) return;

    setOrdering(true);
    try {
      const payload = {
        customerName: customerName.trim(),
        tableNumber: tableNumber ? parseInt(tableNumber) : null,
        orderNote: orderNote.trim() || null,
        items: items.map(item => ({
          regionKey: item.regionKey,
          city: item.cityKey,
          category: item.category,
          quantity: item.quantity
        }))
      };

      const res = await createOrder(payload);
      setOrderResult(res.data);
      clearCart();
    } catch (err) {
      alert('Sipariş oluşturulurken hata oluştu');
    }
    setOrdering(false);
  };

  if (!isCartOpen) return null;

  return (
    <div className="cart-overlay" onClick={() => { setIsCartOpen(false); setOrderResult(null); }}>
      <div className="cart-sidebar" onClick={e => e.stopPropagation()}>
        <div className="cart-header">
          <h3>Sepetim ({totalItems})</h3>
          <button className="cart-close" onClick={() => { setIsCartOpen(false); setOrderResult(null); }}>✕</button>
        </div>

        {orderResult ? (
          <div className="order-success">
            <div className="success-icon">✓</div>
            <h3>Sipariş Onaylandı!</h3>
            <p className="order-id">Sipariş No: <strong>#{orderResult.orderId}</strong></p>
            <p className="order-customer">{orderResult.customerName}</p>

            <div className="order-lines">
              {orderResult.lines.map((line, i) => (
                <div key={i} className="order-line">
                  <span>{categoryIcons[line.category]} {line.dishName}</span>
                  <span className="order-line-detail">
                    {line.quantity}x ₺{line.unitPrice} = <strong>₺{line.lineTotal}</strong>
                  </span>
                </div>
              ))}
            </div>

            <div className="order-total-final">
              <span>Genel Toplam</span>
              <span>₺{orderResult.totalAmount.toFixed(2)}</span>
            </div>

            <p className="order-prep-time">Tahmini Süre: <strong>{orderResult.estimatedPrepTime} dakika</strong></p>
            <p className="order-status">Durum: <strong>{orderResult.status}</strong></p>

            <button className="btn-primary" onClick={() => {
              const id = orderResult.orderId;
              setOrderResult(null);
              setIsCartOpen(false);
              window.location.href = `/track?id=${id}`;
            }}>
              Siparişimi Takip Et
            </button>
            <button className="btn-secondary" onClick={() => { setOrderResult(null); setIsCartOpen(false); }}>
              Menüye Dön
            </button>
          </div>
        ) : (
          <>
            <div className="cart-items">
              {items.length === 0 ? (
                <div className="cart-empty">
                  <span className="empty-icon">🛒</span>
                  <p>Sepetiniz boş</p>
                  <p className="empty-hint">Bölge sayfalarından yemek ekleyin</p>
                </div>
              ) : (
                items.map((item, index) => (
                  <div key={index} className="cart-item">
                    <div className="cart-item-info">
                      <span className="cart-item-icon">{categoryIcons[item.category]}</span>
                      <div>
                        <div className="cart-item-name">{item.name}</div>
                        <div className="cart-item-meta">{item.region} · {item.city}</div>
                      </div>
                    </div>
                    <div className="cart-item-actions">
                      <div className="quantity-control">
                        <button onClick={() => updateQuantity(index, item.quantity - 1)}>−</button>
                        <span>{item.quantity}</span>
                        <button onClick={() => updateQuantity(index, item.quantity + 1)}>+</button>
                      </div>
                      <span className="cart-item-price">₺{(item.price * item.quantity).toFixed(0)}</span>
                      <button className="cart-item-remove" onClick={() => removeItem(index)}>🗑</button>
                    </div>
                  </div>
                ))
              )}
            </div>

            {items.length > 0 && (
              <div className="cart-footer">
                <div className="cart-total">
                  <span>Toplam</span>
                  <span className="cart-total-price">₺{totalAmount.toFixed(2)}</span>
                </div>

                <input
                  type="text"
                  placeholder="Adınızı girin..."
                  value={customerName}
                  onChange={e => setCustomerName(e.target.value)}
                  className="cart-name-input"
                />

                <div className="cart-row-inputs">
                  <input
                    type="number"
                    placeholder="Masa No"
                    value={tableNumber}
                    onChange={e => setTableNumber(e.target.value)}
                    className="cart-table-input"
                  />
                  <input
                    type="text"
                    placeholder="Sipariş notu (opsiyonel)"
                    value={orderNote}
                    onChange={e => setOrderNote(e.target.value)}
                    className="cart-note-input"
                  />
                </div>

                <button
                  className="btn-order"
                  onClick={handleOrder}
                  disabled={ordering}
                >
                  {ordering ? 'Gönderiliyor...' : 'Sipariş Ver'}
                </button>

                <button className="btn-clear" onClick={clearCart}>
                  Sepeti Temizle
                </button>
              </div>
            )}
          </>
        )}
      </div>
    </div>
  );
}

export default CartSidebar;
