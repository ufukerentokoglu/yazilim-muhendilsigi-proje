import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import { CartProvider } from './context/CartContext';
import { ThemeProvider, useTheme } from './context/ThemeContext';
import { LangProvider, useLang } from './context/LangContext';
import HomePage from './pages/HomePage';
import RegionPage from './pages/RegionPage';
import TrackOrderPage from './pages/TrackOrderPage';
import CartButton from './components/CartButton';
import CartSidebar from './components/CartSidebar';
import './App.css';

function Header() {
  const { theme, toggleTheme } = useTheme();
  const { lang, toggleLang, t } = useLang();

  return (
    <header className="app-header">
      <div className="header-top">
        <div className="header-actions">
          <button onClick={toggleTheme} title={theme === 'light' ? 'Dark' : 'Light'}>
            {theme === 'light' ? '🌙' : '🌞'}
          </button>
          <button onClick={toggleLang} title="Dil / Language">
            🌐 {lang.toUpperCase()}
          </button>
        </div>
        <Link to="/" className="logo">
          <span className="logo-icon">🍽️</span>
          <h1>{t('app.title')}</h1>
        </Link>
        <Link to="/track" className="track-link">
          {t('app.trackOrder')}
        </Link>
      </div>
      <p className="subtitle">{t('app.subtitle')}</p>
    </header>
  );
}

function Footer() {
  const { t } = useLang();
  return (
    <footer className="app-footer">
      <p>{t('app.footer')}</p>
    </footer>
  );
}

function App() {
  return (
    <ThemeProvider>
      <LangProvider>
        <BrowserRouter>
          <CartProvider>
            <div className="app">
              <Header />
              <main>
                <Routes>
                  <Route path="/" element={<HomePage />} />
                  <Route path="/region/:regionKey" element={<RegionPage />} />
                  <Route path="/track" element={<TrackOrderPage />} />
                </Routes>
              </main>
              <Footer />
              <CartButton />
              <CartSidebar />
            </div>
          </CartProvider>
        </BrowserRouter>
      </LangProvider>
    </ThemeProvider>
  );
}

export default App;
