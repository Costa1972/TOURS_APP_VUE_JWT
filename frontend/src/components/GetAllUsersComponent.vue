<template>
  <div class="">
<h1>Hello</h1>
  </div>
</template>

<script>
import UserService from "@/services/UserService";
import Cookies from "js-cookie";
import axios from "axios";

export default {
  name: "GetAllUsersComponent",
  data() {
    return {
      page: 0,
      numberOfPages: 0,
      users: [],
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
      axios.interceptors.request.use((config) => {
        config.headers.authorization = `Bearer ${authToken}`;
        return config;
      });
      this.loading = true;
      const { page, usersPerPage } = this.options;
      let pageNumber = page - 1;
      UserService.getAllUsers().then((response) => {
        return response;
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