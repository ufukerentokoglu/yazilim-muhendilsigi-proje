// Türkçe -> İngilizce yemek adı ve açıklama çevirisi (kelime/ifade tabanlı).
// Proper noun'lar (İstanbul, Bursa, Tekirdağ vb.) olduğu gibi korunur.

// Önce uzun ifadeler, sonra kelimeler. Uzunluk önce çünkü "İskender Kebabı" > "Kebap".
const PHRASES = [
  // Kombinasyonlar — sıra önemli (uzundan kısaya)
  [/\bAda Çayı\b/gi, 'Sage Tea'],
  [/\bİrmik Helvası\b/gi, 'Semolina Halva'],
  [/\bPeynir Helvası\b/gi, 'Cheese Halva'],
  [/\bBadem Ezmesi\b/gi, 'Almond Paste'],
  [/\bKestane Şekeri\b/gi, 'Candied Chestnut'],
  [/\bKestane Şerbeti\b/gi, 'Chestnut Sherbet'],
  [/\bDöner Kebap\b/gi, 'Döner Kebab'],
  [/\bİskender Kebap\b/gi, 'İskender Kebab'],
  [/\bŞalgam Suyu\b/gi, 'Turnip Juice'],
  [/\bTürk Kahvesi\b/gi, 'Turkish Coffee'],
  [/\bTürk Kahve\b/gi, 'Turkish Coffee'],
  [/\bPeynir Tabağı\b/gi, 'Cheese Platter'],
  [/\bTava Ciğeri\b/gi, 'Pan-fried Liver'],
  [/\bCağ Kebabı\b/gi, 'Cağ Kebab'],
  [/\bUrfa Kebabı\b/gi, 'Urfa Kebab'],
  [/\bAdana Kebabı\b/gi, 'Adana Kebab'],
  [/\bAntep Kebabı\b/gi, 'Antep Kebab'],
  [/\bMercimek Çorbası\b/gi, 'Lentil Soup'],
  [/\bYayla Çorbası\b/gi, 'Yogurt Soup'],
  [/\bTarhana Çorbası\b/gi, 'Tarhana Soup'],
  [/\bEzogelin Çorbası\b/gi, 'Ezogelin Soup'],
  [/\bSarımsak Çorbası\b/gi, 'Garlic Soup'],
  [/\bEtli Ekmek\b/gi, 'Meat Flatbread'],
  [/\bHamsi Tava\b/gi, 'Pan-fried Anchovy'],
  [/\bHamsili Pilav\b/gi, 'Anchovy Rice'],
  [/\bMuhammara\b/gi, 'Muhammara'],
  [/\bMırra\b/gi, 'Mırra Coffee'],
  [/\bZeytinyağlı\b/gi, 'Olive Oil'],
  [/\bSütlü Tatlı\b/gi, 'Milky Dessert'],
  [/\bPeynirli\b/gi, 'With Cheese'],
  [/\bYoğurtlu\b/gi, 'With Yogurt'],
  [/\bFermente İçecek\b/gi, 'Fermented Beverage'],
  [/\bİncir Reçeli\b/gi, 'Fig Jam'],
];

