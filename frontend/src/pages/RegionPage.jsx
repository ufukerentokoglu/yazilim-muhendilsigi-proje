import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getRegions, getMenu } from '../services/api';
import { regionThemes, cityDisplayNames, categoryIcons } from '../data/regionData';
import { useCart } from '../context/CartContext';
import { useLang } from '../context/LangContext';
import { translateDish } from '../utils/dishTranslator';

function RegionPage() {
  const { t, lang } = useLang();
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
        <p>{t('region.loading')}</p>
      </div>
    );
  }

  if (!region) {
    return <div className="error">{t('region.notFound')}</div>;
  }

  const dishes = menu?.dishes || [];

  return (
    <div className="region-page" style={{ '--region-primary': theme.primary, '--region-accent': theme.accent, '--region-bg': theme.bg }}>
      <div className="region-banner" style={{ background: theme.gradient }}>
        <button className="back-btn" onClick={() => navigate('/')}>{t('region.back')}</button>
        <span className="banner-emoji">{theme.emoji}</span>
        <h2>{region.name} {t('region.suffix')}</h2>
        <p>{t(`region.desc.${regionKey}`) || theme.description}</p>
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
          {cityDisplayNames[selectedCity] || selectedCity} {t('region.menuTitle')}
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
                  <span>{t(`cat.${dish.category}`)}</span>
                </div>
                <div className="dish-body">
                  <h4>{translateDish(dish.name, lang)}</h4>
                  <p className="dish-desc">{translateDish(dish.description, lang)}</p>
                  <div className="dish-meta">
                    <span className="dish-city">📍 {dish.city}</span>
                    <span className="dish-price">₺{dish.price}</span>
                  </div>
                  <button
                    className={`btn-add-cart ${addedIndex === i ? 'added' : ''}`}
                    style={{ '--btn-color': theme.primary }}
                    onClick={() => handleAddToCart(dish, i)}
                  >
                    {addedIndex === i ? t('region.added') : t('region.addToCart')}
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
