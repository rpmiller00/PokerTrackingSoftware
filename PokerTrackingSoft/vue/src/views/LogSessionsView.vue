<template>
    <div class="spacer"></div>
    <div class="content">
        <div class="intro">
            <h1>Please log session below</h1>
        </div>
        <LogSession />
    </div>
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
    height: 3rem;
}

.intro {
    text-align: center;
}
.content {
    background-color: red;
    height: 100vh;
}
</style>