const WORDS = {
  // İsim çekimleri (iyelik ekleri)
  'Köftesi': 'Meatballs', 'Köfte': 'Meatballs',
  'Kebabı': 'Kebab', 'Kebap': 'Kebab',
  'Çorbası': 'Soup', 'Çorba': 'Soup',
  'Pilavı': 'Rice', 'Pilav': 'Rice',
  'Pidesi': 'Pide', 'Pide': 'Pide',
  'Dolması': 'Stuffed', 'Dolma': 'Stuffed',
  'Sarması': 'Roll', 'Sarma': 'Roll',
  'Ciğeri': 'Liver', 'Ciğer': 'Liver',
  'Balığı': 'Fish', 'Balık': 'Fish',
  'Peyniri': 'Cheese', 'Peynir': 'Cheese',
  'Ekmeği': 'Bread', 'Ekmek': 'Bread',
  'Helvası': 'Halva', 'Helva': 'Halva',
  'Tatlısı': 'Dessert', 'Tatlı': 'Dessert',
  'Baklavası': 'Baklava', 'Baklava': 'Baklava',
  'Künefesi': 'Künefe', 'Künefe': 'Künefe',
  'Mantısı': 'Dumplings', 'Mantı': 'Dumplings',
  'Lahmacunu': 'Lahmacun', 'Lahmacun': 'Lahmacun',
  'Kahvesi': 'Coffee', 'Kahve': 'Coffee',
  'Çayı': 'Tea', 'Çay': 'Tea',
  'Ayran': 'Ayran',
  'Boza': 'Boza',
  'Şerbeti': 'Sherbet', 'Şerbet': 'Sherbet',
  'Salatası': 'Salad', 'Salata': 'Salad',
  'Kavurması': 'Roast', 'Kavurma': 'Roast',
  'Şekeri': 'Candy', 'Şeker': 'Sugar',
  'Sütlacı': 'Rice Pudding', 'Sütlaç': 'Rice Pudding',
  'Börek': 'Pastry', 'Böreği': 'Pastry',
  'Kuymak': 'Kuymak',
  'Pişmaniye': 'Cotton Halva',
  'Hamsi': 'Anchovy',
  'Hamsisi': 'Anchovy',
  'Sardalyası': 'Sardine', 'Sardalya': 'Sardine',
  'Hardaliyesi': 'Hardaliye', 'Hardaliye': 'Hardaliye',
  'Hardalı': 'Mustard Drink',
  'Ihlamur': 'Linden Tea',
  'Bazlama': 'Bazlama Flatbread',
  'Kete': 'Kete Pastry',
  'Kısır': 'Bulgur Salad',
  'Gözleme': 'Gözleme',
  'Kadayıfı': 'Kadayıf', 'Kadayıf': 'Kadayıf',
  'Gullaç': 'Güllaç',
  'Kaygana': 'Kaygana',
  'Turşu': 'Pickle',
  'Turşusu': 'Pickle',
  'Şıra': 'Grape Must',
  'Şirası': 'Grape Must',
  'Limonata': 'Lemonade',
  'Limonatası': 'Lemonade',
  'Karpuz': 'Watermelon',
  'Karpuzu': 'Watermelon',
  'Erik': 'Plum', 'Eriği': 'Plum',
  'Kaymaklı': 'With Cream',
  'Kaymağı': 'Cream',
  'Islama': 'Soaked',
  'Yalancı': 'Vegetarian',

  // Sıfatlar / yardımcı kelimeler
  'Usulü': 'Style', 'usulü': 'style',
  'özel': 'special', 'Özel': 'Special',
  'özgü': 'unique to',
  'geleneksel': 'traditional', 'Geleneksel': 'Traditional',
  'meşhur': 'famous', 'Meşhur': 'Famous',
  'yöresel': 'regional', 'Yöresel': 'Regional',
  'sıcak': 'hot', 'Sıcak': 'Hot',
  'soğuk': 'cold', 'Soğuk': 'Cold',
  'acılı': 'spicy', 'Acılı': 'Spicy',
  'tatlı': 'sweet', 'Tatlı': 'Sweet',
  'ekşi': 'sour', 'Ekşi': 'Sour',
  'köpüklü': 'frothy',
  'çıtır': 'crispy',
  'yapılır': 'made', 'yapılan': 'made',
  'hazırlanan': 'prepared',
  'ile': 'with', 'İle': 'With',
  'klasik': 'classic', 'Klasik': 'Classic',
  'kızartılmış': 'fried',
  'ızgara': 'grilled', 'Izgara': 'Grilled', 'İzgara': 'Grilled',
  'dana': 'beef',
  'kuzu': 'lamb',
  'tavuk': 'chicken',
  'etli': 'with meat',
  'sebzeli': 'with vegetables',
  'yoğurt': 'yogurt', 'Yoğurt': 'Yogurt',
  'yoğurtlu': 'with yogurt',
  'tereyağı': 'butter', 'tereyağ': 'butter',
  'zeytin': 'olive', 'Zeytin': 'Olive',
  'nar': 'pomegranate',
  'badem': 'almond', 'Badem': 'Almond',
  'fıstık': 'pistachio', 'Fıstık': 'Pistachio',
  'ceviz': 'walnut', 'Ceviz': 'Walnut',
  'kestane': 'chestnut', 'Kestane': 'Chestnut',
  'kaymaklı': 'with cream',
  'sütlü': 'milky',
  'süt': 'milk',
  'un': 'flour', 'Un': 'Flour',
  'irmik': 'semolina', 'İrmik': 'Semolina',
  'hamur': 'dough', 'Hamur': 'Dough',
  'beyaz': 'white', 'Beyaz': 'White',
  'kara': 'black',
  'taze': 'fresh', 'Taze': 'Fresh',
  'köy': 'village',
  'demleme': 'brewed',
  'kahvaltısı': 'breakfast', 'kahvaltı': 'breakfast',
  'bölgesi': 'region', 'Bölgesi': 'Region',
  'bölge': 'region',

  // Bağlaçlar
  've': 'and', 'Ve': 'And',
  'ya': 'or',
  'da': '', // kaldır
  'de': '',
  'için': 'for',
  'bir': 'a', 'Bir': 'A',
};

