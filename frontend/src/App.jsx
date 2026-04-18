import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import { CartProvider } from './context/CartContext';
import HomePage from './pages/HomePage';
import RegionPage from './pages/RegionPage';
import TrackOrderPage from './pages/TrackOrderPage';
import CartButton from './components/CartButton';
import CartSidebar from './components/CartSidebar';
import './App.css';

function App() {
  return (
    <BrowserRouter>
      <CartProvider>
        <div className="app">
          <header className="app-header">
            <div className="header-top">
              <a href="/" className="logo">
                <span className="logo-icon">🍽️</span>
                <h1>Türkiye Yöresel Lezzetler</h1>
              </a>
              <Link to="/track" className="track-link">
                📦 Sipariş Takip
              </Link>
            </div>
            <p className="subtitle">7 Bölgenin Eşsiz Lezzetleri</p>
          </header>
          <main>
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/region/:regionKey" element={<RegionPage />} />
              <Route path="/track" element={<TrackOrderPage />} />
            </Routes>
          </main>
          <footer className="app-footer">
            <p>Türkiye'nin Yöresel Lezzetleri</p>
          </footer>
          <CartButton />
          <CartSidebar />
        </div>
      </CartProvider>
    </BrowserRouter>
  );
}

export default App;
