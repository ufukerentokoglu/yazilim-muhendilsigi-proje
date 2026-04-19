export const translations = {
  tr: {
    // Login
    'login.title': '🍽️ Admin Paneli',
    'login.subtitle': 'Yönetim paneline erişmek için giriş yapın',
    'login.username': 'Kullanıcı adı',
    'login.password': 'Şifre',
    'login.submit': 'Giriş Yap',
    'login.error': 'Kullanıcı adı veya şifre hatalı',

    // Header
    'header.title': '🍽️ Admin Paneli',
    'header.badge': 'Sipariş Yönetimi',
    'header.total': 'Toplam Sipariş',
    'header.active': 'Aktif',
    'header.cancelled': 'İptal',
    'header.revenue': 'Ciro',
    'header.popular': '🏆 Popüler',
    'header.prices': '💰 Fiyatlar',
    'header.dayend': '📊 Gün Sonu',

    // Popular
    'popular.title': '🏆 En Çok Sipariş Edilen Yemekler',
    'popular.orders': 'sipariş',

    // Empty / Loading
    'loading': 'Yükleniyor...',
    'empty.title': 'Henüz sipariş yok',
    'empty.desc': 'Müşteri arayüzünden (localhost:5173) sipariş geldiğinde burada görünecek',

    // Order card
    'card.table': 'Masa',
    'card.total': 'Toplam',
    'card.prep': 'Süre',
    'card.ready': 'Hazır',
    'card.cancel': '✕ İptal',
    'card.remove': '🗑️ Kaldır',
    'card.print': '🖨️ Yazdır',
    'card.cancelConfirm': 'Bu siparişi iptal etmek istediğine emin misin?',
    'card.takeawayNote': 'Müşteri gelip alacak',
    'dayend.confirm': 'Gün sonu raporu oluşturulacak ve tüm siparişler sıfırlanacak. Emin misin?',
    'dayend.error': 'Gün sonu raporu oluşturulurken hata oluştu',

    // Status
    'status.Bekliyor': 'Bekliyor',
    'status.Hazırlanıyor': 'Hazırlanıyor',
    'status.Hazır': 'Hazır',
    'status.Teslim Edildi': 'Teslim Edildi',
    'status.İptal Edildi': 'İptal Edildi',

    // Order type
    'ordertype.TAKEAWAY': 'Paket',
    'ordertype.DINE_IN': 'Servis',

    // Prices modal / Product manager
    'price.title': '🍽️ Ürün Yönetimi',
    'price.all': 'Tümü',
    'price.dish': 'Yemek',
    'price.category': 'Kategori',
    'price.city': 'Şehir',
    'price.price': 'Fiyat',
    'price.prep': 'Süre (dk)',
    'price.description': 'Açıklama',
    'price.add': '+ Yeni Yemek',
    'price.addTitle': 'Yeni Yemek Ekle',
    'price.edit': 'Düzenle',
    'price.save': 'Kaydet',
    'price.cancel': 'İptal',
    'price.delete': 'Sil',
    'price.deleteConfirm': 'Bu yemeği silmek istediğinizden emin misiniz?',
    'price.name': 'Yemek adı',
    'price.required': 'Lütfen tüm alanları doldurun',

    // Categories
    'cat.Ana Yemek': 'Ana Yemek',
    'cat.Başlangıç': 'Başlangıç',
    'cat.Tatlı': 'Tatlı',
    'cat.İçecek': 'İçecek',

    'unit.min': 'dk'
  },

  en: {
    'login.title': '🍽️ Admin Panel',
    'login.subtitle': 'Sign in to access the management panel',
    'login.username': 'Username',
    'login.password': 'Password',
    'login.submit': 'Sign In',
    'login.error': 'Invalid username or password',

    'header.title': '🍽️ Admin Panel',
    'header.badge': 'Order Management',
    'header.total': 'Total Orders',
    'header.active': 'Active',
    'header.cancelled': 'Cancelled',
    'header.revenue': 'Revenue',
    'header.popular': '🏆 Popular',
    'header.prices': '💰 Prices',
    'header.dayend': '📊 Day End',

    'popular.title': '🏆 Most Ordered Dishes',
    'popular.orders': 'orders',

    'loading': 'Loading...',
    'empty.title': 'No orders yet',
    'empty.desc': 'New orders from the customer app (localhost:5173) will appear here',

    'card.table': 'Table',
    'card.total': 'Total',
    'card.prep': 'Time',
    'card.ready': 'Ready',
    'card.cancel': '✕ Cancel',
    'card.remove': '🗑️ Remove',
    'card.print': '🖨️ Print',
    'card.cancelConfirm': 'Are you sure you want to cancel this order?',
    'card.takeawayNote': 'Customer will pick up',
    'dayend.confirm': 'The day-end report will be generated and all orders reset. Are you sure?',
    'dayend.error': 'Error while generating day-end report',

    'status.Bekliyor': 'Pending',
    'status.Hazırlanıyor': 'Preparing',
    'status.Hazır': 'Ready',
    'status.Teslim Edildi': 'Delivered',
    'status.İptal Edildi': 'Cancelled',

    'ordertype.TAKEAWAY': 'Takeaway',
    'ordertype.DINE_IN': 'Dine-in',

    'price.title': '🍽️ Product Management',
    'price.all': 'All',
    'price.dish': 'Dish',
    'price.category': 'Category',
    'price.city': 'City',
    'price.price': 'Price',
    'price.prep': 'Time (min)',
    'price.description': 'Description',
    'price.add': '+ New Dish',
    'price.addTitle': 'Add New Dish',
    'price.edit': 'Edit',
    'price.save': 'Save',
    'price.cancel': 'Cancel',
    'price.delete': 'Delete',
    'price.deleteConfirm': 'Are you sure you want to delete this dish?',
    'price.name': 'Dish name',
    'price.required': 'Please fill all fields',

    'cat.Ana Yemek': 'Main Dish',
    'cat.Başlangıç': 'Appetizer',
    'cat.Tatlı': 'Dessert',
    'cat.İçecek': 'Beverage',

    'unit.min': 'min'
  }
};
