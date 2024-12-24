<template>
    <h1>Список участников</h1>
  <br/>
    <div class="content">
      <div v-for="user in users" :key="user.id">
        {{
          `${user.id + '.'} ${user.lastName} ${user.firstName} ${user.patronymic} ${user.username}
          -${user.phones.map(phone => `${phone.phoneNumber} (${phone.phoneType})
          -${user.payments.map(payment => `${payment.amount} (${payment.basisOfPayment})`)}`).join(', ')}`
        }}
      </div>
    </div>

<!--    <li v-for="user in users" :key="user.id">-->
<!--      {{ user.id }}-->
<!--      {{ user.lastName }}-->
<!--      {{ user.firstName }}-->
<!--      {{ user.patronymic }}-->
<!--      {{ user.username }},-->
<!--      <li v-for="phone in user.phones" :key="phone.id">-->
<!--        {{ phone.phoneNumber }}-->
<!--        {{ phone.phoneType }}-->
<!--      </li>-->

<!--    </li>-->
</template>

<script>
import Cookies from "js-cookie";
import axios from "axios";

export default {
  name: "GetAllUsersComponent",
  data() {
    return {
      page: 0,
      numberOfPages: 0,
      users: [],
      phones: [],
      payments: [],
      loading: true,
      options: {},
      headers: [
        {text: "Фамилия", value: "lastName"},
        {text: "Имя", value: "firstName"},
        {text: "Отчество", value: "patronymic"},
        {text: "email", value: "username"},
        {text: "Телефон", value: "phone"},
      ],
    };
  },
  watch: {
    options: {
      handler() {
        this.getAllUsers()
      },
    },
  },
  methods: {
    getAllUsers() {
      const authToken = Cookies.get('token');
      if (authToken) {
        axios.defaults.headers.common.Authorization = 'Bearer ' + authToken;
      }
      axios.get('http://localhost:8080/api/users').then(response => {
        this.users = response.data;
      });
    },
  },
  mounted() {
    this.getAllUsers();
  }
};
</script>

<style scoped>

</style>