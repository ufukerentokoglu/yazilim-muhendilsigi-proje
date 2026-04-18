import { createContext, useContext, useState } from 'react';

const CartContext = createContext();

export function CartProvider({ children }) {
  const [items, setItems] = useState([]);
  const [isCartOpen, setIsCartOpen] = useState(false);

  const addItem = (dish) => {
    setItems(prev => {
      const existing = prev.find(
        i => i.name === dish.name && i.city === dish.city && i.category === dish.category
      );
      if (existing) {
        return prev.map(i =>
          i === existing ? { ...i, quantity: i.quantity + 1 } : i
        );
      }
      return [...prev, { ...dish, quantity: 1 }];
    });
  };

  const removeItem = (index) => {
    setItems(prev => prev.filter((_, i) => i !== index));
  };

  const updateQuantity = (index, quantity) => {
    if (quantity <= 0) {
      removeItem(index);
      return;
    }
    setItems(prev => prev.map((item, i) => i === index ? { ...item, quantity } : item));
  };

  const clearCart = () => setItems([]);

  const totalAmount = items.reduce((sum, item) => sum + item.price * item.quantity, 0);
  const totalItems = items.reduce((sum, item) => sum + item.quantity, 0);

  return (
    <CartContext.Provider value={{
      items, addItem, removeItem, updateQuantity, clearCart,
      totalAmount, totalItems,
      isCartOpen, setIsCartOpen
    }}>
      {children}
    </CartContext.Provider>
  );
}

export const useCart = () => useContext(CartContext);
