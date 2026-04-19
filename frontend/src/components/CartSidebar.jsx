import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCart } from '../context/CartContext';
import { useLang } from '../context/LangContext';
import { categoryIcons } from '../data/regionData';
import { createOrder } from '../services/api';
import { translateDish } from '../utils/dishTranslator';

function CartSidebar() {
  const navigate = useNavigate();
  const { t, lang } = useLang();
  const { items, removeItem, updateQuantity, clearCart, totalAmount, totalItems, isCartOpen, setIsCartOpen } = useCart();
  const [customerName, setCustomerName] = useState('');
  const [tableNumber, setTableNumber] = useState('');
  const [orderNote, setOrderNote] = useState('');
  const [orderType, setOrderType] = useState('DINE_IN');
  const [orderResult, setOrderResult] = useState(null);
  const [ordering, setOrdering] = useState(false);

  const handleOrder = async () => {
    if (!customerName.trim()) {
      alert(t('cart.needName'));
      return;
    }
    if (orderType === 'DINE_IN' && !tableNumber) {
      alert(t('cart.needTable'));
      return;
    }
    if (items.length === 0) return;

    setOrdering(true);
    try {
      const payload = {
        customerName: customerName.trim(),
        tableNumber: orderType === 'DINE_IN' && tableNumber ? parseInt(tableNumber) : null,
        orderNote: orderNote.trim() || null,
        orderType,
        items: items.map(item => ({
          dishId: item.id,
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
      alert(err.response?.data?.message || t('cart.orderError'));
    }
    setOrdering(false);
  };

  if (!isCartOpen) return null;

  return (
    <div className="cart-overlay" onClick={() => { setIsCartOpen(false); setOrderResult(null); }}>
      <div className="cart-sidebar" onClick={e => e.stopPropagation()}>
        <div className="cart-header">
          <h3>{t('cart.title')} ({totalItems})</h3>
          <button className="cart-close" onClick={() => { setIsCartOpen(false); setOrderResult(null); }}>✕</button>
        </div>

        {orderResult ? (
          <div className="order-success">
            <div className="success-icon">✓</div>
            <h3>{t('success.title')}</h3>
            <p className="order-id">{t('success.orderNo')} <strong>#{orderResult.orderId}</strong></p>
            <p className="order-customer">{orderResult.customerName}</p>
            {orderResult.orderType && (
              <div
                className="order-type-badge-success"
                style={{ background: orderResult.orderTypeColor }}
              >
                {orderResult.orderTypeIcon} {t(`ordertype.${orderResult.orderType === 'TAKEAWAY' ? 'takeaway' : 'dinein'}`)}
                {orderResult.tableNumber ? ` · ${t('track.table')} ${orderResult.tableNumber}` : ''}
              </div>
            )}

            <div className="order-lines">
              {orderResult.lines.map((line, i) => (
                <div key={i} className="order-line">
                  <span>{categoryIcons[line.category]} {translateDish(line.dishName, lang)}</span>
                  <span className="order-line-detail">
                    {line.quantity}x ₺{line.unitPrice} = <strong>₺{line.lineTotal}</strong>
                  </span>
                </div>
              ))}
            </div>

            <div className="order-total-final">
              <span>{t('success.total')}</span>
              <span>₺{orderResult.totalAmount.toFixed(2)}</span>
            </div>

            <p className="order-prep-time">{t('success.prepTime')} <strong>{orderResult.estimatedPrepTime} {t('success.minutes')}</strong></p>
            <p className="order-status">{t('success.status')} <strong>{t(`status.${orderResult.status}`)}</strong></p>

            <button className="btn-primary" onClick={() => {
              const id = orderResult.orderId;
              setOrderResult(null);
              setIsCartOpen(false);
              navigate(`/track?id=${id}`);
            }}>
              {t('success.trackBtn')}
            </button>
            <button className="btn-secondary" onClick={() => { setOrderResult(null); setIsCartOpen(false); }}>
              {t('success.backMenu')}
            </button>
          </div>
        ) : (
          <>
            <div className="cart-items">
              {items.length === 0 ? (
                <div className="cart-empty">
                  <span className="empty-icon">🛒</span>
                  <p>{t('cart.empty')}</p>
                  <p className="empty-hint">{t('cart.emptyHint')}</p>
                </div>
              ) : (
                items.map((item, index) => (
                  <div key={index} className="cart-item">
                    <div className="cart-item-info">
                      <span className="cart-item-icon">{categoryIcons[item.category]}</span>
                      <div>
                        <div className="cart-item-name">{translateDish(item.name, lang)}</div>
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
                  <span>{t('cart.total')}</span>
                  <span className="cart-total-price">₺{totalAmount.toFixed(2)}</span>
                </div>

                <div className="order-type-selector">
                  <button
                    type="button"
                    className={`order-type-btn ${orderType === 'DINE_IN' ? 'active dine-in' : ''}`}
                    onClick={() => setOrderType('DINE_IN')}
                  >
                    <span className="order-type-icon">🍽️</span>
                    <span>
                      <div className="order-type-label">{t('ordertype.dinein')}</div>
                      <div className="order-type-desc">{t('ordertype.dinein.desc')}</div>
                    </span>
                  </button>
                  <button
                    type="button"
                    className={`order-type-btn ${orderType === 'TAKEAWAY' ? 'active takeaway' : ''}`}
                    onClick={() => setOrderType('TAKEAWAY')}
                  >
                    <span className="order-type-icon">📦</span>
                    <span>
                      <div className="order-type-label">{t('ordertype.takeaway')}</div>
                      <div className="order-type-desc">{t('ordertype.takeaway.desc')}</div>
                    </span>
                  </button>
                </div>

                <input
                  type="text"
                  placeholder={t('cart.namePlaceholder')}
                  value={customerName}
                  onChange={e => setCustomerName(e.target.value)}
                  className="cart-name-input"
                />

                <div className="cart-row-inputs">
                  {orderType === 'DINE_IN' && (
                    <input
                      type="number"
                      placeholder={t('cart.tablePlaceholder')}
                      value={tableNumber}
                      onChange={e => setTableNumber(e.target.value)}
                      className="cart-table-input"
                    />
                  )}
                  <input
                    type="text"
                    placeholder={t('cart.notePlaceholder')}
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
                  {ordering ? t('cart.ordering') : t('cart.order')}
                </button>

                <button className="btn-clear" onClick={clearCart}>
                  {t('cart.clear')}
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
