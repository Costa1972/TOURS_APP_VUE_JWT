import { createApp } from 'vue'
import App from './App.vue'
import { createRouter, createWebHistory} from "vue-router";
import SignInComponent from "@/components/SignInComponent.vue";
import SignUpComponent from "@/components/SignUpComponent.vue";
import 'bootstrap/dist/css/bootstrap.min.css';
import Cookies from "js-cookie";
import GetAllUsersComponent from "@/components/GetAllUsersComponent.vue";
import HomePageComponent from "@/components/HomePageComponent.vue";

const routes = [
    {path: '/', component: HomePageComponent},
    {path: "/api/auth/signUp", component: SignUpComponent},
    {path: "/api/auth/signIn", component: SignInComponent},
    {path: "/api/users", component: GetAllUsersComponent},
]

const router = createRouter({
    history: createWebHistory(),
    routes
});

const app = createApp(App)
app.use(router);
app.use(Cookies);
app.mount("#app");

