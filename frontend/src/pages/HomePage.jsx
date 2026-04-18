import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getRegions } from '../services/api';
import { regionThemes } from '../data/regionData';

function HomePage() {
  const [regions, setRegions] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    getRegions()
      .then(res => {
        setRegions(res.data);
        setLoading(false);
      })
      .catch(() => setLoading(false));
  }, []);

  if (loading) {
    return (
      <div className="loading">
        <div className="spinner"></div>
        <p>Bölgeler yükleniyor...</p>
      </div>
    );
  }

  return (
    <div className="home-page">
      <div className="hero-section">
        <p>Türkiye'nin 7 coğrafi bölgesinden birini seçerek yöresel lezzetleri keşfedin</p>
      </div>

      <div className="regions-grid">
        {regions.map(region => {
          const theme = regionThemes[region.key] || {};
          return (
            <div
              key={region.key}
              className="region-card"
              style={{
                '--card-primary': theme.primary,
                '--card-accent': theme.accent,
                '--card-bg': theme.bg,
                '--card-gradient': theme.gradient
              }}
              onClick={() => navigate(`/region/${region.key}`)}
            >
              <div className="region-card-header" style={{ background: theme.gradient }}>
                <span className="region-emoji">{theme.emoji}</span>
                <h3>{region.name}</h3>
              </div>
              <div className="region-card-body">
                <p className="region-desc">{theme.description}</p>
                <div className="region-cities">
                  {region.cities.map(city => (
                    <span key={city} className="city-tag">{city}</span>
                  ))}
                </div>
              </div>
              <div className="region-card-footer">
                <span>Menüyü Keşfet →</span>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default HomePage;
