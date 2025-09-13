import './assets/main.css'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'


import { createApp } from 'vue'
import { createPinia } from 'pinia'
// import PhotoAlbum from './views/PhotoAlbum.vue'
import MiniHomeView from './views/MiniHomeView.vue'
// import App from './App.vue'
// import router from './router'

const app = createApp(MiniHomeView)

app.use(createPinia())
// app.use(router)

app.mount('#app')
