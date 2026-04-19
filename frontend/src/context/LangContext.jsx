import { createContext, useContext, useEffect, useState } from 'react';
import { translations } from '../data/translations';

const LangContext = createContext();
const STORAGE_KEY = 'turkishmenu.lang';

export function LangProvider({ children }) {
  const [lang, setLang] = useState(() => {
    try {
      return localStorage.getItem(STORAGE_KEY) || 'tr';
    } catch {
      return 'tr';
    }
  });

  useEffect(() => {
    document.documentElement.setAttribute('lang', lang);
    try { localStorage.setItem(STORAGE_KEY, lang); } catch {}
  }, [lang]);

  const t = (key) => translations[lang]?.[key] ?? translations.tr[key] ?? key;
  const toggleLang = () => setLang(l => l === 'tr' ? 'en' : 'tr');

  return (
    <LangContext.Provider value={{ lang, t, toggleLang }}>
      {children}
    </LangContext.Provider>
  );
}

export const useLang = () => useContext(LangContext);
