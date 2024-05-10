<!------------------------- TEMPLATE -------------------------------->
<template>
  <div id="capstone-app">

    <router-view id="views" />

  </div>
</template>

<!------------------------- SCRIPT ---------------------------------->
<script>

export default {
  name: 'App',
}

</script>


<!------------------------- STYLE ---------------------------------->
<style scoped>



#capstone-app {
  width: 100vw;
  height: 100vh;
  background-image: linear-gradient(to bottom right, #0bffd6, #c1ddd8); /* Example gradient */

}
</style>