import { useEffect, useState } from 'react';
import { getRegions, getDishes, createDish, updateDish, deleteDish } from './services/api';
import { useLang } from './context/LangContext';
import { translateDish } from './utils/dishTranslator';

const categoryIcons = {
  'Ana Yemek': '🍖',
  'Başlangıç': '🥣',
  'Tatlı': '🍰',
  'İçecek': '🥤'
};

const CATEGORIES = ['Ana Yemek', 'Başlangıç', 'Tatlı', 'İçecek'];

function PriceManager({ onClose }) {
  const { t, lang } = useLang();
  const [regions, setRegions] = useState([]);
  const [selectedRegion, setSelectedRegion] = useState(null);
  const [regionCities, setRegionCities] = useState([]);
  const [dishes, setDishes] = useState([]);
  const [editingId, setEditingId] = useState(null);
  const [editForm, setEditForm] = useState({});
  const [showAddForm, setShowAddForm] = useState(false);
  const [addForm, setAddForm] = useState({
    name: '', description: '', price: '', prepTime: '20',
    city: '', category: 'Ana Yemek'
  });
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

  const refresh = (region) => {
    getDishes(region).then(res => setDishes(res.data));
  };

  useEffect(() => {
    if (selectedRegion) {
      refresh(selectedRegion);
      const r = regions.find(r => r.key === selectedRegion);
      if (r) {
        setRegionCities(r.cities);
        setAddForm(f => ({ ...f, city: r.cities[0] || '' }));
      }
    }
  }, [selectedRegion, regions]);

  const startEdit = (dish) => {
    setEditingId(dish.id);
    setEditForm({
      name: dish.name,
      description: dish.description || '',
      price: dish.price,
      prepTime: dish.prepTime,
      city: dish.city,
      category: dish.category
    });
  };

  const cancelEdit = () => {
    setEditingId(null);
    setEditForm({});
  };

  const saveEdit = async () => {
    if (!editForm.name?.trim() || !editForm.price) {
      alert(t('price.required'));
      return;
    }
    setSaving(true);
    try {
      await updateDish(editingId, {
        name: editForm.name.trim(),
        description: editForm.description,
        price: parseFloat(editForm.price),
        prepTime: parseInt(editForm.prepTime) || 20,
        city: editForm.city,
        category: editForm.category
      });
      refresh(selectedRegion);
      cancelEdit();
    } catch {
      alert('Güncelleme hatası');
    }
    setSaving(false);
  };

  const handleDelete = async (id) => {
    if (!confirm(t('price.deleteConfirm'))) return;
    await deleteDish(id);
    refresh(selectedRegion);
  };

  const handleAdd = async () => {
    if (!addForm.name.trim() || !addForm.price || !addForm.city) {
      alert(t('price.required'));
      return;
    }
    setSaving(true);
    try {
      await createDish(selectedRegion, {
        name: addForm.name.trim(),
        description: addForm.description,
        price: parseFloat(addForm.price),
        prepTime: parseInt(addForm.prepTime) || 20,
        city: addForm.city,
        category: addForm.category
      });
      setAddForm({
        name: '', description: '', price: '', prepTime: '20',
        city: regionCities[0] || '', category: 'Ana Yemek'
      });
      setShowAddForm(false);
      refresh(selectedRegion);
    } catch {
      alert('Ekleme hatası');
    }
    setSaving(false);
  };

  return (
    <div className="price-overlay" onClick={onClose}>
      <div className="price-modal" onClick={e => e.stopPropagation()}>
        <div className="price-modal-header">
          <h3>{t('price.title')}</h3>
          <div className="price-modal-header-actions">
            <button
              className="price-add-btn"
              onClick={() => setShowAddForm(v => !v)}
              style={{ background: showAddForm ? '#27ae60' : '#8e44ad' }}
            >
              {t('price.add')}
            </button>
            <button className="price-close" onClick={onClose}>✕</button>
          </div>
        </div>

        <div className="price-region-tabs">
          {regions.map(r => (
            <button
              key={r.key}
              className={`price-tab ${selectedRegion === r.key ? 'active' : ''}`}
              onClick={() => { setSelectedRegion(r.key); cancelEdit(); setShowAddForm(false); }}
            >
              {r.name}
            </button>
          ))}
        </div>

        {showAddForm && (
          <div className="dish-form">
            <h4>{t('price.addTitle')}</h4>
            <div className="dish-form-row">
              <input
                type="text"
                placeholder={t('price.name')}
                value={addForm.name}
                onChange={e => setAddForm({...addForm, name: e.target.value})}
              />
              <input
                type="number"
                placeholder={t('price.price')}
                value={addForm.price}
                onChange={e => setAddForm({...addForm, price: e.target.value})}
                style={{ width: '110px' }}
              />
              <input
                type="number"
                placeholder={t('price.prep')}
                value={addForm.prepTime}
                onChange={e => setAddForm({...addForm, prepTime: e.target.value})}
                style={{ width: '110px' }}
              />
            </div>
            <div className="dish-form-row">
              <select
                value={addForm.city}
                onChange={e => setAddForm({...addForm, city: e.target.value})}
              >
                {regionCities.map(c => <option key={c} value={c}>{c}</option>)}
              </select>
              <select
                value={addForm.category}
                onChange={e => setAddForm({...addForm, category: e.target.value})}
              >
                {CATEGORIES.map(c => <option key={c} value={c}>{categoryIcons[c]} {c}</option>)}
              </select>
            </div>
            <textarea
              placeholder={t('price.description')}
              value={addForm.description}
              onChange={e => setAddForm({...addForm, description: e.target.value})}
              rows="2"
            />
            <div className="dish-form-actions">
              <button className="price-save-btn-lg" onClick={handleAdd} disabled={saving}>
                ✓ {t('price.save')}
              </button>
              <button className="price-cancel-btn-lg" onClick={() => setShowAddForm(false)}>
                ✕ {t('price.cancel')}
              </button>
            </div>
          </div>
        )}

        <div className="price-list">
          <div className="price-list-header">
            <span>{t('price.dish')}</span>
            <span>{t('price.category')}</span>
            <span>{t('price.city')}</span>
            <span>{t('price.price')}</span>
            <span></span>
          </div>
          {dishes.map((dish, idx) => {
            const rowKey = dish.id ?? `${dish.regionKey}-${dish.city}-${dish.category}-${idx}`;
            const isEditing = editingId != null && dish.id != null && editingId === dish.id;

            if (isEditing) {
              return (
                <div key={rowKey} className="price-row price-row-editing">
                  <input
                    type="text"
                    value={editForm.name}
                    onChange={e => setEditForm({...editForm, name: e.target.value})}
                    className="price-edit-input"
                    style={{ width: '100%' }}
                  />
                  <select
                    value={editForm.category}
                    onChange={e => setEditForm({...editForm, category: e.target.value})}
                    className="price-edit-input"
                  >
                    {CATEGORIES.map(c => <option key={c} value={c}>{c}</option>)}
                  </select>
                  <select
                    value={editForm.city}
                    onChange={e => setEditForm({...editForm, city: e.target.value})}
                    className="price-edit-input"
                  >
                    {regionCities.map(c => <option key={c} value={c}>{c}</option>)}
                  </select>
                  <input
                    type="number"
                    value={editForm.price}
                    onChange={e => setEditForm({...editForm, price: e.target.value})}
                    className="price-edit-input"
                  />
                  <div className="price-edit-group" style={{ justifyContent: 'flex-end' }}>
                    <button className="price-save-btn" onClick={saveEdit} disabled={saving} title={t('price.save')}>✓</button>
                    <button className="price-cancel-btn" onClick={cancelEdit} title={t('price.cancel')}>✕</button>
                  </div>
                </div>
              );
            }

            return (
              <div key={rowKey} className="price-row">
                <span className="price-dish-name">{translateDish(dish.name, lang)}</span>
                <span className="price-category">{categoryIcons[dish.category]} {t(`cat.${dish.category}`)}</span>
                <span className="price-city">{dish.city}</span>
                <span className="price-value">₺{dish.price}</span>
                <div className="price-edit-group" style={{ justifyContent: 'flex-end' }}>
                  <button
                    className="price-edit-btn"
                    onClick={() => dish.id != null ? startEdit(dish) : alert('Backend eski kod çalıştırıyor. Lütfen yeniden başlatın.')}
                    title={t('price.edit')}
                  >✏️</button>
                  <button
                    className="price-edit-btn price-delete-btn"
                    onClick={() => dish.id != null ? handleDelete(dish.id) : alert('Backend eski kod çalıştırıyor.')}
                    title={t('price.delete')}
                  >🗑️</button>
                </div>
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
}

export default PriceManager;
