import { useEffect, useState } from 'react';
import { getRegions, getDishPrices, updatePrice } from './services/api';

const categoryIcons = {
  'Ana Yemek': '🍖',
  'Başlangıç': '🥣',
  'Tatlı': '🍰',
  'İçecek': '🥤'
};

function PriceManager({ onClose }) {
  const [regions, setRegions] = useState([]);
  const [selectedRegion, setSelectedRegion] = useState(null);
  const [regionCities, setRegionCities] = useState([]);
  const [dishes, setDishes] = useState([]);
  const [editingKey, setEditingKey] = useState(null);
  const [editPrice, setEditPrice] = useState('');
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    getRegions().then(res => {
      setRegions(res.data);
      if (res.data.length > 0) {
        setSelectedRegion(res.data[0].key);
        setRegionCities(res.data[0].cities);
      }
    });
  }, []);

  useEffect(() => {
    if (selectedRegion) {
      getDishPrices(selectedRegion).then(res => setDishes(res.data));
      const r = regions.find(r => r.key === selectedRegion);
      if (r) setRegionCities(r.cities);
    }
  }, [selectedRegion]);

  const findCityKey = (displayName) => {
    // dishes her şehirden 4 tane geliyor, sırasıyla regionCities'e map edebiliriz
    // Ama daha güvenli: displayName'i regionCities'teki key ile eşleştir
    for (const cityKey of regionCities) {
      // Basit eşleştirme: displayName'in küçük harfini key ile karşılaştır
      const normalized = displayName.toLowerCase()
        .replace('ı', 'i').replace('ö', 'o').replace('ü', 'u')
        .replace('ş', 's').replace('ç', 'c').replace('ğ', 'g')
        .replace('â', 'a').replace('î', 'i').replace('İ', 'i').replace(' ', '');
      if (normalized === cityKey || normalized.includes(cityKey) || cityKey.includes(normalized)) {
        return cityKey;
      }
    }
    // Fallback: sıra ile eşleştir
    const idx = dishes.findIndex(d => d.city === displayName);
    const cityIdx = Math.floor(idx / 4);
    return regionCities[cityIdx] || displayName.toLowerCase();
  };

  const handleSave = async (dish) => {
    const newPrice = parseFloat(editPrice);
    if (isNaN(newPrice) || newPrice <= 0) return;

    setSaving(true);
    const cityKey = findCityKey(dish.city);
    await updatePrice(selectedRegion, cityKey, dish.category, newPrice);

    const res = await getDishPrices(selectedRegion);
    setDishes(res.data);
    setEditingKey(null);
    setEditPrice('');
    setSaving(false);
  };

  const getDishKey = (dish) => `${dish.city}-${dish.category}`;

  return (
    <div className="price-overlay" onClick={onClose}>
      <div className="price-modal" onClick={e => e.stopPropagation()}>
        <div className="price-modal-header">
          <h3>💰 Fiyat Yönetimi</h3>
          <button className="price-close" onClick={onClose}>✕</button>
        </div>

        <div className="price-region-tabs">
          {regions.map(r => (
            <button
              key={r.key}
              className={`price-tab ${selectedRegion === r.key ? 'active' : ''}`}
              onClick={() => setSelectedRegion(r.key)}
            >
              {r.name}
            </button>
          ))}
        </div>

        <div className="price-list">
          <div className="price-list-header">
            <span>Yemek</span>
            <span>Kategori</span>
            <span>Şehir</span>
            <span>Fiyat</span>
            <span></span>
          </div>
          {dishes.map(dish => {
            const key = getDishKey(dish);
            const isEditing = editingKey === key;

            return (
              <div key={key} className="price-row">
                <span className="price-dish-name">{dish.name}</span>
                <span className="price-category">{categoryIcons[dish.category]} {dish.category}</span>
                <span className="price-city">{dish.city}</span>
                {isEditing ? (
                  <div className="price-edit-group">
                    <input
                      type="number"
                      value={editPrice}
                      onChange={e => setEditPrice(e.target.value)}
                      className="price-edit-input"
                      autoFocus
                    />
                    <button className="price-save-btn" onClick={() => handleSave(dish)} disabled={saving}>✓</button>
                    <button className="price-cancel-btn" onClick={() => setEditingKey(null)}>✕</button>
                  </div>
                ) : (
                  <>
                    <span className="price-value">₺{dish.price}</span>
                    <button
                      className="price-edit-btn"
                      onClick={() => { setEditingKey(key); setEditPrice(dish.price.toString()); }}
                    >
                      ✏️
                    </button>
                  </>
                )}
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
}

export default PriceManager;
