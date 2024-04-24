<template>
    <div class="spacer"></div>
    <div class="intro">
        <h1>Please log session below</h1>
    </div>
    <LogSession/>
</template>

<script>
import LogSession from '../components/LogSession.vue'
export default {
   components: {
        LogSession
   }

}
</script>

<style>
.spacer {
  background-color: rgb(255, 255, 255);
  opacity: .25;
  height: 4rem;
}
div.intro {
    text-align: center;
}

</style>