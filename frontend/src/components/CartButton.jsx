import { useCart } from '../context/CartContext';

function CartButton() {
  const { totalItems, totalAmount, setIsCartOpen } = useCart();

  return (
    <button className="cart-fab" onClick={() => setIsCartOpen(true)}>
      <span className="cart-fab-icon">🛒</span>
      {totalItems > 0 && (
        <>
          <span className="cart-fab-badge">{totalItems}</span>
          <span className="cart-fab-total">₺{totalAmount.toFixed(0)}</span>
        </>
      )}
    </button>
  );
}

export default CartButton;