// Kelimeleri diacritic'siz karşılaştırma için
function stripDiacritics(s) {
  return s.replace(/İ/g, 'I').replace(/ı/g, 'i')
          .replace(/Ğ/g, 'G').replace(/ğ/g, 'g')
          .replace(/Ü/g, 'U').replace(/ü/g, 'u')
          .replace(/Ö/g, 'O').replace(/ö/g, 'o')
          .replace(/Ş/g, 'S').replace(/ş/g, 's')
          .replace(/Ç/g, 'C').replace(/ç/g, 'c')
          .replace(/Â/g, 'A').replace(/â/g, 'a')
          .replace(/Î/g, 'I').replace(/î/g, 'i');
}

const WORD_MAP_CI = Object.fromEntries(
  Object.entries(WORDS).map(([k, v]) => [stripDiacritics(k).toLowerCase(), v])
);

function isProperNoun(token) {
  // Büyük harfle başlayan ve sözlükte olmayan kelimeler — muhtemelen şehir/özel isim
  return /^[A-ZÇĞİÖŞÜ]/.test(token);
}

function translateToken(token) {
  const key = stripDiacritics(token).toLowerCase();
  const mapped = WORD_MAP_CI[key];
  if (mapped !== undefined) return mapped;
  // Sözlükte yok, olduğu gibi bırak
  return token;
}

export function translateDish(text, lang) {
  if (!text || lang !== 'en') return text;

  let out = text;
  for (const [re, replacement] of PHRASES) {
    out = out.replace(re, replacement);
  }

  // Kelime kelime dönüştür — noktalama ve apostrof koruması
  out = out.split(/(\s+|,|\.|'|'|!|\?|-|\(|\))/).map(part => {
    if (!part || /^\s+$/.test(part) || /^[,.'"!?\-()]$/.test(part)) return part;
    // "Tekirdağ'ın" gibi iyelik eklerini temizle
    const stripped = part.replace(/['']\w+$/, '');
    if (stripped && stripped !== part) {
      const translated = translateToken(stripped);
      if (translated !== stripped) return translated;
    }
    return translateToken(part);
  }).join('');

  // Gereksiz boşlukları temizle
  out = out.replace(/\s+/g, ' ').replace(/\s([,.!?])/g, '$1').trim();
  return out;
}
