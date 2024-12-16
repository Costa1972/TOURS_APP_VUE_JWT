import { createApp } from 'vue'
import App from './App.vue'
import { createRouter, createWebHistory} from "vue-router";
import SignInComponent from "@/components/SignInComponent.vue";
import SignUpComponent from "@/components/SignUpComponent.vue";
import 'bootstrap/dist/css/bootstrap.min.css';

const routes = [
    {path: '/', component: SignUpComponent},
    {path: "/api/auth/signUp", component: SignUpComponent},
    {path: "/api/auth/signIn", component: SignInComponent},
]

const router = createRouter({
    history: createWebHistory(),
    routes
});

const app = createApp(App)
app.use(router);
app.mount("#app");

