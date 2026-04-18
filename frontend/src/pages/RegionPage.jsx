import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getRegions, getMenu } from '../services/api';
import { regionThemes, cityDisplayNames, categoryIcons } from '../data/regionData';
import { useCart } from '../context/CartContext';

function RegionPage() {
  const { regionKey } = useParams();
  const navigate = useNavigate();
  const { addItem } = useCart();
  const [region, setRegion] = useState(null);
  const [selectedCity, setSelectedCity] = useState(null);
  const [menu, setMenu] = useState(null);
  const [loading, setLoading] = useState(true);
  const [addedIndex, setAddedIndex] = useState(null);

  const theme = regionThemes[regionKey] || {};

  useEffect(() => {
    getRegions().then(res => {
      const found = res.data.find(r => r.key === regionKey);
      if (found) {
        setRegion(found);
        setSelectedCity(found.cities[0]);
      }
      setLoading(false);
    });
  }, [regionKey]);

  useEffect(() => {
    if (selectedCity) {
      setMenu(null);
      getMenu(regionKey, selectedCity).then(res => {
        setMenu(res.data);
      });
    }
  }, [regionKey, selectedCity]);

  const handleAddToCart = (dish, index) => {
    addItem({
      ...dish,
      regionKey: regionKey,
      cityKey: selectedCity
    });
    setAddedIndex(index);
    setTimeout(() => setAddedIndex(null), 800);
  };

  if (loading) {
    return (
      <div className="loading">
        <div className="spinner"></div>
        <p>Yükleniyor...</p>
      </div>
    );
  }

  if (!region) {
    return <div className="error">Bölge bulunamadı</div>;
  }

  const dishes = menu ? [menu.mainDish, menu.appetizer, menu.dessert, menu.beverage] : [];

  return (
    <div className="region-page" style={{ '--region-primary': theme.primary, '--region-accent': theme.accent, '--region-bg': theme.bg }}>
      <div className="region-banner" style={{ background: theme.gradient }}>
        <button className="back-btn" onClick={() => navigate('/')}>← Geri</button>
        <span className="banner-emoji">{theme.emoji}</span>
        <h2>{region.name} Bölgesi</h2>
        <p>{theme.description}</p>
      </div>

      <div className="city-selector">
        {region.cities.map(city => (
          <button
            key={city}
            className={`city-btn ${selectedCity === city ? 'active' : ''}`}
            style={selectedCity === city ? { background: theme.primary, color: '#fff' } : {}}
            onClick={() => setSelectedCity(city)}
          >
            {cityDisplayNames[city] || city}
          </button>
        ))}
      </div>

      <div className="menu-section">
        <h3>
          {cityDisplayNames[selectedCity] || selectedCity} Yöresel Menüsü
        </h3>

        {!menu ? (
          <div className="loading">
            <div className="spinner"></div>
          </div>
        ) : (
          <div className="dishes-grid">
            {dishes.map((dish, i) => (
              <div key={i} className="dish-card" style={{ animationDelay: `${i * 0.1}s` }}>
                <div className="dish-category" style={{ background: theme.primary }}>
                  <span className="dish-icon">{categoryIcons[dish.category]}</span>
                  <span>{dish.category}</span>
                </div>
                <div className="dish-body">
                  <h4>{dish.name}</h4>
                  <p className="dish-desc">{dish.description}</p>
                  <div className="dish-meta">
                    <span className="dish-city">📍 {dish.city}</span>
                    <span className="dish-price">₺{dish.price}</span>
                  </div>
                  <button
                    className={`btn-add-cart ${addedIndex === i ? 'added' : ''}`}
                    style={{ '--btn-color': theme.primary }}
                    onClick={() => handleAddToCart(dish, i)}
                  >
                    {addedIndex === i ? '✓ Eklendi' : '+ Sepete Ekle'}
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default RegionPage;
