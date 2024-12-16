<template>
  <div class="container mt-5">
    <div class="row-justify-content-center">
      <div class="col-md-6">
        <div class="card">
          <div class="card-header">Новый участник</div>
          <div class="card-body">
            <form @submit.prevent="signUpUser">
              <div class="form-group">
                <input v-model="user.lastName" class="form-control" type="text" placeholder="Фамилия"/>
              </div>
              <div class="form-group">
                <input v-model="user.firstName" class="form-control" type="text" placeholder="Имя"/>
              </div>
              <div class="form-group">
                <input v-model="user.patronymic" class="form-control" type="text" placeholder="Отчество"/>
              </div>
              <div class="form-group">
                <input v-model="user.username" class="form-control" type="text" placeholder="email"/>
              </div>
              <div class="form-group">
                <input v-model="user.password" class="form-control" type="password" placeholder="пароль"/>
              </div>
              <div class="form-group">
                <input v-model="user.confirmPassword" class="form-control" type="password" placeholder="пароль еще раз"/>
              </div>
              <div class="form-group">
                <input v-model="user.phone" class="form-control" type="text" placeholder="телефон"/>
              </div>
              <button class="btn btn-primary" type="submit">Отправить</button>
            </form>
            <div class="mt-3">
              <span>Уже зарегистрированы? <router-link to="/api/auth/signIn">Войти</router-link></span>
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
        lastName: '',
        firstName: '',
        patronymic: '',
        username: '',
        password: '',
        confirmPassword: '',
        phone: '',
      }
    };
  },
  methods: {
    signUpUser() {
      AuthService.signUp(this.user).then(response => {
        console.log(response);
      });
    }
  }
}
</script>

<style scoped>

</style>