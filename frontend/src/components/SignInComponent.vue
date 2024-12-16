<template>
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card">
          <div class="card-header">Войти</div>
          <div class="card-body">
            <form @submit.prevent="signInUser">
              <div class="form-group">
                <input v-model="user.username" type="text" placeholder="email"/>
              </div>
              <div class="form-group">
                <input v-model="user.password" type="password" placeholder="пароль"/>
              </div>
              <button class="btn btn-primary" type="submit">Отправить</button>
            </form>
            <div class="mt-3">
              <span>Не зарегистрированы? <router-link to="/api/auth/signUp">Регистрация</router-link></span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AuthService from "@/services/AuthService";

export default {
  data() {
    return {
      user: {
        username: '',
        password: '',
      }
    };
  },
  methods: {
    signInUser() {
      AuthService.signIn(this.user).then(response => {
        console.log(response);
      });
    }
  }
};
</script>

<style scoped>

</style>