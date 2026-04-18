import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { CartProvider } from './context/CartContext';
import HomePage from './pages/HomePage';
import RegionPage from './pages/RegionPage';
import CartButton from './components/CartButton';
import CartSidebar from './components/CartSidebar';
import './App.css';

function App() {
  return (
    <BrowserRouter>
      <CartProvider>
        <div className="app">
          <header className="app-header">
            <a href="/" className="logo">
              <span className="logo-icon">🇹🇷</span>
              <h1>Türkiye Yöresel Lezzetler</h1>
            </a>
            <p className="subtitle">Abstract Factory Design Pattern ile 7 Bölge, 28 Şehir, Eşsiz Tatlar</p>
          </header>
          <main>
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/region/:regionKey" element={<RegionPage />} />
            </Routes>
          </main>
          <footer className="app-footer">
            <p>Yazılım Mühendisliği Projesi — Abstract Factory Pattern</p>
          </footer>
          <CartButton />
          <CartSidebar />
        </div>
      </CartProvider>
    </BrowserRouter>
  );
}

export default App;
