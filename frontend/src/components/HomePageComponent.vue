<script>

import getAllUsers from "@/components/GetAllUsersComponent.vue";
import {defineComponent} from "vue";

export default defineComponent({
  computed: {
    getAllUsers() {
      return getAllUsers
    }
  }
})

</script>

<template>
  <div class="home-page">
    <h1>List of users</h1>
    <div class="container">
      <form @submit.prevent="getAllUsers"></form>
    </div>
  </div>

</template>

<style scoped>

</style>